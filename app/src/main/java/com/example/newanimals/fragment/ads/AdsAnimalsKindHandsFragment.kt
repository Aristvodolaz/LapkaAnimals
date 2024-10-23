package com.example.newanimals.fragment.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.fragment.BaseFragment
import com.example.newanimals.viewModel.board.AdsAnimalsKindHandsViewModel

class AdsAnimalsKindHandsFragment: BaseFragment() {

    private lateinit var model: AdsAnimalsKindHandsViewModel

    companion object{
        fun newInstance(): AdsAnimalsKindHandsFragment = AdsAnimalsKindHandsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this)[AdsAnimalsKindHandsViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                model.animalsPreviewView()
            }
        }
    }

    override fun layoutId(): Int {
        TODO("Not yet implemented")
    }
}