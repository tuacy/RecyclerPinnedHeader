package com.tuacy.recyclerpinnedheader.linear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.tuacy.pinnedheader.PinnedHeaderItemDecoration;
import com.tuacy.pinnedheader.PinnedHeaderRecyclerView;
import com.tuacy.recyclerpinnedheader.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class LinearRecyclerActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, LinearRecyclerActivity.class));
	}

	private PinnedHeaderRecyclerView mRecyclerView;
	private Context                  mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_linear_recycler);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mRecyclerView = findViewById(R.id.recycler_linear);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
		mRecyclerView.setLayoutManager(layoutManager);
	}

	private void initEvent() {
		mRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
			@Override
			public void onPinnedHeaderClick(int adapterPosition) {
				Toast.makeText(mContext, "点击了悬浮标题 position = " + adapterPosition, LENGTH_SHORT).show();
			}
		});
	}

	private void initData() {
		LinearRecyclerAdapter adapter = new LinearRecyclerAdapter(obtainData());
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
	}

	private List<String> obtainData() {
		List<String> list = new ArrayList<>();
		list.add("2016-07-20");
		list.add("萍乡");
		list.add("高安");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-21");
		list.add("江西");
		list.add("南昌");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-22");
		list.add("中国");
		list.add("北京");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-23");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-24");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-25");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-26");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-27");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-28");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-29");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-30");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-21");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		return list;
	}
}
