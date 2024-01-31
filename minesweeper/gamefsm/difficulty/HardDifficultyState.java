package minesweeper.gamefsm.difficulty;

import minesweeper.gamefsm.data.GameData;

public class HardDifficultyState implements IDifficultyState {
    private int row;
    private int col;
    private int mineNum;

    public HardDifficultyState() {
        this.row = 16;
        this.col = 30;
        this.mineNum = 99;
    }

    @Override
    public void setMapRow() {
        GameData gameData = GameData.getInstance();
        gameData.setRow(row);
    }

    @Override
    public void setMapCol() {
        GameData gameData = GameData.getInstance();
        gameData.setCol(col);
    }

    @Override
    public int getMineNum() {
        return mineNum;
    }
    
}
