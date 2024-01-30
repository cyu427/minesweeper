package minesweeper.gamefsm.cellfsm.cellstates;

import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

// 没被打开模式
public class UnopenedCellState implements ICellState {

    /**
     * 取得方块标志
     * 
     * @return 地图上没被打开的表示
     */
    @Override
    public String getSymbol(CellData cell) {
        return "#";
    }

    @Override
    public void enter(CellData cell) {
    }

    /**
     * 更新状态
     */
    @Override
    public void next(CellData cell) {
        // 游戏信息
        GameData gameData = GameData.getInstance();
        
        // 如果方块有雷 -> 进入有雷模式
        if (gameData.isFlagMode()) {
            cell.setFlaggedCellState();
        } else if (cell.isMine()) {
            cell.setMineCellState();
        } else {
            cell.setEmptyCellState();
            gameData.decreaseEmptyCellNum(); // 在游戏信息里，减去空白方块数
        }
    }

    @Override
    public void previous(CellData cell) {
        
    }
}
