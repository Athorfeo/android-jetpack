package com.obcompany.androidjetpack.app.ui.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.obcompany.androidjetpack.databinding.FragmentDetailMovieBinding
import com.obcompany.androidjetpack.utilities.DialogUtil
import com.obcompany.androidjetpack.utilities.InjectionUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailMovieFragment: Fragment() {
    private lateinit var model: DetailMovieViewModel
    private var disposable: Disposable? = null
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        progressDialog = DialogUtil.progress(activity!!)

        val movieId = DetailMovieFragmentArgs.fromBundle(arguments!!).movieId

        val factory = InjectionUtil.provideDetailMovieViewModelFactory()
        model = ViewModelProviders.of(this, factory).get(DetailMovieViewModel::class.java)

        init(binding, movieId)

        return binding.root
    }

    fun init(binding: FragmentDetailMovieBinding, movieId: Int){
        disposable = model.searchMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    progressDialog.hide()
                    val imageUrl = "http://image.tmdb.org/t/p/w500" + result.imagePath
                    Toast.makeText(context, imageUrl, Toast.LENGTH_SHORT).show()
                    binding.movie = result
                    Glide.with(this).load(imageUrl).into(binding.imageMovie)
                },
                { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }
            )
    }
}