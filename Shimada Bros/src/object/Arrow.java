/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import ui.ObjectHolder;

public class Arrow extends Entity{

	private final int speed = 15;
	private double originX, originY, distance, speedX, speedY;
	private int angle;
	private Image arrowPic;
	
	public Arrow(double x, double y, double z, int dx, int dy, double ox, double oy) {
		super(x, y, z, dx, dy);

		//calculate angle
		originX = ox;
		originY = oy;
		distance = Math.sqrt(Math.pow(x-originX,2) + Math.pow(y-originY,2));
		speedX = ((originX-x)/distance) * 21;
		speedY = ((originY-y)/distance) * 21;
		arrowPic =  new WritableImage(ObjectHolder.getInstance().arrow.getPixelReader(), 0, 0, 100, 100);
		
		angle = (int) Math.toDegrees(Math.acos((originX-x)/distance));
	}

	public void move(){
		x += speedX;
		y += speedY;
		setCollision(x+25, x+75, y+25, y+75);
		
		
		if(x >= 1280 || x <= 0 || y >= 720 || y <= 0){
			Destroyed();
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		//Draw arrow with calculated angle
		if((angle <= 7 && speedY <= 0) || (angle >= 173 && speedY <= 0)){
			arrowPic =  new WritableImage(ObjectHolder.getInstance().arrow.getPixelReader(), 0, 0, 100, 100);
			
		}else if(angle <= 7 && speedY >= 0 || (angle >= 173 && speedY >= 0)){
			arrowPic =  new WritableImage(ObjectHolder.getInstance().arrow.getPixelReader(), 0, 100, 100, 100);
		}
		else{
			for(int i = 0; i < 11; i++){
				if(angle >= 8+15*i && angle <= 22+15*i){
					if(Double.compare(0, speedY) == 1){
						arrowPic =  new WritableImage(ObjectHolder.getInstance().arrow.getPixelReader(), 100*(i+1), 0, 100, 100);
					}else{
						arrowPic =  new WritableImage(ObjectHolder.getInstance().arrow.getPixelReader(), 100*(11-i), 100, 100, 100);
					}
					break;
				}
			}
		}
			
		gc.drawImage(arrowPic, x-40, y-40);
	}

	@Override
	public boolean isCollide() {
		// TODO Auto-generated method stub
		return false;
	}

}
