package minesweeper.gamefsm.cellfsm.cellstates;

import minesweeper.gamefsm.data.CellData;

public interface ICellState {
    String getSymbol(CellData cell); // 拿方块标志
    void enter(CellData cell); // 进入方块模式
    void next(CellData cell); // 更新方块模式 
    void previous(CellData cell); 
}
