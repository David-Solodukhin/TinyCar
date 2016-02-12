package com.yoyo.dookin.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.yoyo.dookin.DroidGameProject;
import com.yoyo.dookin.DroidGameProject2;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideStatusBar(true);
		useImmersiveMode(true);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DroidGameProject2(), config);
	}
}
