package com.ambiws.androidarchitecture.features.list.ui.list

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

enum class UserViewType(val adapterDelegate: AdapterDelegate<List<UserListItemModel>>) {
    DEFAULT_USER_TYPE(UserAdapterDelegate.userDefaultAdapterDelegate()),
    PREMIUM_USER_TYPE(UserAdapterDelegate.userPremiumAdapterDelegate()),
}
