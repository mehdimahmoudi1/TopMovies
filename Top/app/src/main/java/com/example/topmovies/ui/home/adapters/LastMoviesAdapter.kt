package com.example.topmovies.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.MoviesListItemsBinding
import com.example.topmovies.models.lastmovies.ResponseLastMovies.Data
import javax.inject.Inject
//Adapter with paging - Differ Item Callback
class LastMoviesAdapter @Inject constructor() :
    PagingDataAdapter<Data,LastMoviesAdapter.ViewHolder>(differCallback) {
    //View Binding
    private lateinit var binding: MoviesListItemsBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LastMoviesAdapter.ViewHolder {
        binding = MoviesListItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder()
    }
    override fun onBindViewHolder(holder: LastMoviesAdapter.ViewHolder, position: Int) {
        //Get Item from differ
        holder.setData(getItem(position)!!)
        //Don't show duplicate item
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun setData(item: Data){
            binding.apply {
                title.text = item.title.toString()
                txtCountry.text = item.country.toString()
                txtRate.text = item.imdbRating.toString()
                txtYear.text = item.year.toString()
                imgPoster.load(item.poster){
                    crossfade(true)
                    crossfade(1000)
                }
                //Set root on clicked
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    //Set On Click
    private var onItemClickListener : ((Data)->Unit)? = null

    //Set listener
    fun setOnClickListener(listener : ((Data)-> Unit)?){
        onItemClickListener = listener
    }

    //Differ callback
    companion object{
        val differCallback = object : DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }
}