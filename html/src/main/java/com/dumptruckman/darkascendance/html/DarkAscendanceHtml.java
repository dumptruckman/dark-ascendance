package com.dumptruckman.darkascendance.html;

import com.dumptruckman.darkascendance.core.DarkAscendance;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class DarkAscendanceHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new DarkAscendance();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
