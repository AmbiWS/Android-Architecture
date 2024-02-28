package com.ambiws.androidarchitecture.features.lists.list.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.androidarchitecture.base.BaseFragment
import com.ambiws.androidarchitecture.base.UiState
import com.ambiws.androidarchitecture.base.list.DefaultListDiffer
import com.ambiws.androidarchitecture.databinding.FragmentListBinding
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserAdapterDelegate
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserListDecorator
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserListItemModel
import com.ambiws.androidarchitecture.utils.RecyclerViewOnBottomScrolledListener
import com.ambiws.androidarchitecture.utils.extensions.subscribe
import com.ambiws.androidarchitecture.utils.extensions.subscribeNullable
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>(
    FragmentListBinding::inflate
) {

    // TODO fix item duplication on 'favorite' action
    // TODO optimize network calls
    // TODO implement storage feature for data as 'favorite'
    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<UserListItemModel>(),
            UserAdapterDelegate.userDefaultAdapterDelegate { userId, isFavorite ->
                viewModel.setFavorite(userId, isFavorite)
            },
            UserAdapterDelegate.userPremiumAdapterDelegate(),
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
