package com.tuacy.pinnedheader;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PinnedHeaderRecyclerView extends RecyclerView {

	public interface OnPinnedHeaderClickListener {

		void onPinnedHeaderClick(int adapterPosition);
	}

	public PinnedHeaderRecyclerView(Context context) {
		super(context);
	}

	public PinnedHeaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public PinnedHeaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private OnPinnedHeaderClickListener mPinnedHeaderClickListener;

	public void setOnPinnedHeaderClickListener(OnPinnedHeaderClickListener listener) {
		mPinnedHeaderClickListener = listener;
	}

	private boolean mPinnedHeaderHandle;


	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		if (mPinnedHeaderClickListener == null) {
			return super.onInterceptTouchEvent(e);
		}
		IPinnedHeaderDecoration pinnedHeaderInterface = getPinnedHeaderDecoration();
		if (pinnedHeaderInterface == null) {
			return super.onInterceptTouchEvent(e);
		}
		Rect pinnedHeaderRect = pinnedHeaderInterface.getPinnedHeaderRect();
		int pinnedHeaderPosition = pinnedHeaderInterface.getPinnedHeaderPosition();
		if (pinnedHeaderRect == null || pinnedHeaderPosition == -1) {
			return super.onInterceptTouchEvent(e);
		}
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (pinnedHeaderRect.contains((int) e.getX(), (int) e.getY())) {
					return true;
				}
				break;
		}
		return super.onInterceptTouchEvent(e);
	}


	/**
	 * 如果有固定的header的情况
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mPinnedHeaderClickListener == null) {
			return super.onTouchEvent(ev);
		}
		IPinnedHeaderDecoration pinnedHeaderInterface = getPinnedHeaderDecoration();
		if (pinnedHeaderInterface == null) {
			return super.onTouchEvent(ev);
		}
		Rect pinnedHeaderRect = pinnedHeaderInterface.getPinnedHeaderRect();
		int pinnedHeaderPosition = pinnedHeaderInterface.getPinnedHeaderPosition();
		if (pinnedHeaderRect == null || pinnedHeaderPosition == -1) {
			return super.onTouchEvent(ev);
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPinnedHeaderHandle = false;
				if (pinnedHeaderRect.contains((int) ev.getX(), (int) ev.getY())) {
					mPinnedHeaderHandle = true;
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (mPinnedHeaderHandle) {
					if (!pinnedHeaderRect.contains((int) ev.getX(), (int) ev.getY())) {
						MotionEvent cancel = MotionEvent.obtain(ev);
						cancel.setAction(MotionEvent.ACTION_CANCEL);
						super.dispatchTouchEvent(cancel);

						MotionEvent down = MotionEvent.obtain(ev);
						down.setAction(MotionEvent.ACTION_DOWN);
						return super.dispatchTouchEvent(down);
					} else {
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				float x = ev.getX();
				float y = ev.getY();
				// 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发pinned header 点击
				if (mPinnedHeaderHandle && pinnedHeaderRect.contains((int) x, (int) y)) {
					mPinnedHeaderClickListener.onPinnedHeaderClick(pinnedHeaderPosition);
					mPinnedHeaderHandle = false;
					return true;
				}
				mPinnedHeaderHandle = false;
				break;
			default:
				break;
		}
		return super.onTouchEvent(ev);
	}

	public IPinnedHeaderDecoration getPinnedHeaderDecoration() {
		int decorationIndex = 0;
		ItemDecoration itemDecoration;
		do {
			itemDecoration = getItemDecorationAt(decorationIndex);
			if (itemDecoration instanceof IPinnedHeaderDecoration) {
				return (IPinnedHeaderDecoration) itemDecoration;
			}
			decorationIndex++;
		} while (itemDecoration != null);
		return null;
	}
}
