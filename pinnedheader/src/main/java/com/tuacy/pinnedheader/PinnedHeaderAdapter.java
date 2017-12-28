package com.tuacy.pinnedheader;


import android.support.v7.widget.RecyclerView;

public abstract class PinnedHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	/**
	 * 判断该positio对应的位置是要固定
	 *
	 * @param position adapter position
	 * @return true or false
	 */
	public abstract boolean isPinnedPosition(int position);

}
