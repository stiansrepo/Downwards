package ui;

 // @author laptopng34
import core.Game;
import entities.Monster;
import entities.Player;
import things.ThingType;
import things.Chest;
import map.WorldMap;
import map.TileType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import things.Door;
import things.Thing;

public class MapPanel extends JPanel implements ComponentListener {

    private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private double width = gd.getDisplayMode().getWidth();
    private double height = gd.getDisplayMode().getHeight();
    private int sc = (int) Math.round(width / height * 1);

    private Frame frame;
    private BufferedImage drawnMap;
    private int offsetMaxX;
    private int offsetMaxY;
    private int offsetMinX = 0;
    private int offsetMinY = 0;
    private int camX;
    private int camY;
    private WorldMap map;
    private Game game;
    private final int size = 20;
    private final int scale = 4;
    private Player player;
    private Monster[] monsters;

    public MapPanel(Frame frame) throws IOException {
        this.frame = frame;
        this.game = frame.getGame();
        addListeners();
        this.addComponentListener(this);
        setFocusable(true);
        player = game.getPlayer();
        map = game.getMap();
        player = game.getPlayer();
        monsters = game.getMonsters();
        //offsetMaxX = (map.getWidth() - getWidth()) * 20;
        //offsetMaxY = (map.getHeight() - getHeight()) * 20;
        drawMap();
    }

    private void init() {
        this.game = frame.getGame();
    }

    private void addListeners() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    game.keyPressed(e);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                moveCamera();
                repaint();
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grabFocus();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void initCam() {
        // offsetMaxX = ((map.getWidth() * size) - (getWidth() * size)) * scale;
        // offsetMaxY = ((map.getHeight() * size) - (getHeight() * size)) * scale;
        offsetMaxX = scale * size * map.getWidth() - scale * getWidth() / (size);
        offsetMaxY = scale * size * map.getHeight() - scale * getHeight() / (size);
        moveCamera();
        repaint();
    }

    public void moveCamera() {
        //camX = ((player.x - getWidth()));
        //camY = ((player.y - getHeight()));

        camX = (player.x * size - getWidth() / scale / 2);
        camY = (player.y * size - getHeight() / scale / 2);
        if (camX > offsetMaxX) {
            camX = offsetMaxX;
        } else if (camX < offsetMinX) {
            camX = offsetMinX;
        }
        if (camY > offsetMaxY) {
            camY = offsetMaxY;
        } else if (camY < offsetMinY) {
            camY = offsetMinY;
        }

    }

    public void drawMapChange(int x, int y, Color c) {
        Graphics2D g2d = drawnMap.createGraphics();
        g2d.setColor(c);
        g2d.fillRect(x * 20, y * 20, 20, 20);
    }

    public void drawMapChangeImage(int x, int y, String c) throws IOException {
        Graphics2D g2d = drawnMap.createGraphics();
        String path = "";
        path = c;
        File file = new File(path);
        BufferedImage bi = ImageIO.read(file);
        g2d.setColor(game.getMap().getTerrain()[x][y].getType().getColor());
        g2d.fillRect(x * 20, y * 20, 20, 20);
        g2d.drawImage(bi, x * 20, y * 20, null);
    }

    public void drawMap() throws IOException {
        drawnMap = new BufferedImage(map.getWidth() * 20, map.getHeight() * 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawnMap.createGraphics();

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (true) {
                    TileType t = map.getTile(i, j).getType();

                    g2d.setColor(t.getColor());

                    g2d.fillRect((i * 20), (j * 20), 20, 20);
                }
                if (map.getThing(i, j) != null) {

                    ThingType tt = map.getThing(i, j).getType();
                    String path = "";
                    switch (tt) {
                        case CHEST:
                            Chest c = (Chest) map.getThing(i, j);
                            if (c.isEmpty()) {
                                path = getClass().getClassLoader().getResource(c.getType().getFilePathEmpty()).getPath();
                            } else {
                                path = getClass().getClassLoader().getResource(c.getType().getFilePath()).getPath();
                            }
                            break;
                        case DOOR:
                            Door d = (Door) map.getThing(i, j);
                            if (d.getOpen()) {
                                path = getClass().getClassLoader().getResource(d.getType().getFilePath()).getPath();
                            } else {
                                path = getClass().getClassLoader().getResource(d.getType().getFilePathClosed()).getPath();
                            }
                            break;
                        default:
                            break;
                    }
                    File file = new File(path);
                    BufferedImage bi = ImageIO.read(file);
                    g2d.drawImage(bi, i * 20, j * 20, null);
                }
            }
        }

        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        ;
        g2d.scale(scale, scale);
        g2d.translate(-camX, -camY);
        g2d.drawImage(drawnMap, null, this);

        for (Monster m : monsters) {
            try {
                m.paint(g2d);

            } catch (IOException ex) {
                Logger.getLogger(MapPanel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            player.paint(g2d);

        } catch (IOException ex) {
            Logger.getLogger(MapPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        g2d.translate(camX, camY);

        if (game.getGameOver()) {
            g2d.scale(1, 1);
            g2d.setColor(Color.MAGENTA);
            g2d.setFont(new Font("Courier", Font.PLAIN, 5));
            g2d.drawString("GAME OVER", 1, 15);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e
    ) {

    }

    @Override
    public void componentHidden(ComponentEvent e
    ) {

    }

    @Override
    public void componentResized(ComponentEvent e
    ) {
        initCam();
    }

    @Override
    public void componentShown(ComponentEvent e
    ) {
        moveCamera();
    }

}
