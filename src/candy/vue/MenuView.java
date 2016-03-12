/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.vue;

import candy.model.Picture;
import candy.model.Sons;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 *
 * @author WAMBA KEVIN
 */
public class MenuView {
    private Scene scene;
    private String title;
    private StackPane root;
    private Picture picture;
    private Stage stage;
    private Image chargementBackground;
    private Image logo;
    private Button play;
    private AudioClip Sound;
    public MenuView(){
        title="Chargement";
        try{
            logo = new Image("/resource/design/logo.png");
            chargementBackground = new Image("/resource/design/chargement.PNG");
            
        } 
        catch(IllegalArgumentException e2){
            System.err.println(e2);
        }
        catch(RuntimeException e){
            System.err.println(e);
        }
        
        root = new StackPane();
        root.setBackground(new Background(
                               new BackgroundImage(chargementBackground,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        root.setPadding(Insets.EMPTY);
        this.scene = new Scene(root, 850, 550);
        scene.getStylesheets().add("/resource/design/style.css");
    }

    public void show(Stage stage){
      this.stage = stage;
      stage.setTitle(title);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.getIcons().add(logo);
      stage.show();
   }
    public void setMenu(){
        title="Menu";
        StackPane rootTemp = new StackPane();
        play = new Button("Jouer !");
        play.setMaxSize(198,98);
        play.getStyleClass().add("play");
        rootTemp.getChildren().add(play);
        rootTemp.setBackground(new Background(
                               new BackgroundImage(picture.getBackground(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        rootTemp.setPadding(Insets.EMPTY);
        scene.setRoot(rootTemp);
        this.stage.setTitle(title);
        try{
        Sound = new AudioClip(Sons.class.getResource("/resource/son/intro.mp3").toExternalForm());
        Sound.play();
        }catch(RuntimeException e){
            
        }
        
    }
    public void setPicture(Picture picture){
        this.picture = picture;
    }
    public Button getPlayButton(){
        return this.play;
    }

    public void showError(String err) {
        
    }
    public void stopSound(){
        Sound.stop();
    }
}
