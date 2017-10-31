package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import main.Main;
import ui.ObjectHolder;
import ui.Renderable;

public class Hanzo extends Character{

	private double Grav = 0.35;
	private double Inertia = 1;
	private double yAccel = 0;
	private double xAccel = 0;
	private int jumpCount;
	double lastY;
	private WritableImage hanzoBody, hanzoLeg, hanzoFX;
	private int lastFrame;
	
	public Hanzo(double x, double y, double z, int dx, int dy, int hp, int moveSpeed) {
		super(x, y, z, dx, dy, hp, moveSpeed);
		isJump = false;
		isMove = false;
		isDown = false;
		isAir = true;
		isAtk = false;
		lastY = y;
		lastFrame = 0;
		hanzoBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),0, 0, 150, 150);
		hanzoLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),600, 150, 150, 150);
		hanzoFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),0, 150, 150, 150);
	}
	
	
	@Override
	public void draw(GraphicsContext gc) {
		//gc.setFill(Color.RED);
		//gc.fillOval(x, y, 50, 75);
		if(directY != 0){
			if(directX == 1){
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),150, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),900, 150, 150, 150);
			}else if(directX == -1){
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),450, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),1050, 150, 150, 150);
			}else{
				if(lastDirectX == 1){
					hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),150, 0, 150, 150);
					hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),900, 150, 150, 150);
				}else{
					hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),450, 0, 150, 150);
					hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),1050, 150, 150, 150);
				}
			}
		}else if(isMove){
			if(directX == 1){
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),150, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2) + 1) * 150, 150, 150, 150);
			}else if(directX == -1){
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),450, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2) + 4)* 150, 150, 150, 150);
			}
		}else{
			if(lastDirectX == 1){
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),150, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),0, 150, 150, 150);
			}else{
				hanzoBody = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),450, 0, 150, 150);
				hanzoLeg = new WritableImage(ObjectHolder.getInstance().hanzoPic.getPixelReader(),450, 150, 150, 150);
			}
			
		}
		gc.drawImage(hanzoBody, x-55, y-50);
		gc.drawImage(hanzoLeg, x-55, y-50);
		//gc.drawImage(hanzoFX, x-55, y-50);
		
	}

	@Override
	public void move() {
		
		if((Double.compare(y, 600) == 1 || Double.compare(y + yAccel, 600) == 1) && !isJump){
			y = 600;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
			
		}
		if((x >= 938 && x <= 1115) && directY != -1 && Double.compare(y+yAccel, 457) == 1 && Double.compare(y, 457) != 1 && !isJump && !isDown){
			y = 457;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		if((x >= 544 && x <= 721) && directY != -1 && Double.compare(y+yAccel, 457) == 1 && Double.compare(y, 457) != 1 && !isJump && !isDown){
			y = 457;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		if((x >= 101 && x <= 303) && directY != -1 && Double.compare(y+yAccel, 457) == 1 && Double.compare(y, 457) != 1 && !isJump && !isDown){
			y = 457;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		if((x >= 185 && x <= 1018) && directY != -1 && Double.compare(y+yAccel, 290) == 1 && Double.compare(y, 290) != 1 && !isJump && !isDown){
			y = 290;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		if((x >= 306 && x <= 483) && directY != -1 && Double.compare(y+yAccel, 127) == 1 && Double.compare(y, 127) != 1 && !isJump && !isDown){
			y = 127;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		if((x >= 689 && x <= 966) && directY != -1 && Double.compare(y+yAccel, 127) == 1 && Double.compare(y, 127) != 1 && !isJump && !isDown){
			y = 127;
			yAccel = 0;
			jumpCount = 0;
			directY = 0;
		}
		
		if(isJump){
			y -= yAccel;
			yAccel -= Grav;
		}else{
			if(isAir){
				y += yAccel;
				yAccel += Grav;
			}
		}
		if(Double.compare(yAccel, 0) == -1){
			isJump = false;
			yAccel = 0;
		}
		
		if(isMove){
			x += xAccel;
			double I;
			if(directY != 0){
				I = 0.2;
			}else{
				I = Inertia*2;
			}
			if(directX == -1){
				xAccel -= I;
				if(xAccel < -moveSpeed){
					xAccel = -moveSpeed;
				}
				
			}else if(directX == 1){
				xAccel += I;
				if(xAccel > moveSpeed){
					xAccel = moveSpeed;
				}
			}
		}else{
		x += xAccel;
			
			if(directY == 0){
				if(xAccel < 0){

					if(xAccel + Inertia*4 > 0){
						xAccel = 0;
					}else{
						xAccel += Inertia*4;
					}
				}else if(xAccel > 0){
					if(xAccel - Inertia*4 < 0){
						xAccel = 0;
					}else{
						xAccel -= Inertia*4;
					}
				}else{
					xAccel = 0;
				}
			}
			

		}
		if(x >= 1230){
			x = 1230;

		}
		if(x <= 0){
			x = 0;

		}
		 if(Double.compare(y, lastY) == 1){
			 directY = 1;
		 }else if(Double.compare(y, lastY) == -1){
			 directY = -1;
		 }
		lastY = y;
		setCollision(x, x+50, y, y+75);
		isCollide();
	}

	public void Jump(){
		jumpCount++;
		if(jumpCount <= 1){
			isJump = true;
			yAccel = 11;
		}
	}
	public void setMoving(boolean move){
		isMove = move;
	}
	
	public void setDown(boolean down){
		isDown = down;
	}
	public boolean isDown(){
		return isDown;
	}
	
	public void checkAirtime(){
		
		if(directY == 0){
			isAir = false;
		}else{
			isAir = true;
		}
	}
	
	public void attack(double mouseX, double mouseY){
		ObjectHolder.getInstance().playSound(1);
		ObjectHolder.getInstance().add(new Arrow(x, y, 0, 0, 0, mouseX, mouseY));
	}
	
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCollide() {
		setCollision(x, x+50, y, y+75);
	
		for(Entity obj: ObjectHolder.getInstance().getObject()){
			
			if(!obj.equals(this)){
				//System.out.format("body: %f, %f, %f, %f\n", body[0], body[1], body[2], body[3]);
				//System.out.format("obj: %f, %f, %f, %f\n", obj.getCollision()[0], obj.getCollision()[1], obj.getCollision()[2], obj.getCollision()[3]);
				if((Double.compare(obj.getCollision()[0],body[0]) != -1 && Double.compare(obj.getCollision()[0],body[1]) != 1) || (Double.compare(obj.getCollision()[1],body[0]) != -1 && Double.compare(obj.getCollision()[1],body[1]) != 1)){
					if((Double.compare(obj.getCollision()[2],body[2]) != -1 && Double.compare(obj.getCollision()[2],body[3]) != 1) || (Double.compare(obj.getCollision()[3],body[2]) != -1 && Double.compare(obj.getCollision()[3],body[3]) != 1)){
						if(obj instanceof AttackDummy){
							reduceHP(30);
							ObjectHolder.getInstance().playSound(3);
							obj.Destroyed();
						}
						return true;
					}
			
				}
			}
		}
		return false;
	}

}
