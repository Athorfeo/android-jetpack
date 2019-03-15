package com.obcompany.androidjetpack.ui.movie.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.obcompany.androidjetpack.R
import com.obcompany.androidjetpack.utilities.InjectionUtil
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.obcompany.androidjetpack.databinding.FragmentSearchMovieBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.obcompany.androidjetpack.utilities.DialogUtil
import android.view.inputmethod.InputMethodManager
import android.app.Activity

class SearchMovieFragment: Fragment(), View.OnClickListener, View.OnKeyListener {
    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var model: SearchMovieViewModel
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        progressDialog = DialogUtil.progress(activity!!)

        val factory = InjectionUtil.provideSearchMovieViewModelFactory()
        model = ViewModelProviders.of(this, factory).get(SearchMovieViewModel::class.java)
        binding.viewModel = model
        binding.buttonSearch.setOnClickListener(this)
        binding.imageButton.setOnClickListener(this)

        binding.editText.setOnKeyListener(this)

        init(binding)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonSearch -> {
                model.searchMovies(binding.editText.text.toString())
            }
            R.id.imageButton -> {
                val direction = SearchMovieFragmentDirections.toAboutFragment()
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

    private fun init(binding: FragmentSearchMovieBinding){
        model.isSearching().observe(this, Observer {
            if(it){
                progressDialog.show()
            }else{
                progressDialog.hide()
            }
        })

        model.movies.observe(this, Observer { data ->
            binding.hasSearched = true
            binding.textSearch.text = "${data.size} results found"

            if(data.isNotEmpty()){
                binding.recycler.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = SearchMovieAdapter(data, context)
                    isNestedScrollingEnabled = false
                }
            }else{
                binding.recycler.apply {
                    adapter = null
                }
            }
        })
    }
}