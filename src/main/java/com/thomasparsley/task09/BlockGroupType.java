package com.thomasparsley.task09;

import java.util.Collections;
import java.util.Random;

import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

public enum BlockGroupType {
    S,
    S_TRANSPOSED,
    Z,
    Z_TRANSPOSED,
    L,
    L_TRANSPOSED,
    J,
    J_TRANSPOSED,
    SQUARE,
    I,
    I_TRANSPOSED,
    T,
    T_INVERTED,
    T_TRANSPOSED,
    T_TRANSPOSED_INVERTED;

    private static final List<BlockGroupType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BlockGroupType getRandomType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public int getWidth() {
        return switch (this) {
            case S, Z -> 3;
            case S_TRANSPOSED, Z_TRANSPOSED -> 2;
            case L, J -> 2;
            case L_TRANSPOSED, J_TRANSPOSED -> 3;
            case SQUARE -> 2;
            case I -> 1;
            case I_TRANSPOSED -> 4;
            case T, T_INVERTED -> 3;
            case T_TRANSPOSED, T_TRANSPOSED_INVERTED -> 2;
            default -> 0;
        };
    }

    public int getHeight() {
        return switch (this) {
            case S, Z -> 2;
            case S_TRANSPOSED, Z_TRANSPOSED -> 3;
            case L, J -> 3;
            case L_TRANSPOSED, J_TRANSPOSED -> 2;
            case SQUARE -> getWidth();
            case I -> 4;
            case I_TRANSPOSED -> 1;
            case T, T_INVERTED -> 2;
            case T_TRANSPOSED, T_TRANSPOSED_INVERTED -> 3;
            default -> 0;
        };
    }

    public Color getColor() {
        return switch (this) {
            case S, S_TRANSPOSED -> Color.GREEN;
            case Z, Z_TRANSPOSED -> Color.RED;
            case L, L_TRANSPOSED -> Color.ORANGERED;
            case J, J_TRANSPOSED -> Color.DARKBLUE;
            case SQUARE -> Color.YELLOWGREEN;
            case I, I_TRANSPOSED -> Color.TEAL;
            case T, T_INVERTED, T_TRANSPOSED, T_TRANSPOSED_INVERTED -> Color.VIOLET;
        };
    }

    public boolean[][] getPattern() {
        return switch (this) {
            case S -> getPatternS();
            case S_TRANSPOSED -> getPatternSTransposed();
            case Z -> getPatternZ();
            case Z_TRANSPOSED -> getPatternZTransposed();
            case L -> getPatternL();
            case L_TRANSPOSED -> getPatternLTransposed();
            case J -> getPatternJ();
            case J_TRANSPOSED -> getPatternJTransposed();
            case SQUARE -> getPatternSquare();
            case I -> getPatternI();
            case I_TRANSPOSED -> getPatternITransposed();
            case T -> getPatternT();
            case T_INVERTED -> getPatternTInverted();
            case T_TRANSPOSED -> getPatternTTransposed();
            case T_TRANSPOSED_INVERTED -> getPatternTTransposedInverted();
        };
    }

    public BlockGroupType getNextType() {
         return switch (this) {
            case S -> S_TRANSPOSED;
            case S_TRANSPOSED -> S;
            case Z -> Z_TRANSPOSED;
            case Z_TRANSPOSED -> Z;
            case L -> L_TRANSPOSED;
            case L_TRANSPOSED -> L;
            case J -> J_TRANSPOSED;
            case J_TRANSPOSED -> J;
            case SQUARE -> SQUARE;
            case I -> I_TRANSPOSED;
            case I_TRANSPOSED -> I;
            case T -> T_TRANSPOSED;
            case T_TRANSPOSED -> T_INVERTED;
            case T_INVERTED -> T_TRANSPOSED_INVERTED;
            case T_TRANSPOSED_INVERTED -> T;
         };
    }

    private static boolean[][] getPatternS() {
        return new boolean[][] {
                { false, true, true },
                { true, true, false }
        };
    }

    private static boolean[][] getPatternSTransposed() {
        return new boolean[][] {
                { true, false },
                { true, true },
                { false, true },
        };
    }

    private static boolean[][] getPatternZ() {
        return new boolean[][] {
                { true, true, false },
                { false, true, true }
        };
    }

    private static boolean[][] getPatternZTransposed() {
        return new boolean[][] {
                { false, true },
                { true, true },
                { true, false },
        };
    }

    private static boolean[][] getPatternL() {
        return new boolean[][] {
                { true, false },
                { true, false },
                { true, true },
        };
    }

    private static boolean[][] getPatternLTransposed() {
        return new boolean[][] {
                { false, false, true },
                { true, true, true },
        };
    }

    private static boolean[][] getPatternJ() {
        return new boolean[][] {
                { false, true },
                { false, true },
                { true, true },
        };
    }

    private static boolean[][] getPatternJTransposed() {
        return new boolean[][] {
                { true, true, true },
                { false, false, true },
        };
    }

    private static boolean[][] getPatternSquare() {
        return new boolean[][] {
                { true, true },
                { true, true }
        };
    }

    private static boolean[][] getPatternI() {
        return new boolean[][] {
                { true },
                { true },
                { true },
                { true },
        };
    }

    private static boolean[][] getPatternITransposed() {
        return new boolean[][] {
                { true, true, true, true }
        };
    }

    private static boolean[][] getPatternT() {
        return new boolean[][] {
                { false, true, false },
                { true, true, true }
        };
    }

    private static boolean[][] getPatternTInverted() {
        return new boolean[][] {
                { true, true, true },
                { false, true, false }
        };
    }

    private static boolean[][] getPatternTTransposed() {
        return new boolean[][] {
                { true, false },
                { true, true },
                { true, false },
        };
    }

    private static boolean[][] getPatternTTransposedInverted() {
        return new boolean[][] {
                { false, true },
                { true, true },
                { false, true },
        };
    }
}
