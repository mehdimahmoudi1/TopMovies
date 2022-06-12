package com.example.topmovies.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentSplashBinding
import com.example.topmovies.utils.UserDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    // DataStore
    @Inject
    lateinit var userDataStore : UserDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            lifecycle.coroutineScope.launchWhenCreated {
                delay(3000)
                userDataStore.getUserToken().collect{
                    if (it.isEmpty()){
                        findNavController().navigate(R.id.actionSplashToRegister)
                    }else{
                        findNavController().navigate(R.id.action_to_home)
                    }
                }
            }
        }
    }
}