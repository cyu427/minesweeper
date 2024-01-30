package minesweeper.gamefsm.gamestates;

import java.util.Scanner;

import minesweeper.gamefsm.GameController;
import minesweeper.gamefsm.data.GameData;

/* 游戏输状态 */
public class LoseGameState implements IGameState {

    @Override
    public void enter() {
        System.out.println("GAME OVER\n");
    }

    @Override
    public void next(GameController game) {
        GameData gameData = GameData.getInstance();
        String userOption;

        Scanner inputReader = new Scanner(System.in);

        do {
            lostGameView();
            userOption = inputReader.nextLine();
            userOption = userOption.trim();

            switch (userOption) {
                case "1":
                    game.setPlayingGameState();
                    gameData.getLastCell().previousState();
                    break;
                case "2":
                    game.setFinishedGameState();
                    break;
                default:
                    System.out.println("请输入 1 或 2");
                    break;
            }

        } while (isInputValid(userOption));

    }

    private void lostGameView() {
        System.out.println("返回到上一个动作吗？");
        System.out.println("1. 返回");
        System.out.println("2. 不返回");
    }

    private boolean isInputValid(String input) {
        boolean isUndoInput = input.equals("1");
        boolean isNotUndoInput = input.equals("2");
        return (!isUndoInput && !isNotUndoInput);
    }


    
}
