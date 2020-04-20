package com.kn_110;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener {


    //----------------VARIABLES------------------
    private int highScorePoints;
    private int scorePoints;
    private int delay;
    private int W = 470;
    private int H = 650;
    private int scale;
    private int matrixH;
    private int matrixW;

    private String[] matrix;

    private boolean lost;
    private boolean pause;
    private boolean inited;
    private boolean muted;
    //----------------/VARIABLES------------------


    //------------------OBJECTS-------------------
    private File highScoreFile;

    private Timer timer;
    private JLabel score = new JLabel();
    private JLabel highScore = new JLabel();
    //------------------/OBJECTS------------------


    //----------------CONSTRUCTOR-----------------
    GamePanel() {
        highScoreLoader();
        highScoreLoader();
//      blankCursorFeature();
        setOpaque(true);
        setBackground(new Color(0, 0, 0));
        setSize(W, H);
        setFocusable(true);
        addKeyListener(new gameKeyAdapter());
        addKeyListener(new pauseGameOver());

        initGame();
    }
    //----------------/CONSTRUCTOR----------------


    //------------------PRELOAD-------------------
    private void highScoreLoader() {
        StringBuffer bufferString = new StringBuffer("");
        String mainString = "";
        try {
            highScoreFile = new File("Tetris\\information\\high_score.txt");
            Scanner myReader = new Scanner(highScoreFile);
            mainString = myReader.nextLine();
            bufferString.append(mainString);
            myReader.close();
        } catch (java.io.FileNotFoundException e1) {
            System.out.println("An error occurred while reading file high_score.txt");
            e1.printStackTrace();
        }
        highScorePoints = Integer.parseInt(mainString);
    }

    private void writeToHighScore() {
        try {
            highScoreFile = new File("Tetris\\information\\high_score.txt");
            FileWriter myWriter = new FileWriter(highScoreFile);
            myWriter.append("");
            myWriter.write(Integer.toString(scorePoints));
            myWriter.close();
        } catch (IOException e1) {
            System.out.println("An error occurred.");
            e1.printStackTrace();
        }
    }

    private void soundFXLoader(String filePath) {
        if (!muted) {
            try {
                File soundFX1 = new File(filePath);
                AudioInputStream SFX = AudioSystem.getAudioInputStream(soundFX1);
                Clip clip = AudioSystem.getClip();
                clip.open(SFX);
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("failed to load audio file");
            }
        }
    }
    //------------------/PRELOAD-------------------


    //--------------------INI---------------------
    private void initGame() {
        lost = false;
        pause = false;
        inited = true;
        muted = false;

        delay = 1000;
        scale = 30;

        matrixH = getHeight() / scale;
        matrixW = getWidth() / scale;
        matrix = new String[matrixH];
        resetMatrix();

        timer = new Timer(delay, this);
    }
    //-------------------/INI---------------------


    //-------------------PUBLIC--------------------
    public boolean getEndGame() {
        return lost;
    }

    public boolean getPause() {
        return pause;
    }

    public boolean getMuted() {
        return muted;
    }

    public void mute() {
        muted = !muted;
    }

    public void scoreEditor(JLabel score) {
        this.score = score;
    }

    public void highScoreEditor(JLabel highScore) {
        this.highScore = highScore;
    }

    public void highScoreLogic() {
        if (scorePoints < highScorePoints) {
            highScore.setText("High Score: " + highScorePoints);
        } else {
            highScore.setText("High Score: " + scorePoints);
            writeToHighScore();
        }
    }


    //-------------------/PUBLIC--------------------


    //------------------PAINT BLOCK------------------
    private void paintGrid(Graphics g) {
        for (int i = 0; i < matrixH; i++) {
            for (int j = 0; j < matrixW; j++) {
                if (matrix[i].charAt(j) == ' ') {
                    g.setColor(new Color(88, 130, 125));
                    g.fillRect(j * scale, i * scale, scale, scale);
                    g.setColor(new Color(82, 84, 95, 255));
                    g.drawRect(j * scale, i * scale, scale, scale);
                }
            }
        }
    }
    //------------------/PAINT BLOCK-----------------


    //------------------LOGIC BLOCK------------------
    private void resetMatrix() {
        for (int i = 0; i < matrixH; i++) {
            matrix[i] = " ";
            for (int j = 0; j < matrixW; j++) {
                matrix[i] += " ";
            }
        }
    }
    //------------------/LOGIC BLOCK-----------------


    //------------IMPLEMENTED AND EXTENDED-----------
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrid(g);
        if (!lost) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!lost) {

        }
        repaint();
    }

    class pauseGameOver extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!lost) {
                // mute option
                if (!muted) {
                    if (e.getKeyCode() == KeyEvent.VK_M)
                        muted = true;
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_M)
                        muted = false;
                }
                if (!pause) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_TAB) {
                        timer.stop();
                        repaint();
                        pause = true;
                    }
                } else {
                    int[] allKeyCodes = new int[223];
                    for (int i = 0; i < allKeyCodes.length; i++) {
                        allKeyCodes[i] = i + 1;
                        if (e.getKeyCode() == allKeyCodes[i]) {
                            timer.start();
                            pause = false;
                        }
                    }
                }
            } else {
                // game over logic
                timer.stop();
                int[] allKeyCodes = new int[223];
                for (int i = 0; i < allKeyCodes.length; i++) {
                    allKeyCodes[i] = i + 1;
                    if (e.getKeyCode() == allKeyCodes[i]) {
                        initGame();
                    }
                }
            }
        }
    }

    // controls of snake
    class gameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {

            }
            if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)) {

            }
            if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {

            }
            if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {

            }
        }
    }
    //------------IMPLEMENTED AND EXTENDED-----------
}
