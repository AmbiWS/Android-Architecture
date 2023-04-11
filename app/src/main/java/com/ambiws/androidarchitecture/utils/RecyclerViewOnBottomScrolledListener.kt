package com.ambiws.androidarchitecture.utils

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewOnBottomScrolledListener(
    private val action: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1) && dy > 0) {
            action.invoke()
        } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
            // Scrolled to top, nothing to do
        }
    }
}
