package com.example.topmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.topmovies.databinding.FragmentHomeBinding
import com.example.topmovies.ui.home.adapters.GenresAdapter
import com.example.topmovies.ui.home.adapters.LastMoviesAdapter
import com.example.topmovies.ui.home.adapters.TopMoviesAdapter
import com.example.topmovies.ui.search.SearchFragmentDirections
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showVisibility
import com.example.topmovies.viewmodels.topmovies.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    //ViewModel
    private val viewModel: HomeViewModel by viewModels()

    //Top Movies Adapter
    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    //Last Movies Adapter
    @Inject
    lateinit var lastMoviesAdapter: LastMoviesAdapter

    //Genres Adapter
    @Inject
    lateinit var genresAdapter: GenresAdapter

    //PagerHelper
    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    //call api in onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // call api
        viewModel.getTopMovies(6)
        viewModel.getGenres()
        //Get last movies
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.getLastMoviesList.collect {
                lastMoviesAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init view
        binding.apply {
            //Get Top Movies
            viewModel.getTopMoviesLive.observe(viewLifecycleOwner) { movieResponse ->
                topMoviesAdapter.differ.submitList(movieResponse.data)
                topMovieRecycler.initRecycler(
                    layouManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ),
                    adapter = topMoviesAdapter
                )
                //Indicator
                pagerHelper.attachToRecyclerView(topMovieRecycler)
                indicator.attachToRecyclerView(topMovieRecycler, pagerHelper)
            }
            //Genres
            viewModel.getGenresLive.observe(viewLifecycleOwner) { genresList ->
                genresAdapter.differ.submitList(genresList)
                genresRecycler.initRecycler(
                    layouManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ),
                    adapter = genresAdapter
                )
            }

            lastMoviesRecycler.initRecycler(
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
                lastMoviesAdapter
            )

            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    loading.showVisibility(true)
                    movieScroll.showVisibility(false)
                } else {
                    loading.showVisibility(false)
                    movieScroll.showVisibility(true)
                }
            }

            topMoviesAdapter.setOnClickListener {
                val direction = HomeFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
            lastMoviesAdapter.setOnClickListener {
                val direction = HomeFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
        }
    }
}