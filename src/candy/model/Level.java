
package candy.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author WAMBA KEVIN
 */
public class Level {
    private boolean unlock;
    private int number;
    private Tile[][] tile;
    private int target_bronze;
    private int target_argent;
    private int target_or;
    private int moves;
    private int y;
    private int x;
    private String localisation;
    private int color;
    private String type;
    private JSONObject obj;
    
    public Level(){
        
    }
    //les setteurs
    public void setUnlock(boolean unlock){
        this.unlock = unlock;
    }
    public void setObj(JSONObject obj){
        this.obj=obj;
    }
    public void  setNumber(int number){
        this.number = number;
    }
    public void setTile(Tile[][] tile){
        this.tile = tile;
    }
    public void setTarget_bronze(int bronze){
        this.target_bronze = bronze;
    }
    public void setTarget_argent(int argent){
        this.target_argent = argent;
    }
    public void setTarget_or(int or){
        this.target_or = or;
    }
    public void setMoves(int move){
        this.moves = move;
    }
    public  void setX(int x){
        this.x = x;
    }
    public void  setY(int y){
        this.y = y;
    }
    public void setLocalisation(String localisation){
        this.localisation=localisation;
    }
    public void setColor(int color){
        this.color = color;
    }
    public void setType(String type){
        this.type = type;
    }
    public void unlock(){
        try {
            obj.put("unlock",true);
        } catch (JSONException ex) {}
        unlock=true;
    }
    //les getters
    public boolean getUnlock(){
        return this.unlock;
    }
    public int  getNumber(){
        return this.number;
    }
    public Tile[][] getTile(){
        return this.tile;
        
    }
    public  int getX(){
        return this.x;
    }
    public int  getY(){
        return this.y;
    }
    public int getTarget_bronze(){
        return this.target_bronze;
    }
    public int getTarget_argent(){
        return this.target_argent;
    }
    public int getTarget_or(){
        return this.target_or;
    }
    public int  getMoves(){
        return this.moves;
    }
    public String getLocalisation(){
        return this.localisation;
    }
    public int getColor(){
        return this.color;
    }
    public String getType(){
        return this.type;
    }

    public void save(int score){
        try {
            obj.put("lastScore",score);
            FileWriter fluxEcrit = new FileWriter("level/"+number+".json");
            BufferedWriter out = new BufferedWriter(fluxEcrit);
            out.write(obj.toString());
            out.newLine();
            out.close();
        } catch (JSONException ex) {} catch (IOException ex) {System.out.println("erreur");}
        
    }
    public void renitialize(){
        try {
            this.moves=obj.getJSONObject("target").getInt("moves");
            String str =obj.getString("tile");
        JSONArray array = new JSONArray( str);
        JSONArray arrayTwo = array. getJSONArray(0);
        Tile[][] temp = new Tile[array.length()][arrayTwo.length()];
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
        tile=temp;
        } catch (JSONException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
