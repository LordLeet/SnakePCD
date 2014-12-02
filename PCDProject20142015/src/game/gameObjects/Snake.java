package game.gameObjects;

import game.graphicInterface.Board;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

public class Snake extends Observable implements Runnable {

	private Color snakeColor;

	private Thread snakeThread;
	private boolean running = false;

	private LinkedList<Point> snakeBody = new LinkedList<>();
	private Point head;
	private Point snakeNextLocation;
	private Point destinationPoint;

	private Board board;

	private static final int SNAKE_MAX_SIZE = 5;
	private static final int SNAKE_INITIAL_SIZE = 2;

	private Random r = new Random();
	private int nextdirection;
	private boolean up = false, down = false, left = false, right = false ,idle = true;

	public Snake(Point head, Color snakeColor, Board board) {

		this.head = head;
		this.snakeColor = snakeColor;
		this.board = board;
		generateSnake(head);

	}

	/*
	 * ##################### # Sets & Gets # #####################
	 */
	public LinkedList<Point> getSnake() {
		return snakeBody;
	}

	public Color getSnakeColor() {
		return snakeColor;
	}

	public Point getSnakeHeadLocation() {
		return head;
	}

	public boolean getSnakeState() {
		return running;
	}

	public void setSnakeDestination(Point p) {
		destinationPoint = p;
		idle = false;
		System.out.println("Destination: [" + p.x + "|" + p.y + "]");
	}

	private Point getDestination() {
		return destinationPoint;
	}
	/*
	 * ##################### # Thread # #####################
	 */
	@Override
	public void run() {

		while (running) {

			//moveTo();
			pickDirection();

			try {
				Thread.sleep(1000);
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

	public void pauseSnake() {
		idle = true;
		System.out.println("PAROU");
	}

	public void resumeSnake() {
		idle = false;
		System.out.println("VOLTOU");
	}

	/*
	 * ##################### # SNAKE MOVEMENT # #####################
	 */

	private void generateSnake(Point point) {

		snakeBody.clear();

		for (int i = 0; i <= SNAKE_INITIAL_SIZE; i++) {

			snakeBody.addFirst(new Point(point.x++, point.y));

		}

	}

	private void moveSnakeUp() {
		right = false; up = true;	down = false;	left = false;

		snakeNextLocation = new Point (head.x,head.y--);

	}

	private void moveSnakeDown() {
		right = false; 	up = false;	down = true;	left = false;

		snakeNextLocation = new Point (head.x,head.y++);
	}

	private void moveSnakeLeft() {
		right = false;	up = false;	down = false;	left = true;

		snakeNextLocation = new Point (head.x--,head.y);
	}

	private void moveSnakeRight() {
		right = true;	up = false;	down = false;	left = false;

		snakeNextLocation = new Point (head.x++,head.y);
	}

	synchronized private void pickDirection() {

		int x = 0;

		if(getDestination() != null) {


			if (getDestination().x > head.x)
				x = 0;
			if (getDestination().x < head.x)
				x = 1;
			if (getDestination().y < head.y)
				x = 2;
			if (getDestination().y > head.y)
				x = 3;
			if (getDestination().equals(head)) {
				idle = true;
				System.out.println("Destination Reached");
			}
		}

		if(idle)
			// Escolhe para que lado a Snake anda
			x = r.nextInt(4);

		snakeNextLocation = snakeBody.getFirst();

		switch (x) {

		// Right
		case 0:
			if (!left && snakeNextLocation.x <= board.getPositionsPerLine()) {
				moveSnakeRight();
				notifyObservers("MOVED_RIGHT");
			}
			break;

			// Left
		case 1:
			if (!right && snakeNextLocation.x > 0) {
				moveSnakeLeft();
				notifyObservers("MOVED_LEFT");
			}
			break;
			// Up
		case 2:
			if (!down && snakeNextLocation.y > 0) {
				moveSnakeUp();
				notifyObservers("MOVED_UP");
			}
			break;
			// Down
		case 3:
			if (!up && snakeNextLocation.y <= board.getPositionsPerLine()) {
				moveSnakeDown();
				notifyObservers("MOVED_DOWN");
			}
			break;
		}
		//	}

		if (!(snakeBody.contains(snakeNextLocation))) {
			// Adiciona um quadrado a' Snake
			snakeBody.addFirst(snakeNextLocation);
			// Para a Snake não crescer indefinidamente
			if (snakeBody.size() > SNAKE_MAX_SIZE) {
				snakeBody.removeLast();
			}
		}
		System.out.println(snakeNextLocation);
		setChanged();
	}
}
