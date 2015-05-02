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
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements ComponentListener {

    private BufferedImage drawnMap;
    private int offsetMaxX;
    private int offsetMaxY;
    private int offsetMinX = 0;
    private int offsetMinY = 0;
    private int camX;
    private int camY;
    private Map map;
    private Game game;
    private final int scale = 15;
    private Player player;
    private Monster[] monsters;

    public MapPanel(Game game) {

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
                player.move();
                moveCamera();
                repaint();
            }
        });
        this.addComponentListener(this);
        setFocusable(true);
        this.game = game;
        player = game.getPlayer();
        map = game.getMap();
        player = game.getPlayer();
        monsters = game.getMonsters();
        offsetMaxX = map.getWidth() - getWidth();
        offsetMaxY = map.getHeight() - getHeight();
        drawMap();

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

    private void drawMap() {
        drawnMap = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawnMap.createGraphics();

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                TileType t = map.getTile(i, j).getType();
                switch (t) {
                    case FLOOR:
                        g2d.setColor(Color.GRAY);
                        break;
                    case GRASS:
                        g2d.setColor(Color.GREEN);
                        break;
                    case WALL:
                        g2d.setColor(Color.BLACK);
                        break;
                    default:
                        break;

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
        player.paint(g2d);
        for (Monster m : monsters){
            m.paint(g2d);
        }
        g2d.translate(camX, camY);

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        initCam();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        moveCamera();
    }
}
