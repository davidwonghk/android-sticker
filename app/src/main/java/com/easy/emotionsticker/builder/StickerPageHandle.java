package com.easy.emotionsticker.builder;

import com.easy.emotionsticker.callback.StickerCallback;

public class StickerPageHandle {
	String tabName;
	StickerCallback callback;

	public StickerPageHandle(String tabName, StickerCallback callback) {
		this.tabName = tabName;
		this.callback = callback;
	}
}
