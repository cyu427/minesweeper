package minesweeper.gamefsm.gamestates;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.gamefsm.GameController;
import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

/* 游玩状态（用户玩的状态）*/
public class PlayingGameState implements IGameState {

    /* 进入状态 */
    @Override
    public void enter() {
        GameData gameData = GameData.getInstance();
        Scanner inputReader = new Scanner(System.in);
        String userOption;
        do {
            printOptions();
            printMap();
            userOption = inputReader.nextLine();
            userOption = userOption.trim();

            switch (userOption) {
                case "1":
                    chooseCell();
                    break;
                case "2":
                    gameData.setIsFlagMode();
                    break;
                case "3":
                    undo();
                    break;
                default:
                    System.out.println("请输入 1 或 2 或 3");
                    break;
            }
        } while (isUserOptionValid(userOption));
    }

    /* 更新状态 */
    @Override
    public void next(GameController game) {
        GameData gameData = GameData.getInstance(); // 游戏信息
        CellData currentCell = gameData.getCurrentCell();

        // 如果游戏输 --> 更新到游戏输模式
        if (currentCell.isCellGameOverState()) {
            printMap();
            game.setLoseGameState();
        // 如果游戏赢 --> 跟信道游戏赢模式
        } else if (gameData.getEmptyCellNum() == 0) {
            printMap();
            game.setWinGameState();
        // 如果在游玩 --> 更新到现在的游玩模式
        } else {
            game.setPlayingGameState();
        }
    }


    private void printOptions() {
        GameData gameData = GameData.getInstance();
        String mode;
        if (gameData.isFlagMode()) {
            mode = "是"; 
        } else {
            mode = "否"; 
        }

        System.out.println("\n下旗模式: " + mode);
        System.out.println("1. 请选择方块");
        System.out.println("2. 下旗模式开关");
        System.out.println("3. 返回上一个动作\n");
    }

    /**
     * 印出地图
     *  
     * @param gameData 游戏信息
     */
    /*private void printMap() {
        GameData gameData = GameData.getInstance();
        CellData cell;

        for (int y = 0; y < gameData.getRow(); y++) {
            for (int x = 0; x < gameData.getCol(); x++) {
                cell = gameData.getCell(x, y);
                System.out.print(cell.getCellSymbol());

                if (x == gameData.getCol()-1) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }*/

    private void printMap() {
        GameData gameData = GameData.getInstance();
        CellData cell;

        for (int y = 0; y <= gameData.getRow(); y++) {
            for (int x = 0; x <= gameData.getCol(); x++) {
                
                if (x == 0 && y == 0) {
                    System.out.print(" ");
                } else if (y == 0) {
                    System.out.print(x-1);
                } else if (x == 0) {
                    System.out.print(y-1);
                } else {
                    cell = gameData.getCell(x-1, y-1);
                    System.out.print(cell.getCellSymbol());
                }

                if (x == gameData.getCol()) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    private void chooseCell() {
        GameData gameData = GameData.getInstance(); // 游戏信息
        int cellCoord[]; // 方块坐标
        CellData cell;
        
        // 取得用户方块坐标
        cellCoord = getCellCoord();
        int x = cellCoord[0];
        int y = cellCoord[1];
        cell = gameData.getCell(x, y);

        // 启动方块状态机
        cell.enterState();
        cell.nextState();

        if (!cell.isCellUnopenedState()) {
            gameData.addToCellCommand(cell);
        }
    }

        /**
     * 从用户取得方块坐标
     * 
     * @return int cellCoord --> 方块坐标
     */
    private int[] getCellCoord() {

        int currentX;
        int currentY;
        int[] cellCoord = new int[2];

        currentX = getCoordUserInput("X");
        currentY = getCoordUserInput("Y");

        cellCoord[0] = currentX;
        cellCoord[1] = currentY;

        return cellCoord;
    }

    /**
     * 从用户取得方块坐标
     * 
     * @param coordType 坐标类型，x或y
     * @return int coord --> 用户坐标
    */
    private int getCoordUserInput(String coordType) {
        Scanner inputReader = new Scanner(System.in);
        String input;
        int coord;
        do {
            System.out.print("请输入 " +  coordType + ": ");
            input = inputReader.nextLine().trim();
        } while (!isCellCoordValid(input)); // 查看输入是否在1-6之间

        coord = Integer.valueOf(input)-1;

        return coord;
    }

    /**
     * 查看输入是否在1-6之间 
     * 
     * @param input 用户输入
     * @return 是否输入在1-6之间
     */
    private boolean isCellCoordValid(String input) {
        String regex = "^[1-6]$";
        Pattern inputPattern = Pattern.compile(regex);
        Matcher inputMatcher = inputPattern.matcher(input);

        if (inputMatcher.matches()) {
            return true;
        } else {
            System.out.println("请输入1-6之间的数字");
            return false;
        }
    }

    private void undo() {
        GameData gameData = GameData.getInstance();
        if (!gameData.isCellCommandEmpty()) {
            gameData.getLastCell().previousState();
            System.out.println("\n已返回");
        } else {
            System.out.println("\n无法返回");
        }
    }

    private boolean isUserOptionValid(String input) {
        boolean isChooseCell = input.equals("1");
        return (!isChooseCell);
    }

}
