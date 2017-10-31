/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package main;
import Logic.Logic;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ObjectHolder;
import ui.Screen_game;
import ui.Screen_menu;

public class Main extends Application{

	private static final Main instance = new Main();
	private static Scene gameScene;
	private static Scene menuScene;
	private static int currentFrame;
	private static int frame;
	private static AnimationTimer gameUpdate;
	private static Stage primaryStage;
	private static Screen_game game;
	private static Screen_menu menu;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;

		ObjectHolder.getInstance().LoadRes();

		toMenu();
		
		this.primaryStage.sizeToScene();
		this.primaryStage.setResizable(false);
		this.primaryStage.show();
		
		
	}
	
	public void toGame(){
		//go to Game Screen and Start new game
		currentFrame = 0;
		frame = 0;
		ObjectHolder.getInstance().LoadRes();
		Logic logic = new Logic();
		game = new Screen_game(1280,720, logic);
		
		gameUpdate = new AnimationTimer() {
			Long start = 0l;

			@Override
			public void handle(long now) {

				if (start == 0l)
					start = now;
				long diff = now - start;
				if (diff >= 10000000l) {
					frame++;
					if(frame == 9){
						currentFrame++;
						if(currentFrame == 10){
							currentFrame = 0;
						}
						frame = 0;
					}
					logic.update();
					game.drawObject();
					start = 0l;
				}
			}
		};
		
		gameScene = new Scene(game, 1280, 720);
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}
	public void toMenu(){
		//go to menu and reset everything
		ObjectHolder.getInstance().LoadRes();
		menu = new Screen_menu();
		menuScene = new Scene(menu, 1280, 720);
		this.primaryStage.setScene(menuScene);
	}
	public void stopUpdate(){
		gameUpdate.stop();
	}
	public void startUpdate(){
		gameUpdate.start();
	}
	
	public void endGame(){
		game.endScreen();
	}
	
	public static int getCurrentFrame(){
		return currentFrame;
	}
	public static int getRunFrame(){
		return frame;
	}
	public static Main getInstance(){
		return instance;
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}

}
