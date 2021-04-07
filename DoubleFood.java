import java.util.Random;

import javax.swing.ImageIcon;

class DoubleFood extends _Object implements Collisable{
    
    private ImageIcon foodIcon = new ImageIcon("Sliku neku dodajte");
    private static Random r = new Random();
    private static int lowX = 1;
    private static int highX = 47;
    private static int lowY = 1;
    private static int highY = 23;

    public DoubleFood() {
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
        //Score=+2 
        //Snake.DoubleFood();
    }
}
