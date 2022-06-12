package com.example.topmovies.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.MoviesListItemsBinding
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.models.lastmovies.ResponseLastMovies.Data
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var binding: MoviesListItemsBinding
    var movieList = emptyList<MoviesEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        binding = MoviesListItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bindData(movieList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = movieList.size


    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MoviesEntity) {
            binding.apply {
                title.text = item.title
                txtCountry.text = item.country
                txtRate.text = item.imdbRating
                txtYear.text = item.year
                imgPoster.load(item.poster) {
                    crossfade(true)
                    crossfade(1000)
                }
                root.setOnClickListener {
                    onItemClickListener.let {

                    }
                }
            }
        }
    }

    //Set On Click
    private var onItemClickListener: ((Data) -> Unit)? = null

    //Set listener
    fun setOnClickListener(listener: ((Data) -> Unit)?) {
        onItemClickListener = listener
    }

    //set data with differ call back
    fun setData(data: List<MoviesEntity>) {
        val movieDiffUtil = MovieDiffUtils(movieList, data)
        val diffUtil = DiffUtil.calculateDiff(movieDiffUtil)
        movieList = data
        diffUtil.dispatchUpdatesTo(this)
    }

    class MovieDiffUtils(private val oldItem: List<MoviesEntity>, private val newItem: List<MoviesEntity>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}