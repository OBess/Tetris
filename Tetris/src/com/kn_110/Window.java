package com.kn_110;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// Interface Class
class Window extends JFrame {

    //------------------------INITIALIZATION----------------------------
    private final JPanel masterPanel;
    private final JPanel leftPanel;
    private final JPanel gridPanel;
    private final JPanel borderPanel;
    private final JPanel buttonPanel;
    private final GamePanel gamePanel;
    private final FutureFigures futureFigures;

    private final JLabel timerLabel;
    private final JLabel score;
    private final JLabel highScore;

    private final JButton mute;
    private final ImageIcon muteIcon;
    private final ImageIcon unMuteIcon;

    private final Timer timer;
    private final Timer observer;

    private boolean buttonState;

    private int tm = 0;
    //------------------------/INITIALIZATION---------------------------


    Window() {


        //------------------------NEW OBJECTS---------------------------
        masterPanel = new JPanel();
        leftPanel = new JPanel();
        gridPanel = new JPanel();
        borderPanel = new JPanel();
        buttonPanel = new JPanel();

        score = new JLabel();
        highScore = new JLabel();
        timerLabel = new JLabel();

        mute = new JButton();
        muteIcon = new ImageIcon("Tetris\\images\\Mute_zipped.png");
        unMuteIcon = new ImageIcon("Tetris\\images\\unMute_zipped.png");

        gamePanel = new GamePanel();
        futureFigures = new FutureFigures();
        //------------------------/NEW OBJECTS---------------------------


        //------------------------------UI-------------------------------
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.getEndGame()) {
                    if (!gamePanel.getPause()) {
                        String time = String.format("%02d:%02d:%02d", tm / 360, tm / 60, tm % 60);
                        tm += 1;
                        timerLabel.setText("Time: " + time);
                    }
                }
            }
        });

        observer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gamePanel.getEndGame()) {
                    tm = 0;
                    timerLabel.setText("Time: 00:00:00");
                    timerLabel.updateUI();
                }
                if (gamePanel.getMuted()) {
                    mute.setIcon(muteIcon);
                } else {
                    mute.setIcon(unMuteIcon);
                }
            }
        });

        timerLabel.setText("Time: 00:00:00");
        timerLabel.setForeground(new Color(135, 135, 135, 255));
        timerLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        score.setText("Score: 0");
        score.setFont(new Font("Dialog", Font.BOLD, 20));
        score.setForeground(new Color(255, 255, 255));

        highScore.setText("High Score: 0");
        highScore.setFont(new Font("Dialog", Font.BOLD, 20));
        highScore.setForeground(new Color(222, 222, 222));

        mute.setIcon(unMuteIcon);
        mute.setSize(100, 100);
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonState = !buttonState;
                if (!buttonState)
                    mute.setIcon(muteIcon);
                else
                    mute.setIcon(unMuteIcon);
                gamePanel.mute();
                gamePanel.requestFocus();
            }
        });
        mute.setContentAreaFilled(false);
        mute.setFocusPainted(false);
        mute.setOpaque(true);
        mute.setBackground(new Color(66, 66, 66));
        mute.setBorder(null);

        buttonPanel.setBackground(new Color(66, 66, 66));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        buttonPanel.add(mute);

        gridPanel.setLayout(new GridLayout(3, 0, 5, 0));
        gridPanel.setBackground(new Color(66, 66, 66));
        gridPanel.add(score);
        gridPanel.add(highScore);

        borderPanel.setLayout(new BorderLayout());
        borderPanel.setBackground(new Color(66, 66, 66));
        borderPanel.add(gridPanel, "North");
        borderPanel.add(futureFigures, "Center");
//        borderPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        leftPanel.setLayout(new FlowLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));
        leftPanel.setBackground(new Color(66, 66, 66));
        leftPanel.add(borderPanel, "Left");

        // masterPanel where all interface and game elements will locate
        masterPanel.setLayout(new BorderLayout());
        masterPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        masterPanel.setBackground(new Color(66, 66, 66));
        masterPanel.add(leftPanel, "West");
        masterPanel.add(gamePanel, "Center");
        //------------------------------/UI---------------------------------


        //----------------------------FRAME---------------------------------
        setTitle("Tetris0.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(masterPanel);
        setSize(699, 726);
        setResizable(false);
        setVisible(true);
        //----------------------------/FRAME--------------------------------


        //---------------------------POST LOGIC-----------------------------
        gamePanel.scoreEditor(score);
        gamePanel.highScoreEditor(highScore);
        gamePanel.highScoreLogic();
        gamePanel.requestFocus();

        observer.start();
        this.timer.start();

        try{
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_SPACE);
            rb.keyRelease(KeyEvent.VK_SPACE);
        } catch (AWTException e) {}
        //---------------------------/POST LOGIC-----------------------------

    }


    public static void main(final String[] args) {
        new Window();
    }
}

