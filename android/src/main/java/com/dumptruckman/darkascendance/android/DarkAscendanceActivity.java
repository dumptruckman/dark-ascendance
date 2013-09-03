package com.dumptruckman.darkascendance.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dumptruckman.darkascendance.client.GameSettings;
import com.dumptruckman.darkascendance.shared.DarkAscendance;
import recs.Entity;
import recs.EntityWorld;

public class DarkAscendanceActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        EntityWorld world = new EntityWorld();
        Entity entity = new Entity();
        world.addEntity(entity);
        //get
        //initialize(new DarkAscendance(new GameSettings()), config);
    }
}
