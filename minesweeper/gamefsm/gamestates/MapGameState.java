package minesweeper.gamefsm.gamestates;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import minesweeper.gamefsm.CellDataController;
import minesweeper.gamefsm.GameController;
import minesweeper.gamefsm.data.GameData;
import minesweeper.gamefsm.difficulty.EasyDifficultyState;
import minesweeper.gamefsm.difficulty.HardDifficultyState;
import minesweeper.gamefsm.difficulty.IDifficultyState;
import minesweeper.gamefsm.difficulty.MediumDifficultyState;

// 地图状态 （设置游戏地图和雷）
public class MapGameState implements IGameState {
    private int mineNum;

    /* 进入状态 */
    @Override
    public void enter() {
        chooseDifficulty();
        initialiseMap(); // 设置没雷的地图
        setAllMine(); //地图里设雷
        setAllAdjacentIndicator(); // 设置方块周边雷数
    }

    /* 更新状态 */
    @Override
    public void next(GameController gameController) {
        gameController.setPlayingGameState(); // 更新到游玩状态
    }

    private void chooseDifficulty() {
        Scanner inputReader = new Scanner(System.in);
        String userOption;
        IDifficultyState difficulty = null;

        GameData gameData = GameData.getInstance();

        do {
            chooseDifficultyView();
            userOption = inputReader.nextLine().trim();

            switch (userOption) {
                case "1":
                    difficulty = new EasyDifficultyState();
                    break;
                case "2":
                    difficulty = new MediumDifficultyState();
                    break;
                case "3":
                    difficulty = new HardDifficultyState();
                    break;
                default:
                    System.out.println("请输入 1 或 2 或 3");
                    break;
            }

        } while (!isDifficultyValid(userOption));

        difficulty.setMapCol();
        difficulty.setMapRow();
        mineNum = difficulty.getMineNum();
        gameData.setCellMap();
    }

    private void chooseDifficultyView() {
        System.out.println("请选择难度：\n");
        System.out.println("1. 简单: 9x9 10个雷");
        System.out.println("2. 中级: 16x16 40个雷");
        System.out.println("3. 高级: 30x16 99个雷\n");
    }

    private boolean isDifficultyValid(String input) {
        boolean easyInput = input.equals("1");
        boolean mediumInput = input.equals("2");
        boolean hardInput = input.equals("3");

        return (easyInput || mediumInput || hardInput);
    }

    /** 
     * 设置空白地图
     * 
     * @param gameData 游戏信息
     * @return 没有 
     */
    private void initialiseMap() {
        GameData gameData = GameData.getInstance();
        CellDataController cellModelController = new CellDataController();

        for (int y = 0; y < gameData.getRow(); y++) {
            for (int x = 0; x < gameData.getCol(); x++) {
                cellModelController.intialiseCell(x, y);
            }
        }   
    }

    /**
     * 设置所有雷 
     * 
     * @param mineNum 雷数
     * @param gameData 有信息
     * @return 没有
     */
    private void setAllMine() {
        GameData gameData = GameData.getInstance();
        CellDataController cellModelController = new CellDataController();
        int x;
        int y;
        int totalMapSize;

        totalMapSize = gameData.getRow() * gameData.getCol();
        
        // 随机得到雷的位置
        Set<Integer> mineLocation = new Random().ints(1, totalMapSize)
        .distinct()
        .limit(mineNum)
        .boxed()
        .collect(Collectors.toSet());

        // 计算出雷的坐标
        for (int mine : mineLocation) {
            y = ((int) (Math.ceil((double)mine/gameData.getCol()))); // 不算零的Y坐标
            x = mine - (y - 1)*gameData.getCol(); // 不算零的X坐标

            x--; // 算上零的X坐标
            y--; // 算上零的Y坐标

            cellModelController.setControllerCell(x, y);
            cellModelController.setCellMine();

            gameData.setEmptyCellNum(mineNum); // 计算所有空白方块数量
        }
    }

    /**
     * 计算所有方块周边雷数 
     * 
     * @param gameData 游戏信息
     * @reutrn 没有
    */
    public void setAllAdjacentIndicator() {
        GameData gameData = GameData.getInstance();
        CellDataController cellModelController = new CellDataController();

        for (int y = 0; y < gameData.getRow(); y++) {
            for (int x = 0; x < gameData.getCol(); x++) {
                cellModelController.setControllerCell(x, y);
                cellModelController.setCellAdjacencyMineNum(); // 计算一个方块的周边雷数
            }
        }
    }
}
