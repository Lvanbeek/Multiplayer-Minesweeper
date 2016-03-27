/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweepers2Player;

import java.util.Scanner;
import static minesweepers2Player.MinesweeperUI.drawBoard;

/**
 *
 * @author Laurens
 */
public class Minesweepers {

    
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in); 
        MinesweeperUI frame;
        int diff = 0;
        int h = 9;
        int w = 9;
        int bs = 10;

        try
        {
            System.out.println("What difficulty would you like to use?");
            System.out.print("0 = No Bomb Increase.");
            System.out.print("1 = 1 Bomb every 5 seconds.");
            System.out.print("2 = 1 Bomb every 35 seconds.");
            System.out.println("3 = 1 Bomb every 110 seconds.");
            diff = scan.nextInt();
            
            System.out.println("What height would you like for the map?");
            h = scan.nextInt();
            
            System.out.println("What width would you like for the map?");
            w = scan.nextInt();
            
            System.out.println("How many bombs would you like?");
            System.out.println("NOTE: Max # of bombs can be 1 for every 8 squares.");
            bs = scan.nextInt();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        scan.close();
        frame = new MinesweeperUI(h,w,bs, diff);
        drawBoard(frame);
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinesweeperUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /*
         * Create and display the form
         */
        final int h1 = h;
        final int w1 = w;
        final int bs1 = bs;
        final int diff1 = diff;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MinesweeperUI(h1,w1,bs1,diff1).setVisible(true);
            }
        });
        System.out.println("0-8 = number of adjacent mines ++ -1 = unclicked ++ -2 = mine "
                + "++ -3 = flag over mine ++ -4 = flag over non-mine");
    }
    
}
