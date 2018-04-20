package com.tuacy.recyclerexpand.expand;

/**
 * RecyclerView adapter 里面每个position对应的信息
 */
public class ExpandGroupIndexEntity {

	private int mGroupIndex;
	private int mChildIndex;
	private int mChildCount;

	public ExpandGroupIndexEntity(int groupIndex, int childIndex, int childCount) {
		mGroupIndex = groupIndex;
		mChildIndex = childIndex;
		mChildCount = childCount;
	}

	public int getGroupIndex() {
		return mGroupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		mGroupIndex = groupIndex;
	}

	public int getChildIndex() {
		return mChildIndex;
	}

	public void setChildIndex(int childIndex) {
		mChildIndex = childIndex;
	}

	public int getChildCount() {
		return mChildCount;
	}

	public void setChildCount(int childCount) {
		mChildCount = childCount;
	}
}
