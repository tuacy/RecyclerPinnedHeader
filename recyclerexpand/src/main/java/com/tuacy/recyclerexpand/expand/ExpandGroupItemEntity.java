package com.tuacy.recyclerexpand.expand;


import java.util.List;

/**
 * 每个分组对应的entity
 *
 * @param <G> 标题栏entity
 * @param <S> 子项entity
 */
public class ExpandGroupItemEntity<G, S> {

	/**
	 * 分组对应的标题栏
	 */
	private G       mParent;
	/**
	 * 分组里面的子项
	 */
	private List<S> mChildList;
	/**
	 * 分组展开还是收起
	 */
	private boolean mExpand;

	public G getParent() {
		return mParent;
	}

	public void setParent(G parent) {
		mParent = parent;
	}

	public List<S> getChildList() {
		return mChildList;
	}

	public void setChildList(List<S> childList) {
		mChildList = childList;
	}

	public boolean isExpand() {
		return mExpand;
	}

	public void setExpand(boolean expand) {
		mExpand = expand;
	}
}
