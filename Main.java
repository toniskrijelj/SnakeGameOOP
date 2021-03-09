
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.awt.Graphics;
//import java.util.Timer;

import javax.swing.Timer;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //Gameplay gameplay = new Gameplay();
        Snake snake = new Snake();
        JFrame obj = new JFrame();
        obj.setBounds(0, 0, 1316, 779); // 280
        obj.setBackground(Color.BLACK);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.add(snake);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class _Object
{
    protected int x, y;
    
    public _Object(int x, int y)
    {
        Move(x, y);
    }
    
    public void Move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void addX(int size){
        this.x += size;
    }

    public void addY(int size){
        this.y += size;
    }

    public int GetX() { return x; }
    public int GetY() { return y; }
}

class SnakeBody extends _Object{

    SnakeBody(int x, int y) {
        super(x, y);
    }
    SnakeBody getSnakeBody(){
        return this;
    }
}

class Snake extends JPanel implements KeyListener, ActionListener{
    private static final long serialVersionUID = 1L;
    
    private Stack<SnakeBody> fix;

    private ImageIcon snakeHead;
    private ImageIcon snakeTail;

    private int snakeLenght = 30; // krece od nula
    private Vector<SnakeBody> snakeBody;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false; 
    private boolean down = false;

    private Timer timer;
    private int delay = 100;
    private static int size = 20;
    private boolean started = false;

    public Snake(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false); 
        snakeBody = new Vector<SnakeBody>();
        for(int i = 0; i <= snakeLenght; i++){
            snakeBody.add(new SnakeBody(i*size, 20));
        }
        repaint();
        snakeHead = new ImageIcon("Body.png");
        snakeTail = new ImageIcon("Tail.png");
        timer = new Timer(delay, this);
        fix = new Stack<SnakeBody>();
        timer.start();
    }

    public void paint(Graphics g){
        if(!started){
            for(int i = 0; i <= snakeLenght; i++){
                snakeHead.paintIcon(this, g, snakeBody.get(i).GetX(), snakeBody.get(i).GetY());
            }
            started = true;
        }
        else{
            while(!fix.empty()){
                SnakeBody head = fix.pop();
                SnakeBody tail = fix.pop();
                snakeTail.paintIcon(this, g, tail.GetX(), tail.GetY());
                snakeHead.paintIcon(this, g, head.GetX(), head.GetY());
            }
        }
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, 1280, 720);
        //snakeImage.paintIcon(this, g, body[0], body[1]);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SnakeBody head = new SnakeBody(snakeBody.get(snakeLenght).GetX(), snakeBody.get(snakeLenght).GetY());
        if(right){
            if(head.GetX()<1280)
            {
                head.addX(size);
            }
            else
            {
                head.addX(-1280);
            }
        }
        else if(left){
            if(head.GetX()>0)
            {
                head.addX(-size);
            }
            else
            {
                head.addX(1280);
            }
        }
        else if(up){
            if(head.GetY()>0)
            {
                head.addY(-size);
            }
            else
            {
                head.addY(720);
            }
        }
        else if(down){
            if(head.GetY()<720)
            {
                head.addY(size);
            }
            else
            {
                head.addY(-720);
            }
        }
        snakeBody.add(head); // dodao novi i prebacio da je glava
        fix.add(snakeBody.get(0));
        fix.add(head);
        snakeBody.remove(0); // obrisao poslednji         
        repaint();

        System.out.println(" X " + head.GetX() + " Y " + head.GetY());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            if(!right){
                left = true;
                up = false;
                down = false;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_W) {
            if(!down){
                up = true;
                right = false;
                left = false;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_S) {
            if(!up){
                down = true;
                left = false;
                right = false;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_D) {
            if(!left){
                right = true;
                up = false;
                down = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}


















/*
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
        if(moves == 0){
            snakeXLenght[2] = 50;
            snakeXLenght[1] = 75;
            snakeXLenght[0] = 100;
            
            snakeYLenght[2] = 100;
            snakeYLenght[1] = 100;
            snakeYLenght[0] = 100;
               
        }

        draw title image border
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);

        draw the title image
        titleImage = new ImageIcon("path.png");
        titleImage.paintIcon(this, g, 25, 11);

        draw border for gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 27, 851, 577);
        
        draw background for the gameplay
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        rightMouth = new ImageIcon("path.png");
        rightMouth.paintIcon(this, , snakeXLenght[0], snakeYLenght[0]);

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
        g.dispose();
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
*/