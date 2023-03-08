package com.ambiws.androidarchitecture.features.list.ui

import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.databinding.FragmentListBinding

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>(
    FragmentListBinding::inflate
) {

    override fun setupListeners() {
        super.setupListeners()
        binding.toolbar.ivLeftAction.setOnClickListener {
            viewModel.navigateBack()
        }
    }
}
