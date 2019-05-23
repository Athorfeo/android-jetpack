package com.obcompany.androidjetpack.app.ui.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.obcompany.androidjetpack.databinding.FragmentDetailMovieBinding
import com.obcompany.androidjetpack.utility.Status
import com.obcompany.androidjetpack.utility.DialogUtil
import com.obcompany.androidjetpack.utility.ViewModelFactoryUtil
import io.reactivex.disposables.Disposable

class DetailMovieFragment: Fragment() {
    private lateinit var model: DetailMovieViewModel
    private var disposable: Disposable? = null
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        progressDialog = DialogUtil.progress(activity!!)

        val movieId = DetailMovieFragmentArgs.fromBundle(arguments!!).movieId

        val factory = ViewModelFactoryUtil.provideDetailMovieFactory()
        model = ViewModelProviders.of(this, factory).get(DetailMovieViewModel::class.java)

        init(binding, movieId)

        return binding.root
    }

    private fun init(binding: FragmentDetailMovieBinding, movieId: Int){
        model.isLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                progressDialog.show()
            }else{
                progressDialog.hide()
            }
        })

        model.searchMovie(movieId).observe(viewLifecycleOwner, Observer { response ->
            when (response?.status) {
                Status.LOADING -> {
                    model.setLoading(true)
                }
                Status.SUCCESS -> {
                    model.setLoading(false)
                    if(response.data != null){
                        val data = response.data
                        val imageUrl = "http://image.tmdb.org/t/p/w500" + data.imagePath
                        binding.movie = data
                        Glide.with(this).load(imageUrl).into(binding.imageMovie)
                    }
                }
                Status.ERROR -> {
                    model.setLoading(false)
                }
            }
        })
    }
}