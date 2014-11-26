package game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

public class SnakePart extends Observable implements Runnable {

	private int xSnakeCoordinate, ySnakeCoordinate;
	private static final int SNAKE_BODY_SIZE = 20;
	private int Xsquare, Ysquare;
	private Color snakeColor;

	private Thread snakeThread;
	private boolean running = false;

	private SnakePart snakepart;
	public LinkedList<SnakePart> snake = new LinkedList<SnakePart>();
	private LinkedList<Point> snakeLocation = new LinkedList<Point>();
	private Point snakePoint;
	private Point nextMovePoint;

	private static final int SNAKE_MAX_SIZE = 10;
	private static final int SNAKE_INITIAL_SIZE = 3;

	private boolean up = false, down = false, left = false, right = false;
	private Random r = new Random();

	public SnakePart(int x, int y, Color snakeColor) {
		xSnakeCoordinate = x;
		ySnakeCoordinate = y;
		Xsquare = SNAKE_BODY_SIZE;
		Ysquare = SNAKE_BODY_SIZE;
		this.snakeColor = snakeColor;

	}

	/*
	 * ##################### # Drawing Section # #####################
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xSnakeCoordinate, ySnakeCoordinate, Xsquare, Ysquare);
		g.setColor(getSnakeColor());
		g.fillRect(xSnakeCoordinate + 2, ySnakeCoordinate + 2, Xsquare - 4,
				Ysquare - 4);
	}

	public void drawHead(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xSnakeCoordinate, ySnakeCoordinate, Xsquare, Ysquare);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xSnakeCoordinate + 2, ySnakeCoordinate + 2, Xsquare - 4,
				Ysquare - 4);
	}

	public void drawSnake(Graphics g) {
		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
			snake.getLast().drawHead(g);

		}

	}


	/*
	 * ##################### # Sets & Gets # #####################
	 */
	public LinkedList<SnakePart> getSnake() {
		return snake;
	}
	
	public LinkedList<Point> getSnakeLocation() {
		return snakeLocation;
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

	private Color getSnakeColor() {
		return snakeColor;
	}

	/*
	 * ##################### # Thread # #####################
	 */
	@Override
	public void run() {

		while (running) {

			pickDirection();

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void start() {
		running = true;

		if (snakeThread == null) {
			snakeThread = new Thread(this, "Snake");
			snakeThread.start();
		}
	}

	/*
	 * ##################### # SNAKE MOVEMENT # #####################
	 */
	private void moveSnakeUp() {

		right = false;
		up = true;
		down = false;
		left = false;

		ySnakeCoordinate -= SNAKE_BODY_SIZE;
		nextMovePoint = new Point (xSnakeCoordinate,ySnakeCoordinate);

		if (!(snakeLocation.contains(nextMovePoint))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(xSnakeCoordinate, ySnakeCoordinate, getSnakeColor());
			snake.addLast(snakepart);
			snakePoint = new Point(xSnakeCoordinate,ySnakeCoordinate);
			snakeLocation.addLast(snakePoint);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKE_MAX_SIZE) {
				snake.removeFirst();
				snakeLocation.removeFirst();
			}
		}
		else System.err.println("Ocupado | UP" );
		//	}

	}

	private void moveSnakeDown() {

		right = false;
		up = false;
		down = true;
		left = false;

		ySnakeCoordinate += SNAKE_BODY_SIZE;
		nextMovePoint = new Point (xSnakeCoordinate,ySnakeCoordinate);

		if (!(snakeLocation.contains(nextMovePoint))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(xSnakeCoordinate, ySnakeCoordinate, getSnakeColor());
			snake.addLast(snakepart);
			snakePoint = new Point(xSnakeCoordinate,ySnakeCoordinate);
			snakeLocation.addLast(snakePoint);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKE_MAX_SIZE) {
				snake.removeFirst();
				snakeLocation.removeFirst();
			}
		}

		else System.err.println("Ocupado | DOWN");
	}

	private void moveSnakeLeft() {

		right = false;
		up = false;
		down = false;
		left = true;

		xSnakeCoordinate -= SNAKE_BODY_SIZE;
		nextMovePoint = new Point (xSnakeCoordinate,ySnakeCoordinate);

		if (!(snakeLocation.contains(nextMovePoint))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(xSnakeCoordinate, ySnakeCoordinate, getSnakeColor());
			snake.addLast(snakepart);
			snakePoint = new Point(xSnakeCoordinate,ySnakeCoordinate);
			snakeLocation.addLast(snakePoint);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKE_MAX_SIZE) {
				snake.removeFirst();
				snakeLocation.removeFirst();
			}
		}

		else System.err.println("Ocupado | LEFT");
	}

	private void moveSnakeRight() {

		right = true;
		up = false;
		down = false;
		left = false;

		xSnakeCoordinate += SNAKE_BODY_SIZE;
		nextMovePoint = new Point (xSnakeCoordinate,ySnakeCoordinate);

		if (!(snakeLocation.contains(nextMovePoint))) {

			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(xSnakeCoordinate, ySnakeCoordinate, getSnakeColor());
			snake.addLast(snakepart);
			snakePoint = new Point(xSnakeCoordinate,ySnakeCoordinate);
			snakeLocation.addLast(snakePoint);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKE_MAX_SIZE) {
				snake.removeFirst();
				snakeLocation.removeFirst();
			}
		}

		else System.err.println("Ocupado | RIGHT");
	}

	private void pickDirection() {

		int x = r.nextInt(4);

		// Escolhe para que lado a Snake anda
		switch (x) {

		// Right
		case 0:
			if (!left && xSnakeCoordinate <= 400 - SNAKE_BODY_SIZE) {

				moveSnakeRight();
				setChanged();
				notifyObservers("MOVED_RIGHT");
			}

			break;

			// Left
		case 1:
			// porque a grid começa no 20|20 e vem da direita temos de
			// fazer um square x 2
			if (!right && xSnakeCoordinate >= SNAKE_BODY_SIZE * 2) {

				moveSnakeLeft();
				setChanged();
				notifyObservers("MOVED_LEFT");
			}
			break;

			// Up
		case 2:
			if (!down && ySnakeCoordinate >= SNAKE_BODY_SIZE * 2) {

				moveSnakeUp();
				setChanged();
				notifyObservers("MOVED_UP");
			}
			break;

			// Down
		case 3:
			if (!up && ySnakeCoordinate <= 400 - SNAKE_BODY_SIZE) {

				moveSnakeDown();
				setChanged();
				notifyObservers("MOVED_DOWN");
			}
			break;

		default:
			break;
		}
	}

}
