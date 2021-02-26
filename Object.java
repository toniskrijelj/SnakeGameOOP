package snake;

public class Object
{
    protected int x, y;
    
    public Object(int x, int y)
    {
        Move(x, y)
    }
    
    public void Move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    int GetX() { return x; }
    int GetY() { return y; }
}
