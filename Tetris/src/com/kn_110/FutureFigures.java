package com.kn_110;

import javax.swing.*;
import java.awt.*;

public class FutureFigures extends JPanel {
    private int scale;
    private int matrixH;
    private int rotate;

    private String figure[];

    //--------------CONSTRUCTOR----------------
    FutureFigures() {
//        setBackground(Color.BLACK);
        setBackground(new Color(66, 66, 66));
    }
    //--------------/CONSTRUCTOR----------------


    //----------------PUBLIC--------------------

    public void setFigure(String[] figure) {
        this.figure = figure;
    }

    public void setMatrixH(int matrixH) {
        this.matrixH = matrixH;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    //----------------/PUBLIC-------------------


    //-----------------LOGIC--------------------

    //-----------------/LOGIC-------------------


    //---------------PAINT BLOCK----------------
    private void paintFigure(Graphics g) {
        for (int i = 0; i < matrixH; i++) {
            for (int j = 0; j < figure[i].length(); j++) {
                if (figure[i].charAt(j) == ' ') {
                    g.setColor(new Color(41, 42, 49));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'r') {
                    g.setColor(new Color(255, 0, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'y') {
                    g.setColor(new Color(255, 224, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'g') {
                    g.setColor(new Color(131, 0, 255));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'b') {
                    g.setColor(new Color(255, 132, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'p') {
                    g.setColor(new Color(255, 0, 217));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'v') {
                    g.setColor(new Color(0, 141, 255));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (figure[i].charAt(j) == 'h') {
                    g.setColor(new Color(54, 180, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else {
                    g.setColor(new Color(208, 222, 214));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }
                g.setColor(new Color(59, 59, 59, 131));
                g.drawRect(j * scale, i * scale, scale, scale);
            }
        }
    }
    //---------------/PAINT BLOCK---------------


    //---------IMPLEMENTED AND EXTENDED---------
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(66,66,66));
        g.fillRect(0,0,1000,1000);
        paintFigure(g);
    }
    //---------/IMPLEMENTED AND EXTENDED--------
}
