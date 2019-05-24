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
import com.obcompany.androidjetpack.app.ui.BaseFragment
import com.obcompany.androidjetpack.utility.Status
import com.obcompany.androidjetpack.utility.ViewModelFactoryUtil

class SearchMovieFragment: BaseFragment(), View.OnClickListener, View.OnKeyListener {
    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var model: SearchMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model = ViewModelProviders.of(this, ViewModelFactoryUtil.provideSearchMovieFactory()).get(SearchMovieViewModel::class.java)
        loadingDialog = DialogUtil.progress(activity!!)
        binding.recycler.visibility = View.GONE

        val adapter = SearchMovieAdapter()
        init(adapter)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonSearch -> {
                searchMovie()
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

    private fun init(adapter: SearchMovieAdapter){
        val factory = ViewModelFactoryUtil.provideSearchMovieFactory()
        model = ViewModelProviders.of(this, factory).get(SearchMovieViewModel::class.java)
        binding.viewModel = model
        binding.recycler.adapter = adapter
        binding.recycler.isNestedScrollingEnabled = false

        binding.buttonSearch.setOnClickListener(this)
        binding.imageButton.setOnClickListener(this)
        binding.editText.setOnKeyListener(this)

        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: SearchMovieAdapter){
        model.isLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                loadingDialog.show()
            }else{
                loadingDialog.hide()
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
                            binding.recycler.visibility = View.VISIBLE
                            adapter.submitList(data)
                        }else{
                            binding.recycler.visibility = View.GONE
                            adapter.submitList(null)
                        }
                    }else{
                        if(binding.editText.text.isEmpty()){
                            model.searchMovies(binding.editText.text.toString())
                        }
                    }
                }
                Status.ERROR -> {
                    model.setLoading(false)
                }
            }
        })
    }

    private fun searchMovie(){
        if(!binding.editText.text.isEmpty()){
            model.searchMovies(binding.editText.text.toString())
        }
    }
}