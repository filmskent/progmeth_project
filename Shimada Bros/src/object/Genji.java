/*
Author: Sorapon Pornsomchai 5831076021
		Supawit Kunopagarnwong 5831073021
*/
package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import main.Main;
import ui.ObjectHolder;
import ui.Renderable;

public class Genji extends Character{

	private double Grav = 0.35;
	private double Inertia = 1;
	private double yAccel = 0;
	private double xAccel = 0;
	private boolean isDash;
	private int jumpCount, frameCheck, lastFrame;
	double lastY;
	private WritableImage genjiBody, genjiLeg, genjiFX;
	private AttackDummy dummy;
	
	public Genji(double x, double y, double z, int dx, int dy, int hp, int moveSpeed) {
		super(x, y, z, dx, dy, hp, moveSpeed);
		isJump = false;
		isMove = false;
		isDown = false;
		isAir = true;
		isAtk = false;
		lastY = y;
		lastFrame = 0;
		genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),0, 0, 150, 150);
		genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),600, 150, 150, 150);
		genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),0, 150, 150, 150);
		setCollision(x, x+50, y, +75);
	}
	
	
	@Override
	public void draw(GraphicsContext gc) {

		//draw image by direction of x and y
		if(directY != 0){
			if(directX == 1){
				genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+1) * 150, 0, 150, 150);
				genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),150, 150, 150, 150);
			}else if(directX == -1){
				genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+4) * 150, 0, 150, 150);
				genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),450, 150, 150, 150);
			}else{
				if(lastDirectX == 1){
					genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+1) * 150, 0, 150, 150);
					genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),150, 150, 150, 150);
				}else{
					genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+4) * 150, 0, 150, 150);
					genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),450, 150, 150, 150);
				}
			}
		}else if(isMove){
			if(directX == 1){
				genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+1) * 150, 0, 150, 150);
				genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),(Main.getInstance().getCurrentFrame() % 2) * 150, 150, 150, 150);
			}else if(directX == -1){
				genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2)+4) * 150, 0, 150, 150);
				genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),((Main.getInstance().getCurrentFrame() % 2) + 2)* 150, 150, 150, 150);
			}
		}else{
			genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),0, 0, 150, 150);
			genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),900, 150, 150, 150);
		}
		
		if(isAtk){
			
			if(lastFrame == Main.getInstance().getRunFrame()){
				frameCheck++;
			}
			
			if(frameCheck <= 3){
				if(directX == 1){
					genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),450, 0, 150, 150);
					genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),(frameCheck-1)*150, 0, 150, 150);
				}else if(directX == -1){
					genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),900, 0, 150, 150);
					genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),(frameCheck+3)*150, 0, 150, 150);
				}else{
			
					if(lastDirectX == 1){
						genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),450, 0, 150, 150);
						if(directY == 0)genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),600, 150, 150, 150);
						else genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),150, 150, 150, 150);
						genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),(frameCheck-1)*150, 0, 150, 150);
					}else if(lastDirectX == -1){
						genjiBody = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),900, 0, 150, 150);
						if(directY == 0)genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),750, 150, 150, 150);
						else genjiLeg = new WritableImage(ObjectHolder.getInstance().genjiPic.getPixelReader(),450, 150, 150, 150);
						genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),(frameCheck+3)*150, 0, 150, 150);
					}
				}

			}else{
				genjiFX = new WritableImage(ObjectHolder.getInstance().effectPic.getPixelReader(),0, 150, 150, 150);
				isAtk = false;
				frameCheck = 0;
				dummy.Destroyed();
			}
		}
		gc.drawImage(genjiBody, x-55, y-50);
		gc.drawImage(genjiLeg, x-55, y-50);
		gc.drawImage(genjiFX, x-55, y-50);
		
	}

	@Override
	public void move() {
		
		//check platform
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
		
		//check y condition
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
		
		//check if pressing A and D while  jump
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
		if(jumpCount <= 2){
			isJump = true;
			yAccel = 10;
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
	
	public void checkAirtime(double lastYAcc){
		
		if(directY == 0){
			isAir = false;
		}else{
			isAir = true;
		}
	}
	
	@Override
	public void attack() {
		if(isAtk == false){
			ObjectHolder.getInstance().playSound(0);
			lastFrame = Main.getInstance().getRunFrame();
			frameCheck = 0;
			isAtk = true;
			dummy = new AttackDummy(x, y, 0, 0, 0, this);
			ObjectHolder.getInstance().add(dummy);
		}
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
						if(obj instanceof Arrow){
							reduceHP(20);
							obj.Destroyed();
							ObjectHolder.getInstance().playSound(2);
							
						}
							
						return true;
					}
			
				}
			}
		}
		return false;
	}

}
