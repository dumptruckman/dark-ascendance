package com.dumptruckman.darkascendance.shared.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.client.components.Player;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovementSystemTest {

    Position position;
    Velocity velocity;
    Player player;
    MovementSystem movementSystem;

    @Before
    public void setUp() throws Exception {
        position = new Position().setX(0).setY(0).setRotation(0);
        velocity = new Velocity();
        player = new Player(new OrthographicCamera());
        player.getCamera().position.x = 0;
        player.getCamera().position.y = 0;
        movementSystem = new MovementSystem(0F, false);
    }

    @Test
    public void testBasicMovement() {
        velocity.setX(50F).setY(50F);
        movementSystem.processMovement(position, velocity, 1F);
        assertEquals(50F, position.getX(), 0.0001F);
        assertEquals(50F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);

        velocity.setX(-50F).setY(-50F);
        movementSystem.processMovement(position, velocity, 1F);
        assertEquals(0F, position.getX(), 0.0001F);
        assertEquals(0F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);

        movementSystem.processMovement(position, velocity, 1F);
        assertEquals(-50F, position.getX(), 0.0001F);
        assertEquals(-50F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);
    }

    @Test
    public void testBasicMovementHalfDelta() {
        velocity.setX(50F).setY(50F);
        movementSystem.processMovement(position, velocity, .5F);
        assertEquals(25F, position.getX(), 0.0001F);
        assertEquals(25F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);

        velocity.setX(-50F).setY(-50F);
        movementSystem.processMovement(position, velocity, .5F);
        assertEquals(0F, position.getX(), 0.0001F);
        assertEquals(0F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);

        movementSystem.processMovement(position, velocity, .5F);
        assertEquals(-25F, position.getX(), 0.0001F);
        assertEquals(-25F, position.getY(), 0.0001F);
        assertEquals(0F, position.getRotation(), 0.0001F);
    }

    // TODO Move to own test class
    /*
    @Test
    public void testCameraMovesAlso() {
        velocity.setX(50F).setY(50F);
        MovementSystem.processMovement(position, velocity, player, .5F);
        Vector3 cameraVector = player.getCamera().position;
        assertEquals(25F, cameraVector.x, 0.0001F);
        assertEquals(25F, cameraVector.y, 0.0001F);
    }
    */
}
