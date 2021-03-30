package com.customs.bag.util.call_back;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public abstract class RecyclerPages extends RecyclerView.OnScrollListener {
    public static final String TAG = RecyclerPages.class.getSimpleName();

    public static int previousTotal = 0; // The total number of items in the dataset after the last load
    public static boolean loading = true; // True if we are still waiting for the last set of data to load.
    public static final int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    public static int firstVisibleItem, visibleItemCount, totalItemCount;
    public static LinearLayoutManager mLinearLayoutManager;
    public static int current_page = 1;
    public RecyclerPages(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }
    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);
}
