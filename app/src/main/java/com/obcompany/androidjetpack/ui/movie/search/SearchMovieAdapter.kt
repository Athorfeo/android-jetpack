package com.obcompany.androidjetpack.ui.movie.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obcompany.androidjetpack.data.api.model.SearchedMovie
import com.obcompany.androidjetpack.databinding.MovieListItemBinding
import androidx.navigation.findNavController


class SearchMovieAdapter(private val items: List<SearchedMovie>, private val context: Context): RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(context), parent, false))
        //return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(createOnClickListener( items[position].id ), items[position])
        }
    }

    private fun createOnClickListener(movieId: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction = SearchMovieFragmentDirections.toDetailMovieFragment(movieId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder (private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, movie: SearchedMovie){
            with(binding) {
                clickListener = listener
                viewModel = SearchMovieAdapterViewModel(movie)
                executePendingBindings()
            }
        }
    }
}