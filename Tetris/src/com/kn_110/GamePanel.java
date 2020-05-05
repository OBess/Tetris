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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
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
    private String[] shadowMatrix;


    private boolean lost;
    private boolean isEnd;
    private boolean pause;
    private boolean inited;
    private boolean muted;
    private boolean skip;
    private boolean reset;
    private boolean fall;
    private boolean fallOnce;
    private boolean fallShadow;
    private boolean isShadow;
    private boolean shadowMode;
    //----------------/VARIABLES------------------


    //------------------OBJECTS-------------------
    private File highScoreFile;

    private Timer timer;

    private JLabel score = new JLabel();
    private JLabel highScore = new JLabel();
    private JComboBox level = new JComboBox();

    private Figures figure = new Figures();
    private Figures figureShadow;
    private FutureFigures futureFigures;
    //------------------/OBJECTS------------------


    //----------------CONSTRUCTOR-----------------
    GamePanel() {
        highScoreLoader();
        highScoreLoader();
        blankCursorFeature();
        setOpaque(true);
        setBackground(new Color(66, 66, 66));
        setSize(W, H);
        addKeyListener(new gameKeyAdapter());
        addKeyListener(new pauseGameOver());

        scale = 30;
        shadowMode = true;
        matrixH = getHeight() / scale;
        matrixW = getWidth() / scale;
        initGame();

//        setFocusable(true);
    }
    //----------------/CONSTRUCTOR----------------


    //------------------PRELOAD-------------------
    public void highScoreLoader() {
        StringBuffer bufferString = new StringBuffer("");
        String mainString = "";
        try {
            highScoreFile = new File("Tetris\\data\\information\\high_score.txt");
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

    private void writeToHighScore(int score) {
        try {
            highScoreFile = new File("Tetris\\data\\information\\high_score.txt");
            FileWriter myWriter = new FileWriter(highScoreFile);
            myWriter.append("");
            myWriter.write(Integer.toString(score));
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

    private void blankCursorFeature() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }
    //------------------/PRELOAD-------------------


    //--------------------INI---------------------
    private void initGame() {
        lost = false;
        inited = true;
        muted = false;
        delay = 400;

        matrix = new String[matrixH];
        copyMatrix = new String[matrixH];
        shadowMatrix = new String[matrixH];
        initMatrix();

        figure.setMatrixX(getWidth() / 2 / scale - 1);
        figure.setMatrixWidth(getWidth() / scale);
        figure.reset();
        figureShadow = figure;

        resetFutureFigure();
        resetShadowFigure();

        score.setText("Score: 0");
        if (level.getItemCount() == 8) level.setSelectedIndex(7);

        timer = new Timer(delay, this);
        if (!reset) setPaused();
        else timer.start();
    }
    //-------------------/INI---------------------


    //-------------------PUBLIC--------------------
    public int getHighScore() {
        return highScorePoints;
    }

    public boolean isEndGame() {
        return lost;
    }

    public boolean isPause() {
        return pause;
    }

    public boolean isMuted() {
        return muted;
    }

    public boolean isShadowMode() {
        return shadowMode;
    }

    public boolean isReset() {
        return reset;
    }

    public void mute() {
        muted = !muted;
    }

    public void setPaused() {
        if (!pause) {
            pause = true;
            timer.stop();
        } else {
            pause = false;
            timer.start();
        }
        repaint();
    }

    public void setReset() {
        if(!pause) {
            reset = true;
            timer.stop();
            initGame();
        }
    }

    public void shadowModify() {
        shadowMode = !shadowMode;
        resetShadowFigure();
    }

    public void scoreEditor(JLabel score) {
        this.score = score;
    }

    public void highScoreEditor(JLabel highScore) {
        this.highScore = highScore;
    }

    public void setLevel(JComboBox level) {
        this.level = level;
    }

    public void highScoreLogic() {
        if (scorePoints < highScorePoints) {
            highScore.setText("High Score: " + highScorePoints);
            writeToHighScore(highScorePoints);
        } else {
            highScore.setText("High Score: " + scorePoints);
            writeToHighScore(scorePoints);
        }
    }

    public void setFutureFigures(FutureFigures futureFigures) {
        this.futureFigures = futureFigures;
        resetFutureFigure();
    }

    public void setLevel(int levelInt) {
        timer.setDelay(50 * levelInt);
    }

    public void triggerAction(String name) {
        switch (name) {
            case "mute":
                mute();
                break;
            case "shadowModify":
                shadowModify();
                break;
            case "pause":
                setPaused();
                break;
            case "reset":
                setReset();
                break;
        }
    }
    //-------------------/PUBLIC--------------------

    //------------------PAINT BLOCK------------------

    private void paintGrid(Graphics g) {
        for (int i = 0; i < matrixH; i++) {
            for (int j = 0; j < matrixW; j++) {
                if (matrix[i].charAt(j) == ' ') {
                    g.setColor(new Color(41, 42, 49));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'r') {
                    g.setColor(new Color(255, 0, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'y') {
                    g.setColor(new Color(255, 224, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'g') {
                    g.setColor(new Color(131, 0, 255));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'b') {
                    g.setColor(new Color(255, 132, 0));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'p') {
                    g.setColor(new Color(255, 0, 217));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'v') {
                    g.setColor(new Color(0, 141, 255));
                    g.fillRect(j * scale, i * scale, scale, scale);
                } else if (matrix[i].charAt(j) == 'h') {
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
        paintShadow(g);
    }

    private void paintPause(Graphics g) {
        if (pause) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(0, 0, matrixW * scale, matrixH * scale);
            stringInTheMiddle("Pause", g, new Color(255, 111, 0, 255), 50, 1, 2);
            stringInTheMiddle("press  [M]  to turn sounds on/off", g, Color.WHITE, 15, 0, 1.7);
            stringInTheMiddle("press  [F]  to turn shadow mode on/off", g, Color.WHITE, 15, 0, 1.61);
            stringInTheMiddle("press  [W]  /  [ARROW UP]  to rotate figure", g, Color.WHITE, 15, 0, 1.52);
            stringInTheMiddle("press  [R]  to reset current game", g, Color.WHITE, 15, 0, 1.45);
            stringInTheMiddle("press  [SPACE]  to skip movement", g, Color.WHITE, 15, 0, 1.38);
            stringInTheMiddle("press  [ANY KEY]  to play", g, new Color(20, 134, 0, 211), 17, 0, 1.2);
        }
    }

    private void paintGameOver(Graphics g) {
        if (lost) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(0, 0, matrixW * scale, matrixH * scale);
            stringInTheMiddle("Game Over", g, Color.RED, 50, 1, 2);
            stringInTheMiddle("press any key to restart", g, Color.WHITE, 20, 0, 1.7);
            if (!isEnd) {
                isEnd = true;
            }
        }
    }

    private void paintResetScreen(Graphics g) {
        if (reset) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(0, 0, matrixW * scale, matrixH * scale);
            stringInTheMiddle("Reseted", g, Color.RED, 50, 1, 2);
            reset = false;
        }
    }

    private void stringInTheMiddle(String string, Graphics g, Color color, int fontScale, int fontStyle, double yPos) {
        g.setFont(new Font("Dialog", fontStyle, fontScale));
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(string, g);
        int x = (matrixW * scale - (int) r.getWidth()) / 2;
        int y = (int) ((matrixH * scale - (int) r.getHeight()) / yPos + fm.getAscent());
        g.setColor(color);
        g.drawString(string, x, y);
    }

    private void paintShadow(Graphics g) {
        for (int i = 0; i < matrixH; i++) {
            for (int j = 0; j < matrixW; j++) {
                if (shadowMatrix[i].charAt(j) == 'r') {
                    g.setColor(new Color(255, 0, 0, 255));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'y') {
                    g.setColor(new Color(255, 224, 0));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'g') {
                    g.setColor(new Color(131, 0, 255));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'b') {
                    g.setColor(new Color(255, 132, 0));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'p') {
                    g.setColor(new Color(255, 0, 217));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'v') {
                    g.setColor(new Color(0, 141, 255));
                    g.drawRect(j * scale, i * scale, scale, scale);
                } else if (shadowMatrix[i].charAt(j) == 'h') {
                    g.setColor(new Color(54, 180, 0));
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
            shadowMatrix[i] = " ";
            for (int j = 0; j < matrixW; j++) {
                matrix[i] += " ";
                copyMatrix[i] += " ";
                shadowMatrix[i] += " ";
            }
        }
    }

    private void resetMatrix() {
        if (matrixH >= 0) System.arraycopy(copyMatrix, 0, matrix, 0, matrixH);
    }

    private void remBlock() {
        if (matrixH >= 0) System.arraycopy(matrix, 0, copyMatrix, 0, matrixH);
    }

    private void visualizeBlock() {
        for (int i = 0; i < figure.getHeightFigure(); i++) {
            for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotate()].length(); j++) {
                if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) != ' ') {
                    matrix[i + figure.getMatrixY()] = matrix[i + figure.getMatrixY()].substring(0, figure.getMatrixX() + j)
                            + figure.getFigure()[i + figure.getMatrixRotate()].charAt(j)
                            + matrix[i + figure.getMatrixY()].substring(figure.getMatrixX() + j + 1);
                }
            }
        }
    }

    private void moveY() {
        boolean moveUp = false;
        for (int i = 0; i < figure.getHeightFigure(); i++) {
            for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotate()].length(); j++) {
                if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) != ' ') {
                    if (i + figure.getMatrixY() + 1 < matrixH) {
                        if (matrix[i + figure.getMatrixY() + 1].charAt(j + figure.getMatrixX()) != ' ') {
                            moveUp = true;
                            if (i + 1 < figure.getHeightFigure())
                                if (figure.getFigure()[i + figure.getMatrixRotate() + 1].charAt(j) != ' ')
                                    moveUp = false;
                        }
                    } else
                        moveUp = true;
                }

                if (moveUp)
                    break;
            }
            if (moveUp)
                break;
        }
        if (moveUp) {
            fall = false;
            figure.setMatrixY(figure.getMatrixY());
            resetMatrix();
            visualizeBlock();
            remBlock();
            figure.reset();
            resetMatrix();
            figure.setMatrixX(matrixW / 2);
            resetFutureFigure();
            resetShadowFigure();
        } else
            figure.setMatrixY(figure.getMatrixY() + 1);
    }

    private void moveShadowY() {
        boolean moveUp = false;
        for (int i = 0; i < figureShadow.getHeightFigure(); i++) {
            for (int j = 0; j < figureShadow.getFigure()[i + figureShadow.getMatrixRotate()].length(); j++) {
                if (figureShadow.getFigure()[i + figureShadow.getMatrixRotate()].charAt(j) != ' ') {
                    if (i + figureShadow.getMatrixY() + 1 < matrixH) {
                        if (matrix[i + figureShadow.getMatrixY() + 1].charAt(j + figureShadow.getMatrixX()) != ' ') {
                            moveUp = true;
                            if (i + 1 < figureShadow.getHeightFigure())
                                if (figureShadow.getFigure()[i + figureShadow.getMatrixRotate() + 1].charAt(j) != ' ')
                                    moveUp = false;
                        }
                    } else
                        moveUp = true;
                }

                if (moveUp)
                    break;
            }
            if (moveUp)
                break;
        }
        if (moveUp) {
            fallShadow = false;
        } else
            figureShadow.setMatrixY(figureShadow.getMatrixY() + 1);
    }

    private void moveX(int dir) {
        boolean moveBack = false;
        for (int i = 0; i < figure.getHeightFigure(); i++) {
            if (dir > 0) {
                for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotate()].length(); j++) {
                    if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) != ' ') {
                        if (j + figure.getMatrixX() + 1 < matrixW) {
                            if (matrix[i + figure.getMatrixY()].charAt(j + figure.getMatrixX() + 1) != ' ') {
                                moveBack = true;
                                if (j + 1 < figure.getHeightFigure()) {
                                    if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j + 1) != ' ') {
                                        moveBack = false;
                                    }
                                }
                            }
                        } else
                            moveBack = true;
                    }
                    if (moveBack)
                        break;
                }
            } else if (dir < 0) {
                for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotate()].length(); j++) {
                    if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) != ' ') {
                        if (j + figure.getMatrixX() - 1 >= 0) {
                            if (matrix[i + figure.getMatrixY()].charAt(j + figure.getMatrixX() - 1) != ' ') {
                                moveBack = true;
                                if (j - 1 >= 0) {
                                    if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j - 1) != ' ') {
                                        moveBack = false;
                                    }
                                }
                            }
                        } else
                            moveBack = true;
                    }
                    if (moveBack)
                        break;
                }
            }
            if (moveBack)
                break;
        }
        if (!moveBack)
            figure.setMatrixX(figure.getMatrixX() + dir);
        resetMatrix();
        visualizeBlock();
    }

    private void rotate() {
        boolean rot = true, up = false;
        int count = 0;
        for (int i = 0; i < figure.getHeightFigure(); i++) {
            for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotated()].length(); j++) {
                if (figure.getFigure()[i + figure.getMatrixRotated()].charAt(j) != ' ') {
                    if (j + figure.getMatrixX() < 0) {
                        count++;
                    } else if (j + figure.getMatrixX() >= matrixW) {
                        count--;
                    } else if (i + figure.getMatrixY() >= matrixH) {
                        up = true;
                        count--;
                    } else {
                        if (matrix[i + figure.getMatrixY()].charAt(j + figure.getMatrixX()) != ' ' &&
                                figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) == ' ') {
                            rot = false;
                        }
                    }
                }
                if (!rot)
                    break;
            }
            if (!rot)
                break;
        }
        if (rot) {
            if (!up)
                figure.setMatrixX(figure.getMatrixX() + count);
            else
                figure.setMatrixY(figure.getMatrixY() + count);

            figure.rotate();
            resetMatrix();
            visualizeBlock();
        }
    }

    private void deleteRow() {
        int count = 0;
        for (int i = 0; i < matrixH; i++) {
            if (copyMatrix[i].indexOf(' ') == matrixW) {
                System.arraycopy(copyMatrix, 0, copyMatrix, 1, i);
                copyMatrix[0] = " ";
                for (int j = 0; j < matrixW; j++) {
                    copyMatrix[0] += " ";
                }
                resetMatrix();
                visualizeBlock();
                scorePoints++;
                soundFXLoader("Tetris\\data\\sounds\\1X.wav");
                count++;
                score.setText("Score: " + scorePoints);
                if (timer.getDelay() > 50) {
                    timer.setDelay(timer.getDelay() - 2);
                    levelOutputLogic();
                }
                System.out.println(timer.getDelay());
                repaint();
                resetShadowFigure();
            }
        }
        if (count == 4) {
            scorePoints += 16;
            soundFXLoader("Tetris\\data\\sounds\\4X.wav");
            score.setText("Score: " + scorePoints);
        }
    }

    private void gameOver() {
        if (figure.getMatrixY() == 0) {
            skip = true;
        }
        if (skip) {
            if (matrix[0].indexOf('r') != -1
                    || matrix[0].indexOf('g') != -1
                    || matrix[0].indexOf('v') != -1
                    || matrix[0].indexOf('y') != -1
                    || matrix[0].indexOf('b') != -1
                    || matrix[0].indexOf('p') != -1
                    || matrix[0].indexOf('h') != -1) {
                scorePoints = 0;
                soundFXLoader("Tetris\\data\\sounds\\game_over.wav");
                lost = true;
            }
            skip = false;
        }
    }

    private void levelOutputLogic() {
        if (timer.getDelay() % 50 == 0) {
            level.setSelectedIndex(level.getSelectedIndex() - 1);
        }
    }

    private void resetFutureFigure() {
        if (futureFigures != null) {
            this.futureFigures.setScale(scale);
            this.futureFigures.setMatrixH(figure.getHeightFigure());
            this.futureFigures.setFigure(figure.getFutureFigure());
            this.futureFigures.setRotate(figure.getMatrixRotate());
            this.futureFigures.repaint();
        }
    }

    private void resetShadowFigure() {
        for (int i = 0; i < matrixH; i++) {
            shadowMatrix[i] = " ";
            for (int j = 0; j < matrixW; j++)
                shadowMatrix[i] += " ";
        }
        if (shadowMode) {
            figureShadow = new Figures(figure);
            fallShadow = true;
            isShadow = true;
            while (fallShadow) {
                moveShadowY();
                repaint();
            }
            visualizeBlock(figureShadow, shadowMatrix);
        }
    }

    private void visualizeBlock(Figures figure, String[] matrix) {
        for (int i = 0; i < figure.getHeightFigure(); i++) {
            for (int j = 0; j < figure.getFigure()[i + figure.getMatrixRotate()].length(); j++) {
                if (figure.getFigure()[i + figure.getMatrixRotate()].charAt(j) != ' ') {
                    matrix[i + figure.getMatrixY()] = matrix[i + figure.getMatrixY()].substring(0, figure.getMatrixX() + j)
                            + figure.getFigure()[i + figure.getMatrixRotate()].charAt(j)
                            + matrix[i + figure.getMatrixY()].substring(figure.getMatrixX() + j + 1);
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
        paintPause(g);
        paintGameOver(g);
        paintResetScreen(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!lost) {
            moveY();
            gameOver();
            deleteRow();
            highScoreLogic();
        }
        repaint();
    }

    class pauseGameOver extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!lost) {
                // mute option
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    triggerAction("mute");
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    triggerAction("shadowModify");
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    triggerAction("reset");
                }
                if (!pause) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_TAB) {
                        triggerAction("pause");
                    }

                } else {
                    int[] allKeyCodes = new int[223];
                    for (int i = 0; i < allKeyCodes.length; i++) {
                        allKeyCodes[i] = i + 1;
                        if (e.getKeyCode() == allKeyCodes[i]) {
                            setPaused();
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
                moveX(-1);
                resetShadowFigure();
                repaint();
                soundFXLoader("Tetris\\data\\sounds\\move_left.wav");
            }
            if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
                rotate();
                resetShadowFigure();
                repaint();
                soundFXLoader("Tetris\\data\\sounds\\rotate.wav");
            }
            if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
                moveY();
                repaint();
                soundFXLoader("Tetris\\data\\sounds\\move_down.wav");
            }
            if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
                moveX(1);
                resetShadowFigure();
                repaint();
                soundFXLoader("Tetris\\data\\sounds\\move_right.wav");
            }
            if ((e.getKeyCode() == KeyEvent.VK_SPACE)) {
                if (fallOnce) {
                    soundFXLoader("Tetris\\data\\sounds\\skip.wav");
                    fall = true;
                    while (fall) {
                        moveY();
                        repaint();
                    }
                }
                fallOnce = true;
            }
        }
    }
    //-----------/IMPLEMENTED AND EXTENDED-----------
}
