package com.example.topmovies.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentDetailBinding
import com.example.topmovies.db.MoviesEntity
import com.example.topmovies.ui.detail.adapter.ActorsAdapter
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showVisibility
import com.example.topmovies.viewmodels.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding

    //Adapter
    @Inject
    lateinit var actorAdapter: ActorsAdapter
    //entity
    @Inject
    lateinit var moviesEntity: MoviesEntity
    //ViewModel
    private val viewModel: DetailViewModel by viewModels()

    // NavArgs
    private var movieId = 0
    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set movie id
        movieId = args.movieId
        //get movie id
        if (movieId > 0){
            viewModel.getMoviesDetail(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //set data
            viewModel.getDetailLive.observe(viewLifecycleOwner){response->
                poster.load(response.poster){
                    crossfade(true)
                    crossfade(1000)
                }
                imgPosterBack.load(response.poster)
                title.text = response.title.toString()
                txtRate.text = response.imdbRating.toString()
                txtYear.text = response.year.toString()
                txtTime.text = response.runtime.toString()
                txtSummary.text = response.plot.toString()
                //adapter
                actorAdapter.differ.submitList(response.images)
                //init recyclerview
                actorsRecycler.initRecycler(
                    layouManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),
                    adapter = actorAdapter
                )
                //favorite set on click
                favorite.setOnClickListener {
                    moviesEntity.id = movieId
                    moviesEntity.poster = response.poster.toString()
                    moviesEntity.title = response.title.toString()
                    moviesEntity.imdbRating = response.imdbRating.toString()
                    moviesEntity.year = response.year.toString()
                    moviesEntity.country = response.country.toString()
                    viewModel.isFavorite(movieId,moviesEntity)
                }
            }

            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    loading.showVisibility(true)
                    scrollDetail.showVisibility(false)
                }else{
                    loading.showVisibility(false)
                    scrollDetail.showVisibility(true)
                }
                //Favorite Check
                lifecycle.coroutineScope.launchWhenCreated {
                    if (viewModel.exists(movieId)){
                        favorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.fusion_red))
                    }else{
                        favorite.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                    }
                }
                //Is favorite
                viewModel.isFavorite.observe(viewLifecycleOwner){isFavorite->
                    if (isFavorite){
                        favorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.fusion_red))
                    }else{
                        favorite.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white))
                    }
                }
                //back set on click
                back.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }
}