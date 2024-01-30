package minesweeper.gamefsm.gamestates;

import minesweeper.gamefsm.GameController;

public interface IGameState {
    void enter(); // 进入模式
    void next(GameController game); // 更新模式
}
