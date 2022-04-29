package com.thomasparsley.task09;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {

    public static final int SIZE = GameLogic.GRID_BLOCK_SIZE;
    public static final int MOVE = SIZE + GameLogic.GRID_GAP;

    private int x;
    private int y;

    private Rectangle mesh;
    private BlockGroup group;

    public Block(Color color, int x, int y) {
        mesh = new Rectangle();
        mesh.setWidth(SIZE);
        mesh.setHeight(SIZE);
        mesh.setX(x * MOVE);
        mesh.setY(y * MOVE);
        mesh.setFill(color);

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getMesh() {
        return mesh;
    }

    public BlockGroup getGroup() {
        return group;
    }

    public void setGroup(BlockGroup group) {
        this.group = group;
    }

    private void move(int newX, int newY) {
        x += newX;
        y += newY;

        mesh.setX(x * MOVE);
        mesh.setY(y * MOVE);
    }

    public void moveDown() {
        move(0, 1);
    }

    public void moveLeft() {
        move(-1, 0);
    }

    public void moveRight() {
        move(1, 0);
    }
}
