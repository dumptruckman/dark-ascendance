package com.dumptruckman;

import recs.EntityWorld;

class GameStateManager {

    GameState currentState = GameState.MAIN_MENU;

    void setGameState(GameState gameState) {
        if (currentState != gameState) {
            this.currentState = gameState;
        }
    }

    void selectSystemsForState(EntityWorld entityWorld) {
        switch (currentState) {
            case MAIN_MENU:
                mainMenu(entityWorld);
                break;
            default:
                break;
        }
    }

    private void mainMenu(EntityWorld entityWorld) {

    }
}
