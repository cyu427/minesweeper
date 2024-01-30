package minesweeper.gamefsm.data;

import java.util.ArrayList;

/* 游戏信息 */
public class GameData {
    private static GameData INSTANCE;
    private int row; // 地图总x数
    private int col; // 地图总y数
    private CellData[][] cellMap; // 地图
    private int emptyCellNum; // 空白方块总数量
    private boolean isFlagMode;
    private ArrayList<CellData> cellOrder;

    private GameData() {
        this.row = 6;
        this.col = 6;
        this.cellMap = new CellData[row][col];
        this.isFlagMode = false;;
        this.cellOrder = new ArrayList<CellData>();
    }

    /**
     * 单例
     * 
     * @return 游戏信息
     */
    public static GameData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameData();
        }
        return INSTANCE;
    }

    /**
     * 取得地图y数
     * 
     * @return int row --> 地图y数
     */
    public int getRow() {
        return row;
    }

    /**
     * 取得地图x数
     * 
     * @return int col --> 地图x数
     */
    public int getCol() {
        return col;
    }

    /**
     * 从地图取得方块
     * 
     * @param x 方块x坐标
     * @param y 方块y坐标
     * @return 方块
     */
    public CellData getCell(int x, int y) {
        return cellMap[y][x];
    }
    
    /**
     * 在指定坐标设置方块
     * 
     * @param x 方块x坐标
     * @param y 方块y坐标
     * @param cell 方块
     */
    public void setCell(int x, int y, CellData cell) {
        cellMap[y][x] = cell;
    }

    /**
     * 取得空白方块数
     * 
     * @return int emptyCellNum --> 空白方块数
     */
    public int getEmptyCellNum() {
        return emptyCellNum;
    }

    /**
     * 设置空白方块总数
     * 
     * @param mineNum 雷数
     */
    public void setEmptyCellNum(int mineNum) {
        emptyCellNum = row*col - mineNum;
    }

    /** 
     * 减去方块空白数
     */
    public void decreaseEmptyCellNum() {
        emptyCellNum--;
    }

    public void undoEmptyCellNum() {
        emptyCellNum++;
    }

    public boolean isFlagMode() {
        return isFlagMode;
    }

    public void setIsFlagMode() {
        if (isFlagMode) {
            this.isFlagMode = false;
        } else {
            this.isFlagMode = true;
        }
    }

    public void addToCellCommand(CellData cell) {
        cellOrder.add(cell);
    }

    public CellData getCurrentCell() {
        int lastIndex = cellOrder.size() - 1;

        return cellOrder.get(lastIndex);
    }

    public CellData getLastCell() {
        int lastIndex = cellOrder.size() - 1;
        CellData lastCell = cellOrder.get(lastIndex);
        cellOrder.remove(lastIndex);

        return lastCell;      
    }

    public boolean isCellCommandEmpty() {
        return cellOrder.isEmpty();
    }
}
