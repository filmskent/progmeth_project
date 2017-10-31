/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AttackDummy extends Entity{
	private Character owner;
	
	public AttackDummy(double x, double y, double z, int dx, int dy, Character owner) {
		super(x, y, z, dx, dy);
		this.owner = owner;
	}

	@Override
	public void move() {
		setCollision(x, x+45, y, y+70);


		if(owner.getDirectX() == 1){
			x = owner.getX() + 55;
		}else if(owner.getDirectX() == -1){
			x = owner.getX() - 60;
		}else{
			if(owner.lastDirectX == 1){
				x = owner.getX() + 55;
			}else{
				x = owner.getX() - 60;
			}
		}
		
		y = owner.getY();
		
		
		
	}

	@Override
	public void draw(GraphicsContext gc) {	
	}

	@Override
	public boolean isCollide() {
		return false;
	}

}
