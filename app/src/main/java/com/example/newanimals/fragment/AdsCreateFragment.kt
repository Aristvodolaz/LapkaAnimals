package com.example.newanimals.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.AdsCreateViewModel

class AdsCreateFragment: Fragment() {
    private lateinit var modelView: AdsCreateViewModel

    companion object{
//        fun newInstance(): AdsCreateFragment = AdsCreateFragment()

        @JvmStatic
        fun newInstance(): AdsCreateFragment = AdsCreateFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        modelView = ViewModelProvider(this)[AdsCreateViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent { modelView.viewPreviewCreateAds() }
        }
    }
}