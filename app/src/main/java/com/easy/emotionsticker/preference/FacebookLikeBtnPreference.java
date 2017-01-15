package com.easy.emotionsticker.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.easy.emotionsticker.R;
import com.facebook.share.widget.LikeView;


public class FacebookLikeBtnPreference extends Preference {

	public FacebookLikeBtnPreference(Context context, AttributeSet attrs, int defStyle) {super    (context, attrs, defStyle);}
	public FacebookLikeBtnPreference(Context context, AttributeSet attrs) {super(context, attrs);}
	public FacebookLikeBtnPreference(Context context) {super(context);}


	@Override
	protected View onCreateView(ViewGroup parent) {
		// this will create the linear layout defined in ads_layout.xml
		View view = super.onCreateView(parent);

		Context context = getContext();
		final String appUrl = context.getString(R.string.app_url);

		LikeView likeView = (LikeView) view.findViewById(R.id.like_view);
		likeView.setObjectIdAndType( appUrl, LikeView.ObjectType.PAGE );
		likeView.setActivated(true);

		return view;
	}

}
