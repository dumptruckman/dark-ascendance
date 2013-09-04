package com.dumptruckman.darkascendance.android;

import android.graphics.Point;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dumptruckman.darkascendance.client.GameSettings;
import com.dumptruckman.darkascendance.shared.DarkAscendance;

public class DarkAscendanceActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;

        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);

        initialize(new DarkAscendance(new GameSettings(p.x, p.y), "gnarbros.dyndns.org", 25560, 25565), config);
    }
}
