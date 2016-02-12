package com.yoyo.dookin.desktop;


import java.applet.Applet;
import java.util.UUID;

import javax.swing.JApplet;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplet;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yoyo.dookin.DemoLevel;
import com.yoyo.dookin.DroidGameProject;
import com.yoyo.dookin.DroidGameProject2;
import com.yoyo.dookin.GameProject;
//import com.yoyo.dookin.loltest;

public class DesktopLauncher{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7343881568714204183L;
	//private static final long serialVersionUID = 1L;
	private static String ID = UUID.randomUUID().toString();
	
	 
	
	     static {
	
	          ID = UUID.randomUUID().toString();
	
	     }

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1920; // sets window width
        config.height=1080;  // sets window height
        config.title = "Free Play";
        config.resizable = false;
		new LwjglApplication(new DroidGameProject2(), config);
		
		
	}
	 public void init() {
		 LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width=800; // sets window width
	        config.height=840;  // sets window height
	        config.title = "Free Play";
	        config.resizable = false;
	     
	        new HelloWorldApplet(new GameProject(), config);
	 }
	 public void start()
	 {
		 
			
	 }
}