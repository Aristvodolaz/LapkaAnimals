package com.example.newanimals.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.AdsAnimalsFindHomeViewModel

class AdsAnimalsFindHomeFragment:Fragment() {
    private lateinit var modelView:AdsAnimalsFindHomeViewModel
    companion object{
        @JvmStatic
        fun newInstance(): AdsAnimalsFindHomeFragment = AdsAnimalsFindHomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        modelView = ViewModelProvider(this)[AdsAnimalsFindHomeViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                modelView.animalsPreviewView()
            }
        }
    }
}