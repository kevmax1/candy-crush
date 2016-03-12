package candy;

import candy.controler.MenuControler;
import candy.model.MenuModel;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;




/**
 *
 * @author WAMBA KEVIN
 */

public class CandyCrush  extends Application{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setOpacity(0.3);
        MenuModel menuModel = new MenuModel(primaryStage);
        MenuControler menuControler = new MenuControler(menuModel);
        menuControler.show();
    }
}
