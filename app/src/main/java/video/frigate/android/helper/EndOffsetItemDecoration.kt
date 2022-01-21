package video.frigate.android.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Adds an offset to the start of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 *
 *
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the top of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the left of the RecyclerView.
 */
class EndOffsetItemDecoration : RecyclerView.ItemDecoration() {

    private var orientation: Int = 0

    /**
     * Determines the size and location of the offset to be added to the start
     * of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view The child view to be decorated with an offset
     * @param parent The RecyclerView onto which dividers are being added
     * @param state The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) < (parent.adapter?.itemCount ?: Int.MAX_VALUE) - 1)
            return

        orientation = if (parent.layoutManager is LinearLayoutManager)
            (parent.layoutManager as LinearLayoutManager).orientation
        else
            (parent.layoutManager as StaggeredGridLayoutManager).orientation

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (view.width > 0)
                outRect.right = view.width
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            if (view.height > 0)
                outRect.bottom = view.height
        }
    }
}