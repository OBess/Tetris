package com.kn_110;

import java.util.Random;

public class Figures {
    //----------------VARIABLES------------------
    private int matrixX;
    private int matrixY;
    private int matrixWidth;
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

    public void setMatrixWidth(int matrixWidth) {
        this.matrixWidth = matrixWidth;
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
        } else if (dir > 0) {
            boolean moving = true;
            for (int i = 0; i < 3; i++) {
                if (figure[i + matrixYRotate].charAt(2) != ' ')
                    moving = false;
            }
            if (matrixX + 3 < width)
                matrixX += dir;
            else if (moving && matrixX + 2 < width)
                matrixX += dir;
        }
    }

    public void reset() {
        matrixY = 0;
        matrixYRotate = 0;
        randomizer();
    }

    public void rotate() {
        if (matrixX < 0)
            matrixX++;
        if(matrixX + 3 > matrixWidth)
            matrixX--;
        if (matrixYRotate + 3 < figure.length) {
            matrixYRotate += 3;
        } else {
            matrixYRotate = 0;
        }
    }
    //-------------------/PUBLIC-------------------


    //----------------BLOCKS---------------------
    private String[] I = {
            " r ",
            " r ",
            " r ",

            "   ",
            "   ",
            "rrr"
    };

    private String[] J = {
            "   ",
            "g  ",
            "ggg",

            " gg",
            " g ",
            " g ",

            "   ",
            "ggg",
            "  g",

            " g ",
            " g ",
            "gg "
    };

    private String[] L = {
            "   ",
            "  v",
            "vvv",

            " v ",
            " v ",
            " vv",

            "   ",
            "vvv",
            "v  ",

            "vv ",
            " v ",
            " v "
    };

    private String[] O = {
            "   ",
            " yy",
            " yy"
    };

    private String[] S = {
            "   ",
            " bb",
            "bb ",

            " b ",
            " bb",
            "  b"
    };

    private String[] T = {
            "   ",
            " p ",
            "ppp",

            " p ",
            " pp",
            " p ",

            "   ",
            "ppp",
            " p ",

            " p ",
            "pp ",
            " p "
    };

    private String[] Z = {
            "   ",
            "hh ",
            " hh",

            "  h",
            " hh",
            " h "
    };
    //----------------/BLOCKS--------------------


    //------------------LOGIC BLOCK------------------

    //------------------/LOGIC BLOCK-----------------

}
