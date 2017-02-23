package com.dumptruckman;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import recs.EntitySystem;
import recs.EntityWorld;

import static org.junit.Assert.*;

public class GameStateManagerTest {

    private GameStateManager stateManager;
    private EntityWorld world;
    private boolean s1 = false, s2 = false, s3 = false, s4 = false;

    private class TestSystem1 extends EntitySystem {
        @Override
        protected void processSystem(float deltaInSec) {
            super.processSystem(deltaInSec);
            s1 = true;
        }
    }

    private class TestSystem2 extends EntitySystem {
        @Override
        protected void processSystem(float deltaInSec) {
            super.processSystem(deltaInSec);
            s2 = true;
        }
    }

    private class TestSystem3 extends EntitySystem {
        @Override
        protected void processSystem(float deltaInSec) {
            super.processSystem(deltaInSec);
            s3 = true;
        }
    }

    private class TestSystem4 extends EntitySystem {
        @Override
        protected void processSystem(float deltaInSec) {
            super.processSystem(deltaInSec);
            s4 = true;
        }
    }

    @Before
    public void setup() {
        world = new EntityWorld();
        stateManager = new GameStateManager(world);
        assertEquals(GameState.NONE, stateManager.getCurrentState());
        EntitySystem es1 = new TestSystem1();
        EntitySystem es2 = new TestSystem2();
        EntitySystem es3 = new TestSystem3();
        EntitySystem es4 = new TestSystem4();
        stateManager.addSystemsToGameState(GameState.MAIN_MENU, es1, es2, es3);
        stateManager.addSystemsToGameState(GameState.PLAY, es3, es4);
        assertEquals(GameState.NONE, stateManager.getCurrentState());
    }

    @After
    public void tearDown() {
        clearTestFlags();
    }

    private void clearTestFlags() {
        s1 = false;
        s2 = false;
        s3 = false;
        s4 = false;
    }

    private void testFlags(boolean s1, boolean s2, boolean s3, boolean s4) {
        assertEquals(s1, this.s1);
        assertEquals(s2, this.s2);
        assertEquals(s3, this.s3);
        assertEquals(s4, this.s4);
    }

    @Test
    public void processWorldBeforeGameStateSet() {
        world.process(1F);

        testFlags(false, false, false, false);
    }

    @Test
    public void setInitialGameState() {
        stateManager.setGameState(GameState.MAIN_MENU);

        assertEquals(GameState.MAIN_MENU, stateManager.getCurrentState());
        testFlags(false, false, false, false);

        world.process(1F);

        testFlags(true, true, true, false);
    }

    @Test
    public void testSwitchGameState() {
        stateManager.setGameState(GameState.MAIN_MENU);

        assertEquals(GameState.MAIN_MENU, stateManager.getCurrentState());
        testFlags(false, false, false, false);

        world.process(1F);

        testFlags(true, true, true, false);
        clearTestFlags();

        stateManager.setGameState(GameState.PLAY);

        assertEquals(GameState.PLAY, stateManager.getCurrentState());
        testFlags(false, false, false, false);

        world.process(1F);

        testFlags(false, false, true, true);
    }
}
