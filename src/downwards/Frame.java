package downwards;

 // @author laptopng34
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame {

    private Game game;
    private Panel panel;
    public MapPanel mapPanel;
    private InfoPanel infoPanel;

    public Frame() {
        initUI();
    }

    private void initUI() {

        Font myFont = new Font("Verdana", Font.PLAIN, 12);

        BorderLayout gl = new BorderLayout();

        setTitle("Downwards! Pre-Alpha version");

        setExtendedState(MAXIMIZED_BOTH);

        panel = new Panel();
        infoPanel = new InfoPanel(this);
        game = new Game(infoPanel);

        mapPanel = new MapPanel(game);
        mapPanel.setVisible(true);
        mapPanel.setFocusable(true);

        JPanel blank = new JPanel();

        JLabel jl = new JLabel("blank part");
        jl.setFont(myFont);

        blank.add(jl);
        blank.setSize(600, 100);

        panel.setPreferredSize(new Dimension(800, 800));
        mapPanel.setSize(400, 400);
        infoPanel.setPreferredSize(new Dimension(600, 100));

        infoPanel.setSize(600, 300);

        panel.setLayout(gl);
        panel.add(blank, BorderLayout.NORTH);

        panel.add(mapPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
    public Game getGame(){
        return game;
    }

}
