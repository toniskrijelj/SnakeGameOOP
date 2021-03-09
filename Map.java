public class Map {
    int sizeX;
    int sizeY;
    boolean[][] mp;

    public Map()
    {
        sizeX = Grid.sizeX / Grid.gridSize;
        sizeY = Grid.sizeY / Grid.gridSize;
        mp = new boolean[sizeY][sizeX];
    }

    public boolean Check(int x, int y)
    {
        x /= Grid.gridSize;
        y /= Grid.gridSize;
        return !(x < 0 || x >= sizeX || y < 0 || y >= sizeY);
    }

    public boolean Get(int x, int y)
    {
        if(Check(x, y))
        {
            x /= Grid.gridSize;
            y /= Grid.gridSize;
            return mp[y][x];
        }
        System.out.println("OVDE" + x / Grid.gridSize + " " + y / Grid.gridSize); //TODO prebaci mozda u exception
        return false;
    }

    public void Set(int x, int y, boolean v)
    {
        if(Check(x, y))
        {
            x /= Grid.gridSize;
            y /= Grid.gridSize;
            mp[y][x] = v;
        }
        System.out.println(x / Grid.gridSize + " " + y / Grid.gridSize); //TODO prebaci mozda u exception
    }
}
