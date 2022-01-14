package Snake_Game;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Math;

public class GamePannel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 80;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 5;
    int applesEaten = 20;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePannel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        int red = 0, green = 0, blue = 0;

        if(running){
            for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
    
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    
            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    if(applesEaten >= 0 && applesEaten < 10){       //blue
                        red = 50;
                        green = 50;
                        blue = 220;
                        g.setColor(new Color(red, green, blue));
                    }
                    else if(applesEaten >= 10 && applesEaten < 20){ //green
                        red = 50;
                        green = 220;
                        blue = 50;
                        g.setColor(new Color(red, green, blue));
                    }
                    else if(applesEaten >= 20 && applesEaten < 35){ //yellow
                        red = 220;
                        green = 240;
                        blue = 50;
                        g.setColor(new Color(red, green, blue));
                    }
                    else if(applesEaten >= 35 && applesEaten < 50){ //orange
                        red = 240;
                        green = 110;
                        blue = 30;
                        g.setColor(new Color(red, green, blue));
                    }
                    else if(applesEaten >= 50 && applesEaten < 100){    //red
                        red = 240;
                        green = 30;
                        blue = 30;
                        g.setColor(new Color(red, green, blue));
                    }
                    else{                                           //white
                        red = 255;
                        green = 255;
                        blue = 255;
                        g.setColor(new Color(red, green, blue));
                    }
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    if(applesEaten >= 0 && applesEaten < 10){           //blue
                        g.setColor(new Color(red, green + (int)(Math.log(i) * 35), blue));
                    }
                    else if(applesEaten >= 10 && applesEaten < 20){     //green
                        g.setColor(new Color(red + (int)(Math.log(i) * 50), green, blue));
                    }
                    else if(applesEaten >= 20 && applesEaten < 35){     //yellow
                        g.setColor(new Color(red, green - (int)(Math.log(i) * 50), blue));
                    }
                    else if(applesEaten >= 35 && applesEaten < 50){     //orange
                        g.setColor(new Color(red, green + (int)(Math.log(i) * 50), blue));
                    }
                    else if(applesEaten >= 50 && applesEaten < 100){    //red
                        g.setColor(new Color(red, green, blue + (int)(Math.log(i) * 50)));
                    }
                    else{                                               //white
                        g.setColor(new Color(red - (int)(Math.log(i) * 50), green - (int)(Math.log(i) * 50), blue - (int)(Math.log(i) * 50)));
                    }
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //score screen text
            g.setColor(Color.red);
            g.setFont( new Font("Default", Font.BOLD, 15));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE = " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE = " + applesEaten)) / 2, SCREEN_HEIGHT - (UNIT_SIZE / 2));
        }
        else {
            gameOver(g);
        }
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void newApple(){
        boolean valid = true;
        do{
            valid = true;
            appleX = random.nextInt((int)SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt((int)SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            for(int i = bodyParts; i > 0; i--){
                if(appleX == x[i] && appleY == y[i]){
                    valid = false;
                }
            }
        }while(valid == false);
    

    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        //check if head runs into body
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if head hits left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head hits right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head hits top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head hits bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //game over screen text
        g.setColor(Color.red);
        g.setFont( new Font("Default", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);

        //score screen text
        g.setColor(Color.red);
        g.setFont( new Font("Default", Font.BOLD, 15));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("SCORE = " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("SCORE = " + applesEaten)) / 2, SCREEN_HEIGHT / 2 + UNIT_SIZE);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
    
}
