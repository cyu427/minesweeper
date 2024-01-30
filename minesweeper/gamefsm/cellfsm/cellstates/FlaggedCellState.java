package minesweeper.gamefsm.cellfsm.cellstates;

import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

public class FlaggedCellState implements ICellState {

    @Override
    public String getSymbol(CellData cell) {
        return "F";
    }

    @Override
    public void enter(CellData cell) {
        GameData gameData = GameData.getInstance();
        if (!gameData.isFlagMode()) {
            System.out.println("\n方块有旗");
        }
    }

    @Override
    public void next(CellData cell) {
        GameData gameData = GameData.getInstance();
        if (gameData.isFlagMode()) {
            cell.setUnopenedCellState();
        }
    }

    @Override
    public void previous(CellData cell) {
        cell.setUnopenedCellState();
    }
    
}
