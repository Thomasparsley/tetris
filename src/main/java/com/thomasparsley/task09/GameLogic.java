package com.thomasparsley.task09;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GameLogic extends Pane {

    public static final int GRID_BLOCK_SIZE = 25;
    public static final int GRID_COLUMNS = 12;
    public static final int GRID_ROWS = 24;
    public static final int GRID_GAP = 2;

    public static final int WIDTH = GRID_COLUMNS * (GRID_BLOCK_SIZE + GRID_GAP);
    public static final int HEIGHT = GRID_ROWS * (GRID_BLOCK_SIZE + GRID_GAP);

    private Block[][] grid;
    private BlockGroup activeBlockGroup;
    private List<BlockGroup> blockGroups = new ArrayList<>();

    private int score = 0;

    public GameLogic() {
        newActiveBlockGroup();

        setMinWidth(WIDTH);
        setMinHeight(HEIGHT);

        updateGrid();
        updateMesh();

        Timer fallingTimer = new Timer();
        FallingTask fallingTask = new FallingTask();

        fallingTimer.schedule(fallingTask, 0, 500);
    }

    public int getScore() {
        return score;
    }

    public BlockGroup getActiveBlockGroup() {
        return activeBlockGroup;
    }

    private void newActiveBlockGroup() {
        activeBlockGroup = new BlockGroup(BlockGroupType.getRandomType());
        blockGroups.add(activeBlockGroup);
    }

    private void clearGrid() {
        grid = new Block[GRID_ROWS][GRID_COLUMNS];
    }

    public void updateGrid() {
        clearGrid();
        fillGrid();
    }

    private void fillGrid() {
        for (var group : blockGroups) {
            for (var block : group.getBlocks()) {
                grid[block.getY()][block.getX()] = block;
            }
        }
    }

    private void clearAllMesh() {
        this.getChildren().clear();
    }

    private void addAllMesh() {
        for (var row : grid) {
            for (var block : row) {
                if (block == null)
                    continue;

                this.getChildren().add(block.getMesh());
            }
        }
    }

    public void updateMesh() {
        clearAllMesh();
        addAllMesh();
    }

    private boolean collisionsCheck() {
        for (var block : activeBlockGroup.getBlocks()) {
            if (block.getY() + 1 >= GRID_ROWS) {
                return true;
            }

            var blockBelow = grid[block.getY() + 1][block.getX()];
            if (blockBelow != null && block.getGroup() != blockBelow.getGroup()) {
                return true;
            }
        }

        return false;
    }

    private List<Integer> fullRowCheck() {
        var fullRows = new ArrayList<Integer>();

        for (var row = 0; row < grid.length; row++) {
            boolean fullRow = true;

            for (var block : grid[row]) {
                if (block == null) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                for (var block : grid[row]) {
                    if (block.getGroup() == activeBlockGroup) {
                        fullRow = false;
                    }
                }
            }

            if (fullRow) {
                fullRows.add(row);
            }
        }

        return fullRows;
    }

    private void removeFullRow(int x) {
        for (var y = 0; y < grid[x].length; y++) {
            var block = grid[x][y];

            if (block == null) {
                continue;
            }

            var group = block.getGroup();
            group.getBlocks().remove(block);
            grid[x][y] = null;
        }
    }

    private void updateAffterFullRowRemove(int rowIndex) {
        for (var x = rowIndex; x > 0; x--) {
            for (var y = 0; y < grid[x].length; y++) {
                var block = grid[x][y];

                if (block == null) {
                    continue;
                }

                if (block.getGroup() == activeBlockGroup) {
                    continue;
                }

                block.moveDown();
            }
        }
    }

    private void oneIteration() {
        updateGrid();
        var collision = collisionsCheck();

        if (collision) {
            newActiveBlockGroup();
        }

        var fullRows = fullRowCheck();
        if (fullRows.size() > 0) {
            for (var row : fullRows) {
                removeFullRow(row);
                updateAffterFullRowRemove(row);
            }

            score += fullRows.size() * 100;
        }

        updateGrid();
        updateMesh();
        Tetris.updateUI();
    }

    private void moveDown() {
        if (!activeBlockGroup.canMoveDown(grid)) {
            return;
        }

        score++;

        activeBlockGroup.moveDown();
    }

    private void moveLeft() {
        if (!activeBlockGroup.canMoveLeft(grid)) {
            return;
        }

        activeBlockGroup.moveLeft();
    }

    private void moveRight() {
        if (!activeBlockGroup.canMoveRight(grid)) {
            return;
        }

        activeBlockGroup.moveRight();
    }

    private void rotate() {
        if (!activeBlockGroup.canRotate(grid)) {
            return;
        }

        activeBlockGroup.rotate();
    }

    private class FallingTask extends TimerTask {
        public void run() {
            Platform.runLater(new FallingHandler());
        }
    }

    private class FallingHandler implements Runnable {
        public void run() {
            moveDown();

            oneIteration();
        }
    }

    public KeyHandler getKeyHandler() {
        return new KeyHandler();
    }

    public class KeyHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP:
                    rotate();
                    break;
                case DOWN:
                    moveDown();
                    break;
                case LEFT:
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
                default:
                    break;
            }

            oneIteration();
        }
    }
}
