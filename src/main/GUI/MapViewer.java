
package main.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import main.map.ImageManager;
import main.map.MapDataManager;

/**
 *
 * @author Michael
 */

public final class MapViewer extends JPanel implements MouseListener, KeyListener{

    protected static JPopupMenu popMenu;
    protected MapDataManager map;
    protected float x,y,grassX,grassY;
    protected Image grassScroller;
    protected static Image grass;
    
    protected boolean[] keys;
    protected Timer keyTimer;
    protected long lastScrollTime;
    
    //protected 
    
    protected static final float SCROLLSPEED = 10;
    
    public MapViewer(String path){
        
        if(popMenu == null){
            generatePopupMenu();
        }
        
        map = new MapDataManager(path);
        grassScroller = this.generateTerrainScroller();
        grassX=-grass.getWidth(null);
        grassY=-grass.getHeight(null);
        
        keys = new boolean[4];
        
        //Make sure the scroller stays the correct size
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                grassScroller = generateTerrainScroller();
                repaint();
            }
        });
        
        //Add scroll handling
        keyTimer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                long timeElapsed = System.currentTimeMillis() - lastScrollTime;
                float change = timeElapsed*SCROLLSPEED;
                float dx=0,dy=0;
                //UP
                if(keys[0]){
                    dy = change;
                }
                //DOWN
                else if(keys[1]){
                    dy = -change;
                }
                //LEFT
                if(keys[2]){
                    dx = -change;
                }
                //RIGHT
                else if(keys[3]){
                    dx = change;
                }else{
                    keyTimer.stop();
                    return;
                }
                scrollView(dx,dy);
                lastScrollTime = System.currentTimeMillis();
            }
        });
    }
    
    private void scrollView(float x, float y){
        System.out.println("Scroll "+x+" "+y);
        //Move the view
        this.x += x;
        this.y += y;
        //Move the grass
        grassX += x;
        grassY += y;
        
        if(grassX<-grass.getWidth(null)){
            grassX += grass.getWidth(null);
        }else if(grassX>grass.getWidth(null)){
            grassX -= grass.getWidth(null);
        }
        if(grassY<-grass.getHeight(null)){
            grassY += grass.getHeight(null);
        }else if(grassY>grass.getHeight(null)){
            grassY -= grass.getHeight(null);
        }
    }
    
    private void generatePopupMenu(){
        popMenu = new JPopupMenu();
            popMenu.add("Add Structure").addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            popMenu.add("Add Event").addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
    }
    
    private void addStructure(){
        
    }

    @Override
    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D)gr;
        //Draw the grass
        g.drawImage(grassScroller, (int)grassX, (int)grassY, this);
    }

    public Image generateTerrainScroller(){
        if(grass == null){
            grass = ImageManager.getImage("grass01.jpg");
        }
        int width = getWidth()+2*grass.getWidth(null);
        int height = getHeight()+2*grass.getHeight(null);
        BufferedImage image = new BufferedImage(width,height,BufferedImage.SCALE_FAST);
        Graphics2D g = (Graphics2D)image.getGraphics();
        int gdrawx = 0, gdrawy = 0;
        while(gdrawy < height){
            while(gdrawx < width){
                g.drawImage(grass, gdrawx, gdrawy, null);
                gdrawx += grass.getWidth(null);
            }
            gdrawx = 0;
            gdrawy += grass.getHeight(null);
        }
        g.dispose();
        return image;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()){
            popMenu.show(this, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keys[0] = true;
                break;
            case KeyEvent.VK_DOWN:
                keys[1] = true;
                break;
            case KeyEvent.VK_LEFT:
                keys[2] = true;
                break;
            case KeyEvent.VK_RIGHT:
                keys[3] = true;
                break;
            default:
                lastScrollTime = System.currentTimeMillis();
                keyTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keys[0] = false;
                break;
            case KeyEvent.VK_DOWN:
                keys[1] = false;
                break;
            case KeyEvent.VK_LEFT:
                keys[2] = false;
                break;
            case KeyEvent.VK_RIGHT:
                keys[3] = false;
                break;
        }
    }
    
}
