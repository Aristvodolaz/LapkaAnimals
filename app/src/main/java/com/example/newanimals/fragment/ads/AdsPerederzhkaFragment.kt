package com.example.newanimals.fragment.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.board.AdsPerederzhkaViewModel

class AdsPerederzhkaFragment: Fragment() {

    private lateinit var model: AdsPerederzhkaViewModel
    companion object{
        fun newInstance(): AdsPerederzhkaFragment = AdsPerederzhkaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsPerederzhkaViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                model.servicePreviewView()
            }
        }
    }
}