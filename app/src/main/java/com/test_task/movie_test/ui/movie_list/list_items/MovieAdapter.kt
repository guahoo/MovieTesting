package com.test_task.movie_test.ui.movie_list.list_items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test_task.movie_test.data.models.MovieModel
import com.test_task.movie_test.databinding.MovieItemBinding
import com.test_task.movie_test.extensions.cornerImage


class MovieAdapter() : PagingDataAdapter<MovieModel, MovieAdapter.MovieViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(item: MovieModel) = with(binding) {
            ViewCompat.setTransitionName(binding.tvMovieTitle, "name_${item.display_title}")
            ViewCompat.setTransitionName(binding.tvStatus, "status_${item.summary_short}")
            movie = item
            ivMovieAvatar.cornerImage(item.multimedia.src, 10)


        }
    }

    object MovieComparator : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) =
            oldItem.date_updated == newItem.date_updated

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) =
            oldItem == newItem
    }

    interface CharacterClickListener {
        fun onCharacterClicked(binding: MovieItemBinding, movie: MovieModel)
    }
}