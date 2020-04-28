package com.kn_110;

import javax.swing.*;
import java.awt.*;

public class FutureFigures extends JPanel {
    //--------------CONSTRUCTOR----------------
    FutureFigures(){
        setBackground(Color.BLACK);
        setSize(500,600);

    }
    //--------------/CONSTRUCTOR----------------


    //-----------------LOGIC--------------------
    private void getFigure(){

    }
    //-----------------/LOGIC-------------------


    //---------------PAINT BLOCK----------------
    private void paintFigure(){

    }
    //---------------/PAINT BLOCK---------------


    //---------IMPLEMENTED AND EXTENDED---------
    @Override
    public void paintComponent(Graphics g) {
        paintFigure();
    }
    //---------/IMPLEMENTED AND EXTENDED--------
}
