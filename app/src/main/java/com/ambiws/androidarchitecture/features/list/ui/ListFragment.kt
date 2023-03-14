package com.ambiws.androidarchitecture.features.list.ui

import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.base.UiState
import com.ambiws.androidarchitecture.databinding.FragmentListBinding
import com.ambiws.androidarchitecture.utils.extensions.subscribe
import com.ambiws.androidarchitecture.utils.logd

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
            when (state) {
                is UiState.Error -> logd("Error: ${state.error}")
                UiState.Loading -> logd("Loading...")
                UiState.Success -> logd("Success!")
            }
        }
    }
}
