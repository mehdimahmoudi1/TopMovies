package com.example.topmovies.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentRegisterBinding
import com.example.topmovies.models.register.BodyRegister
import com.example.topmovies.utils.UserDataStore
import com.example.topmovies.utils.showVisibility
import com.example.topmovies.viewmodels.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    //DataStore
    @Inject
    lateinit var userDataStore: UserDataStore

    //ViewModel
    private val viewModel: RegisterViewModel by viewModels()

    //BodyRegister
    @Inject
    lateinit var body: BodyRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            submitBtn.setOnClickListener {
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val pass = edtPassword.text.toString()
                if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Snackbar.make(it, "Please fill all fields!", Snackbar.LENGTH_LONG).show()
                } else {
                    body.name = name
                    body.email = email
                    body.password = pass
                }
                //SetData
                viewModel.registerUsr(body)
                //Loading
                viewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
                    if (isVisible) {
                        loading.showVisibility(true)
                        submitBtn.showVisibility(false)
                    } else {
                        loading.showVisibility(false)
                        submitBtn.showVisibility(true)
                    }
                }
                //Register
                viewModel.registerUser.observe(viewLifecycleOwner) { response ->
                    lifecycle.coroutineScope.launchWhenCreated {
                        userDataStore.saveUserToken(response.name.toString())
                    }
                }
            }
        }
    }
}