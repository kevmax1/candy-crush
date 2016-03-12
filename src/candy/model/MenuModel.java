/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.model;

import javafx.stage.Stage;

/**
 *
 * @author WAMBA KEVIN
 */
public class MenuModel {
   private Stage primaryStage = null;
   private Picture image;
   private Sons son;
    
   public MenuModel(Stage primaryStage) {
      this.primaryStage = primaryStage;
   }
   public Stage getPrimaryStage() {
      return primaryStage;
   }
   public int loadRessource(){
      son = new Sons();
      image = new Picture();
      if(image.ChargePicture()==0){
          return 0;
      }
      else{
          return 1;
      }
   }
   
   public Picture getPicture() {
      return this.image;
   }
   public Sons getSons(){
       return this.son;
   }
}
