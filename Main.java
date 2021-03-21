import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame obj = new JFrame();
        ImageIcon backGround = new ImageIcon("sprites/Background.png");
        Painter painter = new Painter();
        Painter.addToPaint(new _Object(0, 0), backGround);
        Snake snake = new Snake(2, 1);
        //Paint paint = new Paint();
        for(int i = 0; i < 5; i++){
            new Food();
        }
        painter.addKeyListener(snake);
        painter.setFocusable(true);
        painter.setFocusTraversalKeysEnabled(false);
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setVisible(true);
        obj.add(painter);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MatrixMap{
    private static _Object[][] map = new _Object[22][46];
    private static MatrixMap matrixMap;

    MatrixMap(){
        matrixMap = this;
    }

    static public void add(_Object object){
        map[object.GetY()/40-1][object.GetX()/40-1] = object;
    }

    static public void updateSnake(SnakeBody snakeHead, SnakeBody snakeTail){
        map[snakeHead.GetY()/40-1][snakeHead.GetX()/40-1] = snakeHead;
        map[snakeTail.GetY()/40-1][snakeTail.GetX()/40-1] = null;
    }

    static public _Object getObject(_Object snakeHead){
        return map[snakeHead.GetY()/40-1][snakeHead.GetX()/40-1];    
    }
}

interface Collisable{
    public void OnCollide();
}

class Teleport extends _Object implements Collisable{

    public Teleport(int x, int y) {
        super(x, y);
    }

    @Override
    public void OnCollide() {

    }
}

class Food extends _Object implements Collisable{
    
    private ImageIcon foodIcon = new ImageIcon("sprites/Orange.png");
    private static Random r = new Random();
    private static int lowX = 1;
    private static int highX = 47;
    private static int lowY = 1;
    private static int highY = 23;

    Food() {
        super((r.nextInt(highX-lowX)+lowX)*40, (r.nextInt(highY-lowY)+lowY)*40);
        while(MatrixMap.getObject(this)!=null){
            x = (r.nextInt(highX-lowX)+lowX)*40;
            y = (r.nextInt(highY-lowY)+lowY)*40;
        };
        MatrixMap.add(this);
        Painter.addToPaint(this, foodIcon);
    }

    @Override
    public void OnCollide() {
        new Food();
        Snake.Food();
    }
}

class Wall extends _Object implements Collisable{

    private static ImageIcon wallIcon = new ImageIcon("sprites/Wall.png");

    public Wall(int x, int y) {
        super(x, y);
        Painter.addToPaint(this, wallIcon);
    }

    @Override
    public void OnCollide() {
        Snake.GameOver();
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
    
    public void moveX(int x){
        this.x += x;
    }

    public void moveY(int y){
        this.y += y;
    }
    public int GetX() { return x; }
    public int GetY() { return y; }
    public void SetX(int x) { this.x = x; }
    public void SetY(int y) { this.y = y; }
}

class Painter extends JPanel{
    private static final long serialVersionUID = 1L;

    private static Painter painter;
    private static Vector<Base> toPaint = new Vector<Base>();

    Font arcadeClassic;
    Color fontColor = new Color(190, 0, 148);
    Color block = new Color(11, 37, 61);

    Painter(){
        painter = this;
        try {
            arcadeClassic = Font.createFont(Font.TRUETYPE_FONT, new File("small_pixel-7.ttf")).deriveFont(70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeClassic);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static class Base{
        boolean type;
        Base(boolean type){
            this.type = type;
        }
        public boolean getType(){
            return type;
        }
    }

    private static class Change<T> extends Base{
        public _Object coordinates;
        public T field;

        Change(_Object coordinates, T field){
            super(field instanceof String);
            this.coordinates = coordinates;
            this.field = field;
        }
    }

    static void addToPaint(_Object coordinates, ImageIcon imageIcon){
        toPaint.add(new Change<ImageIcon>(coordinates, imageIcon));
        painter.repaint();
    }

    static void addToWrite(_Object coordinates, String string){
        toPaint.add(new Change<String>(coordinates, string));
        painter.repaint();
    }

    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < toPaint.size(); i++){
            if(toPaint.get(i).getType()){
                Change<String> nes = (Change<String>) toPaint.get(i); // ne jedi govna
                g.setColor(block);
                g.fillRect(nes.coordinates.x, 940, 72*nes.field.length(), 72);// 940 vrh donjeg taba sta god
                g.setColor(fontColor);
                g.setFont(arcadeClassic);
                g.drawString(nes.field, nes.coordinates.x, nes.coordinates.y);
            }else{
                Change<ImageIcon> nes = (Change<ImageIcon>) toPaint.get(i); // ne jedi govna
                nes.field.paintIcon(this, g, nes.coordinates.GetX(), nes.coordinates.GetY());
            }
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
        Snake.GameOver();
    }
}

class BorderLines{
    private _Object[] borderLines = new _Object[4];
    private ImageIcon[] lines;

    BorderLines(_Object object, ImageIcon[] lines){
        this.lines = lines;
        borderLines[0] = new _Object(24, object.GetY());
        borderLines[1] = new _Object(1888, object.GetY());
        borderLines[2] = new _Object(object.GetX(), 24);
        borderLines[3] = new _Object(object.GetX(), 928);
    }
    public void update(_Object object){
        repaint(lines[1], lines[3]);
        borderLines[0].y = borderLines[1].y = object.GetY();
        borderLines[2].x = borderLines[3].x = object.GetX();
        repaint(lines[0], lines[2]);
    }
    private void repaint(ImageIcon line1, ImageIcon line2){ // repa int XDXDXDXDXD Toni 2k21
        Painter.addToPaint(new _Object(borderLines[0].x, borderLines[0].y), line1);
        Painter.addToPaint(new _Object(borderLines[1].x, borderLines[1].y), line1);
        Painter.addToPaint(new _Object(borderLines[2].x, borderLines[2].y), line2);
        Painter.addToPaint(new _Object(borderLines[3].x, borderLines[3].y), line2);
    }
}
class DisplayScore implements FoodListener{

    private static int score = -1; 
    private static _Object object;   

    DisplayScore(){
        Snake.AddFoodListener(this);
        object = new _Object(40, 995);
        OnEaten();
    }

    @Override
    public void OnEaten() {
        score++;
        Painter.addToWrite(object, "SCORE:" + String.valueOf(score));
    }
}

class Arrows extends _Object{

    private static ImageIcon[] arrowsIcons;
    private static Arrows arrows;

    Arrows() {
        super(1422, 943);
        arrows = this;
        arrowsIcons = new ImageIcon[4];
    }

    public static void setArrowIcon(int index, String imageIcon){
        arrowsIcons[index] = new ImageIcon(imageIcon);
    }

    public static _Object getArrowObject(){
        return arrows;
    }

    public static ImageIcon getArrowIcon(int index){
        return arrowsIcons[index];
    }
}

class Input extends _Object{

    private static Vector<Character> inputs = new Vector<Character>();
    private static Input input;
    private static ImageIcon nothing;
    public Input() {
        super(1532, 940);
        input = this;
        nothing = new ImageIcon("sprites/Nothing.png");
        for(int i = 0; i < 4; i++){
            Painter.addToPaint(new _Object(input.x+80*i, input.y), nothing);
        }
    }
    public static void addInput(char c){
        if(inputs.size()<4){
            for (Character character : inputs) {
                if(c == character)
                {
                    return;
                }
            }
            inputs.add(c);
            update();
        }
    }
    private static void update(){
        for(int i = 0; i < inputs.size();i++){
            Painter.addToWrite(new _Object(80*i+input.x+14, input.y+55), String.valueOf(inputs.get(i)).toUpperCase());
        }
    }
    public static void removeInput(char c){
        if(inputs.removeElement(c)){
            Painter.addToPaint(new _Object(80*inputs.size()+input.x, input.y), nothing);
            update();
        }
    }
}

interface FoodListener{
    public void OnEaten();
}

class Snake implements KeyListener, ActionListener{

    private static Vector<FoodListener> foodListeners;
    
    private static Vector<KeyEvent> keyEvents;

    private static ImageIcon snakeHead;
    private static ImageIcon afterHead1;
    private static ImageIcon afterHead2;
    private static boolean headTrail = true; 

    private static ImageIcon[] lines;
    private static BorderLines borderLines;

    private static ImageIcon snakeTail1;
    private static ImageIcon snakeTail2;
    private static boolean tailTrail = true;

    private int snakeLenght = 10;

    private Vector<SnakeBody> snakeBody;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false; 
    private boolean down = false;
    private static boolean food = false;

    private static Timer timer;
    private static int delay = 100;
    private static int size = 40;

    public static void Food(){
        food = true;
    }

    public static void GameOver(){
    }

    public static void AddFoodListener(FoodListener foodListener){
        foodListeners.add(foodListener);
    }

    public Snake(int x, int y){
        keyEvents = new Vector<KeyEvent>(2);
        foodListeners  = new Vector<FoodListener>();
        snakeBody = new Vector<SnakeBody>();
        snakeHead = new ImageIcon("sprites/snakeHead.png");
        afterHead1 = new ImageIcon("sprites/afterHead1.png");
        afterHead2 = new ImageIcon("sprites/afterHead2.png");
        snakeTail1 = new ImageIcon("sprites/snakeTail1.png");
        snakeTail2 = new ImageIcon("sprites/snakeTail2.png");
        lines = new ImageIcon[4];
        new Arrows();
        new DisplayScore();
        new Input();
        for(int i = 0; i < 4; i++){
            lines[i] = new ImageIcon("sprites/Line" + (i+1) + ".png");
            Arrows.setArrowIcon(i, "sprites/Arrow" + (i+1) + ".png");
        }
        snakeBody.add(new SnakeBody(x*size, y*size));
        borderLines = new BorderLines(snakeBody.get(0), lines);
        Painter.addToPaint(snakeBody.get(0), snakeHead);
        Painter.addToPaint(Arrows.getArrowObject(), Arrows.getArrowIcon(2));
        if(snakeBody.lastElement().GetX()/40%2!=snakeBody.lastElement().GetY()/40%2){
            headTrail = false;
        }
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SnakeBody head = new SnakeBody(snakeBody.lastElement().GetX(), snakeBody.lastElement().GetY());
        if(headTrail){
            Painter.addToPaint(snakeBody.lastElement(), afterHead1); 
            headTrail = false;
        }else{
            Painter.addToPaint(snakeBody.lastElement(), afterHead2);
            headTrail = true;
        }
        if(keyEvents.size()>0){
            KeyEvent keyEvent = keyEvents.get(0);
            if(keyEvent.getKeyCode() == KeyEvent.VK_A) {
                if(!right){
                    left = true;
                    up = false;
                    down = false;
                    Painter.addToPaint(Arrows.getArrowObject(), Arrows.getArrowIcon(0));
                }
            }
            else if(keyEvent.getKeyCode() == KeyEvent.VK_W) {
                if(!down){
                    up = true;
                    right = false;
                    left = false;
                    Painter.addToPaint(Arrows.getArrowObject(), Arrows.getArrowIcon(1));
                }
            }
            else if(keyEvent.getKeyCode() == KeyEvent.VK_S) {
                if(!up){
                    down = true;
                    left = false;
                    right = false;
                    Painter.addToPaint(Arrows.getArrowObject(), Arrows.getArrowIcon(3));
                }
            }
            else if(keyEvent.getKeyCode() == KeyEvent.VK_D) {
                if(!left){
                    right = true;
                    up = false;
                    down = false;
                    Painter.addToPaint(Arrows.getArrowObject(), Arrows.getArrowIcon(2));
                }
            }
            keyEvents.remove(0);
        }
        if(right){
            if(head.GetX()<1840)
            {
                head.moveX(size);
            }
            else
            {
                head.moveX(-1800);
            }
        }
        else if(left){
            if(head.GetX()>40)
            {
                head.moveX(-size);
            }
            else
            {
                head.moveX(1800);
            }
        }
        else if(up){
            if(head.GetY()>40)
            {
                head.moveY(-size);
            }
            else
            {
                head.moveY(840);
            }
        }
        else if(down){
            if(head.GetY()<880)
            {
                head.moveY(size);
            }
            else
            {
                head.moveY(-840);
            }
        }
        Collisable object = (Collisable) MatrixMap.getObject(head);
        if(object != null){
            object.OnCollide();
        }
        snakeBody.add(head);
        MatrixMap.updateSnake(head, snakeBody.get(0));
        Painter.addToPaint(head, snakeHead);
        borderLines.update(head);
        if(!food && snakeBody.size()>snakeLenght){
            if(tailTrail){
                Painter.addToPaint(snakeBody.get(0), snakeTail1);
                tailTrail = false;
            }else{
                Painter.addToPaint(snakeBody.get(0), snakeTail2);
                tailTrail = true;
            }
            snakeBody.remove(0);
        }
        if(food)
        {
            for(FoodListener foodListener : foodListeners) {
                foodListener.OnEaten();
            }
            snakeLenght++;
            food = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keyEvents.size()<2)
        {
            keyEvents.add(e);
        }
        Input.addInput(e.getKeyChar());
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        Input.removeInput(e.getKeyChar());
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