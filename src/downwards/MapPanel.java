package downwards;

 // @author laptopng34
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements ComponentListener {

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
    private final int scale = 23;
    private Player player;
    private Monster[] monsters;

    public MapPanel(Frame frame) {
        this.frame=frame;
        this.game=frame.getGame();
        addListeners();
        this.addComponentListener(this);
        setFocusable(true);
        
        player = game.getPlayer();
        map = game.getMap();
        player = game.getPlayer();
        monsters = game.getMonsters();
        offsetMaxX = map.getWidth() - getWidth();
        offsetMaxY = map.getHeight() - getHeight();
        drawMap();
    }
    
    private void init(){
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
                game.keyPressed(e);
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
        offsetMaxX = map.getWidth() - (getWidth() / scale);
        offsetMaxY = map.getHeight() - (getHeight() / scale);
        moveCamera();
        repaint();
    }

    public void moveCamera() {
        camX = (player.x - getWidth() / scale / 2);
        camY = (player.y - getHeight() / scale / 2);

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
        g2d.fillRect(x, y, 1, 1);
    }

    private void drawMap() {
        drawnMap = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawnMap.createGraphics();

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getThing(i, j) == null) {
                    TileType t = map.getTile(i, j).getType();
                    switch (t) {
                        case FLOOR:
                            g2d.setColor(new Color(153, 153, 153));
                            break;
                        case RUBBLE:
                            g2d.setColor(new Color(93, 93, 93));
                            break;
                        case SILT:
                            g2d.setColor(new Color(206, 187, 67));
                            break;
                        case GRASS:
                            g2d.setColor(new Color(109, 185, 66));
                            break;
                        case GRIT:
                            g2d.setColor(new Color(138,114,87));
                            break;
                        case WALL:
                            g2d.setColor(Color.BLACK);
                            break;
                        case WATER:
                            g2d.setColor(new Color(24, 24, 195));
                            break;
                        default:
                            break;

                    }
                } else {
                    ThingType t = map.getThing(i, j).getType();
                    switch (t) {
                        case CHEST:
                            Chest c = (Chest)map.getThing(i, j);
                            if(c.isEmpty()){
                                g2d.setColor(new Color(190, 190, 0));
                            }
                            else{
                                g2d.setColor(new Color(240, 240, 0));
                            }
                            break;
                        default:
                            break;
                    }
                }
                g2d.fillRect(i, j, 1, 1);
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
            m.paint(g2d);
        }
        player.paint(g2d);
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
