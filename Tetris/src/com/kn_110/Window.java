package com.kn_110;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Interface Class
class Window extends JFrame {

    //------------------------INITIALIZATION----------------------------
    private final JPanel masterPanel;
    private final JPanel leftPanel;
    private final JPanel gridPanel;
    private final JPanel borderPanel;
    private final JPanel borderPanel1;
    private final JPanel buttonPanel;
    private final GamePanel gamePanel;
    private final FutureFigures futureFigures;

    private final JLabel timerLabel;
    private final JLabel score;
    private final JLabel highScore;
    private final JLabel level;

    private final JButton mute;
    private final JButton SMSwitcher;
    private final JButton pause;

    private final JComboBox levelChoser;

    private final ImageIcon icon;
    private final Image iconImage;

    private final Timer timer;
    private final Timer observer;

    private int tm = 0;
    private int iconSize = 35;
    private int iconSize2 = 50;
    private String[] levelChoserCoices = {
            "Level: 8",
            "Level: 7",
            "Level: 6",
            "Level: 5",
            "Level: 4",
            "Level: 3",
            "Level: 2",
            "Level: 1"
    };
    //------------------------/INITIALIZATION---------------------------

    private void buttonCreator(JButton name,
                               String state1IconPath,
                               String state2IconPath,
                               boolean gamePanelGetter,
                               String methodName) {
        ImageIcon state1 = new ImageIcon(new ImageIcon(state1IconPath).getImage().
                getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon state2 = new ImageIcon(new ImageIcon(state2IconPath).getImage().
                getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        name.setIcon(state2);
        name.setSize(100, 100);
        name.addActionListener(e -> {
            if (gamePanelGetter)
                name.setIcon(state1);
            else
                name.setIcon(state2);
            try {
                gamePanel.triggerAction(methodName);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            gamePanel.requestFocus();
        });
        name.setContentAreaFilled(false);
        name.setFocusPainted(false);
        name.setOpaque(true);
        name.setBackground(new Color(66, 66, 66));
        name.setBorder(null);
    }

    private void buttonCreator(JButton name,
                               String state1IconPath,
                               String state2IconPath,
                               boolean gamePanelGetter) {
        ImageIcon state1 = new ImageIcon(new ImageIcon(state1IconPath).getImage().
                getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon state2 = new ImageIcon(new ImageIcon(state2IconPath).getImage().
                getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        if (gamePanelGetter)
            name.setIcon(state1);
        else
            name.setIcon(state2);
    }

    Window() {

        //------------------------NEW OBJECTS---------------------------
        masterPanel = new JPanel();
        leftPanel = new JPanel();
        gridPanel = new JPanel();
        borderPanel = new JPanel();
        borderPanel1 = new JPanel();
        buttonPanel = new JPanel();
        gamePanel = new GamePanel();
        futureFigures = new FutureFigures();

        score = new JLabel();
        highScore = new JLabel();
        timerLabel = new JLabel();
        level = new JLabel();

        mute = new JButton();
        SMSwitcher = new JButton();
        pause = new JButton();

        levelChoser = new JComboBox(levelChoserCoices);
        levelChoser.setSelectedIndex(7);

        icon = new ImageIcon("Tetris\\data\\images\\icon.png");
        iconImage = icon.getImage();
        //------------------------/NEW OBJECTS---------------------------


        //------------------------------UI-------------------------------
        timer = new Timer(1000, e -> {
            if (!gamePanel.isEndGame()) {
                if (!gamePanel.isPause()) {
                    String time = String.format("%02d:%02d:%02d", tm / 3600, tm / 60, tm % 60);
                    tm += 1;
                    timerLabel.setText("Time: " + time);
                }
            }
        });

        observer = new Timer(0, e -> {
            if (gamePanel.isEndGame() || gamePanel.isReset()) {
                tm = 0;
                timerLabel.setText("Time: 00:00:00");
                timerLabel.updateUI();
            }
            buttonCreator(mute,
                    "Tetris\\data\\images\\Mute.png",
                    "Tetris\\data\\images\\unMute.png",
                    gamePanel.isMuted());
            buttonCreator(SMSwitcher,
                    "Tetris\\data\\images\\SMSwitcherIcon.png",
                    "Tetris\\data\\images\\SMSwitcherIcon2.png",
                    gamePanel.isShadowMode());
            buttonCreator(pause,
                    "Tetris\\data\\images\\play.png",
                    "Tetris\\data\\images\\pause.png",
                    gamePanel.isPause());
            if (gamePanel.getHighScore() > 9999)
                setSize(700 + highScore.getText().substring(11,
                        highScore.getText().length() - 1).length() * 10, 726);
            if (!gamePanel.isPause()) {
                gamePanel.requestFocus();
            }
        });

        timerLabel.setText("Time: 00:00:00");
        timerLabel.setForeground(new Color(135, 135, 135, 255));
        timerLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        level.setText("Level: 1");
        level.setForeground(new Color(135, 135, 135, 255));
        level.setFont(new Font("Dialog", Font.BOLD, 20));
        level.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        levelChoser.setFont(new Font("Dialog", Font.BOLD, 20));
        levelChoser.setAlignmentX(LEFT_ALIGNMENT);
        levelChoser.addActionListener(e -> {
            gamePanel.requestFocus();
            gamePanel.setLevel(levelChoser.getSelectedIndex() + 1);
        });

        score.setText("Score: 0");
        score.setFont(new Font("Dialog", Font.BOLD, 20));
        score.setForeground(new Color(255, 255, 255));

        highScore.setText("High Score: 0");
        highScore.setFont(new Font("Dialog", Font.BOLD, 20));
        highScore.setForeground(new Color(222, 222, 222));

        buttonCreator(mute,
                "Tetris\\data\\images\\Mute.png",
                "Tetris\\data\\images\\unMute.png",
                gamePanel.isMuted(),
                "mute"
        );

        buttonCreator(SMSwitcher,
                "Tetris\\data\\images\\SMSwitcherIcon2.png",
                "Tetris\\data\\images\\SMSwitcherIcon.png",
                gamePanel.isShadowMode(),
                "shadowModify"
        );

        buttonCreator(pause,
                "Tetris\\data\\images\\pause.png",
                "Tetris\\data\\images\\play.png",
                gamePanel.isPause(),
                "pause"
        );

        buttonPanel.setBackground(new Color(66, 66, 66));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        buttonPanel.setLayout(new GridLayout(0, 3, 0, 30));
        buttonPanel.add(pause);
        buttonPanel.add(mute);
        buttonPanel.add(SMSwitcher);

        gridPanel.setLayout(new GridLayout(3, 0, 5, 0));
        gridPanel.setBackground(new Color(66, 66, 66));
        gridPanel.add(score);
        gridPanel.add(highScore);
        gridPanel.add(levelChoser);

        borderPanel1.setLayout(new BorderLayout());
        borderPanel1.setBackground(new Color(66, 66, 66));
        borderPanel1.add(gridPanel, "North");
        borderPanel1.add(timerLabel, "South");

        borderPanel.setLayout(new GridLayout(3, 0, 0, 30));
        borderPanel.setBackground(new Color(66, 66, 66));
        borderPanel.add(borderPanel1);
        borderPanel.add(futureFigures);
        borderPanel.add(buttonPanel);

        leftPanel.setLayout(new FlowLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));
        leftPanel.setBackground(new Color(66, 66, 66));
        leftPanel.add(borderPanel, "Left");

        masterPanel.setLayout(new BorderLayout());
        masterPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        masterPanel.setBackground(new Color(66, 66, 66));
        masterPanel.add(leftPanel, "West");
        masterPanel.add(gamePanel, "Center");
        //------------------------------/UI---------------------------------


        //---------------------------POST LOGIC-----------------------------
        gamePanel.scoreEditor(score);
        gamePanel.highScoreEditor(highScore);
        gamePanel.setLevel(levelChoser);
        gamePanel.setFutureFigures(futureFigures);
        gamePanel.highScoreLogic();
        observer.start();
        this.timer.start();
        //---------------------------/POST LOGIC-----------------------------


        //----------------------------FRAME---------------------------------
        setTitle("Tetris 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(masterPanel);

        if (gamePanel.getHighScore() > 9999) {
            setSize(700 + highScore.getText().substring(11,
                    highScore.getText().length() - 1).length() * 10, 726);
        } else {
            setSize(700 + 40, 726);
        }
        setUndecorated(false);
        setResizable(false);
        setVisible(true);
        setIconImage(iconImage);

        gamePanel.requestFocus();
        //----------------------------/FRAME--------------------------------
    }
    public static void main(final String[] args) {new Window();}
}

