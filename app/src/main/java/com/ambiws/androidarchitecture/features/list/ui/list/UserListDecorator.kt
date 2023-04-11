package com.ambiws.androidarchitecture.features.list.ui.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ambiws.androidarchitecture.utils.extensions.dp

class UserListDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemViewType = parent.getChildViewHolder(view).itemViewType
        // TODO increase view type filtering experience (remove enum if possible)
        if (itemViewType == UserViewType.PREMIUM_USER_TYPE.ordinal) {
            outRect.top = 16.dp
            outRect.left = 4.dp
            outRect.right = 4.dp
        } else if (itemViewType == UserViewType.DEFAULT_USER_TYPE.ordinal) {
            outRect.top = 8.dp
            outRect.left = 8.dp
            outRect.right = 8.dp
        }
    }
}
