

package candy.model;

import candy.controler.PlayControler;
import candy.vue.PlayView;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.stage.Stage;
/**
 *
 * @author WAMBA KEVIN
 */
public class PlayModel {
    Board board;
    private Stage primaryStage;
    private Picture picture;
    private ArrayList listLevel;
    Level level;
    int combo=1;
    // pour marquer les cases alignées
    private boolean marked[][];
    PlayView view;
    PlayControler controler;
    private Sons son;
    int x1,y1,x2,y2;

    public PlayModel(Stage primaryStage, Picture picture, Level level,ArrayList listL,Sons son) {
        this.level =level;
        this.listLevel=listL;
        this.primaryStage = primaryStage;
        this.picture = picture;  
        this.son = son;
    }
    //initialize la partie
    public boolean initRessource(){
        this.board=new Board(level); 
        marked = new boolean[level.getY()][level.getX()];
        return true;
    }
    //les setteur
    public void setControler(PlayControler controler){
        this.controler = controler;
    }
    public void setView(PlayView view){
        this.view = view;
    }
    //les getteur
    public Picture getPicture() {
       return this.picture;
    }
    public Stage getPrimaryStage() {
       return primaryStage;
    }
    public Board getBoard(){
        return board;
    }
    public Sons getSons(){
       return this.son;
    }
    public Level getLevel(){
        return level;
    }
    public ArrayList getListLevel(){
        return this.listLevel;
    }
    //determine si le coup jouer est bon ou non et enclanche une serie de methode
    public void detectMatches(int x1,int y1,int x2,int y2){
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        if(board.getGrid(x1, y1).getSpeciale() || board.getGrid(x2, y2).getSpeciale()){
            if(board.getGrid(x1, y1).getSpType()=="D"){
                swap(x1, y1, x2, y2);
                marked[x2][y2]=true;
                removeColor(x2,y2,x1,y1,board.getGrid(x2, y2).getSpeciale() && board.getGrid(x1, y1).getSpType()=="D");
                level.setMoves(level.getMoves()-1);
                controler.updateGame();
                GridAnime anime = new GridAnime(this);
                anime.setDaemon(true);
                anime.start();
            }
            if(board.getGrid(x2, y2).getSpType()=="D"){
                swap(x1, y1, x2, y2);
                marked[x1][y1]=true;
                removeColor(x1,y1,x2,y2,board.getGrid(x1, y1).getSpeciale() && board.getGrid(x2, y2).getSpType()=="D");
                level.setMoves(level.getMoves()-1);
                controler.updateGame();
                GridAnime anime = new GridAnime(this);
                anime.setDaemon(true);
                anime.start();
            }
            if(board.getGrid(x1, y1).getSpeciale() && board.getGrid(x2, y2).getSpeciale()){
                //si on melange deux bonbon spéciaux horizontal et vertical
                if((board.getGrid(x2, y2).getSpType()=="H" && board.getGrid(x1, y1).getSpType()=="V")
                    || (board.getGrid(x2, y2).getSpType()=="V" && board.getGrid(x1, y1).getSpType()=="H")
                    || (board.getGrid(x2, y2).getSpType()=="H" && board.getGrid(x1, y1).getSpType()=="H")
                    || (board.getGrid(x2, y2).getSpType()=="V" && board.getGrid(x1, y1).getSpType()=="V")){
                    swap(x1, y1, x2, y2);
                    removeRowsCols(x2,y2);
                    level.setMoves(level.getMoves()-1);
                    controler.updateGame();
                    GridAnime anime = new GridAnime(this);
                    anime.setDaemon(true);
                    anime.start();
                }
                //melange d'une bonbon embalé avec un bonbon rayé
                if((board.getGrid(x2, y2).getSpType()=="E" && board.getGrid(x1, y1).getSpType()=="V")
                    || (board.getGrid(x2, y2).getSpType()=="E" && board.getGrid(x1, y1).getSpType()=="H")
                    || (board.getGrid(x2, y2).getSpType()=="H" && board.getGrid(x1, y1).getSpType()=="E")
                    || (board.getGrid(x2, y2).getSpType()=="V" && board.getGrid(x1, y1).getSpType()=="E")){
                    level.setMoves(level.getMoves()-1);
                    swap(x1, y1, x2, y2);
                    remove3RowsCols(x2,y2);
                    controler.updateGame();
                    GridAnime anime = new GridAnime(this);
                    anime.setDaemon(true);
                    anime.start();
                }
                //melange de deux bonbons emballé
                if((board.getGrid(x2, y2).getSpType()=="E" && board.getGrid(x1, y1).getSpType()=="E")){
                    level.setMoves(level.getMoves()-1);
                    swap(x1, y1, x2, y2);
                    removeDoubleBorderCandy();
                    controler.updateGame();
                    GridAnime anime = new GridAnime(this);
                    anime.setDaemon(true);
                    anime.start();
                }
            }
        }
        if(isValidSwap(x1, y1, x2, y2)){
            level.setMoves(level.getMoves()-1);
            swap(x1, y1, x2, y2);
            controler.updateGame();
            GridAnime anime = new GridAnime(this);
            anime.setDaemon(true);
            anime.start();
        }else{

        }
        
    }
    //determine les bonbons aligné horizontalement
    public boolean horizontalMatches(int i, int j) {
		if (i < 0 || j < 0 || i >= level.getY()- 2 || j >= level.getX())
			return false;
                if(board.getGrid(i,j).getValue() != -1 && board.getGrid(i+1,j).getValue()!=-1 && board.getGrid(i+2,j).getValue()!=-1){
                    if (board.getGrid(i,j).getValue() == board.getGrid(i+1,j).getValue() && board.getGrid(i,j).getValue() == board.getGrid(i+2,j).getValue())
                            return true;
                }
		return false;
    }
    //determine si les bonbons sont aligné vericalement
    public boolean verticalMatches(int i, int j) {
	if (i < 0 || j < 0 || i >= level.getY() || j >= level.getX()- 2){
            return false;
        }else{
            if(board.getGrid(i,j).getValue() != -1 && board.getGrid(i,j+1).getValue()!=-1 && board.getGrid(i,j+2).getValue()!=-1){
                if (board.getGrid(i,j).getValue() == board.getGrid(i,j+1).getValue() && board.getGrid(i,j).getValue() == board.getGrid(i,j+2).getValue())
                    return true;
            }
        }
	return false;
    }
    //echange deux bonbon
    public void swap(int x1, int y1, int x2, int y2) {
	Tile tmp = new Tile(board.getGrid(x1, y1));
	board.setGrid(x1, y1,board.getGrid(x2,y2));
	board.setGrid(x2,y2,tmp);
    }
    //determine si l'echange est posible
    public boolean isValidSwap(int x1, int y1, int x2, int y2) {
	//on effectue l'échange
	swap(x1, y1, x2, y2);
	boolean newAlignment = false;
	for (int i = 0; i < 3; i++) {
            newAlignment |= horizontalMatches(x1 - i,y1);
            newAlignment |= horizontalMatches(x2 - i, y2);
            newAlignment |= verticalMatches(x1, y1 - i);
            newAlignment |= verticalMatches(x2, y2 - i);
	}
	// puis on annule l'échange
	swap(x1, y1, x2, y2);
	return newAlignment;
    }
    //enlève les bonbons qui sont aligné
    public boolean removeAlignments() {
		// passe 1 : marquer tous les alignements
		for (int i = 0; i < level.getY(); i++) {
			for (int j = 0; j < level.getX(); j++) {
                                if(board.getGrid(i,j).getValue() != -1){
                                    if (horizontalMatches(i,j)) {
					marked[i][j] = marked[i+1][j] = marked[i+2][j] = true;
                                        son.playWrapped();
                                    }
                                    if (verticalMatches(i,j)) {
                                        marked[i][j] = marked[i][j + 1] = marked[i][j + 2] = true;
                                        son.playWrapped();
                                    }
                                }
                                
			}
		}
                getGelatine();
                //gestion du score
                gestScore();
		// passe 2 : supprimer les cases marqu�es
		boolean modified = false;
                modified=setDiscoSpecialCandy();
                modified=setTieCandy();
                modified=setHorizontalSpecialCandy();
                modified=setVerticalSpecialCandy();
                modified=effectSpecialCandy();
		for (int i = 0; i < level.getY(); i++) {
			for (int j = 0; j < level.getX(); j++) {
				if (marked[i][j]) {
					board.getGrid(i,j).setDefault();
					marked[i][j] = false;
					modified = true;
				}
			}
		}
		return modified;
	}
    //gestion des scores
    private void gestScore() throws ArrayIndexOutOfBoundsException{
        int nbrAlign=1;
        for (int i = 0; i < level.getY(); i++) {
            for (int j = 0; j < level.getX(); j++) {
                if(marked[i][j]==true){
                    try{
                        while(marked[i+1][j]==true){
                            nbrAlign++;
                            i++;
                        }
                    }catch(ArrayIndexOutOfBoundsException e){
                        
                    }
                    if(nbrAlign==3)
                        board.setScore(60*combo);
                    if(nbrAlign==4)
                        board.setScore(120*combo);
                    if(nbrAlign==5)
                        board.setScore(200*combo);
                }
                nbrAlign=1;
            }
        }
        for (int i = 0; i < level.getY(); i++) {
            for (int j = 0; j < level.getX(); j++) {
                if(marked[i][j]==true){
                    try{
                        while(marked[i][j+1]==true){
                            nbrAlign++;
                            j++;
                        }
                    }catch(ArrayIndexOutOfBoundsException e){
                        
                    }
                    if(nbrAlign==3)
                        board.setScore(60*combo);
                    if(nbrAlign==4)
                        board.setScore(120*combo);
                    if(nbrAlign==5)
                        board.setScore(200*combo);
                }
                nbrAlign=1;
            }
        }
        
    }
    //effet des bonbon spéciaux
    public boolean effectSpecialCandy(){
            boolean modified=false;
            for (int i = 0; i < level.getY(); i++) {
		for (int j = 0; j < level.getX(); j++) {
                      if (marked[i][j] && board.getGrid(i, j).getSpeciale()) {
			    if(board.getGrid(i, j).getSpType()=="H"){
                                for(int k=0;k<level.getX();k++)
                                    if(board.getGrid(i,k).getValue()>=0)
                                        marked[i][k]=true;
                            }
                            if(board.getGrid(i, j).getSpType()=="V"){
                                for(int k=0;k<level.getY();k++){
                                    if(board.getGrid(k,j).getValue()>=0)
                                        marked[k][j]=true;
                                }
                                    
                            }
                            if(board.getGrid(i, j).getSpType()=="E"){
                                try{
                                    if(board.getGrid(i-1,j-1).getValue()>=0)
                                        marked[i-1][j-1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i-1,j).getValue()>=0)
                                        marked[i-1][j]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i-1,j+1).getValue()>=0)
                                        marked[i-1][j+1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i,j+1).getValue()>=0)
                                        marked[i][j+1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i+1,j+1).getValue()>=0)
                                        marked[i+1][j+1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i+1,j).getValue()>=0)
                                        marked[i+1][j]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i+1,j-1).getValue()>=0)
                                        marked[i+1][j-1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                                try{
                                    if(board.getGrid(i,j-1).getValue()>=0)
                                        marked[i][j-1]=true;
                                }catch(ArrayIndexOutOfBoundsException e){}
                            }
                        }      
                }
            }
            getGelatine();
            return modified;
        }
    //gestion des bonbon speciaux
    public boolean setDiscoSpecialCandy(){
            boolean modified=false;
            //boule disco horizontale
            for (int i = 0; i < level.getY(); i++) {
                for (int j = 0; j < level.getX()-4; j++) {
                    if(board.getGrid(i,j).getValue()>0 && marked[i][j] && marked[i][j+1] && marked[i][j+2] && marked[i][j+3] && marked[i][j+4]){
                        if(board.getGrid(i,j).getValue()==board.getGrid(i, j+1).getValue() 
                                   && board.getGrid(i,j).getValue()==board.getGrid(i, j+2).getValue() 
                                   && board.getGrid(i,j).getValue()==board.getGrid(i, j+3).getValue()
                                   && board.getGrid(i,j).getValue()==board.getGrid(i, j+4).getValue()){
                            board.getGrid(i,j+2).setSpeciale(true);
                            board.getGrid(i,j+2).setSpType("D");
                            board.getGrid(i,j+2).setValue(50);
                            marked[i][j+2]=false;
                            modified=true;
                        }
                    }        
                }
            }
            //boule disco verticale
            for (int i = 0; i < level.getY()-4; i++) {
                for (int j = 0; j < level.getX(); j++) {
                    if(board.getGrid(i,j).getValue()>0 && marked[i][j] && marked[i+1][j] && marked[i+2][j] && marked[i+3][j] && marked[i+4][j]){
                        if(board.getGrid(i,j).getValue()==board.getGrid(i+1, j).getValue() 
                                   && board.getGrid(i,j).getValue()==board.getGrid(i+2,j).getValue() 
                                   && board.getGrid(i,j).getValue()==board.getGrid(i+3,j).getValue()
                                   && board.getGrid(i,j).getValue()==board.getGrid(i+4,j).getValue()){
                            board.getGrid(i+2,j).setSpeciale(true);
                            board.getGrid(i+2,j).setSpType("D");
                            board.getGrid(i+2, j).setValue(50);
                            marked[i+2][j]=false;
                            modified=true;
                        }
                    }        
                }
            }
            getGelatine();
            return modified;
        }
    public boolean setTieCandy(){
            boolean modified=false;
            //recherche des posibilité en L et en T
            for (int i = 0; i < level.getY(); i++) {
			for (int j = 0; j < level.getX(); j++) {
                            //les possibilité en T
                            if(i>=2 && j>=1 && j<level.getX()-1){
                                if(marked[i][j] && marked[i][j+1] && marked[i][j-1] && marked[i-1][j] && marked[i-2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j+1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j-1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i<level.getY()-2 && j>=1 && j<level.getX()-1){
                                if(marked[i][j] && marked[i][j+1] && marked[i][j-1] && marked[i+1][j] && marked[i+2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j+1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j-1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i>=1 && i<level.getY()-1 && j<level.getX()-2){
                                if(marked[i][j] && marked[i][j+1] && marked[i][j+2] && marked[i+1][j] && marked[i-1][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j+1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j+2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-1, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i>=1 && i<level.getY()-1 && j>=2){
                                if(marked[i][j] && marked[i][j-1] && marked[i][j-2] && marked[i+1][j] && marked[i-1][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j-1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j-2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-1, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            //les possiblité en L
                            if(i<level.getY()-2 && j<level.getX()-2){
                                if(marked[i][j] && marked[i][j+1] && marked[i][j+2] && marked[i+1][j] && marked[i+2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j+1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j+2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i+2<level.getY() && j>=2){
                                if(marked[i][j] && marked[i][j-1] && marked[i][j-2] && marked[i+1][j] && marked[i+2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j-1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j-2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i+2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i>=2 && j>=2){
                                if(marked[i][j] && marked[i][j-1] && marked[i][j-2] && marked[i-1][j] && marked[i-2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j-1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j-2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                            if(i>=2 && j<level.getX()-2){
                                if(marked[i][j] && marked[i][j+1] && marked[i][j+2] && marked[i-1][j] && marked[i-2][j]){
                                    if(board.getGrid(i, j).getValue()==board.getGrid(i, j+1).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i, j+2).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-1, j).getValue()&&
                                    board.getGrid(i, j).getValue()==board.getGrid(i-2, j).getValue()){
                                        board.getGrid(i,j).setSpeciale(true);
                                        board.getGrid(i,j).setSpType("E");
                                        marked[i][j]=false;
                                        modified=true;
                                    }
                                }
                            }
                        }
            }
            getGelatine();
            return modified;
        }
    public boolean setVerticalSpecialCandy(){
            boolean modified=false;
            for (int i = 0; i < level.getY(); i++) {
			for (int j = 0; j < level.getX()-3; j++) {
                            if(board.getGrid(i,j).getValue()>0 && marked[i][j] && marked[i][j+1] && marked[i][j+2] && marked[i][j+3]){
				if(board.getGrid(i,j).getValue()==board.getGrid(i, j+1).getValue() 
                                   && board.getGrid(i,j).getValue()==board.getGrid(i, j+2).getValue() 
                                    && board.getGrid(i,j).getValue()==board.getGrid(i, j+3).getValue()){
                                    if(y1>=j && y1<=j+3 && x1==i){
                                          board.getGrid(x1,y1).setSpeciale(true);
                                          board.getGrid(x1,y1).setSpType("V");
                                          marked[x1][y1]=false;
                                      }else if(y2>=j && y2<=j+3 && x2==i){
                                          board.getGrid(x2,y2).setSpeciale(true);
                                          board.getGrid(x2,y2).setSpType("V");
                                          marked[x2][y2]=false;
                                      }else{
                                          board.getGrid(i,j+1).setSpeciale(true);
                                          board.getGrid(i,j+1).setSpType("V");
                                          marked[i][j+1]=false;
                                      }
                                      modified=true;
                                }
                            }    
			}
		}
            return modified;
        }
    public boolean setHorizontalSpecialCandy(){
            boolean modified=false;
            for (int i = 0; i < level.getY()-3; i++) {
			for (int j = 0; j < level.getX(); j++) {
                            if(board.getGrid(i,j).getValue()>0 && marked[i][j] && marked[i+1][j] && marked[i+2][j] && marked[i+3][j]){
				if(board.getGrid(i+1,j).getValue()==board.getGrid(i, j).getValue() 
                                   && board.getGrid(i+2,j).getValue()==board.getGrid(i, j).getValue() 
                                    && board.getGrid(i+3,j).getValue()==board.getGrid(i, j).getValue()){
                                    if(x1>=i && x1<=i+3 && y1==j){
                                          board.getGrid(x1,y1).setSpeciale(true);
                                          board.getGrid(x1,y1).setSpType("H");
                                          marked[x1][y1]=false;
                                      }else if(x2>=i && x2<=i+3 && y2==j){
                                          board.getGrid(x2,y2).setSpeciale(true);
                                          board.getGrid(x2,y2).setSpType("H");
                                          marked[x2][y2]=false;
                                      }else{
                                          board.getGrid(i,j).setSpeciale(true);
                                          board.getGrid(i,j).setSpType("H");
                                          marked[i][j]=false;
                                      }
                                      modified=true;
                                }
                            }    
			}
		}
            return modified;
        }
    //determine si le joueur a gagner ou perdue    
    public void determinateWin(){
        if(level.getType().equalsIgnoreCase("DL")){
            if(level.getMoves()<=0){
                if(board.getScore()>=level.getTarget_bronze()){
                    Level temp=level;
                    boolean next=false;
                    for(int k=0;k<this.listLevel.size();k++){
                        temp=(Level)this.listLevel.get(k);
                        if(this.level.getNumber()+1==temp.getNumber()){
                            temp.unlock();
                            temp.save(0);
                            next=true;
                            break;
                        }
                    }
                    level.save(board.getScore());
                    controler.showWin(temp,next);
                    son.playWin();
                }
                else{
                    controler.showLose();
                    son.playLose();
                }
            }
        }else if(level.getType().equalsIgnoreCase("GL")){
            boolean win = true;
            for (int i = 0; i < level.getY(); i++) {
                for (int j = 0; j < level.getX(); j++) {
                    if(board.getGrid(i, j).getGelatine())
                        win=false;
                }
            }
            //tavleau contenant les elts a retirer après avoir gagné le niveau a gelatine
            if(win && board.getScore()>=level.getTarget_bronze()){
                board.setScore(level.getMoves()*1000);
                Platform.runLater(()->view.updateGame(board, level));
                 Level temp=level;
                    boolean next=false;
                    for(int k=0;k<this.listLevel.size();k++){
                        temp=(Level)this.listLevel.get(k);
                        if(this.level.getNumber()+1==temp.getNumber()){
                            temp.unlock();
                            temp.save(0);
                            next=true;
                            break;
                        }
                    }
                    level.save(board.getScore());
                    controler.showWin(temp,next);
                    son.playWin();
            }else{
                if(level.getMoves()<=0){
                    controler.showLose();
                    son.playLose();
                }    
            }
                
        }
    }
    //retire les elements de couleur qui sont entréé en contact avec une boule disco
    private void removeColor(int x,int y,int x1,int y1,boolean allRemove) {
        //allRemove permet de retirer tout les bonbons si on a fusionner deux boule disco
        for (int i = 0; i < level.getY(); i++) {
            for (int j = 0; j < level.getX(); j++) {
                if(board.getGrid(i, j).getValue()==board.getGrid(x1,y1).getValue() || allRemove){
                    if(board.getGrid(x1,y1).getValue()>=0 && board.getGrid(x1,y1).getValue()<=6){
                        //transformer les bonbons a retiré au type du bonbon touché par la boule disco
                        //board.setGrid(i,j,board.getGrid(x1,y1));
                        marked[i][j]=true;
                        board.setScore(60);
                    }
                }   
            }
        }
        getGelatine();
    }
    //retire une ligne entiere et une colonne entiere
    private void removeRowsCols(int x,int y){
        for(int i=0;i<level.getX();i++){
            if(board.getGrid(x,i).getValue()>=0){
                marked[x][i]=true;
                board.setScore(60);
            }
        }
        for(int i=0;i<level.getY();i++){
            if(board.getGrid(i,y).getValue()>=0){
                 marked[i][y]=true;
                 board.setScore(60);
            }
        }
        getGelatine();
    }
    //retire 3 ligne et 3 colonne
    private void remove3RowsCols(int x, int y) {
        for(int i=0;i<level.getX();i++){
            try{
                if(board.getGrid(x,i).getValue()>=0){
                    marked[x][i]=true;
                    board.setScore(60);
                }
                if(board.getGrid(x-1,i).getValue()>=0){
                    marked[x-1][i]=true;
                    board.setScore(60);
                }
                if(board.getGrid(x+1,i).getValue()>=0){
                    marked[x+1][i]=true;
                    board.setScore(60);
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
        }
        for(int i=0;i<level.getY();i++){
            try{
                if(board.getGrid(i,y).getValue()>=0){
                     marked[i][y]=true;
                     board.setScore(60);
                }
                if(board.getGrid(i,y-1).getValue()>=0){
                     marked[i][y-1]=true;
                     board.setScore(60);
                }
                if(board.getGrid(i,y+1).getValue()>=0){
                     marked[i][y+1]=true;
                     board.setScore(60);
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
        }
        getGelatine();
    }
   //retire les bonbons situé aux allentour de 2 bouton 
    private void removeDoubleBorderCandy() {
        try{
            if(board.getGrid(x1-1,y1-1).getValue()>=0){
                marked[x1-1][y1-1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1-1,y1).getValue()>=0){
                marked[x1-1][y1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1-1,y1+1).getValue()>=0){
                marked[x1-1][y1+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1,y1+1).getValue()>=0){
                marked[x1][y1+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1+1,y1+1).getValue()>=0){
                marked[x1+1][y1+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1+1,y1).getValue()>=0){
                marked[x1+1][y1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1+1,y1-1).getValue()>=0){
                marked[x1+1][y1-1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x1,y1-1).getValue()>=0){
                marked[x1][y1-1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        
        try{
            if(board.getGrid(x2-1,y2-1).getValue()>=0){
                marked[x2-1][y2-1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2-1,y2).getValue()>=0){
                marked[x2-1][y2]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2-1,y2+1).getValue()>=0){
                marked[x2-1][y2+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2,y2+1).getValue()>=0){
                marked[x2][y2+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2+1,y2+1).getValue()>=0){
                marked[x2+1][y2+1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2+1,y2).getValue()>=0){
                marked[x2+1][y2]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2+1,y2-1).getValue()>=0){
                marked[x2+1][y2-1]=true;
                board.setScore(60);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(board.getGrid(x2,y2-1).getValue()>=0){
                marked[x2][y2-1]=true;
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        getGelatine();
    }
    //gestion de la gelatine
    private void getGelatine() {
        for (int i = 0; i < level.getY(); i++) {
            for (int j = 0; j < level.getX(); j++) {
                if(marked[i][j] && board.getGrid(i, j).getGelatine()){
                    board.setScore(1000);
                    board.getGrid(i, j).setGelatine(false);
                }
            }
        }
    }
    
}