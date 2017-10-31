package ui;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Logic.Logic;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

public class Screen_game extends StackPane {
	protected Canvas canvas, backCanvas, overlayCanvas;
	protected GraphicsContext gc, back, overlay;
	protected Image bg;
	private double height, width;
	private static double mouseX;
	private static double mouseY;
	private boolean isPause, isEnd, isStart;
	private Logic logic;
	private MediaView view;
	private MediaPlayer player;
	private ScheduledExecutorService ses;
	private ClassLoader loader = ClassLoader.getSystemClassLoader();

	public Screen_game(double width, double height, Logic logic) {
		this.logic = logic;
		this.width = width;
		this.height = height;
		
		setPrefSize(width, height);
		view = new MediaView();
		overlayCanvas = new Canvas(width, height);
		canvas = new Canvas(width, height);
		backCanvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		back = backCanvas.getGraphicsContext2D();
		overlay = overlayCanvas.getGraphicsContext2D();
		isEnd = false;
		isPause = true;
		isStart = false;
		getChildren().add(backCanvas);
		getChildren().add(canvas);

		getChildren().add(overlayCanvas);
		
	
		canvas.setFocusTraversable(true);
		back.drawImage(ObjectHolder.getInstance().bg[0], 0, 0);
		// event
		addEvent();
		playSound();

	}

	public void addEvent(){
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!logic.PressingKey.contains(event.getCode())) {
					if (event.getCode() == KeyCode.ENTER && isStart) {
						if(isEnd){
							Main.getInstance().toMenu();
							for(int i =  ObjectHolder.getInstance().getObject().size()-1; i >= 0; i--){
								ObjectHolder.getInstance().getObject().remove(i);
							}
							return;
						}else{
							if (!isPause) {
								Main.getInstance().stopUpdate();
								overlay.drawImage(ObjectHolder.getInstance().pause, 0, 0);
								isPause = true;
							} else {
								Main.getInstance().startUpdate();
								overlay.clearRect(0, 0, 1280, 720);
								isPause = false;
							}
						}
					}else if(event.getCode() == KeyCode.ESCAPE && isPause){
						
						Main.getInstance().toMenu();
						for(int i =  ObjectHolder.getInstance().getObject().size()-1; i >= 0; i--){
							ObjectHolder.getInstance().getObject().remove(i);
						}
						
					}else{
						logic.PressingKey.add(event.getCode());
					}
					

				}

			}
		});

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (logic.PressingKey.contains(event.getCode())) {
					logic.PressingKey.remove(event.getCode());

					if (!isPause) {
						if (event.getCode() == KeyCode.UP) {
							logic.hanzo.Jumpable = true;
						} else if (event.getCode() == KeyCode.W) {
							logic.genji.Jumpable = true;
						} else if (event.getCode() == KeyCode.S) {
							logic.genji.setDown(false);
						} else if (event.getCode() == KeyCode.J || event.getCode() == KeyCode.K) {
							logic.genji.attackable = true;
						} else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
							logic.genji.lastDirectX = logic.genji.getDirectX();
						} else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
							logic.hanzo.lastDirectX = logic.hanzo.getDirectX();
						} else if (event.getCode() == KeyCode.DOWN) {
							logic.hanzo.setDown(false);
						}
					}
					

				}
			}
		});


		overlayCanvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY && !isPause && logic.hanzo.getAttackable()) {
					logic.hanzo.attack(mouseX, mouseY);
					logic.hanzo.setAttackable(false);
					Task<Void> sleeper = new Task<Void>() {
			            @Override
			            protected Void call() throws Exception {
			                try {

			                    Thread.sleep(700);
			                } catch (InterruptedException e) {
			                }
			                return null;
			            }
			        };
			        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			            @Override
			            public void handle(WorkerStateEvent event) {
			            	logic.hanzo.setAttackable(true);
			            }
			        });
			        new Thread(sleeper).start();
				}

			}
		});

		overlayCanvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

					mouseX = event.getSceneX();
					mouseY = event.getSceneY();
				}

			}
		});
		overlayCanvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
					mouseX = event.getSceneX();
					mouseY = event.getSceneY();
				}

			}
		});
	}
	
	public void setBackground(int n) {
		back.clearRect(0, 0, width, height);
		back.drawImage(ObjectHolder.getInstance().bg[n], 0, -300);
		back.setFill(Color.LIGHTGREEN);
		back.fillRect(0, 0, 0, 0);
	}

	public void drawObject() {
		gc.clearRect(0, 0, width, height);
		drawHP();
		for (Renderable obj : ObjectHolder.getInstance().getObject()) {
			obj.draw(gc);
		}
	}

	public void drawHP() {
		gc.drawImage(ObjectHolder.getInstance().hpBar, 0, 0);
		gc.drawImage(new WritableImage(ObjectHolder.getInstance().hpGreen.getPixelReader(), 50, 0,
				(int) (441 * ((double) logic.genji.getHP() / 200.0) + 1), 70), 51, 0);
		gc.drawImage(
				new WritableImage(ObjectHolder.getInstance().hpGreen.getPixelReader(),
						789 + (441 - (int) (441 * ((double) logic.hanzo.getHP() / 200.0) + 1)), 0,
						(int) (441 * ((double) logic.hanzo.getHP() / 200.0) + 1), 70),
				789 + (441 - (int) (441 * ((double) logic.hanzo.getHP() / 200.0) + 1)), 0);
	}

	
	public void startGame(){
		ses = Executors.newSingleThreadScheduledExecutor();
		
		ses.scheduleAtFixedRate(new Runnable() {
			int i = 0;
		    @Override
		    public void run() {
		    	overlay.clearRect(0, 0, 1280, 720);
		    	overlay.drawImage(new WritableImage(ObjectHolder.getInstance().battle.getPixelReader(), 0, i*720, 1280, 720), 0, 0);
		    	if(i<=3){
		    		i++;
		    	}
		    	
		    }
		}, 0, 1, TimeUnit.SECONDS);

		
	}
	
	public void playSound(){
		startGame();
		Media media = new Media(loader.getResource("startGame.mp3").toExternalForm());
		player = new MediaPlayer(media);
		view = new MediaView(player);
		getChildren().add(view);
		view.toBack();
		player.play();
		player.setVolume(0.1);
		view.setMediaPlayer(player);
		player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
        
                player.stop();
                Media media = new Media(loader.getResource("hanamura.mp3").toExternalForm());
                MediaPlayer player = new MediaPlayer(media);
                player.play();
        		player.setVolume(0.04);
                player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
                ses.shutdown();
                overlay.clearRect(0, 0, 1280, 720);
                isPause = false;
                isStart = true;
                Main.getInstance().startUpdate();
                return;
            }
        });
	}
	
	public void endScreen(){
		isEnd = true;
		Media bgSound = new Media(loader.getResource("playofthegame-ex.mp3").toExternalForm());
		MediaPlayer soundPlayer = new MediaPlayer(bgSound);
		soundPlayer.play();
		soundPlayer.setVolume(0.2);
		if(logic.genji.getHP() == 0){
			Media media = new Media(loader.getResource("hanzoend.mp4").toExternalForm());
			player = new MediaPlayer(media);
			overlay.drawImage(ObjectHolder.getInstance().victory[1], 0, 0);
		}else{
			Media media = new Media(loader.getResource("genjiend.mp4").toExternalForm());
			player = new MediaPlayer(media);
			overlay.drawImage(ObjectHolder.getInstance().victory[0], 0, 0);
		}
		
		view = new MediaView(player);
		getChildren().add(view);
		view.toFront();
		overlayCanvas.toFront();
		player.play();
		player.setVolume(0.5);
		player.setOnEndOfMedia(new Runnable(){

			@Override
			public void run() {
				overlay.setFill(Color.BLACK);
				overlay.setFont(new Font("Verdana", 20));
				overlay.fillText("Press ENTER to Main Menu", 20, 700);
				
			}
			
		});
	}
}
