/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author WAMBA KEVIN
 */
public class LevelModel {
    private Stage primaryStage = null;
    private Picture image;
    private Sons son;
    private ArrayList listLevel;
    private int[][] map = new int[9][9];
    public LevelModel(Stage primaryStage,Picture image,Sons son) {
       this.listLevel = new ArrayList();
       this.primaryStage = primaryStage;
       this.image = image;
       this.son = son;
    }
    
    public void loadLevel() throws IOException, FileNotFoundException, JSONException{
        File rep = new File("level");
        File[] fichierJs = rep.listFiles((File dir, String name) -> name.matches("\\d+.json$"));
        if(fichierJs!=null){
            for (int i = 0; i <fichierJs.length ; i++) {
                listLevel.add(printLevel(fichierJs[i]));
            }
        }else{
            System.out.println("erreur a gerer plutard");       
        }
    }
    public Level printLevel(File f) throws FileNotFoundException, IOException, JSONException{
        Level levelTemp = new Level();
        FileReader fr = new FileReader(f);
        String jsonStr = "";
        int k;
        while((k=fr.read())!=-1)
            jsonStr += ( char) k;
        JSONObject obj = new JSONObject(jsonStr);
        levelTemp.setUnlock(obj.getBoolean("unlock"));
        levelTemp.setNumber(obj.getInt("number"));
        levelTemp.setLocalisation(obj.getString("localisation"));
        levelTemp.setColor(obj.getInt("color"));
        levelTemp.setType(obj.getString("type"));
        levelTemp.setTarget_bronze(obj.getJSONObject("target").getInt("bronze"));
        levelTemp.setTarget_argent(obj.getJSONObject("target").getInt("argent"));
        levelTemp.setTarget_or(obj.getJSONObject("target").getInt("or"));
        levelTemp.setMoves(obj.getJSONObject("target").getInt("moves"));
        String str =obj.getString("tile");
        JSONArray array = new JSONArray( str);
        JSONArray arrayTwo = array. getJSONArray(0);
        Tile[][] temp = new Tile[array.length()][arrayTwo.length()];
        levelTemp.setY(array.length());
        levelTemp.setX(arrayTwo.length());
        for (int i = 0; i < array.length(); i++) {
            arrayTwo = array.getJSONArray(i);
            for (int j = 0; j < arrayTwo.length(); j++) {
                if(arrayTwo.getInt(j)==-2 && obj.getString("type").equalsIgnoreCase("GL")){
                    Tile tempTile = new Tile(1);
                    temp[i][j]=tempTile;
                    temp[i][j].setGelatine(true);
                }else if(arrayTwo.getInt(j)==-1){
                    Tile tempTile = new Tile(arrayTwo.getInt(j));
                    temp[i][j]=tempTile;
                }
                else{
                    Tile tempTile = new Tile(1);
                    tempTile.setSimple(true);
                    temp[i][j]=tempTile;
                    
                }
                
            }
        }
        levelTemp.setTile(temp);
        levelTemp.setObj(obj);
        return levelTemp;
        
    }
    public Picture getPicture() {
       return this.image;
    }
    
    public ArrayList getListLevel(){
        return this.listLevel;
    }
    public Stage getPrimaryStage() {
       return primaryStage;
    }
    public void setPosInMap(int x,int y){
        this.map[x][y]=1;
    }
    public boolean posOcup(int x,int y){
        return this.map[x][y]==1;
    }
    public Sons getSons(){
       return this.son;
    }
}
