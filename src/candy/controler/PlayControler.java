/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.controler;

import candy.model.Level;
import candy.model.LevelModel;
import candy.model.PlayModel;
import candy.vue.PlayView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.*;
/**
 *
 * @author WAMBA KEVIN
 */

public class PlayControler {
    private PlayModel playModel;
    private PlayView view;
    private Button tile[][];
    private boolean bol=false;
    
    public PlayControler(PlayModel playModel) {
        this.playModel = playModel;
        this.view = new PlayView(this.playModel.getPicture());
        this.playModel.setView(view);
    }
    public void show() {
        view.show(playModel.getPrimaryStage());
        chargement();
    }

    private void chargement() {
        if(this.playModel.initRessource()){
            tile = view.showGame(playModel.getBoard(),playModel.getLevel());
            for(int i=0;i<playModel.getLevel().getY();i++){
                for(int j=0;j<playModel.getLevel().getX();j++){
                   if(playModel.getBoard().getGrid(i,j).getValue()!=-1){
                        tile[j][i].setOnMousePressed (new MouseEvenement());
                        tile[j][i].setOnMouseReleased(new MouseEvenement());
                        tile[j][i].setOnMouseExited  (new MouseEvenement());
                   }
                }
            }
            Button[] temp = new Button[2];
            temp=view.getButtons();
            temp[1].setOnAction(new ShowLevelChose());
        }
        else{
            view.showError("erreur survenue lors du chargement");
        }
    }
    public void updateGame(){
        tile = view.updateGame(playModel.getBoard(),playModel.getLevel());
            for(int i=0;i<playModel.getLevel().getY();i++){
                for(int j=0;j<playModel.getLevel().getX();j++){
                   if(playModel.getBoard().getGrid(i,j).getValue()>=1){
                        tile[j][i].setOnMousePressed (new MouseEvenement());
                        tile[j][i].setOnMouseReleased(new MouseEvenement());
                        tile[j][i].setOnMouseExited  (new MouseEvenement());
                   }
                }
            }
    }
    public void showWin(Level nextLevel,boolean next){
        Button[] temp =new Button[2];
        view.showWin(playModel.getBoard(),playModel.getLevel(),nextLevel,next);
        temp = view.getButtons();
            for(int i=0;i<2;i++){
                temp[0].setOnAction(new GameEvent(nextLevel));
                temp[1].setOnAction(new ShowLevelChose());
            }
    }
    public void showLose() {
        Button[] temp =new Button[2];
        view.showLose(playModel.getBoard(),playModel.getLevel());
        temp = view.getButtons();
            for(int i=0;i<2;i++){
                temp[0].setOnAction(new GameEvent(playModel.getLevel()));
                temp[1].setOnAction(new ShowLevelChose());
            }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++
   // Evenement
   //+++++++++++++++++++++++++++++++++++++++++++++
    class GameEvent implements EventHandler<ActionEvent> {
        private Level levelNext;
        public GameEvent(Level levelNext) {
            this.levelNext=levelNext;
        }

        @Override
        public void handle(ActionEvent event) {
            this.levelNext.renitialize();
           PlayModel playModelNext = new PlayModel(playModel.getPrimaryStage(),playModel.getPicture(),this.levelNext,playModel.getListLevel(),playModel.getSons());
           PlayControler playControler = new PlayControler(playModelNext);
           playModelNext.setControler(playControler);
           playControler.show();
        }
    }

     class ShowLevelChose implements EventHandler<ActionEvent> {

        public ShowLevelChose() {
        }

        @Override
        public void handle(ActionEvent event) {
            LevelModel levelModel = new LevelModel(playModel.getPrimaryStage(),playModel.getPicture(),playModel.getSons());
        LevelControler levelControler = new LevelControler(levelModel);
        levelControler.show();
        }
    }
  
    class MouseEvenement implements EventHandler<MouseEvent>{
        public MouseEvenement(){
        }
        @Override
        public void handle(MouseEvent event) {
            if(event.getEventType()==MOUSE_RELEASED){
                bol=false;
            }
            if(event.getEventType()==MOUSE_PRESSED){
                bol=true;
            }
            if(event.getEventType()==MOUSE_EXITED){
                int is = -1,js = -1,id = -1,jd = -1;
                if(bol==true){
                    for(int i=0;i<playModel.getLevel().getY();i++){
                        for(int j=0;j<playModel.getLevel().getX();j++){
                           if(playModel.getBoard().getGrid(i,j).getValue()!=-1){
                                if(tile[j][i]==event.getSource()){
                                    is=i;
                                    js=j;
                                }
                                if(tile[j][i]==event.getPickResult().getIntersectedNode()){
                                    id=i;
                                    jd=j;
                                }
                           }
                        }
                    }
                    if(jd !=-1 && id!=-1){
                        playModel.detectMatches(is, js, id, jd);
                    }
                }
            bol=false;
            }
            
        }

    }
    class playBtnEventHandler implements EventHandler<ActionEvent> {
       private Level tempLevel;

       public playBtnEventHandler(Level tempLevel) {
           this.tempLevel=tempLevel;
       }

       @Override
       public void handle(ActionEvent event) {
           PlayModel playModelS = new PlayModel(playModel.getPrimaryStage(),playModel.getPicture(),this.tempLevel,playModel.getListLevel(),playModel.getSons());
           PlayControler playControler = new PlayControler(playModelS);
           playModel.setControler(playControler);
           playControler.show();
       }
   }
    class StopBtnEventHandler implements EventHandler<ActionEvent> {
       public StopBtnEventHandler() {
           
       }
       @Override
       public void handle(ActionEvent event) {
           
       }
   }
    
}
