package com.kn_110;

import java.util.Random;

public class Figures {
    //----------------VARIABLES------------------
    private int matrixX;
    private int matrixY;
    private int matrixYRotate;
    private String[] figure;
    //----------------/VARIABLES-----------------


    //----------------CONSTRUCTOR-----------------
    Figures() {
        randomizer();
    }
    //----------------/CONSTRUCTOR----------------


    //-------------------PUBLIC--------------------
    public String[] getFigure() {
        return figure;
    }

    public void setFigure(String[] figure) {
        this.figure = figure;
    }

    public void setMatrixY(int y) {
        matrixY = y;
    }

    public void setMatrixX(int x) {
        matrixX = x;
    }

    public int getMatrixX() {
        return matrixX;
    }

    public int getMatrixY() {
        return matrixY;
    }

    public int getMatrixYRotate() {
        return matrixYRotate;
    }

    public void randomizer() {
        int random = new Random().nextInt(7);
        switch (random) {
            case 0:
                figure = I;
                break;
            case 1:
                figure = J;
                break;
            case 2:
                figure = L;
                break;
            case 3:
                figure = O;
                break;
            case 4:
                figure = S;
                break;
            case 5:
                figure = T;
                break;
            case 6:
                figure = Z;
                break;
        }

        random = new Random().nextInt(figure.length);
        for (int i = 0; i < random; i++)
            rotate();
    }

    public void move(int dir, int width) {
        if (dir < 0) {
            boolean moving = true;
            for (int i = 0; i < 3; i++) {
                if (figure[i + matrixYRotate].charAt(0) != ' ')
                    moving = false;
            }
            if (moving && matrixX + dir >= -1)
                matrixX += dir;
            else if (matrixX + dir >= 0)
                matrixX += dir;
        } else if (matrixX + 3 < width && dir > 0) {
            matrixX += dir;
        }
    }

    public void reset() {
        matrixY = 0;
        matrixYRotate = 0;
        randomizer();
    }

    public void rotate() {
        if (matrixX == -1)
            matrixX = 0;
        if (matrixYRotate + 3 < figure.length) {
            matrixYRotate += 3;
        } else {
            matrixYRotate = 0;
        }
    }
    //-------------------/PUBLIC-------------------


    //----------------BLOCKS---------------------
    private String[] I = {
            " _ ",
            " _ ",
            " _ ",

            "   ",
            "   ",
            "___"
    };

    private String[] J = {
            "   ",
            "_  ",
            "___",

            " __",
            " _ ",
            " _ ",

            "   ",
            "___",
            "  _",

            " _ ",
            " _ ",
            "__ "
    };

    private String[] L = {
            "   ",
            "  _",
            "___",

            " _ ",
            " _ ",
            " __",

            "   ",
            "___",
            "_  ",

            "__ ",
            " _ ",
            " _ "
    };

    private String[] O = {
            "   ",
            " __",
            " __"
    };

    private String[] S = {
            "   ",
            " __",
            "__ ",

            " _ ",
            " __",
            "  _"
    };

    private String[] T = {
            "   ",
            " _ ",
            "___",

            " _ ",
            " __",
            " _ ",

            "   ",
            "___",
            " _ ",

            " _ ",
            "__ ",
            " _ "
    };

    private String[] Z = {
            "   ",
            "__ ",
            " __",

            "  _",
            " __",
            " _ "
    };
    //----------------/BLOCKS--------------------


    //------------------LOGIC BLOCK------------------

    //------------------/LOGIC BLOCK-----------------

}
