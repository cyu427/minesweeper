package minesweeper.gamefsm.gamestates;

import minesweeper.gamefsm.GameController;

public class FinishedGameState implements IGameState {

    @Override
    public void enter() {
        System.out.println("游戏结束");
    }

    @Override
    public void next(GameController game) {
    }
    
}
