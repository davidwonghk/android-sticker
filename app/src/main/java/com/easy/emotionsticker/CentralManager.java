package com.easy.emotionsticker;

import android.content.Context;

import com.easy.emotionsticker.helper.AdHelper;
import com.easy.emotionsticker.helper.ResourcesRepository;
import com.easy.emotionsticker.helper.StickerHistory;
import com.easy.emotionsticker.pick.AppRepository;


public interface CentralManager {
	Context getContext();
	AdHelper getAdHelper();
	StickerHistory getHistory();
	ResourcesRepository getResourcesRepository();
	AppRepository getAppRepository();

}
