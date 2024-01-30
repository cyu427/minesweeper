package minesweeper.gamefsm.cellfsm.cellstates;

import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

public class MineCellState implements ICellState {

    @Override
    public String getSymbol(CellData cell) {
        return "X";
    }

    @Override
    public void enter(CellData cell) {

    }

    @Override
    public void next(CellData cell) {
    }

    @Override
    public void previous(CellData cell) {
        GameData gameData = GameData.getInstance();
        gameData.undoEmptyCellNum();
        cell.setUnopenedCellState();
    }
    
}
