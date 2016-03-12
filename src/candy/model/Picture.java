/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.model;

import javafx.scene.image.Image;

/**
 *
 * @author WAMBA KEVIN
 */
public class Picture {

    private Image[] candy = new Image[26];
    private Image btnVide;
    private Image chargementBackground;
    private Image logo;
    private Image btnImg;
    private Image[] background = new Image[5];
    private Image[] etoile = new Image[4];
    private Image info_bg ;
    private Image statGame;
    private Image[] stageBg = new Image[8];
    public Picture(){
        
    }
    
    public int ChargePicture(){
        try{
            //simple bonbon
            candy[0] = new Image("/resource/bonbon/vert.png");
            candy[1] = new Image("/resource/bonbon/bleue.png");
            candy[2] = new Image("/resource/bonbon/orange.png");
            candy[3] = new Image("/resource/bonbon/jaune.png");
            candy[4] = new Image("/resource/bonbon/rouge.png");
            candy[5] = new Image("/resource/bonbon/violet.png");
            //bonbon rayé horizontale
            candy[6] = new Image("/resource/bonbon/vertH.png");
            candy[7] = new Image("/resource/bonbon/bleueH.png");
            candy[8] = new Image("/resource/bonbon/orangeH.PNG");
            candy[9] = new Image("/resource/bonbon/jauneH.PNG");
            candy[10] = new Image("/resource/bonbon/rougeH.PNG");
            candy[11] = new Image("/resource/bonbon/violetH.PNG");
            //bonbon rayé verticale
            candy[12] = new Image("/resource/bonbon/vertV.PNG");
            candy[13] = new Image("/resource/bonbon/bleueV.png");
            candy[14] = new Image("/resource/bonbon/orangeV.png");
            candy[15] = new Image("/resource/bonbon/jauneV.PNG");
            candy[16] = new Image("/resource/bonbon/rougeV.PNG");
            candy[17] = new Image("/resource/bonbon/violetV.PNG");
            //bonbon emballé
            candy[18] = new Image("/resource/bonbon/vertE.PNG");
            candy[19] = new Image("/resource/bonbon/bleueE.PNG");
            candy[20] = new Image("/resource/bonbon/orangeE.PNG");
            candy[21] = new Image("/resource/bonbon/jauneE.PNG");
            candy[22] = new Image("/resource/bonbon/rougeE.PNG");
            candy[23] = new Image("/resource/bonbon/violetE.PNG");
            //bonbon disco
            candy[24] = new Image("/resource/bonbon/disco.PNG");
            candy[25] = new Image("/resource/bonbon/btnVide.png");
            
            logo = new Image("/resource/design/logo.png");
            chargementBackground = new Image("/resource/design/chargement.PNG");
            
            for(int i=0;i<5;i++){
                String temp = "/resource/design/background"+i+".png";
                background[i] = new Image(temp);
            }
            etoile[0] = new Image("/resource/design/etoile_0.PNG");
            etoile[3] = new Image("/resource/design/etoile_3.PNG");
            info_bg = new Image("/resource/design/info_bg.png");
            statGame = new Image("/resource/design/jeux_stat.PNG");
            stageBg[0] = new Image("/resource/design/candy ville.png");
            btnVide=new Image("/resource/bonbon/btnVide.png");
        }
        catch(IllegalArgumentException e2){
            System.out.println(e2.getMessage());
            return 0;
        }
        catch(RuntimeException e){
            System.out.println("fd");
            return 0;
        }
        return 1;
    }
    public Image getChargementBackground(){
        return this.chargementBackground;
    }
    public Image getLogo(){
        return this.logo;
    }
    public Image getBackground(){
        int i= (int)(5 * Math.random());
        return this.background[i];
    }
    public Image getEtoile(int i){
        return this.etoile[i];
    }
    public Image getInfoBg(){
        return this.info_bg;
    }
    public Image getSampleCandy(int i){
        return this.candy[i];
    }
    public Image getStatGame(){
        return this.statGame;
    }
    public Image getStageBg(String stage){
        if("candy ville".equals(stage)){
            return this.stageBg[0];
        }
        else{
            return this.stageBg[0];
        }
    }
    public Image getBtnVide(){
        return this.btnImg;
    }
}
