import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class Main extends Application {
    public static int br = 0;

    public static void TheEnd() {

        JFrame frame = new JFrame("Game Over");
        JPanel panel = new JPanel();
        // frame.getContentPane();
        JLabel label = new JLabel("G A M E   O V E R");
        Dimension size = label.getPreferredSize();
        label.setBounds(200, 50, 100, 100);
        JButton SecondLifeB = new JButton("Second Chance");
        SecondLifeB.setBounds(75, 150, 150, 75);
        JButton exitB = new JButton("EXIT");

        panel.setLayout(null);
        panel.add(label);
        if (br < 2) {
            panel.add(SecondLifeB);
            exitB.setBounds(275, 150, 150, 75);
        } else {
            exitB.setBounds(150, 150, 200, 75);
        }

        panel.add(exitB);
        SecondLifeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == SecondLifeB) {
                    frame.dispose();
                    game();
                }
            }
        });
        exitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exitB) {
                    frame.dispose();
                }
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public static void game() {
        br++;
        JFrame obj = new JFrame();
        if (br < 3) {

            ImageIcon backGround = new ImageIcon("sprites/Background.png");
            Painter painter = new Painter();
            Painter.addToPaint(new _Object(0, 0), backGround);
            Snake snake = new Snake(2, 1, obj);
            new VibeCheck();
            // Paint paint = new Paint();
            for (int i = 0; i < 5; i++) {
                new Food();
            }
            painter.addKeyListener(snake);
            painter.setFocusable(true);
            painter.setFocusTraversalKeysEnabled(false);
            obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
            obj.setVisible(true);
            obj.add(painter);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            /*
             * while(true){ if(Snake.getBr() == 1){ obj.dispose(); TheEnd(); break; } }
             */
        } else {
            obj.dispose();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();
        Label title = new Label("S N A K E");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setMinHeight(75);
        pane.setTop(title);

        Button start = new Button("Start");
        start.setMinSize(100, 40);
        pane.setLeft(start);

        Button quit = new Button("Quit");
        quit.setMinSize(100, 40);
        pane.setRight(quit);

        Button readFile = new Button("About us");
        readFile.setMinSize(100, 40);
        pane.setCenter(readFile);

        BorderPane.setAlignment(start, Pos.CENTER);
        BorderPane.setAlignment(quit, Pos.CENTER);

        readFile.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1) {
                try {
                    // constructor of file class having file as argument
                    File file = new File("D:\\Skola\\OOP\\SnakeGameOOP\\ReadMe.txt");
                    if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
                    {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists()) // checks file exists or not
                        desktop.open(file); // opens the specified file
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        start.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1) {
                stage.close();
                game();

            }
        });

        quit.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1) {
                stage.close();
            }
        });

        Scene scene = new Scene(pane, 320, 240);

        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("started");
        launch(args);
        System.out.println("finish");

    }

}

class VibeCheck implements ActionListener {
    private static Timer timer;
    private static int delay = 500;

    VibeCheck() {
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Painter.budza();
        timer.stop();
    }
}

class MatrixMap {

    private static _Object[][] map = new _Object[22][46];
    private static MatrixMap matrixMap;

    public MatrixMap() {
        matrixMap = this;
    }

    static public void add(_Object object) {
        map[object.GetY() / 40 - 1][object.GetX() / 40 - 1] = object;
    }

    static public void updateSnake(SnakeBody head, SnakeBody snakeTail) {
        map[head.GetY() / 40 - 1][head.GetX() / 40 - 1] = head;
        map[snakeTail.GetY() / 40 - 1][snakeTail.GetX() / 40 - 1] = null;
    }

    static public _Object getObject(_Object head) {
        return map[head.GetY() / 40 - 1][head.GetX() / 40 - 1];
    }
}

interface Collisable {
    public void OnCollide();
}

class Teleport extends _Object implements Collisable {

    public Teleport(int x, int y) {
        super(x, y);
    }

    @Override
    public void OnCollide() {
    }
}

class Food extends _Object implements Collisable {

    private ImageIcon foodIcon = new ImageIcon("sprites/Orange.png");
    private static Random r = new Random();
    private static int lowX = 1;
    private static int highX = 47;
    private static int lowY = 1;
    private static int highY = 23;

    public Food() {
        super((r.nextInt(highX - lowX) + lowX) * 40, (r.nextInt(highY - lowY) + lowY) * 40);
        while (MatrixMap.getObject(this) != null) {
            x = (r.nextInt(highX - lowX) + lowX) * 40;
            y = (r.nextInt(highY - lowY) + lowY) * 40;
        }
        ;
        MatrixMap.add(this);
        Painter.addToPaint(this, foodIcon);
    }

    @Override
    public void OnCollide() {
        new Food();
        Snake.Food();
    }
}

class Wall extends _Object implements Collisable {

    private static ImageIcon wallIcon = new ImageIcon("sprites/Wall.png");

    public Wall(int x, int y) {
        super(x, y);
        Painter.addToPaint(this, wallIcon);
    }

    @Override
    public void OnCollide() {
        // Snake.setBr(1);
        // Main.TheEnd();
        Snake.GameOver();
    }
}

class _Object {
    protected int x, y;

    public _Object(int x, int y) {
        Move(x, y);
    }

    public void Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addToX(int x) {
        this.x += x;
    }

    public void addToY(int y) {
        this.y += y;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }
}

class Painter extends JPanel {
    private static final long serialVersionUID = 1L;

    private static Painter painter;
    private static Vector<Base> toPaint = new Vector<Base>();
    private static Vector<Base> lastPaint = new Vector<Base>();

    private static boolean firstTime = true;
    private static Font arcadeClassic;
    private static Color fontColor = new Color(190, 0, 148);
    private static Color block = new Color(11, 37, 61);

    public Painter() {
        painter = this;
        try {
            arcadeClassic = Font.createFont(Font.TRUETYPE_FONT, new File("small_pixel-7.ttf")).deriveFont(70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeClassic);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static class Base {
        boolean type;

        Base(boolean type) {
            this.type = type;
        }

        public boolean getType() {
            return type;
        }
    }

    private static class Change<T> extends Base {
        public _Object coordinates;
        public T field;

        Change(_Object coordinates, T field) {
            super(field instanceof String);
            this.coordinates = coordinates;
            this.field = field;
        }
    }

    public static void addToPaint(_Object coordinates, ImageIcon imageIcon) {
        toPaint.add(new Change<ImageIcon>(coordinates, imageIcon));
        painter.repaint();
    }

    public static void addToWrite(_Object coordinates, String string) {
        toPaint.add(new Change<String>(coordinates, string));
        painter.repaint();
    }

    public static void budza() {
        for (int i = 0; i < toPaint.size(); i++) {
            lastPaint.add(toPaint.get(i));
        }
        toPaint.clear();
        for (int i = 0; i < lastPaint.size(); i++) {
            toPaint.add(lastPaint.get(i));
        }
        lastPaint.clear();
        firstTime = false;
        painter.repaint();
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < toPaint.size(); i++) {
            if (firstTime) {
                lastPaint.add(toPaint.get(i));
            }
            if (toPaint.get(i).getType()) {
                Change<String> nes = (Change<String>) toPaint.get(i); // ne jedi govna
                g.setColor(block);
                g.fillRect(nes.coordinates.x, 940, 72 * nes.field.length(), 72);// 940 vrh donjeg taba sta god
                g.setColor(fontColor);
                g.setFont(arcadeClassic);
                g.drawString(nes.field, nes.coordinates.x, nes.coordinates.y);
            } else {
                Change<ImageIcon> nes = (Change<ImageIcon>) toPaint.get(i); // ne jedi govna
                nes.field.paintIcon(this, g, nes.coordinates.GetX(), nes.coordinates.GetY());
            }
        }
        toPaint.clear();
    }
}

class SnakeBody extends _Object implements Collisable {

    SnakeBody(int x, int y) {
        super(x, y);
    }

    @Override
    public void OnCollide() {
        // Snake.setBr(1);
        // Main.TheEnd();
        Snake.GameOver();
    }
}

class BorderLines {

    private _Object[] borderLines = new _Object[4];
    private ImageIcon[] lines;

    BorderLines(_Object object, ImageIcon[] lines) {
        this.lines = lines;
        borderLines[0] = new _Object(24, object.GetY());
        borderLines[1] = new _Object(1888, object.GetY());
        borderLines[2] = new _Object(object.GetX(), 24);
        borderLines[3] = new _Object(object.GetX(), 928);
    }

    public void update(_Object object) {
        repaint(lines[1], lines[3]);
        borderLines[0].y = borderLines[1].y = object.GetY();
        borderLines[2].x = borderLines[3].x = object.GetX();
        repaint(lines[0], lines[2]);
    }

    private void repaint(ImageIcon line1, ImageIcon line2) { // repa int :))) Toni 2k21
        Painter.addToPaint(new _Object(borderLines[0].x, borderLines[0].y), line1);
        Painter.addToPaint(new _Object(borderLines[1].x, borderLines[1].y), line1);
        Painter.addToPaint(new _Object(borderLines[2].x, borderLines[2].y), line2);
        Painter.addToPaint(new _Object(borderLines[3].x, borderLines[3].y), line2);
    }
}

class DisplayScore implements FoodListener {

    private static int score = -1;
    private static _Object object;

    DisplayScore() {
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

class Arrows extends _Object {

    private static ImageIcon[] arrowsIcons;
    private static Arrows arrows;

    public Arrows() {
        super(1422, 943);
        arrows = this;
        arrowsIcons = new ImageIcon[4];
    }

    public static void setArrowIcon(int headIndex, String imageIcon) {
        arrowsIcons[headIndex] = new ImageIcon(imageIcon);
    }

    public static ImageIcon getArrowIcon(int headIndex) {
        return arrowsIcons[headIndex];
    }

    public static _Object getObject() {
        return arrows;
    }
}

class Input extends _Object {

    private static Vector<Character> inputs = new Vector<Character>();
    private static Input input;
    private static ImageIcon nothing = new ImageIcon("sprites/Nothing.png");

    public Input() {
        super(1532, 940);
        input = this;
        for (int i = 0; i < 4; i++) {
            Painter.addToPaint(new _Object(input.x + 80 * i, input.y), nothing);
        }
    }

    public static void addInput(char c) {
        if (inputs.size() < 4) {
            for (Character character : inputs) {
                if (c == character) {
                    return;
                }
            }
            inputs.add(c);
            update();
        }
    }

    public static void removeInput(char c) {
        if (inputs.removeElement(c)) {
            Painter.addToPaint(new _Object(80 * inputs.size() + input.x, input.y), nothing);
            update();
        }
    }

    private static void update() {
        for (int i = 0; i < inputs.size(); i++) {
            Painter.addToWrite(new _Object(80 * i + input.x + 14, input.y + 55),
                    String.valueOf(inputs.get(i)).toUpperCase());
        }
    }
}

interface FoodListener {
    public void OnEaten();
}

class Snake implements KeyListener, ActionListener {

    private static Vector<FoodListener> foodListeners = new Vector<FoodListener>();

    private static Vector<KeyEvent> keyEvents = new Vector<KeyEvent>(2);

    private static int snakeLenght = 3;
    private static Vector<SnakeBody> snakeBody = new Vector<SnakeBody>(snakeLenght);

    private static int headIndex = 2;
    private static ImageIcon[] head = new ImageIcon[4];
    private static ImageIcon afterHead1 = new ImageIcon("sprites/afterHead1.png");
    private static ImageIcon afterHead2 = new ImageIcon("sprites/afterHead2.png");
    private static boolean headTrail = true;

    private static ImageIcon snakeTail1 = new ImageIcon("sprites/Tail1.png");
    private static ImageIcon snakeTail2 = new ImageIcon("sprites/Tail2.png");
    private static boolean tailTrail = true;

    private static ImageIcon[] lines = new ImageIcon[4];
    private static BorderLines borderLines;

    private static boolean right = true;
    private static boolean left = false;
    private static boolean up = false;
    private static boolean down = false;
    private static boolean food = false;

    private static Timer timer;
    private static int delay = 100;
    private static int size = 40;

    private static int br = 0;
    private static JFrame o;

    public static void Food() {
        food = true;
    }

    public static void setBr(int i) {
        br = i;
    }

    public static int getBr() {
        return br;
    }

    public static void GameOver() {
        timer.stop();
        o.dispose();
        Main.TheEnd();
    }

    public static void AddFoodListener(FoodListener foodListener) {
        foodListeners.add(foodListener);
    }

    public Snake(int x, int y, JFrame obj) {
        new Arrows();
        new DisplayScore();
        new Input();
        for (int i = 0; i < 4; i++) {
            head[i] = new ImageIcon("sprites/Head" + (i + 1) + ".png");
            lines[i] = new ImageIcon("sprites/Line" + (i + 1) + ".png");
            Arrows.setArrowIcon(i, "sprites/Arrow" + (i + 1) + ".png");
        }
        snakeBody.add(new SnakeBody(x * size, y * size));
        borderLines = new BorderLines(snakeBody.get(0), lines);
        Painter.addToPaint(snakeBody.get(0), head[headIndex]);
        Painter.addToPaint(Arrows.getObject(), Arrows.getArrowIcon(2));
        if (snakeBody.lastElement().GetX() / 40 % 2 != snakeBody.lastElement().GetY() / 40 % 2) {
            headTrail = false;
        }
        o = obj;
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SnakeBody newHead = new SnakeBody(snakeBody.lastElement().GetX(), snakeBody.lastElement().GetY());
        if (headTrail) {
            Painter.addToPaint(snakeBody.lastElement(), afterHead1);
            headTrail = false;
        } else {
            Painter.addToPaint(snakeBody.lastElement(), afterHead2);
            headTrail = true;
        }
        if (keyEvents.size() > 0) {
            KeyEvent keyEvent = keyEvents.get(0);
            if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                if (!right) {
                    left = true;
                    up = false;
                    down = false;
                    headIndex = 0;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
                if (!down) {
                    up = true;
                    right = false;
                    left = false;
                    headIndex = 1;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                if (!up) {
                    down = true;
                    left = false;
                    right = false;
                    headIndex = 3;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
                if (!left) {
                    right = true;
                    up = false;
                    down = false;
                    headIndex = 2;
                }
            }
            Painter.addToPaint(Arrows.getObject(), Arrows.getArrowIcon(headIndex));
            keyEvents.remove(0);
        }
        if (right) {
            if (newHead.GetX() < 1840) {
                newHead.addToX(size);
            } else {
                newHead.addToX(-1800);
            }
        } else if (left) {
            if (newHead.GetX() > 40) {
                newHead.addToX(-size);
            } else {
                newHead.addToX(1800);
            }
        } else if (up) {
            if (newHead.GetY() > 40) {
                newHead.addToY(-size);
            } else {
                newHead.addToY(840);
            }
        } else if (down) {
            if (newHead.GetY() < 880) {
                newHead.addToY(size);
            } else {
                newHead.addToY(-840);
            }
        }
        Collisable object = (Collisable) MatrixMap.getObject(newHead);
        if (object != null) {
            object.OnCollide();
        }
        snakeBody.add(newHead);
        MatrixMap.updateSnake(newHead, snakeBody.get(0));
        Painter.addToPaint(newHead, head[headIndex]);
        borderLines.update(newHead);
        if (!food && snakeBody.size() > snakeLenght) {
            if (tailTrail) {
                Painter.addToPaint(snakeBody.get(0), snakeTail1);
                tailTrail = false;
            } else {
                Painter.addToPaint(snakeBody.get(0), snakeTail2);
                tailTrail = true;
            }
            snakeBody.remove(0);
        }
        if (food) {
            for (FoodListener foodListener : foodListeners) {
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
        if (keyEvents.size() < 2) {
            keyEvents.add(e);
        }
        Input.addInput(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Input.removeInput(e.getKeyChar());
    }
}