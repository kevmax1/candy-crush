/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package candy.model;

import javafx.scene.media.AudioClip;

/**
 *
 * @author WAMBA ZOKO Kevin
 */
public class Sons {
    private AudioClip intro;
    private AudioClip delicious;
    private AudioClip divine;
    private AudioClip juicy;
    private AudioClip lose;
    private AudioClip sugar_crush;
    private AudioClip tasty;
    private AudioClip win;
    private AudioClip wrapped;

    public Sons() {
        try{
        intro = new AudioClip(Sons.class.getResource("/resource/son/intro.mp3").toExternalForm());
        delicious = new AudioClip(Sons.class.getResource("/resource/son/delicious.mp3").toExternalForm());
        divine = new AudioClip(Sons.class.getResource("/resource/son/divine.mp3").toExternalForm());
        juicy = new AudioClip(Sons.class.getResource("/resource/son/juicy.mp3").toExternalForm());
        lose = new AudioClip(Sons.class.getResource("/resource/son/lose.mp3").toExternalForm());
        sugar_crush = new AudioClip(Sons.class.getResource("/resource/son/sugar_crush.mp3").toExternalForm());
        tasty = new AudioClip(Sons.class.getResource("/resource/son/tasty.mp3").toExternalForm());
        win = new AudioClip(Sons.class.getResource("/resource/son/win.mp3").toExternalForm());
        wrapped = new AudioClip(Sons.class.getResource("/resource/son/wrapped.mp3").toExternalForm());
        }catch(RuntimeException e){
            System.out.println("erreur de chargement des musique");
        }
        
    }
    public void playIntro(){
        intro.play();
    }
    public void playDelicious(){
        juicy.stop();
        delicious.play();
    }
    public void playDivine(){
        juicy.stop();
        delicious.stop();
        divine.play();
    }
    public void playJuicy(){
        juicy.play();
    }
    public void playLose(){
        lose.play();
    }
    public void playSugarCrush(){
        sugar_crush.play();
    }
    public void playTasty(){
        tasty.play();
    }
    public void playWin(){
        win.play();
    }
    public void playWrapped(){
        wrapped.play();
    }
}
