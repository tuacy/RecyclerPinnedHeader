package com.tuacy.recyclerexpand;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.tuacy.pinnedheader.PinnedHeaderItemDecoration;
import com.tuacy.pinnedheader.PinnedHeaderRecyclerView;
import com.tuacy.recyclerexpand.expand.ExpandGroupItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private Context                  mContext;
	private PinnedHeaderRecyclerView mRecyclerView;
	private LinearLayoutManager      mLayoutManager;
	private PatrolGroupAdapter       mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mRecyclerView = findViewById(R.id.recycler_order_list);
		mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
		mRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
	}

	private void initEvent() {
		/**
		 * 当标题栏被悬浮的时候的点击功能
		 */
		mRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
			@Override
			public void onPinnedHeaderClick(int adapterPosition) {
				mAdapter.switchExpand(adapterPosition);
				//标题栏被点击之后，滑动到指定位置
				mLayoutManager.scrollToPositionWithOffset(adapterPosition, 0);
			}
		});
	}

	private void initData() {
		mAdapter = new PatrolGroupAdapter();
		mAdapter.setData(obtainDataList());
		mRecyclerView.setAdapter(mAdapter);
	}

	private List<ExpandGroupItemEntity<String, PatrolItem>> obtainDataList() {
		List<ExpandGroupItemEntity<String, PatrolItem>> dataList = new ArrayList<>();

		for (int group = 0; group < 10; group++) {
			ExpandGroupItemEntity<String, PatrolItem> groupItem = new ExpandGroupItemEntity<>();
			groupItem.setExpand(true);
			groupItem.setParent("分组 " + group);
			List<PatrolItem> childList = new ArrayList<>();
			for (int child = 0; child < group + 1; child++) {
				PatrolItem childItem = new PatrolItem();
				childItem.setTime("2018-04-20 15:00");
				childItem.setFactoryName((2000 + child) + " 项目");
				childItem.setUser("电工 " + child);
				childItem.setState(child % 5);
				childList.add(childItem);
			}
			groupItem.setChildList(childList);
			dataList.add(groupItem);
		}

		return dataList;
	}
}
