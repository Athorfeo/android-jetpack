package com.obcompany.androidjetpack.app.ui.movie

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.obcompany.androidjetpack.R
import com.obcompany.androidjetpack.databinding.FragmentSearchMovieBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import android.view.inputmethod.InputMethodManager
import android.app.Activity
import android.util.Log
import android.view.*
import com.obcompany.androidjetpack.app.ui.BaseFragment
import com.obcompany.androidjetpack.utility.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.snackbar.Snackbar



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
        binding.moviesRecycler.visibility = View.GONE

        val adapter = SearchMovieAdapter()
        init(adapter)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.searchButton -> {
                searchMovie()
            }
            R.id.nextButton -> {
                nextPage()
            }
            R.id.backButton -> {
                backPage()
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> {
                when (keyCode){
                    KeyEvent.KEYCODE_ENTER -> {
                        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)

                        model.searchMovies(binding.searchEditText.text.toString())
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
        binding.moviesRecycler.adapter = adapter
        binding.moviesRecycler.isNestedScrollingEnabled = false

        binding.searchButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.searchEditText.setOnKeyListener(this)

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

        model.page.observe(viewLifecycleOwner, Observer {
            val page = it ?: 1
            binding.pageText.text = page.toString()
        })

        model.movies.observe(viewLifecycleOwner, Observer { response ->
            binding.hasSearched = true
            val data = response.results
            binding.searchText.text = getString(R.string.text_results_searched, data.size)

            if (!data.isNullOrEmpty() || data.size > 0) {
                binding.moviesRecycler.visibility = View.VISIBLE
                adapter.submitList(data)
            }else{
                binding.moviesRecycler.visibility = View.GONE
                adapter.submitList(null)
            }
        })
    }

    private fun searchMovie(){
        if(validate()){
            model.searchMovies(binding.searchEditText.text.toString())
        }
    }

    private fun nextPage(){
        model.nextPage()
    }

    private fun backPage(){
        model.backPage()
    }

    private fun validate(): Boolean {
        var isCorrect = true
        when {
            binding.searchEditText.text.isEmpty() -> {
                isCorrect = false
                SnackbarUtil.default(binding.root, "El campo de texto no puede ser vacio")
            }
            context?.let {
                !NetworkUtil.isOnline(it)
            } ?: false -> {
                isCorrect = false
                SnackbarUtil.default(binding.root, "No hay conextión a internet")
            }
        }
        return isCorrect
    }
}