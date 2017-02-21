package com.dumptruckman;

import recs.EntitySystem;
import recs.EntityWorld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GameStateManager {

    private final EntityWorld world;

    private GameState currentState = GameState.NONE;
    private Map<GameState, Set<EntitySystem>> gameStateSystems = new HashMap<GameState, Set<EntitySystem>>(GameState.values().length);

    GameStateManager(EntityWorld world) {
        this.world = world;
        gameStateSystems.put(GameState.NONE, new HashSet<EntitySystem>(0));
    }

    void addSystemsToGameState(GameState gameState, EntitySystem... systems) {
        Set<EntitySystem> existing = gameStateSystems.get(gameState);
        if (existing == null) {
            existing = new HashSet<EntitySystem>();
            gameStateSystems.put(gameState, existing);
        }
        Set<EntitySystem> currentSystems = gameStateSystems.get(currentState);
        for (EntitySystem s : systems) {
            existing.add(s);
            if (!currentSystems.contains(s)) {
                s.setEnabled(false);
            }
            if (!world.hasSystem(s)) {
                world.addSystem(s);
            }
        }
    }

    void setGameState(GameState gameState) {
        if (currentState != gameState) {
            for (EntitySystem s : gameStateSystems.get(currentState)) {
                s.setEnabled(false);
            }
            this.currentState = gameState;
            for (EntitySystem s : gameStateSystems.get(currentState)) {
                s.setEnabled(true);
            }
        }
    }
}
