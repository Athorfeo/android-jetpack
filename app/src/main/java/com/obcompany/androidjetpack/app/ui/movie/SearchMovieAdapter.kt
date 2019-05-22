package com.obcompany.androidjetpack.app.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.obcompany.androidjetpack.data.api.model.SearchedMovie
import com.obcompany.androidjetpack.databinding.MovieListItemBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.obcompany.androidjetpack.R


class SearchMovieAdapter: ListAdapter<SearchedMovie, SearchMovieAdapter.ViewHolder>(
    DiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.movie_list_item, parent, false
            )
        )
        //return ViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        //return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { searchedMovie ->
            with(holder) {
                itemView.tag = searchedMovie
                bind(createOnClickListener(searchedMovie.id), searchedMovie)
            }
        }
    }

    private fun createOnClickListener(movieId: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                SearchMovieFragmentDirections.toDetailMovieFragment(movieId)
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

    private class DiffCallback : DiffUtil.ItemCallback<SearchedMovie>() {

        override fun areItemsTheSame(oldItem: SearchedMovie, newItem: SearchedMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchedMovie, newItem: SearchedMovie): Boolean {
            return oldItem == newItem
        }
    }
}