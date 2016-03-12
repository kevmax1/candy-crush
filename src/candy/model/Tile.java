
package candy.model;

/**
 *
 * @author WAMBA ZOKO KEVIN
 */
public class Tile {
    private int value;
    private boolean gelatine=false;
    private boolean simple=false;
    private boolean speciale=false;
    private String spType;
    private boolean object=false;
    public Tile(int value){
        this.value = value;
    }
    public Tile(Tile tile){
        //this.setGelatine(tile.getGelatine());
        this.setObject(tile.getObject());
        this.setSimple(tile.getSimple());
        this.setSpType(tile.getSpType());
        this.setSpeciale(tile.getSpeciale());
        this.setValue(tile.getValue());
    }
    public void setDefault(){
        this.simple=true;
        this.speciale=false;
        this.object=false;
        this.value=0;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void setGelatine(boolean gel){
        this.gelatine = gel;
    }
    public void setSimple(boolean simple){
        this.simple = simple;
    }
    public void setObject(boolean object){
        this.object = object;
    }
    public void setSpType(String spType){
        this.spType = spType;
    }
    public void setSpeciale(boolean speciale){
        this.speciale = speciale;
    }
    public int getValue(){
        return this.value;
    }
    public boolean getGelatine(){
        return  this.gelatine;
    }
    public boolean getSimple(){
        return this.simple;
    }
    public boolean getObject(){
        return this.object;
    }
    public String getSpType(){
        return this.spType;
    }
    public boolean getSpeciale(){
        return this.speciale;
    }
}
