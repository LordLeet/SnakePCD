package game.graphicInterface;

import game.gameObjects.SnakePart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;


public class Board extends JComponent implements Observer {

	private static final long serialVersionUID = 1L;

	private int squaresPerLine = 20;
	private final static int SQUARESIZE = 20;

	private int maxWidth = squaresPerLine * SQUARESIZE;
	private int maxHeight = squaresPerLine * SQUARESIZE;

	private int mouseLine;
	private int mouseColloumn;
	private int mouseX;
	private int mouseY;
	private Point mouseCLick;

	private SnakePart snake1;
	private SnakePart snake2;
	private LinkedList<SnakePart> snakes = new LinkedList<SnakePart>();
	private final static int PLAYERS = 2;

	public Board() {

		setPreferredSize(new Dimension(maxWidth, maxHeight));

		snake1 = new SnakePart(100, 100, Color.RED);
		snake1.addObserver(this);
		snake1.start();
		snakes.add(snake1);

		snake2 = new SnakePart(400, 400, Color.BLUE);
		snake2.addObserver(this);
		snake2.start();
		snakes.add(snake2);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			synchronized public void mouseClicked(MouseEvent arg0) {
				getMouseLocation(arg0);	

			}
		});

	}

	private void getMouseLocation(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		mouseLine = (int) arg0.getX() / SQUARESIZE;
		mouseColloumn = (int) arg0.getY() / SQUARESIZE;
		mouseCLick = new Point(mouseLine*SQUARESIZE, mouseColloumn*SQUARESIZE);

		System.out.println(mouseX + " " + mouseY + " Line: " + mouseLine + " Colloumn: " + mouseColloumn);

		if (snake1.getSnakeLocation().contains(mouseCLick)) {
			System.out.println("SNAKE");
			synchronized (snake1) {

				try {
					snake1.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		
		else snake1.notifyAll();
	}

	public void paint(Graphics g) {
		// para fazer reset a' window antes de recomeçar
		refreshGrid(g);
		drawGrid(g);
		drawHighlight(g);
		snake1.drawSnake(g);
		snake2.drawSnake(g);

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

	private void drawHighlight(Graphics g) {
		g.setColor(Color.GRAY);
		if (mouseLine != 0 || mouseColloumn != 0)
			g.fillRect(mouseLine*SQUARESIZE, mouseColloumn*SQUARESIZE, SQUARESIZE, SQUARESIZE);	
	}

	private void refreshGrid(Graphics g) {

		g.clearRect(20, 20, (squaresPerLine * SQUARESIZE) + SQUARESIZE,
				(squaresPerLine * SQUARESIZE) + SQUARESIZE);
	}

	@Override
	public void update(Observable observable, Object obj) {
		synchronized (this) {
			repaint();
		}

	}

}
