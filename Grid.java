public class Grid 
{
    public static int sizeX = 1316;
    public static int sizeY = 779;

    public static int gridSize = 20;

    public static Vector2 GetGridPosition(int x, int y)
    {
        return new Vector2((x/gridSize)*gridSize, (y/gridSize)*gridSize);
    }
}
