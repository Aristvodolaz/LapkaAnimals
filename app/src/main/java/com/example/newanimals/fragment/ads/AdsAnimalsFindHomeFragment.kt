package com.example.newanimals.fragment.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.viewModel.board.AdsAnimalsFindHomeViewModel

class AdsAnimalsFindHomeFragment: Fragment() {
    companion object{
        fun newInstance(): AdsAnimalsFindHomeFragment = AdsAnimalsFindHomeFragment()
    }

    private lateinit var model: AdsAnimalsFindHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsAnimalsFindHomeViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                model.animalsPreviewView()
            }
        }
    }

}