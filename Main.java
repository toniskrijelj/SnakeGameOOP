import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Queue;
import java.util.Random;
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
    private Collisable[][] collisables;
    public static void main(String[] args) {
        Painter painter = new Painter();
        Snake snake = new Snake();
        Paint paint = new Paint();
        JFrame obj = new JFrame();
        for(int i = 0; i <= 64; i++){
            MatrixMap.add(new Wall(i*20, 0));
            MatrixMap.add(new Wall(i*20, 720));
        }
        for(int i = 1; i < 36; i++){
            MatrixMap.add(new Wall(0, i*20));
            MatrixMap.add(new Wall(1280, i*20));
        }
        painter.addKeyListener(snake);
        painter.setFocusable(true);
        painter.setFocusTraversalKeysEnabled(false);
        obj.setBounds(0, 0, 1316, 779);
        obj.setBackground(Color.BLACK);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.add(painter);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MatrixMap{
    private static _Object[][] map = new _Object[37][65];
    private static MatrixMap matrixMap;

    MatrixMap(){
        matrixMap = this;
    }

    static public void add(_Object object){
        map[object.GetY()/20][object.GetX()/20] = object;
    }

    static public void updateSnake(SnakeBody snakeHead, SnakeBody snakeTail){
        map[snakeHead.GetY()/20][snakeHead.GetX()/20] = snakeHead;
        map[snakeTail.GetY()/20][snakeTail.GetX()/20] = null;
    }

    static public _Object getObject(SnakeBody snakeHead){
        return map[snakeHead.GetY()/20][snakeHead.GetX()/20];    
    }
}

interface Collisable{
    public void OnCollide();
}

class Food extends _Object implements Collisable{
    
    private ImageIcon foodIcon = new ImageIcon("Food.png");
    private static Random r = new Random();
    private static int low = 36;
    private static int high = 64;

    Food() {
        super((r.nextInt(high-1)+1)*20, (r.nextInt(low-1)+1)*20);
        MatrixMap.add(this);
        Painter.addToPaint(this, foodIcon);
        System.out.println("HRANA " + this.GetX() + " " +  this.GetY());
    }

    @Override
    public void OnCollide() {
        Snake.Hrana();
    }
}

class Wall extends _Object implements Collisable{

    private static ImageIcon wallIcon = new ImageIcon("Wall.png");

    public Wall(int x, int y) {
        super(x, y);
        Painter.addToPaint(this, wallIcon);
    }

    @Override
    public void OnCollide() {
        System.out.println("DEAD");
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
    public void SetX(int x) { this.x = x; }
    public void SetY(int y) { this.y = y; }
}

class Painter extends JPanel{
    private static final long serialVersionUID = 1L;

    private static Painter painter;

    private static Vector<Change> toPaint = new Vector<Change>();

    Painter(){
        painter = this;
    }

    private static class Change{
        public _Object coordinates;
        public ImageIcon imageIcon;

        Change(_Object coordinates, ImageIcon imageIcon){
            this.coordinates = coordinates;
            this.imageIcon = imageIcon;
        }
    }

    static void addToPaint(_Object coordinates, ImageIcon imageIcon){
        toPaint.add(new Change(coordinates, imageIcon));
        painter.repaint();
    }

    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < toPaint.size(); i++){
            Painter.toPaint.get(i).imageIcon.paintIcon(this, g, Painter.toPaint.get(i).coordinates.GetX(), Painter.toPaint.get(i).coordinates.GetY());
        }
        toPaint.clear();
    }
}

class SnakeBody extends _Object implements Collisable{

    SnakeBody(int x, int y) {
        super(x, y);
    }

    @Override
    public void OnCollide() {
        System.out.println("BODYSNAKE");
    }
}

class Snake implements KeyListener, ActionListener{

    private static Food food;
    private static boolean hrana = false;

    private static ImageIcon snakeHead;
    private static ImageIcon snakeTail;

    private int snakeLenght = 3; // krece od nula
    private Vector<SnakeBody> snakeBody;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false; 
    private boolean down = false;

    private static Timer timer;
    private static int delay = 100;
    private int size = 20;

    public Snake(){
        food = new Food();
        snakeBody = new Vector<SnakeBody>();
        snakeHead = new ImageIcon("Body.png");
        snakeTail = new ImageIcon("Tail.png");
        timer = new Timer(delay, this);
        for(int i = 0; i <= snakeLenght; i++){
            snakeBody.add(new SnakeBody(i*size, 20));
            Painter.addToPaint(snakeBody.get(i), snakeHead);
        }
        timer.start();
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
        Collisable object = (Collisable) MatrixMap.getObject(head);
        if(object != null){
            object.OnCollide();
        }
        MatrixMap.updateSnake(head, snakeBody.get(0));
        snakeBody.add(head); // dodao novi i prebacio da je glava
        Painter.addToPaint(head, snakeHead);
        if(!hrana){
            Painter.addToPaint(snakeBody.get(0), snakeTail);
            snakeBody.remove(0); // obrisao poslednji
        }
        else
        {
            snakeLenght++;
            hrana = false;
            food = new Food();
        }
    }

    public static void Hrana(){
        hrana = true;
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