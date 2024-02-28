package com.ambiws.androidarchitecture.features.lists.list.ui.list

import androidx.core.view.isVisible
import com.ambiws.androidarchitecture.R
import com.ambiws.androidarchitecture.databinding.ItemUserCardBinding
import com.ambiws.androidarchitecture.databinding.ItemUserPremiumCardBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object UserAdapterDelegate {

    fun userDefaultAdapterDelegate(
        onFavoriteClick: (Long, Boolean) -> Unit,
    ) : AdapterDelegate<List<UserListItemModel>> {
        return adapterDelegateViewBinding<UserDefaultItemModel, UserListItemModel, ItemUserCardBinding>(
            { layoutInflater, parent ->
                ItemUserCardBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with (binding) {
                    tvNameAndAge.text = item.nameAndAge
                    tvGender.text = item.gender

                    tvCompany.isVisible = !item.company.isNullOrBlank()
                    item.company?.let { company ->
                        tvCompany.text = context.getString(R.string.company_user_string, company)
                    }

                    tvAddress.isVisible = !item.address.isNullOrBlank()
                    item.address?.let { address ->
                        tvAddress.text = address
                    }

                    item.skills?.let { skills ->
                        tvSkills.text = skills
                    } ?: run {
                        tvSkills.text = context.getString(R.string.no_skills_provided)
                    }
                    checkBoxFavorite.isChecked = item.isFavorite ?: false
                    checkBoxFavorite.setOnClickListener {
                        onFavoriteClick.invoke(item.id, checkBoxFavorite.isChecked)
                    }
                }
            }
        }
    }

    fun userPremiumAdapterDelegate() : AdapterDelegate<List<UserListItemModel>> {
        return adapterDelegateViewBinding<UserPremiumItemModel, UserListItemModel, ItemUserPremiumCardBinding>(
            { layoutInflater, parent ->
                ItemUserPremiumCardBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with(binding) {
                    tvNameAndAge.text = item.nameAndAge
                    tvGender.text = item.gender
                    tvBio.text = item.bio
                }
            }
        }
    }
}
