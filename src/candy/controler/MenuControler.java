/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.controler;

import candy.model.LevelModel;
import candy.model.MenuModel;
import candy.vue.MenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author WAMBA KEVIN
 */
public class MenuControler {
    
    private MenuModel menuModel;
    private MenuView view;
    
    public MenuControler(MenuModel menuModel){
        this.menuModel = menuModel;
        this.view = new MenuView();
    }
    public void show() {
        view.show(menuModel.getPrimaryStage());
        chargement();
    }
    public void chargement(){
        if(menuModel.loadRessource()==0){
            
        }else{
            view.setPicture(menuModel.getPicture());
            view.setMenu();
            view.getPlayButton().setOnAction(new playBtnEventHandler());  
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++
   // Evenement
   //+++++++++++++++++++++++++++++++++++++++++++++
 
    
   class playBtnEventHandler implements EventHandler<ActionEvent>{
 
      @Override
      public void handle(ActionEvent e) {   
        LevelModel levelModel = new LevelModel(menuModel.getPrimaryStage(),menuModel.getPicture(),menuModel.getSons());
        LevelControler levelControler = new LevelControler(levelModel);
        levelControler.show();
        view.stopSound();
      }
   }
}
