package com.kn_110;

import java.util.Random;

public class Figures {
    //----------------VARIABLES------------------
    private int matrixX;
    private int matrixY;
    private int matrixWidth;
    private int matrixRotate;
    private int matrixRotated;
    private int heightFigure = 4;
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

    public int getHeightFigure() {
        return heightFigure;
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

    public int getMatrixRotate() {
        return matrixRotate;
    }

    public int getMatrixRotated() {
        return matrixRotated;
    }

    public void randomizer() {
//        int random = new Random().nextInt(7);
//        switch (random) {
//            case 0:
//                figure = I;
//                break;
//            case 1:
//                figure = J;
//                break;
//            case 2:
//                figure = L;
//                break;
//            case 3:
//                figure = O;
//                break;
//            case 4:
//                figure = S;
//                break;
//            case 5:
//                figure = T;
//                break;
//            case 6:
//                figure = Z;
//                break;
//        }
//
//        random = new Random().nextInt(figure.length);
//        for (int i = 0; i < random; i++)
//            rotate();
       figure = I;
    }

    public void reset() {
        matrixY = 0;
        matrixRotate = 0;
        matrixRotated = 0;
        randomizer();
    }

    public void rotate() {
        if (matrixRotate + heightFigure < figure.length) {
            matrixRotate += heightFigure;
        } else {
            matrixRotate = 0;
        }

        matrixRotated = matrixRotate;
        if (matrixRotated + heightFigure < figure.length) {
            matrixRotated += heightFigure;
        } else {
            matrixRotated = 0;
        }
    }
    //-------------------/PUBLIC-------------------


    //----------------BLOCKS---------------------
    private String[] I = {
            " r  ",
            " r  ",
            " r  ",
            " r  ",

            "rrrr",
            "    ",
            "    ",
            "    "
    };

    private String[] J = {
            "g   ",
            "ggg ",
            "    ",
            "    ",

            "gg  ",
            "g   ",
            "g   ",
            "    ",

            "ggg ",
            "  g ",
            "    ",
            "    ",

            "  g ",
            "  g ",
            " gg ",
            "    "
    };

    private String[] L = {

            "  v ",
            "vvv ",
            "    ",
            "    ",

            "v   ",
            "v   ",
            "vv  ",
            "    ",

            "vvv ",
            "v   ",
            "    ",
            "    ",

            " vv ",
            "  v ",
            "  v ",
            "    "
    };

    private String[] O = {
            "  yy",
            "  yy",
            "    ",
            "    "
    };

    private String[] S = {
            "  bb",
            " bb ",
            "    ",
            "    ",

            "  b ",
            "  bb",
            "   b",
            "    "
    };

    private String[] T = {
            "  p ",
            " ppp",
            "    ",
            "    ",

            "  p ",
            "  pp",
            "  p ",
            "    ",

            " ppp",
            "  p ",
            "    ",
            "    ",

            "  p ",
            " pp ",
            "  p ",
            "    "
    };

    private String[] Z = {
            " hh ",
            "  hh",
            "    ",
            "    ",


            "   h",
            "  hh",
            "  h ",
            "    ",
    };
    //----------------/BLOCKS--------------------


    //------------------LOGIC BLOCK------------------

    //------------------/LOGIC BLOCK-----------------

}
