package minesweeper.gamefsm.gamestates;

import minesweeper.gamefsm.GameController;

/* 游戏赢状态 */
public class WinGameState implements IGameState {

    @Override
    public void enter() {
        System.out.println("恭喜，你赢了");
    }

    @Override
    public void next(GameController game) {
        game.setFinishedGameState();
    }
    
}
