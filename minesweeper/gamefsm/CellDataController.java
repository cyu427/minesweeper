package minesweeper.gamefsm;

import minesweeper.gamefsm.data.CellData;
import minesweeper.gamefsm.data.GameData;

public class CellDataController {
    private GameData gameData = GameData.getInstance();
    private CellData cell;

    public CellDataController() {
    }

    public void setControllerCell(int x, int y) {
        this.cell = gameData.getCell(x, y);
    }

    public void setControllerCell(CellData cell) {
        this.cell = cell;
    }

    public void intialiseCell(int x, int y) {
        cell = new CellData(x, y);
        gameData.setCell(x, y, cell);
    }

    public void setCellMine() {
        cell.setMine();
        gameData.setCell(cell.getCellPositionX(), cell.getCellPositionY(), cell);
    }

    public boolean isCellMine(int x, int y) {
        CellData cell;

        cell = gameData.getCell(x, y);

        return cell.isMine();
    }

    public void setCellAdjacencyMineNum() {
        CellData adjacentCell;
        int adjacencyMine = 0;
        int xPos;
        int yPos;

        for (int yOff = -1; yOff <= 1; yOff++) {
            for (int xOff = -1; xOff <= 1; xOff++) {
                if (isValidCell(cell.getCellPositionX(), cell.getCellPositionY(), xOff, yOff)) {
                    xPos = cell.getCellPositionX() + xOff;
                    yPos = cell.getCellPositionY() + yOff;
                    adjacentCell = gameData.getCell(xPos, yPos);

                    if (adjacentCell.isMine()) {
                        adjacencyMine++;
                    }
                }
            }
        }

        cell.setAdjacencyMineNum(adjacencyMine);
        gameData.setCell(cell.getCellPositionX(), cell.getCellPositionY(), cell);
    }

    public boolean isValidCell(int x, int y, int xOff, int yOff) {
        GameData gameData = GameData.getInstance();
        int xPos;
        int yPos;

        xPos = x + xOff;
        yPos = y + yOff;

        boolean noOffset = !(xOff == 0 && yOff == 0);
        boolean xInBoundary = (xPos >= 0 && xPos < gameData.getCol());
        boolean yInBoundary = (yPos >= 0 && yPos < gameData.getRow());

        return (noOffset && xInBoundary && yInBoundary);
    }

}
