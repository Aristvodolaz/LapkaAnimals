package com.example.newanimals.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.AdsServiceViewModel

class AdsServiceFragment: Fragment() {

    private lateinit var model: AdsServiceViewModel

    companion object{
        @JvmStatic
        fun newInstance(): AdsServiceFragment = AdsServiceFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsServiceViewModel::class.java]
        return ComposeView(requireContext())
            .apply {
                setContent {
                    model.servicePreviewView()
                }
            }
    }
}