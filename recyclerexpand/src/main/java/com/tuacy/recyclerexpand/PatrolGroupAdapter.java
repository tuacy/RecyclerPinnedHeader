package com.tuacy.recyclerexpand;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuacy.recyclerexpand.expand.ExpandGroupItemEntity;
import com.tuacy.recyclerexpand.expand.RecyclerExpandBaseAdapter;
import com.tuacy.recyclerexpand.utils.DensityUtils;
import com.tuacy.recyclerexpand.utils.ResourceUtils;


public class PatrolGroupAdapter extends RecyclerExpandBaseAdapter<String, PatrolItem, RecyclerView.ViewHolder> {


	/**
	 * 悬浮标题栏被点击的时候，展开收起切换功能
	 */
	public void switchExpand(int adapterPosition) {
		int groupIndex = mIndexMap.get(adapterPosition).getGroupIndex();
		ExpandGroupItemEntity entity = mDataList.get(groupIndex);
		entity.setExpand(!entity.isExpand());
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == VIEW_TYPE_ITEM_TIME) {
			TitleItemHolder holder = new TitleItemHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_order_title, parent, false));
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ExpandGroupItemEntity entity = (ExpandGroupItemEntity) v.getTag();
					entity.setExpand(!entity.isExpand());
					notifyDataSetChanged();
				}
			});
			return holder;
		} else {
			return new SubItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_order_sub, parent, false));
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreatePinnedViewHolder(ViewGroup parent, int viewType) {
		TitleItemHolder holder = (TitleItemHolder) super.onCreatePinnedViewHolder(parent, viewType);
		holder.mViewSpace.setVisibility(View.GONE);
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
			int groupIndex = mIndexMap.get(position).getGroupIndex();
			TitleItemHolder itemHolder = (TitleItemHolder) holder;
			itemHolder.itemView.setTag(mDataList.get(groupIndex));
			itemHolder.mTextTime.setText(mDataList.get(groupIndex).getParent());
			itemHolder.mImageExpandFlag.setImageResource(
				mDataList.get(groupIndex).isExpand() ? R.drawable.ic_up_indicate : R.drawable.ic_down_indicate);
			if (mDataList.get(groupIndex).isExpand()) {
				if (mIndexMap.get(position).getChildCount() == 0) {
					itemHolder.mViewSpace.setVisibility(View.VISIBLE);
				} else {
					itemHolder.mViewSpace.setVisibility(View.GONE);
				}
			} else {
				itemHolder.mViewSpace.setVisibility(View.VISIBLE);
			}
		} else {
			SubItemHolder subHolder = (SubItemHolder) holder;
			int groupIndex = mIndexMap.get(position).getGroupIndex();
			int childIndex = mIndexMap.get(position).getChildIndex();
			PatrolItem subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
			subHolder.itemView.setTag(subItem);
			subHolder.mTextTime.setText(subItem.getTime().substring(11, 16));
			subHolder.mTextCompanyName.setText(subItem.getFactoryName());
			subHolder.mTextUsers.setText(subItem.getUser());
			subHolder.mTextState.setText(getStateDes(subHolder.mTextState.getContext(), subItem.getState()));
			subHolder.mTextState.setTextColor(getStateColor(subHolder.mTextState.getContext(), subItem.getState()));
			GradientDrawable gradientDrawable = (GradientDrawable) subHolder.mImageState.getBackground();
			gradientDrawable.setStroke(DensityUtils.dp2px(subHolder.mImageState.getContext(), 2),
									   getStateColor(subHolder.mImageState.getContext(), subItem.getState()));
		}
	}

	@Override
	public void onBindPinnedViewHolder(RecyclerView.ViewHolder holder, int position) {
		super.onBindPinnedViewHolder(holder, position);
		TitleItemHolder itemHolder = (TitleItemHolder) holder;
		itemHolder.mViewSpace.setVisibility(View.GONE);
	}

	private static int getStateColor(Context context, int state) {
		int color = ResourceUtils.getColor(context, R.color.order_state_waiting_reception);
		switch (state) {
			case 0:
				color = ResourceUtils.getColor(context, R.color.order_state_waiting_reception);
				break;
			case 1:
				color = ResourceUtils.getColor(context, R.color.order_state_distributed);
				break;
			case 2:
				color = ResourceUtils.getColor(context, R.color.order_state_progressing);
				break;
			case 3:
				color = ResourceUtils.getColor(context, R.color.order_state_auditing);
				break;
			case 4:
				color = ResourceUtils.getColor(context, R.color.order_state_finished);
				break;
			case 5:
				color = ResourceUtils.getColor(context, R.color.order_state_not_started);
				break;
		}
		return color;
	}

	private static String getStateDes(Context context, int state) {
		String des = context.getString(R.string.waiting_reception);
		switch (state) {
			case 0:
				des = context.getString(R.string.waiting_reception);
				break;
			case 1:
				des = context.getString(R.string.distributed);
				break;
			case 2:
				des = context.getString(R.string.progressing);
				break;
			case 3:
				des = context.getString(R.string.auditing);
				break;
			case 4:
				des = context.getString(R.string.finished);
				break;
			case 5:
				des = context.getString(R.string.not_started);
				break;
		}
		return des;
	}

	static class TitleItemHolder extends RecyclerView.ViewHolder {

		View      mViewSpace;
		TextView  mTextTime;
		ImageView mImageExpandFlag;

		TitleItemHolder(View itemView) {
			super(itemView);
			mViewSpace = itemView.findViewById(R.id.view_space);
			mTextTime = itemView.findViewById(R.id.text_time);
			mImageExpandFlag = itemView.findViewById(R.id.image_expand_flag);
		}
	}

	static class SubItemHolder extends RecyclerView.ViewHolder {

		ImageView mImageState;
		TextView  mTextTime;
		TextView  mTextCompanyName;
		TextView  mTextUsers;
		TextView  mTextState;

		SubItemHolder(View itemView) {
			super(itemView);
			mImageState = itemView.findViewById(R.id.image_state);
			mTextTime = itemView.findViewById(R.id.text_time);
			mTextCompanyName = itemView.findViewById(R.id.text_company_name);
			mTextUsers = itemView.findViewById(R.id.text_repair_user);
			mTextState = itemView.findViewById(R.id.text_repair_flag);
		}
	}
}
