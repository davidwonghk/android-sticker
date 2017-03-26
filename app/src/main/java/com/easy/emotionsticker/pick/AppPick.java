package com.easy.emotionsticker.pick;

import android.content.ActivityNotFoundException;
import android.net.Uri;

/**
 * Created by david on 31/08/2016.
 */
public interface AppPick {
	int getIcon();
	String getAppName();
	void sendToApplication(Uri sticker) throws ActivityNotFoundException;
}
