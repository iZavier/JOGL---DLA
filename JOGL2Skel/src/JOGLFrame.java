
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * @author Shivan patel
 */
public class JOGLFrame extends JFrame {
    public static DLAGLEventListener l = new DLAGLEventListener();
    public static GLProfile glprofile = GLProfile.getDefault();
    public static GLCapabilities glcapabilities = new GLCapabilities( glprofile );
    public static GLCanvas glcanvas = new GLCanvas( glcapabilities );
    public static JButton reDraw;
    public static JButton four;
    public static JButton eight;
    public static JButton[] butts = new JButton[3];
    public static JOGLFrame instance;

    public JOGLFrame() throws HeadlessException {
        setPreferredSize(new Dimension(600,700)); 
        glcanvas.setPreferredSize(new Dimension(600,600));
        glcanvas.addGLEventListener(l);
        getContentPane().add(glcanvas);

       
        
        pack();
    }   
    
    public static void main(String[] args) {
        instance = new JOGLFrame();
        
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.setVisible(true);
        JPanel panel = new JPanel();
        reDraw = new JButton("Re Draw");
        four = new JButton("4-Connect");
        eight = new JButton("8-Connect");
        panel.setLayout(new FlowLayout());
        
        String[] buttons = {"Re Draw","Four Way","Eight Way"};
        for (int i = 0; i < buttons.length; i++) {            
            butts[i] = new JButton(buttons[i]);
            ActionListener lis = new AddButtonListener(butts[i]);
            butts[i].addActionListener(lis);
            panel.add(butts[i]);
    }
        instance.add(panel, BorderLayout.SOUTH);
        instance.pack();
   
}

    private static class AddButtonListener implements ActionListener {
        public String t2;
        public AddButtonListener(JButton butt) {
            t2 = butt.getText();

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (t2.equals("Re Draw")) { 
                instance.setSize(new Dimension(600,700));
                instance.setSize(new Dimension(600,701));
            } else if (t2.equals("Four Way")) { 
                l.setCon(4);
            } else if (t2.equals("Eight Way")) { 
                l.setCon(8);
            }
        }
    }
}
