package com.belatrix.events.presentation.ui.common;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author lburgos
 */
public class EventSpaceDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public EventSpaceDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public EventSpaceDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Rect outRect, RecyclerView.LayoutManager layoutManager, int position, int itemCount) {
        DisplayMode displayMode = resolveDisplayMode(layoutManager);
        switch (displayMode) {
            case HORIZONTAL:
                outRect.left = mItemOffset;
                outRect.right = position == itemCount - 1 ? mItemOffset : 0;
                outRect.top = mItemOffset;
                outRect.bottom = mItemOffset;
                break;
            case VERTICAL:
                outRect.left = mItemOffset;
                outRect.right = mItemOffset;
                outRect.top = mItemOffset;
                outRect.bottom = position == itemCount - 1 ? mItemOffset : 0;
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int cols = gridLayoutManager.getSpanCount();
                    if (position == 0) {
                        outRect.left = mItemOffset;
                        outRect.top = mItemOffset;
                        outRect.right = mItemOffset;
                    } else {
                        outRect.left = position % cols == cols - 1 ? mItemOffset : (mItemOffset / 2);
                        outRect.top = mItemOffset / 2;
                        outRect.right = position % cols == cols - 1 ? (mItemOffset / 2) : mItemOffset;
                    }
                    outRect.bottom = mItemOffset / 2;
                }
                break;
        }
    }

    private DisplayMode resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) return DisplayMode.GRID;
        if (layoutManager.canScrollHorizontally()) return DisplayMode.HORIZONTAL;
        return DisplayMode.VERTICAL;
    }

    private enum DisplayMode {
        VERTICAL, HORIZONTAL, GRID
    }
}
