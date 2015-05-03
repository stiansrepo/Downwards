package downwards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author DesktopStian
 */
public class InfoPanel extends JPanel{
    
    private String res = "";
    private JLabel jl;
    
    public InfoPanel(){
        jl = new JLabel();
        add(jl);
    }

    public void updateInfo(String info){
        res = info;
        jl.setText(res);
    }
}   