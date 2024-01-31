package minesweeper.gamefsm.difficulty;

public interface IDifficultyState {
    void setMapRow();
    void setMapCol();
    int getMineNum();
}

