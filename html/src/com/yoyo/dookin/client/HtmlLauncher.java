package com.yoyo.dookin.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.yoyo.dookin.GameProject;


public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1080, 720);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new GameProject();
        }
}