package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Player extends Component {

    private OrthographicCamera camera;

    public Player(OrthographicCamera camera) {
        this.camera = camera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
