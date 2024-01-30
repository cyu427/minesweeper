package minesweeper.gamefsm.data;

import minesweeper.gamefsm.cellfsm.cellstates.EmptyCellState;
import minesweeper.gamefsm.cellfsm.cellstates.FlaggedCellState;
import minesweeper.gamefsm.cellfsm.cellstates.ICellState;
import minesweeper.gamefsm.cellfsm.cellstates.MineCellState;
import minesweeper.gamefsm.cellfsm.cellstates.UnopenedCellState;

public class CellData {
    private boolean hasMine; // 是否有雷
    private int adjacencyMineNum; // 雷周边数
    private int[] position = new int[2];
    
    private ICellState unopenedCellState;
    private ICellState emptyCellState;
    private ICellState mineCellState;
    private ICellState flaggedCellState;
    private ICellState currentCellState;

    public CellData(int x, int y) {
        this.hasMine = false;
        this.adjacencyMineNum = 0;
        this.position[0] = x;
        this.position[1] = y;

        this.unopenedCellState = new UnopenedCellState();
        this.emptyCellState = new EmptyCellState();
        this.mineCellState = new MineCellState();
        this.flaggedCellState = new FlaggedCellState();
        this.currentCellState = unopenedCellState;
    }

    /**
     * 取得方块是否有雷
     * 
     * @return 有没有雷
     */
    public boolean isMine() {
        return hasMine;
    }

    /**
     * 方块里设置雷
     */
    public void setMine() {
        this.hasMine = true;
    }

    /**
     * 取得周边雷数
     * 
     * @return 周边雷数
     */
    public int getAdjacencyMineNum() {
        return adjacencyMineNum;
    }

    /**
     * 设置周边雷数
     * 
     * @param adjacencyMineNum 周边雷数
     */
    public void setAdjacencyMineNum(int adjacencyMineNum) {
        this.adjacencyMineNum = adjacencyMineNum;
    }

    public int getCellPositionX() {
        return position[0];
    }

    public int getCellPositionY() {
        return position[1];
    }

// *****************************************************

    public String getCellSymbol() {
        return currentCellState.getSymbol(this);
    }

    public void enterState() {
        currentCellState.enter(this);
    }

    public void nextState() {
        currentCellState.next(this);
    }

    public void previousState() {
        currentCellState.previous(this);
    }

    public void setUnopenedCellState() {
        GameData gameData = GameData.getInstance();
        this.currentCellState = unopenedCellState;
        gameData.setCell(getCellPositionX(), getCellPositionY() , this);

    }

    public void setEmptyCellState() {
        GameData gameData = GameData.getInstance();
        this.currentCellState = emptyCellState;
        gameData.setCell(getCellPositionX(), getCellPositionY() , this);
    }

    public void setMineCellState() {
        GameData gameData = GameData.getInstance();
        this.currentCellState = mineCellState;
        gameData.setCell(getCellPositionX(), getCellPositionY() , this);
    }

    public void setFlaggedCellState() {
        GameData gameData = GameData.getInstance();
        this.currentCellState = flaggedCellState;
        gameData.setCell(getCellPositionX(), getCellPositionY() , this);
    }

    public boolean isCellEmptyState() {
        return currentCellState instanceof EmptyCellState;
    }

    public boolean isCellUnopenedState() {
        return currentCellState instanceof UnopenedCellState;
    }

    public boolean isCellGameOverState() {
        return currentCellState instanceof MineCellState;
    }
}
