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

// 地图状态 （设置游戏地图和雷）
public class MapGameState implements IGameState {

    /* 进入状态 */
    @Override
    public void enter() {
        setMapSize();
        initialiseMap(); // 设置没雷的地图
        setAllMine(getMineNum()); //地图里设雷
        setAllAdjacentIndicator(); // 设置方块周边雷数
    }

    /* 更新状态 */
    @Override
    public void next(GameController gameController) {
        gameController.setPlayingGameState(); // 更新到游玩状态
    }

    private void setMapSize() {
        GameData gameData = GameData.getInstance();
        int mapSizeX;
        int mapSizeY;

        mapSizeX = getMapSize("X");
        mapSizeY = getMapSize("Y");

        gameData.setRow(mapSizeY);
        gameData.setCol(mapSizeX);
        gameData.setCellMap();
    }

    private int getMapSize(String coordType) {
        Scanner inputReader = new Scanner(System.in);
        String input;
        int coord;
        do {
            System.out.print("请输入地图 " +  coordType + ": ");
            input = inputReader.nextLine().trim();
        } while (!isMapSizeValid(input)); // 查看输入是否在1-6之间

        coord = Integer.valueOf(input);

        return coord;
    }

    private boolean isMapSizeValid(String input) {
        String regex = "^(?:[6-9]|1[0-9]|2[0-9]|30)$";
        Pattern inputPattern = Pattern.compile(regex);
        Matcher inputMatcher = inputPattern.matcher(input);

        if (inputMatcher.matches()) {
            return true;
        } else {
            System.out.println("请输入6-30之间的数字");
            return false;
        }
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
    private void setAllMine(int mineNum) {
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
     * 从用户取得雷数
     * 
     * @return int mineNum -> 雷数
     */
    private int getMineNum() {
        Scanner inputReader = new Scanner(System.in);
        String mineNum; // 雷数

        do {
            System.out.print("请输入雷数: ");
            mineNum = inputReader.nextLine().trim();
        } while (!isInputValid(mineNum));  // 查看输入是否1-35之间

        return Integer.valueOf(mineNum);
    }

    /**
     * 查看输入是否1-35之间
     * 
     * @param input 用户输入
     * @return 是否输入在1-35之间
     */
    private boolean isInputValid(String input) {
        GameData gameData = GameData.getInstance();
        String regex = "^(?:[1-9]|[1-2][0-9]|3[0-5])$";
        Pattern inputPattern = Pattern.compile(regex);
        Matcher inputMatcher = inputPattern.matcher(input);
        int totalMapSize = gameData.getCol() * gameData.getRow();

        if (inputMatcher.matches()) {
            return true;
        } else {
            System.out.println("请输入1-" + totalMapSize + "之间的雷数");
            return false;
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
