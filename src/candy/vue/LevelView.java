/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.vue;

import candy.model.Level;
import candy.model.Picture;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author WAMBA KEVIN
 */
public class LevelView {
    private Scene scene;
    private String title;
    private BorderPane root;
    private GridPane rootMap;
    private Picture picture;
    private Image logo;
    private BorderPane InfoBg;
    public LevelView(Picture picture){
        this.picture = picture;
        title="chargement";
        root = new BorderPane();
        root.setBackground(new Background(
                               new BackgroundImage(picture.getChargementBackground(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        root.setPadding(Insets.EMPTY);
        this.scene = new Scene(root, 850, 550);
        scene.getStylesheets().add("/resource/design/style.css");
        rootMap = new GridPane();
    }
    public void show(Stage stage) {
      stage.setTitle(title);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.getIcons().add(picture.getLogo());
      stage.show();
    }
    public void showMap(Stage stage){
        this.title="choix etape";
        stage.setTitle(title);
        root.setBackground(new Background(
                               new BackgroundImage(picture.getBackground(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        root.setCenter(rootMap);
        rootMap.setHgap(10);
        rootMap.setVgap(10);
        rootMap.setAlignment(Pos.CENTER);
    }
    public Button addLevel(int level,String type,boolean  unlock,int x,int y){
        String name =""+level; 
        Button btn =new Button(name);
        btn.getStyleClass().add("level-"+type);
        if(unlock==false)
            btn.setDisable(true);
        rootMap.add(btn,x,y);
        return btn;
    }
    public void showError(String message) {
        Label err = new Label(message);
        err.getStyleClass().add("erreur");
        root.setBottom(err);
        scene.setRoot(root);
        
    }
    public Button[] showInfo(Level level){
        InfoBg = new BorderPane();
        GridPane Info = new GridPane();
        InfoBg.setBackground(new Background(
                               new BackgroundImage(picture.getInfoBg(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        InfoBg.setMinSize(400, 500);
        InfoBg.setMaxSize(400, 500);
        InfoBg.setPadding(new Insets(20));
        InfoBg.setCenter(Info);
        
        String temp = "Niveau "+level.getNumber();
        Label levelNumber = new Label(temp);
        levelNumber.getStyleClass().add("label_niveau");
        levelNumber.setMinSize(100, 30);
        
        Info.add(levelNumber,1,0,3,1);
        Info.add(new ImageView(picture.getEtoile(1)),1,1,3,2);
        
        temp = "Objectif : "+level.getTarget_bronze();
        Label objectif = new Label(temp);
        objectif.setMinSize(100, 30);
        objectif.setBackground( new Background(
                                new BackgroundFill(Color.BLUE,
                                CornerRadii.EMPTY,
                                null ),
                                new BackgroundFill(Color.BLUE,
                                new CornerRadii(20),
                                new Insets(15)    )));
        Info.add(objectif,2,2,2,1);
        Button play = new Button("  Jouer !  ");
        play.getStyleClass().add("play");
        play.setMinSize(198,98);
        Button close = new Button("X");
        close.getStyleClass().add("close");
        Info.add(play,3,6,8,1);
        InfoBg.setTop(close);
        close.setAlignment(Pos.TOP_RIGHT);
        TranslateTransition tr =new   TranslateTransition(Duration.millis(200), InfoBg);
        tr.setFromX(600);
        tr.setToX(10);
        tr.setCycleCount(1);
        tr.play();
        root.setCenter(InfoBg);
        rootMap.setVisible(false);
        Button[] tempButton = new Button[2];
        tempButton[0] = play;
        tempButton[1] = close;
        return tempButton;
    }
    public void moveInfo(){
       root.getChildren().remove(InfoBg);
       rootMap.setVisible(true);
       root.setCenter(rootMap);
    }
}
