package com.tuacy.pinnedheader;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class PinnedHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	/**
	 * 判断该position对应的位置是要固定
	 *
	 * @param position adapter position
	 * @return true or false
	 */
	public abstract boolean isPinnedPosition(int position);


	public RecyclerView.ViewHolder onCreatePinnedViewHolder(ViewGroup parent, int viewType) {
		return onCreateViewHolder(parent, viewType);
	}

	public void onBindPinnedViewHolder(VH holder, int position) {
		onBindViewHolder(holder, position);
	}

}
