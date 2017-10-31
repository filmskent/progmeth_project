


package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import object.Entity;




public class ObjectHolder {
	private static final ObjectHolder instance = new ObjectHolder();
	
	private static List<Entity> object;
	private Comparator<Renderable> comparator;
	
	public Image[] bg, victory;
	public Image genjiPic, effectPic, hanzoPic, arrow, hpBar, hpGreen, playBt, title, pause, load, battle;
	public Media[] sound;
	public ObjectHolder(){
		
		object = new ArrayList<Entity>();
		comparator = (Renderable o1, Renderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};

	}
	
	public static ObjectHolder getInstance() {
		return instance;
	}
	public void addSort(Entity obj) {
		add(obj);
		sort();
	}
	public void add(Entity obj) {
		object.add(obj);
	}
	public void remove(int i){
		object.remove(i);
	}
	public void sort(){
		Collections.sort(object, comparator);
	}
	public List<Entity> getObject() {
		return object;
	}
	
	public void playSound(int index){
		Media media = sound[index];
		MediaPlayer player = new MediaPlayer(media);
		player.play();
		player.setVolume(0.1);
	}
	
	public void LoadRes(){
		
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		bg = new Image[4];
		sound = new Media[10];
		victory = new Image[2];
		bg[0] = new Image(loader.getResourceAsStream("Shimada Bros.png"));
		effectPic = new Image(loader.getResourceAsStream("effect_sqn.png"));
		genjiPic = new Image(loader.getResourceAsStream("genji_sqn4.png"));
		hanzoPic = new Image(loader.getResourceAsStream("hanzo_sqn.png"));
		arrow = new Image(loader.getResourceAsStream("arrow_sqn.png"));
		hpBar = new Image(loader.getResourceAsStream("hpBar.png"));
		hpGreen = new Image(loader.getResourceAsStream("hpGreen.png"));
		playBt = new Image(loader.getResourceAsStream("playButton.png"));
		title = new Image(loader.getResourceAsStream("title.png"));
		pause = new Image(loader.getResourceAsStream("pause.png"));
		load = new Image(loader.getResourceAsStream("loadingscreen.gif"));
		battle = new Image(loader.getResourceAsStream("startSqn.png"));
		victory[0] = new Image(loader.getResourceAsStream("genji_victory.png"));
		victory[1] = new Image(loader.getResourceAsStream("hanzo_victory.png"));
		
		sound[0] = new Media(loader.getResource("genjiatk.mp3").toExternalForm());
		sound[1] = new Media(loader.getResource("hanzoatk.mp3").toExternalForm());
		sound[2] = new Media(loader.getResource("genjihurt.mp3").toExternalForm());
		sound[3] = new Media(loader.getResource("hanzohurt.mp3").toExternalForm());

	}
	
}
