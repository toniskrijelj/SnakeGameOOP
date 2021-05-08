

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.lang.ClassNotFoundException;


public class GameW extends JFrame{
    
    JFrame frame = new JFrame();
    ImageIcon backGround = new ImageIcon("sprites/Background.png");
    private int br;
    
    GameW(){

        br = 1;
        frame.setIconImage(backGround.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setVisible(true);
        frame.setLayout(null);
        
    }
    public boolean check(){
        if(br == 1){
            return true;
        }
        return false;
    }
}
