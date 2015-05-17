package ui;

 // @author laptopng34
import core.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame {

    private Game game;
    private Panel panel;
    public MapPanel mapPanel;
    private InfoPanel infoPanel;

    public Frame() throws FileNotFoundException, IOException {
        initUI();
    }

    private void initUI() throws FileNotFoundException, IOException {

        Font myFont = new Font("Verdana", Font.PLAIN, 12);

        BorderLayout gl = new BorderLayout();

        setTitle("Downwards! Pre-Alpha version");

        setExtendedState(MAXIMIZED_BOTH);
        panel = new Panel();
        game = new Game(this);
        infoPanel = new InfoPanel(this);
        mapPanel = new MapPanel(this);
        game.initPanels();
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

        infoPanel.setSize(600, 100);

        panel.setLayout(gl);
        panel.add(blank, BorderLayout.NORTH);

        panel.add(mapPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public Game getGame() {
        return game;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

}
