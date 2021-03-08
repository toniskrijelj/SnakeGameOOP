import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

@SuppressWarnings("serial")
public class Tests extends JPanel {
    private static final int SML_SIDE = 10; // x y
    private static final int SIDE = SML_SIDE * SML_SIDE;
    private static final int GAP = 8; // razmak izmedju
    private static final Color BG = Color.RED; // boja razmaka/background-a
    private static final Dimension BTN_PREF_SIZE = new Dimension(80, 80);
    private JButton[][] buttons = new JButton[SIDE][SIDE];

    public Tests() {
        setBackground(BG);
        setLayout(new GridLayout(SML_SIDE, SML_SIDE, GAP, GAP));
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        JPanel[][] smallPanels = new JPanel[SML_SIDE][SML_SIDE];
        for (int i = 0; i < smallPanels.length; i++) {
            for (int j = 0; j < smallPanels[i].length; j++) {
                smallPanels[i][j] = new JPanel(new GridLayout(SML_SIDE, SML_SIDE));
                add(smallPanels[i][j]);
            }
        }
        /*
        for (int i = 0; i < buttons.length; i++) {
            int panelI = i / SML_SIDE;
            for (int j = 0; j < buttons[i].length; j++) {
                int panelJ = j / SML_SIDE;
                String text = String.format("[%d, %d]", j, i);
                buttons[i][j] = new JButton(text);
                buttons[i][j].setPreferredSize(BTN_PREF_SIZE);
                smallPanels[panelI][panelJ].add(buttons[i][j]);
            }
        }*/
    }

    private static void createAndShowGui() {
        Tests mainPanel = new Tests();

        JFrame frame = new JFrame("JPanelGrid");
        frame.getContentPane().add(mainPanel);
        frame.setBounds(10, 10, 1080, 720);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
    }
}