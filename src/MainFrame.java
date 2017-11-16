/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author andres
 */
public class MainFrame extends JFrame {

    Mine mine;
    ArrayList<Mine> mines = new ArrayList<>();
    DefaultListModel<Mine> minesModel;
    ManageFile filesManager = new ManageFile();
    int selectedMineIndex = -1;
    MineMap currentMineMap;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        minesModel = new DefaultListModel<Mine>();

        loadMenu();
        //writeJsonOfMines();
        UpdateJList();

    }

    private void loadMenu() {

        JMenuBar bar = new JMenuBar();
        JMenu options = new JMenu("Options");
        JMenu help = new JMenu("Help");
        JMenuItem loadMines = new JMenuItem("Load Mines");
        JMenuItem saveMines = new JMenuItem("Save Mines");
        JMenuItem startAnimation = new JMenuItem("Start");
        JMenuItem addMine = new JMenuItem("Add Mine");
        JMenuItem about = new JMenuItem("About");
        bar.add(options);
        bar.add(help);
        options.add(loadMines);
        options.add(saveMines);        
        options.add(addMine);
        options.add(startAnimation);
        help.add(about);

        saveMines.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                writeJsonOfMines();
                System.out.println("Click Save Mines");
            }
        });

        loadMines.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                LoadMinesListFromJson();
                UpdateJList();
                System.out.println("Click Load Mines");

            }
        });

        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Click About");
            }
        });

        addMine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                AddMineToList();
                UpdateJList();
                System.out.println("Click Add Mine");
            }
        });
        
           startAnimation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentMineMap.mine.setEntry(Integer.parseInt(JOptionPane.showInputDialog("Entrada a la mina")));
                currentMineMap.start();
                System.out.println("Click Start");
            }
        });

        setJMenuBar(bar);

    }

    private void AddMineToList() {

        int[][] matrix
                = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 4565, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 4534, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 3453, 0}
                };

        Mine newMine = new Mine(matrix, 80, 1 , 180, 10, 3, "MINA NUEVA");
        newMine.createAdjacency();
        mines.add(newMine);
    }

    private Mine readFileMine() {

        String textJson = filesManager.read("file.json");
        Gson gson = new Gson();
        mine = gson.fromJson(textJson, Mine.class);
        return mine;

    }

    private void writeJsonOfMines() {

        //json.write(mines, "ListMines.json");
        try (Writer writer = new FileWriter("ListMines.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(mines, writer);
        } catch (IOException e) {
            System.out.println("Error writing the mines list : " + e);
        }
    }

    private ArrayList<Mine> LoadMinesListFromJson() {

        BufferedReader br = null;
        String str = "";

        try {

            br = new BufferedReader(new FileReader("ListMines.json"));
            String line = null;
            while ((line = br.readLine()) != null) {
                str += line + "\n";
            }

        } catch (Exception e) {
            System.out.println("Error read the json file with mines list : " + e);
        }

        Gson gson = new Gson();

        Mine[] minesArray = gson.fromJson(str, Mine[].class);
        mines.clear();
        
        for (Mine m : minesArray) {
            m.createAdjacency();
            mines.add(m);
        }
        
        return mines;

    }
    
    private void printMinimePath(int in, int out, Mine mine){
        int[][] path = mine.floydWarshall();
        for (int i = 0; i < path.length; i++) {
            System.out.print(i + "-"); 
        }
        System.out.println("");
        for (int i = 0; i < path.length; i++) {
            System.out.print(path[in][i] + "-");
        }
    }
    
    
    private void printMatrix(int [][] matrix ){
        
        System.out.println("MATRIX SIZE" + matrix.length + "x" + matrix[0].length);
    
        for (int i = 0; i < matrix.length; i++) {
            
            for (int j = 0; j < matrix[0].length; j++) {
                
                System.out.print(matrix[i][j] + "-");
            }
            
            System.out.println(" ");
        }
    
    }

    private void UpdateJList() {

        minesModel.clear();

        for (int i = 0; i < mines.size(); i++) {

            minesModel.addElement(mines.get(i));

        }

        minesList.setModel(minesModel);
        minesList.setSelectedIndex(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        minesList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        minesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minesListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(minesList);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void minesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minesListMouseClicked
        
        
            Mine currentMine = minesList.getSelectedValue();
            selectedMineIndex = minesList.getSelectedIndex();
            if(currentMineMap != null)
                getContentPane().remove(currentMineMap);
            currentMineMap = new MineMap(currentMine);
            getContentPane().add(currentMineMap, BorderLayout.CENTER);
            revalidate();
    }//GEN-LAST:event_minesListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                JFrame fmain = new MainFrame();
                fmain.setVisible(true);
                fmain.setExtendedState(MAXIMIZED_BOTH);
                fmain.setTitle("Minerals & Minerals S.A.");

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Mine> minesList;
    // End of variables declaration//GEN-END:variables

}
