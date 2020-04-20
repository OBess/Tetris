package com.kn_110;

public class Figures {
    //----------------VARIABLES------------------
    private int m_x, m_y;
    private String figure;
    //----------------/VARIABLES-----------------


    //----------------BLOCKS---------------------
    String[] I = {
            " _ ",
            " _ ",
            " _ ",
            "   ",
            "___",
            "   "
    };

    String[] J = {
            "_  ",
            "___",
            "   ",
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

    String[] L = {
            "  _",
            "___",
            "   ",
            "__ ",
            " _ ",
            " _ ",
            "   ",
            "___",
            "_  ",
            " _ ",
            " _ ",
            " __"
    };

    String[] O = {
            "   ",
            " __",
            " __"
    };

    String[] S = {
            " __",
            "__ ",
            "   ",
            " _ ",
            " __",
            "  _"
    };

    String[] T = {
            " _ ",
            "___",
            "   ",
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

    String[] Z = {
            "__ ",
            " __",
            "   ",
            "  _",
            " __",
            " _ "
    };
    //----------------/BLOCKS--------------------


    //-------------------PUBLIC--------------------
    public String getFigure(){ return figure; }
    public void setFigure(String figure){ this.figure = figure;}
    //-------------------/PUBLIC-------------------
}
