package game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

public class SnakePart {
	
	private int xSnakeCoordinate, ySnakeCoordinate;
	private static final int snakePartSize = 20;
	private int Xsquare, Ysquare;
	
	public SnakePart(int x, int y) {
		xSnakeCoordinate = x;
		ySnakeCoordinate = y;
		Xsquare = snakePartSize;
		Ysquare = snakePartSize;
		
	}
	
	public void happening() {}
	
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(xSnakeCoordinate, ySnakeCoordinate, Xsquare, Ysquare);
		g.setColor(Color.GREEN);
		g.fillRect(xSnakeCoordinate + 3, ySnakeCoordinate + 3, Xsquare - 6, Ysquare - 6);
	}

	public int getxSnakeCoordinate() {
		return xSnakeCoordinate;
	}

	public void setxSnakeCoordinate(int xSnakeCoordinate) {
		this.xSnakeCoordinate = xSnakeCoordinate;
	}

	public int getySnakeCoordinate() {
		return ySnakeCoordinate;
	}

	public void setySnakeCoordinate(int ySnakeCoordinate) {
		this.ySnakeCoordinate = ySnakeCoordinate;
	}
	
	

}
