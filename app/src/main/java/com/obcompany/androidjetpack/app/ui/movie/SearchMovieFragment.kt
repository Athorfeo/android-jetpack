package com.obcompany.androidjetpack.app.ui.movie

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.obcompany.androidjetpack.R
import androidx.appcompat.app.AlertDialog
import com.obcompany.androidjetpack.databinding.FragmentSearchMovieBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.obcompany.androidjetpack.utility.DialogUtil
import android.view.inputmethod.InputMethodManager
import android.app.Activity
import com.obcompany.androidjetpack.utility.Status
import com.obcompany.androidjetpack.utility.ViewModelFactoryUtil

class SearchMovieFragment: Fragment(), View.OnClickListener, View.OnKeyListener {
    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var model: SearchMovieViewModel
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        progressDialog = DialogUtil.progress(activity!!)

        val adapter = SearchMovieAdapter()

        init(adapter, binding)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonSearch -> {
                model.searchMovies(binding.editText.text.toString())
            }
            R.id.imageButton -> {
                val direction =
                    SearchMovieFragmentDirections.toAboutFragment()
                findNavController().navigate(direction)
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> {
                when (keyCode){
                    KeyEvent.KEYCODE_ENTER -> {
                        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)

                        model.searchMovies(binding.editText.text.toString())
                        true
                    }
                    else -> false
                }
            }
            else -> false
        }
    }

    private fun init(adapter: SearchMovieAdapter,
                     binding: FragmentSearchMovieBinding){
        val factory = ViewModelFactoryUtil.provideSearchMovieFactory()
        model = ViewModelProviders.of(this, factory).get(SearchMovieViewModel::class.java)
        binding.viewModel = model

        binding.buttonSearch.setOnClickListener(this)
        binding.imageButton.setOnClickListener(this)

        binding.editText.setOnKeyListener(this)

        binding.recycler.adapter = adapter

        subscribeUi(adapter, binding)
    }

    private fun subscribeUi(adapter: SearchMovieAdapter, binding: FragmentSearchMovieBinding){
        model.isLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                progressDialog.show()
            }else{
                progressDialog.hide()
            }
        })

        model.movies.observe(viewLifecycleOwner, Observer { response ->
            when (response?.status) {
                Status.LOADING -> {
                    model.setLoading(true)
                }
                Status.SUCCESS -> {
                    model.setLoading(false)
                    if(response.data != null){
                        binding.hasSearched = true
                        val data = response.data.results
                        binding.textSearch.text = getString(R.string.text_results_searched, data.size)
                        if (!data.isNullOrEmpty()) {
                            adapter.submitList(data)
                        }
                    }
                }
                Status.ERROR -> {
                    model.setLoading(false)
                }
            }
        })
    }
}