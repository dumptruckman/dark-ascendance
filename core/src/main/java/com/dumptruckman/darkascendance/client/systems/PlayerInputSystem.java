package com.dumptruckman.darkascendance.client.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.client.components.Player;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class PlayerInputSystem extends IntervalEntitySystem implements InputProcessor {

    ComponentMapper<Controls> controlsMap;

    private boolean up = false;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean shoot;
    private float timeToFire;

    private Vector3 mouseVector;

    public PlayerInputSystem(float interval) {
        super(interval, Player.class, Controls.class);
        this.mouseVector = new Vector3();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Controls controls = controlsMap.get(entityId);

        mouseVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        boolean changed = false;
        if (controls.up() != up) {
            controls.up(up);
            changed = true;
        }
        if (controls.down() != down) {
            controls.down(down);
            changed = true;
        }
        if (controls.left() != left) {
            controls.left(left);
            changed = true;
        }
        if (controls.right() != right) {
            controls.right(right);
            changed = true;
        }
        if (changed) {
            CommandSendSystem.setPlayerControlsChanged(controls);
        }

        /*
        if(shoot) {
            if(timeToFire <= 0) {
                //EntityFactory.createPlayerBullet(world, position.x - 27, position.y + 2).addToWorld();
                //EntityFactory.createPlayerBullet(world, position.x+27, position.y+2).addToWorld();
                timeToFire = FireRate;
            }
        }
        if(timeToFire > 0) {
            timeToFire -= world.delta;
            if(timeToFire < 0) {
                timeToFire = 0;
            }
        }
        */
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if(keycode == Input.Keys.A) {
            left = true;
        } else if(keycode == Input.Keys.D) {
            right = true;
        } else if(keycode == Input.Keys.W) {
            up = true;
        } else if(keycode == Input.Keys.S) {
            down = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            left = false;
        } else if(keycode == Input.Keys.RIGHT) {
            right = false;
        } else if(keycode == Input.Keys.UP) {
            up = false;
        } else if(keycode == Input.Keys.DOWN) {
            down = false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            shoot = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            shoot = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int i, final int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
