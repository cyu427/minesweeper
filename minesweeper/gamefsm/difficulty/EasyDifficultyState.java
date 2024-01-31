package minesweeper.gamefsm.difficulty;

import minesweeper.gamefsm.data.GameData;

public class EasyDifficultyState implements IDifficultyState  {
    private int row;
    private int col;
    private int mineNum;

    public EasyDifficultyState() {
        this.row = 9;
        this.col = 9;
        this.mineNum = 10;
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
