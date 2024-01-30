package minesweeper.gamefsm.cellfsm.cellstates;

import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

/* 方块空白模式 */
public class EmptyCellState implements ICellState {

    /**
     * 取得周边雷数有打开后的标志
     * 
     * @return 周边雷数
     */
    @Override
    public String getSymbol(CellData cell) {
        return String.valueOf(cell.getAdjacencyMineNum());
    }

    /**
     * 取得进入模式
     * 
     * @param cell 现在方块
     */
    @Override
    public void enter(CellData cell) {

        // 如果被点开
        if (cell.isCellEmptyState()) {
            System.out.println("\n已经点开此处");
        }
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
