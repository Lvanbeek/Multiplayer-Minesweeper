
package minesweepers2Player;
import java.util.Random;

/**
 *
 * @author Laurens
 */
public class Board {
    int board[][];
    public long start;
    public long now;
    public long increase = 0;
    int clicks = 1;
    Player p1 = new Player();
    Player p2 = new Player();
    
    int height, width, nMines, diff;
    boolean firstClick = true;
    
    public Board(int h, int w, int _nMines, int difficulty){
        height = h;
        width = w;
        board = new int[height][width];
        nMines = _nMines;
        diff = difficulty;
        
        if(diff == 0){
            increase = 0;
        }
        if(diff == 1){
            increase = 5;
        }
        if(diff == 2){
            increase = 35;
        }
        if (diff == 3){
            increase = 110;
        }
        
        if(h != w){
            System.out.println("Invalid Size! Size has been set to: " + h + " by " + h);
            width = h;
        }
        if(nMines > ((height * width)/8)){
            System.out.println("TOO MANT BOMBS! # of Bombs has been set to : " + ((height * width)/8));
            nMines = (((height * width) /8));
        }
        
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                board[row][col] = -1;
            }
        }

    }

    public void print(){
        if(p1.hasWon && p2.hasWon){
            System.out.println("BOTH PLAYERS WIN!");
            return;
        }
        if(p1.hasWon && p2.hasLost){
            System.out.println("PLAYER 1 WINS!");
            return;
        }
        if(p2.hasWon && p1.hasLost){
            System.out.println("PLAYER 2 WINS!");
            return;
        }
        if(p1.hasLost && p2.hasLost){
            System.out.println("BOTH PLAYERS LOSE");
            return;}
        for(int row = 0; row<height; row++){
            for(int col = 0; col < width; col++){
                if(board[row][col] >= 0){System.out.print(" ");}
                System.out.print(board[row][col] + " ");}
            System.out.println();
        }
        clicks++;
    }
    public void placeMines(int n_x, int n_y){
        
        Random random = new Random();
        
        for(int i = 0; i < nMines; i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            
            while(isMine(x,y) || x == n_x || y == n_y)
            {
                x = random.nextInt(width - 1);
                y = random.nextInt(height - 1);              
            }
            
            board[x][y] = -2; // mine placed
        }
    }
    public void click(int x, int y)
    {
        if((clicks % 2) == 0 && p2.hasLost){
            clicks++;
        }
        if((clicks % 2) != 0 && p1.hasLost){
            clicks++;
        }
        int x1 = 0,y1 = 0;
        if(increase != 0 && (int)(System.currentTimeMillis()%(increase*1000)) == 0){
            while(board[x1][y1] != -1 && board[x1][y1] != -2){
                while(x1 <= this.width){
                    x1++;
                    while(y1 <= this.height){
                        y1 = 0;
                    }
                }
                while(y1 <= this.height){
                    y1++;
                }
            }
            placeMines(x1,y1);  
        }
        if(board[x][y] == -3 || board[x][y] == -4){
            return;
        }
        if((clicks % 2) != 0){
            
        if(firstClick)
        {
            firstClick = false;
            placeMines(x, y);
            start = System.currentTimeMillis();
            clicks++;
            
        }
        if(isMine(x,y))
        {
            p1.hasLost = true;
            now = System.currentTimeMillis();
            clicks++;
            return;
        }
        
        // you lose
        
        board[x][y] = getAdjescentMines(x, y);
        if(board[x][y] == 0) clickAdjescent(x, y);
        // if a block has no adjescent mines, 'click' all adjescent blocks
        // this has a recursive effect
        
        if(hasWon()){
            p1.hasWon = true;
        } 
        now = System.currentTimeMillis();
        }   
    if((clicks % 2) == 0){
            
        if(firstClick)
        {
            firstClick = false;
            placeMines(x, y);
            start = System.currentTimeMillis();
            clicks++;
            
        }
        if(isMine(x,y))
        {
            p2.hasLost = true;
            now = System.currentTimeMillis();
            return;
        }
        
        // you lose
        
        board[x][y] = getAdjescentMines(x, y);
        if(board[x][y] == 0) clickAdjescent(x, y);
        // if a block has no adjescent mines, 'click' all adjescent blocks
        // this has a recursive effect
        
        if(hasWon()){
            p2.hasWon = true;
        } 
        now = System.currentTimeMillis();
        }
    clicks++;
    }
    
    public void rightClick(int x, int y)
    {   
        if((clicks % 2) == 0 && p2.hasLost){
            clicks++;
        }
        if((clicks % 2) != 0 && p1.hasLost){
            clicks++;
        }
        // right click places a "flag" of where you think a mine is
        // flags are removed by right-clicking again
        // flags can only be placed on previously unclicked areas
        switch (board[x][y]) {
            case -3:
                board[x][y] = -2;
                clicks++;
                break;
            case -4:
                board[x][y] = -1;
                clicks++;
                break;
            case -1:
                board[x][y] = -4;
                clicks++;
                break;
            case -2:
                board[x][y] = -3;
                clicks++;
                break;
            default:
                clicks++;
                break;
        }
    }
    
    public boolean hasWon()
    {
        for(int row = 0; row < height; row++)
        {
            for(int col = 0; col < width; col++)
            {
                if(board[row][col] == -1)
                    return false;
            }           
        }    
        
        return true;
    }
    
    public boolean isMine(int x, int y)
    {
        return board[x][y] == -2;
    }
    
    public void clickAdjescent(int x, int y)
    {
        // 'clicks' all the adjescent blocks
        // doesn't click blocks with 0-values to prevent stackoverflow
        
        if(x == 0)
        {
            if(y == 0) // top-left corner
            {
                if(board[x+1][y] != 0) click(x + 1, y);
                if(board[x+1][y + 1] != 0) click(x + 1, y + 1);
                if(board[x][y+1] != 0) click(x, y + 1);
            }
            else if(y == height - 1) //bottom-left corner
            {
                if(board[x+1][y] != 0) click(x + 1, y);
                if(board[x+1][y-1] != 0) click(x + 1, y - 1);
                if(board[x][y-1] != 0) click(x, y - 1);                
            }
            else // left border
            {
                if(board[x][y-1] != 0) click(x, y - 1);
                if(board[x+1][y-1] != 0) click(x + 1, y - 1);
                if(board[x+1][y] != 0) click(x + 1, y);               
                if(board[x+1][y+1] != 0) click(x + 1, y + 1);                
                if(board[x][y+1] != 0) click(x, y + 1);                 
            }              
        }
        else if(x == width - 1)
        {
            if(y == 0) // top-right corner
            {
                if(board[x-1][y] != 0) click(x - 1, y);
                if(board[x-1][y+1] != 0) click(x - 1, y + 1);
                if(board[x][y+1] != 0) click(x, y + 1);
            }
            else if(y == height - 1) //bottom-right corner
            {
                if(board[x-1][y] != 0) click(x - 1, y);
                if(board[x-1][y-1] != 0) click(x - 1, y - 1);
                if(board[x][y-1] != 0) click(x, y - 1);                
            }
            else //right border
            {
                if(board[x][y-1] != 0) click(x, y - 1);
                if(board[x-1][y-1] != 0) click(x - 1, y - 1);
                if(board[x-1][y] != 0) click(x -1, y);                
                if(board[x-1][y+1] != 0) click(x - 1, y + 1);                 
                if(board[x][y+1] != 0) click(x, y + 1);               
            }                     
        }
        else if(y == 0) // top border
        {
            if(board[x-1][y] != 0) click(x - 1, y);
            if(board[x-1][y+1] != 0) click(x - 1, y + 1);
            if(board[x][y+1] != 0) click(x, y + 1);
            if(board[x+1][y+1] != 0) click(x + 1, y + 1);
            if(board[x+1][y] != 0) click(x + 1, y);
        }
        else if(y == height - 1) // bottom border
        {
            if(board[x-1][y] != 0) click(x - 1, y);
            if(board[x-1][y-1] != 0) click(x - 1, y - 1);
            if(board[x][y-1] != 0) click(x, y - 1);
            if(board[x+1][y-1] != 0) click(x + 1, y - 1);
            if(board[x+1][y] != 0) click(x + 1, y);          
        }
        else // middle
        {
            if(board[x-1][y-1] != 0) click(x - 1, y - 1);
            if(board[x][y-1] != 0) click(x, y - 1);
            if(board[x+1][y-1] != 0) click(x + 1, y - 1);
            if(board[x+1][y] != 0) click(x + 1, y);
            if(board[x+1][y+1] != 0) click(x + 1, y + 1);
            if(board[x][y+1] != 0) click(x, y + 1);
            if(board[x-1][y+1] != 0) click(x - 1, y + 1);
            if(board[x-1][y] != 0) click(x - 1, y);
        }        
    }
    
    public int getAdjescentMines(int x, int y)
    {
        //gets the number of adjescent mines of a block
        //(horizontal, vertical and diagonal)
        int adj = 0;
        
        if(x == 0)
        {
            if(y == 0) // top-left corner
            {
                if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;
                if(board[x+1][y+1] == -2 || board[x+1][y+1] == -3) adj++;
                if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;
            }
            else if(y == height - 1) //bottom-left corner
            {
                if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;
                if(board[x+1][y-1] == -2 || board[x+1][y-1] == -3) adj++;
                if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;                
            }
            else // left border
            {
                if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;
                if(board[x+1][y-1] == -2 || board[x+1][y-1] == -3) adj++;
                if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;                 
                if(board[x+1][y+1] == -2 || board[x+1][y+1] == -3) adj++;                 
                if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;                 
            }              
        }
        else if(x == width - 1)
        {
            if(y == 0) // top-right corner
            {
                if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;
                if(board[x-1][y+1] == -2 || board[x-1][y+1] == -3) adj++;
                if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;
            }
            else if(y == height - 1) //bottom-right corner
            {
                if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;
                if(board[x-1][y-1] == -2 || board[x-1][y-1] == -3) adj++;
                if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;                
            }
            else //right border
            {
                if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;
                if(board[x-1][y-1] == -2 || board[x-1][y-1] == -3) adj++;
                if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;                 
                if(board[x-1][y+1] == -2 || board[x-1][y+1] == -3) adj++;                 
                if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;                 
            }                     
        }
        else if(y == 0) // top border
        {
            if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;
            if(board[x-1][y+1] == -2 || board[x-1][y+1] == -3) adj++;
            if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;
            if(board[x+1][y+1] == -2 || board[x+1][y+1] == -3) adj++;
            if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;
        }
        else if(y == height - 1) // bottom border
        {
            if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;
            if(board[x-1][y-1] == -2 || board[x-1][y-1] == -3) adj++;
            if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;
            if(board[x+1][y-1] == -2 || board[x+1][y-1] == -3) adj++;
            if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;            
        }
        else // middle
        {
            if(board[x-1][y-1] == -2 || board[x-1][y-1] == -3) adj++;
            if(board[x][y-1] == -2 || board[x][y-1] == -3) adj++;
            if(board[x+1][y-1] == -2 || board[x+1][y-1] == -3) adj++;
            if(board[x+1][y] == -2 || board[x+1][y] == -3) adj++;
            if(board[x+1][y+1] == -2 || board[x+1][y+1] == -3) adj++;
            if(board[x][y+1] == -2 || board[x][y+1] == -3) adj++;
            if(board[x-1][y+1] == -2 || board[x-1][y+1] == -3) adj++;
            if(board[x-1][y] == -2 || board[x-1][y] == -3) adj++;
        }
        
        return adj;
    }
}
