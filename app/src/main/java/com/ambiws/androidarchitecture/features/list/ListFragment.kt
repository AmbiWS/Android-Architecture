package com.ambiws.androidarchitecture.features.list

import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.base.EmptyViewModel
import com.ambiws.androidarchitecture.databinding.FragmentListBinding

class ListFragment : BaseFragment<EmptyViewModel, FragmentListBinding>(
    FragmentListBinding::inflate
) {

    override fun setupListeners() {
        super.setupListeners()
        binding.toolbar.ivLeftAction.setOnClickListener {
            viewModel.navigateBack()
        }
    }
}
