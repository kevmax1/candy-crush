

package candy.vue;

import candy.model.Board;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author WAMBA KEVIN
 */

public class PlayView {
    private Scene scene;
    private String title;
    private BorderPane root;
    private GridPane game;
    private Pane stat;
    private Picture picture;
    private Image logo;
    
    private FlowPane InfoBg;
    private Button continuer;
    private Button fermer=new Button("X");
    private Button jouer=new Button("Jouer !");
    
    private Label objectif = new Label("");
    private Label deplacement = new Label("");
    private Label score = new Label("");
    
    public PlayView(Picture picture){
        this.picture = picture;
        title="chargement";
        stat = new Pane();
        game = new GridPane();
        jouer.setMaxSize(198, 98);
        root = new BorderPane();
        root.setBackground(new Background(
                               new BackgroundImage(this.picture.getChargementBackground(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        root.setPadding(Insets.EMPTY);
        this.scene = new Scene(root, 850, 550);
        this.scene.getStylesheets().add("/resource/design/style.css");
        game.getStyleClass().add("grid");
        
    }
    public void show(Stage stage) {
      stage.setTitle(title);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.getIcons().add(this.picture.getLogo());
      stage.show();
    }
    public void showError(String message) {
        Label err = new Label(message);
        err.getStyleClass().add("erreur");
        root.setBottom(err);
        scene.setRoot(root);
        
    }
    public Button[][] showGame(Board board,Level level){
        
        String temp;
        temp = "Objectif :"+level.getTarget_bronze();
        this.objectif.setText(temp);
        temp= "Déplacements :\n" + level.getMoves();
        this.deplacement.setText(temp);
        temp= "Score :\n0";
        this.score.setText(temp);
        this.objectif.getStyleClass().add("labelStat");
        this.objectif.setLayoutX(30);
        this.objectif.setLayoutY(95);
        this.deplacement.getStyleClass().add("labelStat");
        this.deplacement.setLayoutX(30);
        this.deplacement.setLayoutY(180);
        this.deplacement.setTextAlignment(TextAlignment.CENTER);
        this.score.getStyleClass().add("labelStat2");
        this.score.setLayoutX(50);
        this.score.setLayoutY(270);
        this.score.setTextAlignment(TextAlignment.CENTER);
        fermer = new Button();
        fermer.setMinSize(98, 98);
        fermer.getStyleClass().add("exit");
        fermer.setLayoutX(10);
        fermer.setLayoutY(440);
        //statistique
        stat.setBackground(defBg(this.picture.getStatGame()));
        stat.getChildren().add(this.objectif);
        stat.getChildren().add(this.deplacement);
        stat.getChildren().add(this.score);
        stat.getChildren().add(this.fermer);
        stat.setMinSize(233, 500);
        //surface de jeux
        Button[][] btn = new Button[level.getX()][level.getY()];
        for(int i=0;i<level.getY();i++){
            for(int j=0;j<level.getX();j++){
                if(board.getGrid(i,j).getValue()!=-1){
                    btn[j][i]=genererButton(i, j, level, board);
                    game.add(btn[j][i],j,i);
                }
            }
        } 
        //game.setMinSize(btnTemp.getWidth()*level.getX(), minHeight);
        game.setAlignment(Pos.CENTER);
        //ajout des elements
        root.setBackground(defBg(this.picture.getStageBg("candy ville")));
        root.setLeft(stat);
        root.setCenter(game);
        scene.setRoot(root);
        return btn;
    }
    public Background defBg(Image image){
        return new Background(
                               new BackgroundImage(image,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ));
    }
    
    public Button[][] updateGame(Board board, Level level) {
        game.getChildren().remove(0, game.getChildren().size());
        Button[][] btn = new Button[level.getX()][level.getY()];
        for(int i=0;i<level.getY();i++){
            for(int j=0;j<level.getX();j++){
                if(board.getGrid(i,j).getValue()>=0){
                    btn[j][i]=genererButton(i, j, level, board);
                    game.add(btn[j][i],j,i);
                }
            }
        }
        deplacement.setText("Déplacements :\n" + level.getMoves());
        score.setText("Score :\n"+board.getScore());
        root.setCenter(game);
        return btn;
    }
    
    private Button genererButton(int i,int j,Level level,Board board){
        Button btnTemp = new Button("");
        btnTemp.getStyleClass().add("button");
        if(board.getGrid(i, j).getGelatine())
            btnTemp.getStyleClass().add("gelatine");
        if((j==0 && i==0) || (i==0 && j!=0 && board.getGrid(i, j-1).getValue()==-1) || (j==0 && i!=0 && board.getGrid(i-1, j).getValue()==-1)){
            btnTemp.getStyleClass().add("button-sup-left-top");
        }
        if((i==0 && j==level.getX()-1) || (i==0 && j!=level.getX()-1 && board.getGrid(i, j+1).getValue()==-1) || (j==level.getX()-1 && i!=0 && board.getGrid(i-1, j).getValue()==-1)){
            btnTemp.getStyleClass().add("button-sup-right-top");
        }
        if(i==level.getY()-1 && j==0 || (i==level.getY()-1 && j!=0 && board.getGrid(i, j-1).getValue()==-1) || (i!=0 && i!=level.getY()-1 && j==0 && board.getGrid(i+1, j).getValue()==-1)){
            btnTemp.getStyleClass().add("button-sup-left-bottom");
        }
        if((i==level.getY()-1 && j== level.getX()-1) || (i==level.getY()-1 && j!=level.getX()-1 && board.getGrid(i, j+1).getValue()==-1) || (i!=0 && i!=level.getY()-1 && j==level.getX()-1 && board.getGrid(i+1, j).getValue()==-1)){
            btnTemp.getStyleClass().add("button-sup-right-bottom");
        }
        if(board.getGrid(i, j).getSpeciale()){
            if(board.getGrid(i, j).getSpType()=="H")
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(board.getGrid(i,j).getValue()+5)));
            if(board.getGrid(i, j).getSpType()=="V")
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(board.getGrid(i,j).getValue()+11)));
            if(board.getGrid(i, j).getSpType()=="D")
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(24)));
            if(board.getGrid(i, j).getSpType()=="E")
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(board.getGrid(i,j).getValue()+17)));
            
        }else{
            if(board.getGrid(i,j).getValue()!=0)
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(board.getGrid(i,j).getValue()-1)));
            else
                btnTemp.setGraphic(new ImageView(picture.getSampleCandy(25)));
        }
        return btnTemp;
    }
    public void showWin(Board board,Level level,Level nextLevel,boolean next){
        fermer = new Button("X");
        fermer.getStyleClass().add("close");
        TranslateTransition trStat =new   TranslateTransition(Duration.millis(500), stat);
        trStat.setFromY(0);
        trStat.setToY(0);
        trStat.setFromX(0);
        trStat.setToX(-300);
        trStat.setCycleCount(1);
        trStat.play();
        TranslateTransition trGame =new   TranslateTransition(Duration.millis(500), game);
        trGame.setFromY(0);
        trGame.setToY(0);
        trGame.setFromX(0);
        trGame.setToX(900);
        trGame.setCycleCount(1);
        trGame.play();
        trGame.setOnFinished(event->{
            showInfoWin(board,level,nextLevel,next);});
    }
    public void showLose(Board board,Level level){
        fermer = new Button("X");
        fermer.getStyleClass().add("close");
        TranslateTransition trStat =new   TranslateTransition(Duration.millis(500), stat);
        trStat.setFromY(0);
        trStat.setToY(0);
        trStat.setFromX(0);
        trStat.setToX(-300);
        trStat.setCycleCount(1);
        trStat.play();
        TranslateTransition trGame =new   TranslateTransition(Duration.millis(500), game);
        trGame.setFromY(0);
        trGame.setToY(0);
        trGame.setFromX(0);
        trGame.setToX(900);
        trGame.setCycleCount(1);
        trGame.play();
        trGame.setOnFinished(event->{
            showInfoLose(board,level);});
    }
    public Button[] getButtons(){
        Button[] temp =new Button[2];
        temp[0]=jouer;
        temp[1]=fermer;
        return temp;
    }
    public void showInfoWin(Board board,Level level,Level nextlevel,boolean next){
        root.getChildren().remove(root.getLeft());
        InfoBg = new FlowPane();
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
        InfoBg.getChildren().add(Info);
        
        String temp = "Niveau "+level.getNumber();
        Label levelNumber = new Label(temp);
        levelNumber.getStyleClass().add("label_niveau");
        levelNumber.setMinSize(100, 30);
        
        Info.add(levelNumber,1,0,3,1);
        Info.add(new ImageView(picture.getEtoile(1)),1,1,3,2);
        
        temp = "Score :\n "+board.getScore();
        Label score = new Label(temp);
        
        score.setMinSize(100, 30);
        score.setBackground( new Background( 
                                new BackgroundFill(Color.BLUE,
                                CornerRadii.EMPTY,
                                null ),
                                new BackgroundFill(Color.BLUE,
                                new CornerRadii(20),
                                new Insets(15)    )));
        Info.add(score,2,2,2,1);
        Button play = new Button("Continuer!");
        play.setMaxSize(198, 98);
        play.getStyleClass().add("play");
        fermer.getStyleClass().add("close");
        InfoBg.getChildren().add(fermer);
        fermer.setAlignment(Pos.TOP_RIGHT);
        TranslateTransition tr =new   TranslateTransition(Duration.millis(200), InfoBg);
        tr.setFromY(10);
        tr.setToY(10);
        tr.setFromX(600);
        tr.setToX(0);
        tr.setCycleCount(1);
        tr.play();
        root.setCenter(InfoBg);
        if(next){
            Info.add(play,3,6,3,1);
            play.setOnAction(event->{showInfoNext(nextlevel);});
        }
    }
    public void showInfoNext(Level level){
        root.getChildren().remove(root.getLeft());
        InfoBg = new FlowPane();
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
        InfoBg.getChildren().add(Info);
        
        String temp = "Niveau "+level.getNumber();
        Label levelNumber = new Label(temp);
        levelNumber.getStyleClass().add("label_niveau");
        levelNumber.setMinSize(100, 30);
        
        Info.add(levelNumber,1,0,3,1);
        Info.add(new ImageView(picture.getEtoile(1)),1,1,3,2);
        
        temp = "Objectif :\n "+level.getTarget_bronze();
        Label score = new Label(temp);
        
        score.setMinSize(100, 30);
        score.setBackground( new Background( 
                                new BackgroundFill(Color.BLUE,
                                CornerRadii.EMPTY,
                                null ),
                                new BackgroundFill(Color.BLUE,
                                new CornerRadii(20),
                                new Insets(15)    )));
        Info.add(score,2,2,2,1);
        jouer.getStyleClass().add("play");
        jouer.setMinWidth(150);
        fermer.getStyleClass().add("close");
        Info.add(jouer,3,6,3,1);
        InfoBg.getChildren().add(fermer);
        fermer.setAlignment(Pos.TOP_RIGHT);
        TranslateTransition tr =new   TranslateTransition(Duration.millis(200), InfoBg);
        tr.setFromY(10);
        tr.setToY(10);
        tr.setFromX(600);
        tr.setToX(0);
        tr.setCycleCount(1);
        tr.play();
        root.setCenter(InfoBg);
    }
    public void showInfoLose(Board board,Level level){
        root.getChildren().remove(root.getLeft());
        InfoBg = new FlowPane();
        InfoBg.setBackground(new Background(
                               new BackgroundImage(picture.getInfoBg(),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT ) ));
        InfoBg.setMinSize(400, 500);
        InfoBg.setMaxSize(400, 500);
        InfoBg.setPadding(new Insets(20));
        
        String temp = "Niveau "+level.getNumber();
        Label levelNumber = new Label(temp);
        levelNumber.getStyleClass().add("label_niveau");
        levelNumber.setMinSize(150, 30);
        InfoBg.getChildren().add(levelNumber);
        InfoBg.getChildren().add(new ImageView(picture.getEtoile(1)));
        temp = "Vous n'avez pas pas atteint votre objectif";
        Label score = new Label(temp);
        
        score.setMinSize(100, 30);
        score.setBackground( new Background( 
                                new BackgroundFill(Color.BLUE,
                                CornerRadii.EMPTY,
                                null ),
                                new BackgroundFill(Color.BLUE,
                                new CornerRadii(20),
                                new Insets(15)    )));
        InfoBg.getChildren().add(score);
        jouer.setText("Reessayer!");
        jouer.getStyleClass().add("play");
        fermer.getStyleClass().add("close");
        InfoBg.getChildren().add(fermer);
        InfoBg.getChildren().add(jouer);
        fermer.setAlignment(Pos.TOP_RIGHT);
        TranslateTransition tr =new   TranslateTransition(Duration.millis(200), InfoBg);
        tr.setFromY(10);
        tr.setToY(10);
        tr.setFromX(600);
        tr.setToX(0);
        tr.setCycleCount(1);
        tr.play();
        root.setCenter(InfoBg);
    }
}
