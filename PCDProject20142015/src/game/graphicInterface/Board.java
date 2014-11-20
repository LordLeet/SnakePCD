package game.graphicInterface;

import game.gameObjects.SnakePart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.plaf.SliderUI;

public class Board extends JPanel implements Runnable, Observer {

	private boolean running = false;
	private Thread boardThread;
	private int timer = 0;

	private int squaresPerLine = 20;
	private final static int SQUARESIZE = 20;
	private int maxWidth = squaresPerLine * SQUARESIZE;
	private int maxHeight = squaresPerLine * SQUARESIZE;

	private SnakePart snakepart;
	private LinkedList<SnakePart> snake;
	private int snakePosX = 200;
	private int snakePosY = 120;
	private static final int SNAKEMAXSIZE = 5;

	private boolean up = false, down = false, left = false, right = false;

	private Random r = new Random();
	private int time = 0;

	public Board() {

		setPreferredSize(new Dimension(maxWidth, maxHeight));

		snake = new LinkedList<SnakePart>();
		start();
	}

	public void start() {
		running = true;
		boardThread = new Thread(this, "Board");
		boardThread.start();
	}

	public void end() {
	}

	public void paint(Graphics g) {
		// para fazer reset a' window antes de recomeçar
		refreshGrid(g);
		drawGrid(g);
		drawSnake(g);

	}

	@Override
	public void run() {
		while (running) {

			initializeSnake();
			pickDirection();
			repaint();

			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	/*
	 * ############## # DRAWING # ##############
	 */

	private void drawGrid(Graphics g) {

		g.setColor(Color.black);
		for (int i = 0; i <= squaresPerLine; i++) {
			g.drawLine(SQUARESIZE + i * SQUARESIZE, SQUARESIZE, SQUARESIZE + i
					* SQUARESIZE, SQUARESIZE + squaresPerLine * SQUARESIZE);
			g.drawLine(SQUARESIZE, SQUARESIZE + i * SQUARESIZE, SQUARESIZE
					+ squaresPerLine * SQUARESIZE, SQUARESIZE + i * SQUARESIZE);
		}
	}

	private void drawSnake(Graphics g) {
		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
			snake.getLast().drawHead(g);

		}

	}

	private void refreshGrid(Graphics g) {

		g.clearRect(20, 20, (squaresPerLine * SQUARESIZE) + SQUARESIZE,
				(squaresPerLine * SQUARESIZE) + SQUARESIZE);
	}

	/*
	 * ##################### # SNAKE MOVEMENT # #####################
	 */
	private void moveSnakeUp() {

		right = false;
		up = true;
		down = false;
		left = false;

		snakePosY -= SQUARESIZE;

		if (!(snake.contains(snakePosX) && snake.contains(snakePosY))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(snakePosX, snakePosY);
			snake.add(snakepart);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKEMAXSIZE) {
				snake.removeFirst();
			}
		}
	}

	private void moveSnakeDown() {

		right = false;
		up = false;
		down = true;
		left = false;

		snakePosY += SQUARESIZE;

		if (!(snake.contains(snakePosX) && snake.contains(snakePosY))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(snakePosX, snakePosY);
			snake.add(snakepart);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKEMAXSIZE) {
				snake.removeFirst();
			}
		}
	}

	private void moveSnakeLeft() {

		right = false;
		up = false;
		down = false;
		left = true;

		snakePosX -= SQUARESIZE;

		if (!(snake.contains(snakePosX) && snake.contains(snakePosY))) {
			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(snakePosX, snakePosY);
			snake.add(snakepart);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKEMAXSIZE) {
				snake.removeFirst();
			}
		}
	}

	private void moveSnakeRight() {

		right = true;
		up = false;
		down = false;
		left = false;

		snakePosX += SQUARESIZE;

		if (!(snake.contains(snakePosX) && snake.contains(snakePosY))) {

			// Adiciona um quadrado a' Snake
			snakepart = new SnakePart(snakePosX, snakePosY);
			snake.add(snakepart);

			// Para a Snake não crescer indefinidamente
			if (snake.size() > SNAKEMAXSIZE) {
				snake.removeFirst();
			}
		}
	}

	private void pickDirection() {

		int x = r.nextInt(4);
		// int x = 3;

		// Escolhe para que lado a Snake anda
		switch (x) {

		// Right
		case 0:
			if (!left && snakePosX <= maxWidth - SQUARESIZE) {

				moveSnakeRight();

			}

			break;

		// Left
		case 1:
			// porque a grid começa no 20|20 e vem da direita temos de
			// fazer um square x 2
			if (!right && snakePosX >= SQUARESIZE * 2) {

				moveSnakeLeft();

			}
			break;

		// Up
		case 2:
			if (!down && snakePosY >= SQUARESIZE * 2) {

				moveSnakeUp();

			}
			break;

		// Down
		case 3:
			if (!up && snakePosY <= maxHeight - SQUARESIZE) {

				moveSnakeDown();

			}
			break;

		default:
			break;
		}
	}

	private void initializeSnake() {

		if (snake.size() == 0) {
			snakepart = new SnakePart(snakePosX, snakePosY);
			snake.add(snakepart);
		}
	}

	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub

	}

}
