package com.ambiws.androidarchitecture.features.list.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.base.UiState
import com.ambiws.androidarchitecture.base.list.DefaultListDiffer
import com.ambiws.androidarchitecture.databinding.FragmentListBinding
import com.ambiws.androidarchitecture.features.list.ui.list.UserListDecorator
import com.ambiws.androidarchitecture.features.list.ui.list.UserListItemModel
import com.ambiws.androidarchitecture.features.list.ui.list.UserViewType
import com.ambiws.androidarchitecture.utils.RecyclerViewOnBottomScrolledListener
import com.ambiws.androidarchitecture.utils.extensions.subscribe
import com.ambiws.androidarchitecture.utils.extensions.subscribeNullable
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>(
    FragmentListBinding::inflate
) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<UserListItemModel>(),
            UserViewType.DEFAULT_USER_TYPE.adapterDelegate,
            UserViewType.PREMIUM_USER_TYPE.adapterDelegate,
        )
    }

    override fun setupUi() {
        super.setupUi()
        with(binding) {
            rvItems.layoutManager = LinearLayoutManager(requireContext())
            rvItems.addItemDecoration(UserListDecorator())
            rvItems.adapter = adapter
        }
    }

    override fun setupListeners() {
        super.setupListeners()
        with (binding) {
            toolbar.ivLeftAction.setOnClickListener {
                viewModel.navigateBack()
            }
            rvItems.addOnScrollListener(
                RecyclerViewOnBottomScrolledListener {
                    viewModel.loadMoreUsers()
                }
            )
            layoutSwipeRefresh.setOnRefreshListener {
                viewModel.clearUsersList()
                viewModel.initUsersList()
            }
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        subscribe(viewModel.stateLiveEvent) { state ->
            binding.loader.isVisible = state == UiState.Loading
        }
        subscribe(viewModel.usersLiveData) { users ->
            adapter.items = users
        }
        subscribeNullable(viewModel.usersListLoadedLiveEvent) {
            binding.layoutSwipeRefresh.isRefreshing = false
        }
        subscribeNullable(viewModel.usersListExtendedLiveEvent) {
            binding.layoutSwipeRefresh.requestLayout()
        }
    }
}
