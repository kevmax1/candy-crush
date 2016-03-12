/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.controler;

import candy.model.Level;
import candy.model.LevelModel;
import candy.model.PlayModel;
import candy.vue.LevelView;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.json.JSONException;

/**
 *
 * @author WAMBA KEVIN
 */
public class LevelControler {
    private LevelModel levelModel;
    private LevelView view;
    
    public LevelControler(LevelModel levelModel){
        this.levelModel = levelModel;
        this.view = new LevelView(levelModel.getPicture());
    
        
    }
    public void show() {
        view.show(levelModel.getPrimaryStage());
        chargement();
    }
    private void chargement() {
        try {
            this.levelModel.loadLevel();
            chargeChoseLevel(levelModel.getListLevel());

        } catch (IOException ex) {
            view.showError(ex.getMessage());
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            view.showError("  Erreur dans les fichiers de configuration du jeux...");
        }    
    }
    private void chargeChoseLevel(ArrayList listLevel){
        for (int i = 0; i < listLevel.size(); i++) {
            int x;
            int y;
            do{
                x = (int)(Math.random()*9);
                y = (int)(Math.random()*9);
            }while(levelModel.posOcup(x,y));
            levelModel.setPosInMap(x, y);
            Level temp =(Level) listLevel.get(i);
            view.addLevel(temp.getNumber(),temp.getType(),temp.getUnlock(), x, y).setOnAction(new levelBtnEventHandler(i,listLevel,view));
            
        }
        view.showMap(levelModel.getPrimaryStage());
    }
   //+++++++++++++++++++++++++++++++++++++++++++++
   // Evenement
   //+++++++++++++++++++++++++++++++++++++++++++++

    


 
    
class levelBtnEventHandler implements EventHandler<ActionEvent>{
       private int pos;
       private ArrayList listLevel;
       //private LevelView view;
       public levelBtnEventHandler(int pos,ArrayList listLevel,LevelView view){
            this.pos=pos;
            this.listLevel=listLevel;
            //this.view=view;
        }
        @Override
        public void handle(ActionEvent e) {   
            Level temp =(Level) listLevel.get(pos);
            Button[] tempButton = new Button[2];
            tempButton = view.showInfo(temp);
            tempButton[0].setOnAction(new playBtnEventHandler(temp));
            tempButton[1].setOnAction(new StopBtnEventHandler());
            
        }
   }

    class playBtnEventHandler implements EventHandler<ActionEvent> {
       private Level tempLevel;

       public playBtnEventHandler(Level tempLevel) {
           this.tempLevel=tempLevel;
       }

       @Override
       public void handle(ActionEvent event) {
           PlayModel playModel = new PlayModel(levelModel.getPrimaryStage(),levelModel.getPicture(),this.tempLevel,levelModel.getListLevel(),levelModel.getSons());
           PlayControler playControler = new PlayControler(playModel);
           playModel.setControler(playControler);
           playControler.show();
       }
   }
    class StopBtnEventHandler implements EventHandler<ActionEvent> {
       public StopBtnEventHandler() {
           
       }
       @Override
       public void handle(ActionEvent event) {
           view.moveInfo();
       }
   }
}