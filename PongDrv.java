import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class PongDrv{

    public static void main(String[] args) {

        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Pong pong = new Pong();
        frame.add(pong, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Press F for faster, and S for slower"));
        frame.add(bottomPanel, BorderLayout.SOUTH);
                
        frame.setSize(500, 250);
        frame.setVisible(true);
        
        // Make sure the JPanel has the focus
        pong.requestFocusInWindow();
        
    }
}
