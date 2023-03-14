package com.ambiws.androidarchitecture.features.list.ui

import androidx.core.view.isVisible
import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.base.UiState
import com.ambiws.androidarchitecture.databinding.FragmentListBinding
import com.ambiws.androidarchitecture.utils.extensions.subscribe

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>(
    FragmentListBinding::inflate
) {

    override fun setupListeners() {
        super.setupListeners()
        binding.toolbar.ivLeftAction.setOnClickListener {
            viewModel.navigateBack()
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        subscribe(viewModel.stateLiveEvent) { state ->
            binding.loader.isVisible = state == UiState.Loading
        }
    }
}
