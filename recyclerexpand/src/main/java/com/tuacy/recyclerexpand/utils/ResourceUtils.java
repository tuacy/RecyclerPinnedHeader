package com.tuacy.recyclerexpand.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;

public class ResourceUtils {

	public static int getColor(Context context, @ColorRes int colorId) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
			//noinspection deprecation
			return context.getResources().getColor(colorId);
		} else {
			return context.getColor(colorId);
		}
	}

}
