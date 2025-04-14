package com.laioffer.spotify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.laioffer.spotify.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // for fragment
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels() // field injection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        // ComposeView is an UI component in Android Jetpack Compose framework
        // it is used to build interactive, modern and flexible UIs in a declarative way.
        // similar to traditional view class but: instead of defining a layout in XML and then inflating it at runtime,
        // ComposeView allows you to create the UI elements directly in Kotlin code.
        // This makes it easier to crete dynamic and responsive UIs as the UI elements can be updated in realtime
        // based on user input or other events.
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(colors = darkColors()){
                    HomeScreen(viewModel, onTap = {
                        val direction = HomeFragmentDirections.actionHomeFragmentToPlaylistFragment(it)
                        findNavController().navigate(directions = direction)
                        Log.d("HomeFragment", "We tapped ${it.name}")
                    })
                }
            }
        }

    }

    // Use the viewModel in the home fragment
    // handle a very slow response using coroutine
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.uiState.value.isLoading) {
            viewModel.fetchHomeScreen()
        }
    }}