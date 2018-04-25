package com.tuacy.recyclerpinnedheader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tuacy.recyclerpinnedheader.grid.GridRecyclerActivity;
import com.tuacy.recyclerpinnedheader.linear.LinearRecyclerActivity;

public class MainActivity extends AppCompatActivity {


	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		findViewById(R.id.layout_pinned_header_linear).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearRecyclerActivity.startUp(mContext);
			}
		});
		findViewById(R.id.layout_pinned_header_grid).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GridRecyclerActivity.startUp(mContext);
			}
		});
	}
}
