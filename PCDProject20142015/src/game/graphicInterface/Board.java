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
		g.clearRect(20, 20, (squaresPerLine * SQUARESIZE) + SQUARESIZE,
				(squaresPerLine * SQUARESIZE) + SQUARESIZE);

		g.setColor(Color.black);
		for (int i = 0; i <= squaresPerLine; i++) {
			g.drawLine(SQUARESIZE + i * SQUARESIZE, SQUARESIZE, SQUARESIZE + i
					* SQUARESIZE, SQUARESIZE + squaresPerLine * SQUARESIZE);
			g.drawLine(SQUARESIZE, SQUARESIZE + i * SQUARESIZE, SQUARESIZE
					+ squaresPerLine * SQUARESIZE, SQUARESIZE + i * SQUARESIZE);
		}

		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);

		}
	}

	@Override
	public void run() {
		while (running) {
			
			// Cria a Snake
			if (snake.size() == 0) {
				snakepart = new SnakePart(snakePosX, snakePosY);
				snake.add(snakepart);
			}

			/*
			 * try { Thread.currentThread().sleep(100); } catch
			 * (InterruptedException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			timer++;

			if (timer > 5000000) {
				int x = r.nextInt(4);
				//int x = 3;
				
				// Escolhe para que lado a Snake anda
				switch (x) {
				
				// Right
				case 0:
					if (!left && snakePosX <= maxWidth-SQUARESIZE) {

						right = true;
						up = false;
						down = false;
						left = false;
						
						snakePosX += SQUARESIZE;

						// Adiciona um quadrado a' Snake
						snakepart = new SnakePart(snakePosX, snakePosY);
						snake.add(snakepart);

						// Para a Snake não crescer indefinidamente
						if (snake.size() > SNAKEMAXSIZE) {
							snake.removeFirst();
						}

					}

					break;
					
				// Left	
				case 1:
					//porque a grid começa no 20|20 e vem da direita temos de fazer um square x 2
					if (!right && snakePosX >= SQUARESIZE*2) {

						right = false;
						up = false;
						down = false;
						left = true;
						
						snakePosX -= SQUARESIZE;

						// Adiciona um quadrado a' Snake
						snakepart = new SnakePart(snakePosX, snakePosY);
						snake.add(snakepart);

						// Para a Snake não crescer indefinidamente
						if (snake.size() > SNAKEMAXSIZE) {
							snake.removeFirst();
						}
					}
					break;
					
				// Up
				case 2:
					if (!down && snakePosY >= SQUARESIZE*2) {

						right = false;
						up = true;
						down = false;
						left = false;
						
						snakePosY -= SQUARESIZE;

						// Adiciona um quadrado a' Snake
						snakepart = new SnakePart(snakePosX, snakePosY);
						snake.add(snakepart);

						// Para a Snake não crescer indefinidamente
						if (snake.size() > SNAKEMAXSIZE) {
							snake.removeFirst();
						}
					}
					break;
					
				// Down
				case 3:
					if (!up && snakePosY <= maxHeight-SQUARESIZE) {

						right = false;
						up = false;
						down = true;
						left = false;
						
						snakePosY += SQUARESIZE;

						// Adiciona um quadrado a' Snake
						snakepart = new SnakePart(snakePosX, snakePosY);
						snake.add(snakepart);

						// Para a Snake não crescer indefinidamente
						if (snake.size() > SNAKEMAXSIZE) {
							snake.removeFirst();
						}
					}
					break;
				
				default:
					break;
				}

				timer = 0;
			}
			
			repaint();
		}
	}

	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub

	}

}
