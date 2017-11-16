
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andres
 */
public class MineMap extends JPanel implements Runnable{
    
   Mine mine;
    boolean running;
    int w, h;
    Miner miner;
    Thread thread;
    
    public MineMap(Mine mine) {
        super();
        this.mine = mine;
        mine.miners = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if(running)
                    return;
                int x = evt.getX();
                int y = evt.getY();
                int j = x / w;
                int i = y / h;
                mine.getMatrix()[i][j] = mine.getMatrix()[i][j] < 2 ? mine.getMatrix()[i][j] + 1 : 0;
                if(mine.getMatrix()[i][j] > 1){
                    mine.getMatrix()[i][j] = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de Mineral"));
                }
                mine.createAdjacency();
                repaint();
            }
        });
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0, y = 0;
        w = (int)(getWidth() / mine.getMatrix()[0].length);
        h = (int)(getHeight()/ mine.getMatrix().length);
        for (int i = 0; i < mine.getMatrix().length; i++) {
            for (int j = 0; j < mine.getMatrix()[i].length; j++) {
                switch(mine.getMatrix()[i][j]) {
                    case 0:
                        g.setColor(Color.BLACK);
                        break;
                    case 1:
                        g.setColor(Color.YELLOW);
                        break;
                    default:
                        g.setColor(Color.GREEN);
                }
                g.fillRect(x, y, w, h);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, w, h);
                x += w;
            }
            x = 0;
            y += h;
        }
        if(mine.miners != null) {
            g.setColor(Color.WHITE);
            for(Miner m : mine.miners) {
                g.fillOval(m.getX(), m.getY(), 20, 20);
            }
        }
    }

    @Override
    public void run() {
        running = true;
        miner = new Miner(w, h, mine);
        miner.setDepositIndex(Integer.parseInt(JOptionPane.showInputDialog("Ingrese deposito")));
        mine.miners.add(miner);
        new Thread(miner).start();
        while(running) {
            repaint();
            try {
                Thread.sleep(10);
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void start() {
        thread = new Thread(this);
        thread.start();
    }
}
