import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;

import java.util.*;

public class Paint extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
    private static final long serialVersionUID = 1L;

    private ImageIcon white = new ImageIcon("White20x20.png");
    private ImageIcon black = new ImageIcon("Black20x20.png");

    private class Change
    {
        public Vector2 position;
        public boolean what;

        public Change(Vector2 position, boolean what)
        {
            this.position = position;
            this.what = what;
        }
    }

    Map map = new Map();
    Deque<Vector<Change>> undoStack = new LinkedList<Vector<Change>>();
    Deque<Vector<Change>> redoStack = new LinkedList<Vector<Change>>();

    public Paint()
    {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
    }

    Stack<Change> changes = new Stack<Change>();
    public void paint(Graphics g){
        while(!changes.empty())
        {
            Change top = changes.pop();
            if(top.what)
            {
                white.paintIcon(this, g, top.position.x, top.position.y);
            }
            else
            {
                black.paintIcon(this, g, top.position.x, top.position.y);
           }
        }
    }    
    
    boolean holding = false;
    boolean leftClick = false;
    boolean newStack = false;
    @Override
    public void mousePressed(MouseEvent e) {
        if(!holding)
            newStack = true;
        holding = true;
        leftClick = e.getButton() == MouseEvent.BUTTON1;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        holding = false;
    }
    
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if(holding)
        {
            Vector2 position = Grid.GetGridPosition(e.getX(), e.getY());
            if(map.Check(position.x, position.y))
            {
                if(map.Get(position.x, position.y) != leftClick)
                {
                    if(newStack)
                    {
                        undoStack.push(new Vector<Change>());
                        if(undoStack.size() > 100) undoStack.removeLast();
                        newStack = false;
                    }
                    redoStack.clear();
                    undoStack.peek().add(new Change(position, !leftClick));
                    map.Set(position.x, position.y, leftClick);
                    changes.push(new Change(position, leftClick));
                    repaint();
                }
                
            }
        }
        
    }
    
    boolean control = false;
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.isControlDown())
        {
            control = true;
        }
        if(control && e.getKeyCode() == KeyEvent.VK_Z)
        {
            if(undoStack.isEmpty()) return;

            Vector<Change> top = undoStack.pop();
            redoStack.push(top);

            newStack = true;
            for(Change c : top)
            {
                changes.push(c);
                map.Set(c.position.x, c.position.y, c.what);
            }
            repaint();
        }
        if(control && e.getKeyCode() == KeyEvent.VK_Y)
        {
            if(redoStack.isEmpty()) return;

            Vector<Change> top = redoStack.pop();
            newStack = true;
            for(Change c : top)
            {
                c.what = !c.what;
                changes.push(new Change(c.position, c.what));
                map.Set(c.position.x, c.position.y, c.what);
                c.what = !c.what;
            }
            undoStack.push(top);
            repaint();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            control = false;
        }
    }

}