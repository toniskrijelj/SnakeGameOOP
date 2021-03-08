import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.awt.Graphics;
//import java.util.Timer;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //Gameplay gameplay = new Gameplay();
        Test test = new Test();
        JFrame obj = new JFrame();
        obj.setBounds(0, 0, 1280, 720); // 280
        obj.setBackground(Color.black);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.add(test);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Test extends JPanel{
    private static final long serialVersionUID = 1L;
    private ImageIcon body = new ImageIcon("BlackBlock.png");

    public void paint(Graphics g){
        body.paintIcon(this, g, 60, 60);
    }
}

class Gameplay extends JPanel implements KeyListener, ActionListener{

    private int[] snakeXLenght = new int[750];
    private int[] snakeYLenght = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private int moves = 0;

    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;

    private int lenghtOfSnake = 3;
    
    private Timer timer;
    private int delay = 100;

    private ImageIcon snakeImage;

    private ImageIcon titleImage;

    public Gameplay(){
        //addKeyListener(this);
        //setFocusable(true);
        //setFocusTraversalKeysEnabled(false);
        //timer = new Timer(delay, this);
        //timer.start();
    }

    public void paint(Graphics g){

        /*
        if(moves == 0){
            snakeXLenght[2] = 50;
            snakeXLenght[1] = 75;
            snakeXLenght[0] = 100;
            
            snakeYLenght[2] = 100;
            snakeYLenght[1] = 100;
            snakeYLenght[0] = 100;
               
        }
        */

        //draw title image border
        //g.setColor(Color.white);
        //g.drawRect(24, 10, 851, 55);

        //draw the title image
        //titleImage = new ImageIcon("path.png");
        //titleImage.paintIcon(this, g, 25, 11);

        //draw border for gameplay
        //g.setColor(Color.WHITE);
        //g.drawRect(24, 27, 851, 577);
        
        //draw background for the gameplay
        //g.setColor(Color.BLACK);
        //g.fillRect(25, 75, 850, 575);

        //rightMouth = new ImageIcon("path.png");
        //rightMouth.paintIcon(this, , snakeXLenght[0], snakeYLenght[0]);

        /*
        for(int a = 0; a < lenghtOfSnake; a++){
            if(a == 0 && right){
                rightMouth = new ImageIcon("path.png");
                rightMouth.paintIcon(this, , snakeXLenght[a], snakeYLenght[a]);
            }
            else if(a == 0 && left){
                leftMouth = new ImageIcon("path.png");
                leftMouth.paintIcon(this, , snakeXLenght[a], snakeYLenght[a]);
            }else if(a == 0 && up){
                upMouth = new ImageIcon("path.png");
                upMouth.paintIcon(this, , snakeXLenght[a], snakeYLenght[a]);
            }else if(a == 0 && down){
                downMouth = new ImageIcon("path.png");
                downMouth.paintIcon(this, , snakeXLenght[a], snakeYLenght[a]);
            }
            if(a!=0){
                snakeImage = new ImageIcon("path.png");
                snakeImage.paintIcon(this, g, snakeXLenght[a], snakeYLenght[a]);
            }
        }
        */
        //g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moves++;
            right = true;
            if(!left){
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moves++;
            right = true;
            if(!left){
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            moves++;
            right = true;
            if(!left){
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moves++;
            right = true;
            if(!left){
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}