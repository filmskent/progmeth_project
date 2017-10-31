/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package Logic;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Main;
import object.Hanzo;
import object.Entity;
import object.Genji;
import ui.ObjectHolder;
import ui.Renderable;


public class Logic {
	
	public Genji genji;
	public Hanzo hanzo;
	public ArrayList<KeyCode> PressingKey;
	private boolean isOvertime;
	
	public Logic(){
		genji = new Genji(200, 500, 0, 0, 0, 200, 7);
		hanzo = new Hanzo(1000, 500, 0, 0, 0, 200, 6);
		isOvertime = false;
		ObjectHolder.getInstance().add(genji);
		ObjectHolder.getInstance().add(hanzo);
		PressingKey = new ArrayList<KeyCode>();
		
	}
	
	public void update(){
		checkKey();
		
		for(Entity x: ObjectHolder.getInstance().getObject()){
			if(!x.isDestroyed()){
				x.move();
			}
		}
		//remove destroyed object
		for(int i =  ObjectHolder.getInstance().getObject().size()-1; i >= 0; i--){
			if(ObjectHolder.getInstance().getObject().get(i).isDestroyed()){
				ObjectHolder.getInstance().getObject().remove(i);
			}
		}
		
		if(genji.getHP()==0 || hanzo.getHP()==0){
			Main.getInstance().stopUpdate();
			Main.getInstance().endGame();
		}

	}
	
	public void checkKey(){
		//check if key pressed
		if(!PressingKey.isEmpty()){
			if(PressingKey.contains(KeyCode.A) && PressingKey.contains(KeyCode.D)){
				if(PressingKey.indexOf(KeyCode.A) > PressingKey.indexOf(KeyCode.D)){
					genji.setMoving(true);
					genji.setDirectX(-1);
				}else{
					genji.setMoving(true);
					genji.setDirectX(1);
				}
			}else{
				if(PressingKey.contains(KeyCode.A)){
					genji.setMoving(true);
					genji.setDirectX(-1);
				}
				else if(PressingKey.contains(KeyCode.D)){
					genji.setMoving(true);
					genji.setDirectX(1);
				}
			}
			
			if(PressingKey.contains(KeyCode.LEFT) && PressingKey.contains(KeyCode.RIGHT)){
				if(PressingKey.indexOf(KeyCode.LEFT) > PressingKey.indexOf(KeyCode.RIGHT)){
					hanzo.setMoving(true);
					hanzo.setDirectX(-1);
				}else{
					hanzo.setMoving(true);
					hanzo.setDirectX(1);
				}
			}else{
				if(PressingKey.contains(KeyCode.LEFT)){
					hanzo.setMoving(true);
					hanzo.setDirectX(-1);
				}
				else if(PressingKey.contains(KeyCode.RIGHT)){
					hanzo.setMoving(true);
					hanzo.setDirectX(1);
				}
			}
			
			if(PressingKey.contains(KeyCode.W) && genji.Jumpable){
				genji.Jump();
				genji.Jumpable = false;
			}
			if(PressingKey.contains(KeyCode.UP) && hanzo.Jumpable){
				hanzo.Jump();
				hanzo.Jumpable = false;
			}
			if(!(PressingKey.contains(KeyCode.LEFT) || PressingKey.contains(KeyCode.RIGHT))){
				hanzo.setMoving(false);
				hanzo.setDirectX(0);
			}
			if(!(PressingKey.contains(KeyCode.A) || PressingKey.contains(KeyCode.D))){
				genji.setMoving(false);
				genji.setDirectX(0);
			}
			if(PressingKey.contains(KeyCode.S)){
				genji.setDown(true);
			}else{
				genji.setDown(false);
			}
			if(PressingKey.contains(KeyCode.DOWN)){
				hanzo.setDown(true);
			}else{
				hanzo.setDown(false);
			}
			
			if(PressingKey.contains(KeyCode.J) && genji.attackable){
				genji.attack();
				genji.attackable = false;
			}
			
			
		}else{
			//if not pressing any set genji and hanzo not moving
			genji.setMoving(false);
			genji.setDirectX(0);
			hanzo.setMoving(false);
			hanzo.setDirectX(0);
		}
	}
	
}
