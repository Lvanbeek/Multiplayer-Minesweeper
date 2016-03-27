package minesweepers2Player;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MinesweeperUI extends javax.swing.JFrame {

    public int height, width, numMines;
    Board board;
    int i = 0;
    static JPanel panel;
    final JButton[][] buttons;
    int diff = 0;
    

    public MinesweeperUI(int h, int w, int nMines, int difficulty) {
        this.height = h;
        this.width = w;
        this.numMines = nMines;
        this.diff = difficulty;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(height != width){
            JOptionPane.showMessageDialog(null, "Invalid Size! Size has been set to: " + h + " by " + h);
            width = h;
        
        }
        this.board = new Board(this.height, this.width, this.numMines, this.diff);
        this.buttons = new JButton[height][width];

        panel = new JPanel(new GridLayout(height, width));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                buttons[x][y] = new JButton(" ");
                buttons[x][y].setSize(100, 100);
                buttons[x][y].setActionCommand(String.valueOf(x) + "_" + String.valueOf(y));
                buttons[x][y].setName(String.valueOf(x) + "_" + String.valueOf(y));

                buttons[x][y].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String choice = e.getActionCommand();
                        String[] coords = choice.split("_");
                        int t_x = Integer.parseInt(coords[0]);
                        int t_y = Integer.parseInt(coords[1]);
                        board.click(t_x, t_y);

                        if(board.p1.hasLost && board.p2.hasLost) {
                            JOptionPane.showMessageDialog(null, ("Sorry, you both lose!\r\n"  + "Your combined time was: \r\n" + ((board.now - board.start)/1000) + " seconds"));
                            return;
                        }
                        if(board.p1.hasWon && board.p2.hasWon) {
                            JOptionPane.showMessageDialog(null, "You both win!\r\n"  + "Your combined time was: \r\n" + ((board.now - board.start)/1000) + " seconds");
                            return;
                        }
                        if(board.p1.hasLost && board.p2.hasWon){
                            JOptionPane.showMessageDialog(null, "Player 2 wins!\r\n"  + "Your combined time was: \r\n" + ((board.now - board.start)/1000) + " seconds");
                            return;
                        }
                        if(board.p1.hasWon && board.p2.hasLost){
                            JOptionPane.showMessageDialog(null, "Player 1 wins!\r\n"  + "Your combined time was: \r\n" + ((board.now - board.start)/1000) + " seconds");
                            return;
                        }
                        clickBtn();
                        drawBtns();
                    }
                });
                buttons[x][y].addMouseListener(new RightClicker());
                panel.add(buttons[x][y]);
            }
        }

    }

    // this I got from a web forum
    private class RightClicker extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isMetaDown()) {
                JButton src = (JButton) e.getSource();
                String choice = src.getName();
                String[] coords = choice.split("_");
                int t_x = Integer.parseInt(coords[0]);
                int t_y = Integer.parseInt(coords[1]);
                board.rightClick(t_x, t_y);

                clickBtn();
                drawBtns();
            }
        }
    }

    public void drawBtns() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((buttons[x][y].getBackground() == Color.RED || buttons[x][y].getBackground() == Color.BLUE) && board.board[x][y] == -3){
                    y++;
                }
                if (board.board[x][y] == -1 || board.board[x][y] == -2) {
                    buttons[x][y].setBackground(Color.GRAY);
                    buttons[x][y].setText(" ");
                } else if ((((board.clicks ) % 2) != 0) && board.board[x][y] == -3 || board.board[x][y] == -4) {
                    buttons[x][y].setBackground(Color.RED);
                    buttons[x][y].setText("F");
                } else if ((((board.clicks ) % 2) == 0) && board.board[x][y] == -3 || board.board[x][y] == -4){
                    buttons[x][y].setBackground(Color.BLUE);
                    buttons[x][y].setText("F");
                } else {
                    buttons[x][y].setBackground(Color.WHITE);
                    buttons[x][y].setText(String.valueOf(board.board[x][y]));
                }
            }
        }
    }

    public void clickBtn() {
        // this is just to help keep track and for debugging
        board.print();
        System.out.println();
    }

    /**
     * Creates new form MinesweeperUI
     * @param frame
     */
    public static void drawBoard(MinesweeperUI frame) {
        frame.add(panel);
        frame.pack();
        frame.setSize(frame.width * 50, frame.height * 50);
        frame.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
