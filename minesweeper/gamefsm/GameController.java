package minesweeper.gamefsm;

import minesweeper.gamefsm.gamestates.FinishedGameState;
import minesweeper.gamefsm.gamestates.IGameState;
import minesweeper.gamefsm.gamestates.LoseGameState;
import minesweeper.gamefsm.gamestates.MapGameState;
import minesweeper.gamefsm.gamestates.PlayingGameState;
import minesweeper.gamefsm.gamestates.WinGameState;

/* 游戏状态转换 */
public class GameController {

    //存贮所有状态
    IGameState mapGameState = new MapGameState();
    IGameState playingGameState = new PlayingGameState();
    IGameState winGameState = new WinGameState();
    IGameState loseGameState = new LoseGameState();
    IGameState finishedGameState = new FinishedGameState();

    //当前状态
    private IGameState currentGameState = mapGameState;

    /* 设置游戏状态 */
    public void setMapGameState() {
        this.currentGameState = mapGameState;
    }

    public void setPlayingGameState() {
        this.currentGameState = playingGameState;
    }

    public void setWinGameState() {
        this.currentGameState = winGameState;
    }

    public void setLoseGameState() {
        this.currentGameState = loseGameState;
    }

    public void setFinishedGameState() {
        this.currentGameState = finishedGameState;
    }

    /* 进入状态 */
    public void enterState() {
        currentGameState.enter();
    }

    /* 更新状态 */
    public void nextState() {
        currentGameState.next(this);
    }

    public boolean isGameFinished() {
        return currentGameState instanceof FinishedGameState;
    }
}
