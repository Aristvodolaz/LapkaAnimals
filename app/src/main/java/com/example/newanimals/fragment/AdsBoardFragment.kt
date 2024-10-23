package com.example.newanimals.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.board.AdsBoardViewModel

class AdsBoardFragment:Fragment() {
    private lateinit var viewModel: AdsBoardViewModel

    companion object{
        @JvmStatic
        fun newInstance(): AdsBoardFragment = AdsBoardFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AdsBoardViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                viewModel.viewPreviewCreateAds()
            }
        }
    }
}