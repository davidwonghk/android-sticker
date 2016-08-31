package com.easy.emotionsticker.helper;

import android.app.Activity;
import android.graphics.Point;

/**
 * Created by david.wong on 16/08/2016.
 */
public class ScreenHelper {
	private Point point;
	private static ScreenHelper instance;

	private ScreenHelper() {
	}

	public static ScreenHelper getInstance() { return instance; }

	public int getWidth() { return point.x; }
	public int getHeight() { return point.y; }

	public static void setActivity(Activity activity) {
		instance = new ScreenHelper();
		instance.point = getScreenSize(activity);
	}

	private static Point getScreenSize(Activity activity) {
		Point size = new Point();
		activity.getWindowManager().getDefaultDisplay().getSize(size);
		return size;
	}
}
