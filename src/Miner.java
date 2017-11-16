
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andres
 */
public class Miner implements Runnable {
    
    private int x,y;
    private int type;
    private String salary;
    private int extracted;   
    private int buffer;
    private int depositIndex;
    private final Mine mine;
    private int w, h;
    
    public Miner(int w, int h, Mine mine) {
        this.w = w;
        this.h = h;
        this.mine = mine;
    }
    
    public void moveLeft() {
        x -= 1;
    }
    
    
    public void moveRight() {
        x += 1;
    }
    
    public void moveUp() {
        y -= 1;
    }
    
    public void moveDown() {
        y += 1;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * @return the extracted
     */
    public int getExtracted() {
        return extracted;
    }

    /**
     * @param extracted the extracted to set
     */
    public void setExtracted(int extracted) {
        this.extracted = extracted;
    }

    /**
     * @return the buffer
     */
    public int getBuffer() {
        return buffer;
    }

    /**
     * @param buffer the buffer to set
     */
    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    /**
     * @return the depositIndex
     */
    public int getDepositIndex() {
        return depositIndex;
    }

    /**
     * @param dx the depositIndex to set
     */
    public void setDepositIndex(int dx) {
        this.depositIndex = dx;
    }

    @Override
    public void run() {
        boolean running = true;
        int coordI = mine.getEntry() / mine.matrix[0].length;
        int coordJ = mine.getEntry() % mine.matrix[0].length;
        x = (coordJ * w) + (w / 2);
        y = (coordI * h) + (h / 2);
        int depI = depositIndex / mine.matrix[0].length;
        int depJ = depositIndex % mine.matrix[0].length;
        int dx = (depJ * w) + (w / 2);
        int dy = (depI * h) + (h / 2);
        LinkedList<Integer> path = mine.getPathTo(depositIndex);
        int index = path.size() - 1;
        while(running || (x != dx && y != dy)) {
            int next = path.get(index--);
            int nextI = next / mine.matrix[0].length;
            int nextJ = next % mine.matrix[0].length;
            int nextX = nextJ * w + (w / 2), nextY = nextI * h + (h / 2);
            while(x != nextX || y != nextY) {
                if(nextX > x)
                    moveRight();
                else if(nextX < x)
                    moveLeft();
                else if(nextY > y)
                    moveDown();
                else if(nextY < y)
                    moveUp();
                try {
                    Thread.sleep(10);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            if(index < 0) break;
        }
    }
}
