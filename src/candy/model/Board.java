
package candy.model;

/**
 *
 * @author WAMBA KEVIN
 */
public class Board {
    // grille avec un num�ro de couleur par case
    private Tile grid[][];
    // pour marquer les cases non align�es
    private boolean marked[][];
    //score 
    private int score=0;
    //niveau en cours
    private Level level;
    
    public Board(Level level) {
        this.level = level;
        NewGame();
    }
    public Board(Board board){
        this.grid=board.getGrid(board);
    }
    public Tile[][] getGrid(Board board){
        Tile temp[][] = new Tile[board.level.getY()][board.level.getX()];
        for (int i = 0; i <level.getY(); i++) {
            for (int j = 0; j < level.getX(); j++) {
                temp[i][j]=new Tile(board.getGrid(i, j));
            }
        }    
        return temp;
    }
    public Tile getGrid(int x,int y){
        return this.grid[x][y];
    }
    public void setGrid(int x,int y,Tile tile){
        //this.grid[x][y].setGelatine(tile.getGelatine());
        this.grid[x][y].setObject(tile.getObject());
        this.grid[x][y].setSimple(tile.getSimple());
        this.grid[x][y].setSpType(tile.getSpType());
        this.grid[x][y].setSpeciale(tile.getSpeciale());
        this.grid[x][y].setValue(tile.getValue());
    }
    public void NewGame(){
        boolean res;
        do{
            res = generateBoard(); 
        }while(res==false);
        
    }
    public boolean generateBoard(){
    	grid = level.getTile();
        int nbTour = 0;
    	boolean regNb =false;
    	for(int i=0;i<level.getY();i++){
            for(int j=0;j<level.getX();j++){
                if(grid[i][j].getValue()>=0){
                    do{
                        if(nbTour>=6){
                            return false;   
                        }
                        grid[i][j].setValue(((int)(Math.random()*level.getColor()))+1);
			if(i>=2){
                            if(grid[i][j].getValue()==grid[i-1][j].getValue() && grid[i][j].getValue()==grid[i-2][j].getValue()){
				regNb=true;
                                ++nbTour;
                            }
			}
			if(j>=2){
                            if(grid[i][j].getValue()==grid[i][j-1].getValue() && grid[i][j].getValue()==grid[i][j-2].getValue()){
				regNb =true;
                                ++nbTour;
                            }
			}
                    }while(regNb == true);
                }
            }
        }
        return true;
    }
    public void setScore(int score){
        this.score+=score;
    }
    public int getScore(){
        return this.score;
    }
}
