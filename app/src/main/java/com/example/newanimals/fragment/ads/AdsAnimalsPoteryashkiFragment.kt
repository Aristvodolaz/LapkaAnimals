package com.example.newanimals.fragment.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.board.AdsAnimalsPoteryashkiViewModel

class AdsAnimalsPoteryashkiFragment: Fragment() {
    private lateinit var model: AdsAnimalsPoteryashkiViewModel

    companion object{
        fun newInstance(): AdsAnimalsPoteryashkiFragment = AdsAnimalsPoteryashkiFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsAnimalsPoteryashkiViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                model.animalsPreviewView()
            }
        }
    }
}