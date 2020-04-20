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
    private String[] copyMatrix;


    private boolean left;
    private boolean right;
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
    private Figures figure = new Figures();
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

        delay = 100;
        scale = 30;

        matrixH = getHeight() / scale;
        matrixW = getWidth() / scale;
        matrix = new String[matrixH];
        copyMatrix = new String[matrixH];
        initMatrix();

        timer = new Timer(delay, this);
        timer.start();
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
                } else {
                    g.setColor(new Color(22, 222, 197));
                    g.fillRect(j * scale, i * scale, scale, scale);
                    g.setColor(new Color(82, 84, 95, 255));
                    g.drawRect(j * scale, i * scale, scale, scale);
                }
            }
        }

    }


    //------------------/PAINT BLOCK-----------------


    //------------------LOGIC BLOCK------------------
    private void initMatrix() {
        for (int i = 0; i < matrixH; i++) {
            matrix[i] = " ";
            copyMatrix[i] = " ";
            for (int j = 0; j < matrixW; j++) {
                matrix[i] += " ";
                copyMatrix[i] += " ";
            }
        }
    }

    private void resetMatrix(){
        for (int i = 0; i < matrixH; i++) {
            matrix[i] = new String(copyMatrix[i]);
        }
    }

    private void remBlock(){
        for (int i = 0; i < matrixH; i++) {
            copyMatrix[i] = new String(matrix[i]);
        }
    }

    private void visualizeBlock() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (figure.getFigure()[i + figure.getMatrixYRotate()].charAt(j) != ' ') {
                    matrix[i + figure.getMatrixY()] = matrix[i + figure.getMatrixY()].substring(0, figure.getMatrixX() + j) + '1'
                            + matrix[i + figure.getMatrixY()].substring(figure.getMatrixX() + j + 1, matrix[i + figure.getMatrixY()].length());
                }
            }
        }
    }
    //------------------/LOGIC BLOCK-----------------


    //------------IMPLEMENTED AND EXTENDED-----------
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        resetMatrix();
        paintGrid(g);
        if (!lost) {
            visualizeBlock();
            paintGrid(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!lost) {
            if(figure.getMatrixY() + 3 < matrixH) {
                figure.setMatrixY(figure.getMatrixY() + 1);
            }
            else{
                remBlock();
                figure.reset();
            }
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
                figure.move(-1, matrixW);
                repaint();
            }
            if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                figure.rotate();
            }
            if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {

            }
            if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
                figure.move(1, matrixW);
                repaint();
            }
        }
    }
    //------------IMPLEMENTED AND EXTENDED-----------
}
