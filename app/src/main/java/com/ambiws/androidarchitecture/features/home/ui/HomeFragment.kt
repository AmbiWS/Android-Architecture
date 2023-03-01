package com.ambiws.androidarchitecture.features.home.ui

import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override fun setupListeners() {
        super.setupListeners()
        binding.btnList.setOnClickListener {
            viewModel.navigateToListFragment()
        }
    }
}
