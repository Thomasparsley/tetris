package com.thomasparsley.task09;

import java.util.ArrayList;
import java.util.List;

public class BlockGroup {

    private int x = 0;
    private int y = 0;

    private BlockGroupType type;
    private List<Block> blocks = new ArrayList<>();

    public BlockGroup(BlockGroupType type) {
        this.type = type;
        this.x = ((GameLogic.GRID_COLUMNS / 2) - (type.getWidth() / 2));

        blocks = makeBlocksFromType(type);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BlockGroupType getType() {
        return type;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    private List<Block> makeBlocksFromType(BlockGroupType type) {
        List<Block> blocks = new ArrayList<>();
        var pattern = type.getPattern();

        for (int y = 0; y < pattern.length; y++) {
            for (int x = 0; x < pattern[y].length; x++) {
                if (!pattern[y][x])
                    continue;

                var block = new Block(type.getColor(), x + this.x, y + this.y);
                block.setGroup(this);
                blocks.add(block);
            }
        }

        return blocks;
    }

    public boolean canMoveDown(Block[][] grid) {
        for (var block : blocks) {
            if (block.getY() + 1 >= GameLogic.GRID_ROWS) {
                return false;
            }

            var blockBelow = grid[block.getY() + 1][block.getX()];
            if (blockBelow != null && block.getGroup() != blockBelow.getGroup()) {
                return false;
            }
        }

        return true;
    }

    public void moveDown() {
        for (var block : blocks) {
            block.moveDown();
        }

        y++;
    }

    public boolean canMoveLeft(Block[][] grid) {
        for (var block : blocks) {
            if (block.getX() - 1 < 0) {
                return false;
            }

            var blockLeft = grid[block.getY()][block.getX() - 1];
            if (blockLeft != null && block.getGroup() != blockLeft.getGroup()) {
                return false;
            }
        }

        return true;
    }

    public void moveLeft() {
        for (var block : blocks) {
            block.moveLeft();
        }

        x--;
    }

    public boolean canMoveRight(Block[][] grid) {
        for (var block : blocks) {
            if (block.getX() + 1 >= GameLogic.GRID_COLUMNS) {
                return false;
            }

            var blockRight = grid[block.getY()][block.getX() + 1];
            if (blockRight != null && block.getGroup() != blockRight.getGroup()) {
                return false;
            }
        }

        return true;
    }

    public void moveRight() {
        for (var block : blocks) {
            block.moveRight();
        }

        x++;
    }

    public boolean canRotate(Block[][] grid) {
        var nextType = type.getNextType();
        var nextBlocks = makeBlocksFromType(nextType);

        for (var block : nextBlocks) {
            var x = block.getX();
            var y = block.getY();

            if (x < 0 || x >= GameLogic.GRID_COLUMNS) {
                return false;
            }

            if (y < 0 || y >= GameLogic.GRID_ROWS) {
                return false;
            }

            var blockPeek = grid[y][x];
            if (blockPeek != null && block.getGroup() != blockPeek.getGroup()) {
                return false;
            }
        }

        return true;
    }

    public void rotate() {
        type = type.getNextType();
        blocks = makeBlocksFromType(type);
    }
}
