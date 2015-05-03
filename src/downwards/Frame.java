package downwards;

 // @author laptopng34
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame {

    private Game game;
    private Panel panel;
    public MapPanel mappanel;
    private InfoPanel infopanel;

    public Frame() throws InterruptedException {
        initUI();
        
    }

    private void initUI() throws InterruptedException {

        Font myFont = new Font("Verdana", Font.PLAIN, 12);

        try {

            File fontFile = new File(getClass().getClassLoader().getResource("Fixedsys500c.ttf").getFile());

            myFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 15f);

            GraphicsEnvironment ge
                    = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(myFont);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }

        BorderLayout gl = new BorderLayout();

        setTitle("Downwards! Pre-Alpha version");
        //setSize(1000, 1000);

        infopanel = new InfoPanel();
        panel = new Panel();
       
        game = new Game(infopanel);
        mappanel = new MapPanel(game);
        JPanel blank = new JPanel();

        JLabel jl = new JLabel("blank part");
        jl.setFont(myFont);

        blank.add(jl);
        blank.setSize(600, 100);

        panel.setPreferredSize(new Dimension(800, 800));
        mappanel.setSize(600, 600);
        infopanel.setSize(600, 100);

        panel.setLayout(gl);
        panel.add(blank, BorderLayout.NORTH);

        panel.add(mappanel, BorderLayout.CENTER);
        panel.add(infopanel, BorderLayout.SOUTH);

        add(panel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
