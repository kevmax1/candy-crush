/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author WAMBA KEVIN
 */
public class GridAnime extends Thread{
    private PlayModel playModel;
    public GridAnime(PlayModel playModel){
        super("anime");
        this.playModel=playModel;
    }
    @Override
    public void run(){
        boolean remA;
		    boolean modified = false;
                    do{
                        modified = false;
                        remA=playModel.removeAlignments();
                        if(playModel.combo==4)
                            playModel.getSons().playJuicy();
                        else if(playModel.combo==5)
                            playModel.getSons().playDelicious();
                        else if(playModel.combo==6)
                            playModel.getSons().playDivine();
                        playModel.combo++;
                        do{
                            modified = false;
                            try {
                                    Thread.sleep(200);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(GridAnime.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            Platform.runLater(()->playModel.view.updateGame(playModel.board,playModel.level));
                                
                            for (int i = playModel.level.getY()-1; i >= 0; i--) {
                                for (int j = 0; j < playModel.level.getX(); j++) {
                                    if( i==0 && playModel.board.getGrid(i, j).getValue()==0){
                                        modified = true;
                                        playModel.board.getGrid(i,j).setDefault();
                                        playModel.board.getGrid(i, j).setValue(((int)(Math.random()*playModel.level.getColor()))+1);
                                    }else{
                                        if(playModel.board.getGrid(i, j).getValue()==0){
                                            if(playModel.board.getGrid(i-1, j).getValue()!=-1){
                                                 playModel.board.setGrid(i,j,playModel.board.getGrid(i-1,j));
                                                 playModel.board.getGrid(i-1, j).setDefault();
                                                 modified = true;
                                            }else{
                                                int temp = i-1;
                                                boolean tempModif=false;
                                                while(temp!=-1){
                                                    if(playModel.board.getGrid(temp, j).getValue()==-1){
                                                        temp--;
                                                    }else{
                                                        playModel.board.setGrid(i, j,playModel.board.getGrid(temp,j));
                                                        playModel.board.getGrid(temp, j).setDefault();
                                                        modified = true;
                                                        tempModif=true;
                                                        break;
                                                    }
                                                }
                                                if(tempModif==false){
                                                    playModel.board.getGrid(i,j).setDefault();
                                                    playModel.board.getGrid(i,j).setValue(((int)(Math.random()*playModel.level.getColor()))+1);
                                                }
                                            }
                                        }
                                    }

                                }
                                
                            }
                        }while(modified);
                        if(modified==false && remA==false){
                            break;
                        }
                    }while(true);
                    playModel.combo=1;
                    Platform.runLater(()->playModel.controler.updateGame());
                    playModel.determinateWin();
    }
}
