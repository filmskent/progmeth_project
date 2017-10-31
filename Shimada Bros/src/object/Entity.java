/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package object;

import javafx.scene.canvas.GraphicsContext;
import ui.Renderable;

public abstract class Entity implements Renderable{

	public double x, y, z;
	public int directX, directY;
	public double[] body;
	boolean isDestroyed;
	
	
	public Entity(double x, double y, double z, int dx, int dy){
		this.x = x;
		this. y = y;
		this.z = z;
		this.directX = dx;
		this.directY = dy;
		body = new double[4];
	}
	
	//getter
	public double[] getCollision(){
		return body;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}
	public int getDirectX(){
		return directX;
	}
	public int getDirectY(){
		return directY;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	//setter
	public void setCollision(double x1,double x2,double y1,double y2){
		body[0] = x1;
		body[1] = x2;
		body[2] = y1;
		body[3] = y2;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setZ(double z){
		this.z = z;
	}
	public void setDirectX(int x){
		this.directX = x;
	}
	public void setDirectY(int y){
		this.directY = y;
	}
	public void Destroyed() {
		isDestroyed = true;
	}

	
	@Override
	public abstract void draw(GraphicsContext gc);
	public abstract boolean isCollide();
}
