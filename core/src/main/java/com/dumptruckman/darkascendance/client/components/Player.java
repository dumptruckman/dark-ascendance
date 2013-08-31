package com.dumptruckman.darkascendance.client.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import recs.Component;

public class Player extends Component {

    private OrthographicCamera camera;

    public Player(OrthographicCamera camera) {
        this.camera = camera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }


}
