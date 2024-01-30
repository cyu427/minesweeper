package minesweeper;

import minesweeper.gamefsm.GameController;

public class App {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        boolean isGameFinished;

        do {
            isGameFinished = !gameController.isGameFinished();
            gameController.enterState(); // 进入状态
            gameController.nextState(); // 更新状态
                       
        } while (isGameFinished); // 如果没赢或输 --> 游戏继续循环
    }

}
