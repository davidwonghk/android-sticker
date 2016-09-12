package com.easy.emotionsticker.pick;

import android.content.ActivityNotFoundException;

/**
 * Created by david on 31/08/2016.
 */
public interface AppPick {
	int getIcon();
	String getAppName();
	void sendToApplication(String sticker) throws ActivityNotFoundException;
}
