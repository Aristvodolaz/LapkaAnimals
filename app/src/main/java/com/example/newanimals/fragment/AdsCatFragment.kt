package com.example.newanimals.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.AdsCatViewModel

class AdsCatFragment: Fragment() {

    private lateinit var model: AdsCatViewModel

    companion object{
        fun newInstance(): AdsCatFragment = AdsCatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsCatViewModel::class.java]
        return ComposeView(requireContext())
            .apply {
                setContent {
                    model.servicePreviewView()
                }
            }
    }
}