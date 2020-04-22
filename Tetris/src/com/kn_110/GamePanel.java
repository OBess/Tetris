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

        delay = 500;
        scale = 30;

        matrixH = getHeight() / scale;
        matrixW = getWidth() / scale;
        matrix = new String[matrixH];
        copyMatrix = new String[matrixH];
        initMatrix();

        figure.setMatrixX(getWidth() / 2 / scale - 1);
        figure.setMatrixWidth(getWidth() / scale);

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
                } else if (matrix[i].charAt(j) == 'r') {
                    g.setColor(new Color(222, 62, 50));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'y') {
                    g.setColor(new Color(222, 203, 86));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'g') {
                    g.setColor(new Color(112, 222, 130));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'b') {
                    g.setColor(new Color(58, 212, 222));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'p') {
                    g.setColor(new Color(219, 107, 222));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'v') {
                    g.setColor(new Color(32, 137, 222));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else if (matrix[i].charAt(j) == 'h') {
                    g.setColor(new Color(163, 222, 30));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }else{
                    g.setColor(new Color(208, 222, 214));
                    g.fillRect(j * scale, i * scale, scale, scale);
                }
                g.setColor(new Color(82, 84, 95, 255));
                g.drawRect(j * scale, i * scale, scale, scale);
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

    private void resetMatrix() {
        for (int i = 0; i < matrixH; i++) {
            matrix[i] = copyMatrix[i];
        }
    }

    private void remBlock() {
        for (int i = 0; i < matrixH; i++) {
            copyMatrix[i] = matrix[i];
        }
    }

    private void visualizeBlock() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (figure.getFigure()[i + figure.getMatrixYRotate()].charAt(j) != ' ') {
                    matrix[i + figure.getMatrixY()] = matrix[i + figure.getMatrixY()].substring(0, figure.getMatrixX() + j)
                            + figure.getFigure()[i + figure.getMatrixYRotate()].charAt(j)
                                + matrix[i + figure.getMatrixY()].substring(figure.getMatrixX() + j + 1, matrix[i + figure.getMatrixY()].length());
                }
            }
        }
    }

    private void collisionY() {
        boolean moveUp = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (figure.getFigure()[i + figure.getMatrixYRotate()].charAt(j) != ' ' && i + figure.getMatrixY() + 1 < matrixH) {
                    if (matrix[i + figure.getMatrixY() + 1].charAt(j + figure.getMatrixX()) != ' ') {
                        moveUp = true;
                        if (i + 1 < 3)
                            if (figure.getFigure()[i + figure.getMatrixYRotate() + 1].charAt(j) != ' ')
                                moveUp = false;
                    }
                }
                if(moveUp)
                    break;
            }
            if(moveUp)
                break;
        }
        if (moveUp) {
            figure.setMatrixY(figure.getMatrixY());
            resetMatrix();
            visualizeBlock();
            remBlock();
            figure.reset();
            resetMatrix();
            figure.setMatrixX(getWidth() / 2 / scale - 1);
        }
    }

    private void collisionX(int dir) {
        boolean move = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (figure.getFigure()[i + figure.getMatrixYRotate()].charAt(j) != ' ') {
                    if (matrix[i + figure.getMatrixY()].charAt(j + figure.getMatrixX()) != ' ') {
                        move = true;
                        if (j + 1 < 3) {
                            if (figure.getFigure()[i].charAt(j + 1) != ' ') {
                                move = false;
                            }
                        }
                        if(j - 1 >= 0){
                            if (figure.getFigure()[i].charAt(j - 1) != ' ') {
                                move = false;
                            }
                        }
                    }
                }
            }
        }
        if (move) {
            figure.setMatrixX(figure.getMatrixX() - dir);
            resetMatrix();
            visualizeBlock();
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
            if (figure.getMatrixY() + 3 < matrixH) {
                figure.setMatrixY(figure.getMatrixY() + 1);
                collisionY();
            } else {
                remBlock();
                figure.setMatrixX(getWidth() / 2 / scale - 1);
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

    class gameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
                figure.move(-1, matrixW);
//                collisionX(-1);
                repaint();
            }
            if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                figure.rotate();
                resetMatrix();
                visualizeBlock();
                repaint();
            }
            if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
                if (figure.getMatrixY() + 3 < matrixH) {
                    figure.setMatrixY(figure.getMatrixY() + 1);
                    collisionY();
                    repaint();
                }
            }
            if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
                figure.move(1, matrixW);
//                collisionX(1);
                repaint();
            }
        }
    }
    //------------IMPLEMENTED AND EXTENDED-----------
}
