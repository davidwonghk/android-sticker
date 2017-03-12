package com.easy.emotionsticker.helper;

import android.net.Uri;

import com.commonsware.cwac.provider.StreamProvider;


class BitmapHelper {
	private static final String PACKAGE_NAME = "com.easy.emotionsticker";
	private static final Uri PROVIDER= Uri.parse("content://"+PACKAGE_NAME);


	public static Uri getDrawableUri(String path) {
		return (PROVIDER
				.buildUpon()
				.appendPath(StreamProvider.getUriPrefix(PACKAGE_NAME))
				.appendPath("assets/" + path + ".jpeg")
				.build());
	}

}
