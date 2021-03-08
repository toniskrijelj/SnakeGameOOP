//package Snake;

import java.util.*;

public class Snake
{
    public static void main(String[] args) {
        
    }
};

class Object
{
    private int x;
    private int y;

    Object(int x, int y){
        this.x = x;
        this.y = y;
    }
    void setX(int x){
        this.x = x;
    }
    void setY(int y){
        this.y = y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
};

class SnakeHead extends Object
{
    SnakeHead(int x, int y) {
        super(x, y);
    }

}

class SnakeBody extends 
