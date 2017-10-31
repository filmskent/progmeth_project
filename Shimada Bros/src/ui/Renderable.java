package ui;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
	public double getX();
	public double getY();
	public double getZ();
	public void draw(GraphicsContext gc);
	public double[] getCollision();
	public void move();
}
