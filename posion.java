import java.util.Random;

import javax.swing.ImageIcon;

class Posion extends _Object implements Collisable{
    
    private ImageIcon foodIcon = new ImageIcon("dodati sliku");
    private static Random r = new Random();
    private static int lowX = 1;
    private static int highX = 47;
    private static int lowY = 1;
    private static int highY = 23;

    public Posion() {
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
        //Score =- 1 ili bi oznacavalo game over
        //Snake.Posion();
    }
}