package game.graphicInterface;

import game.gameObjects.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JComponent;


public class Board extends JComponent implements Observer {

	private static final long serialVersionUID = 1L;

	private int positionsPerLine = 20;
	private final static int SQUARESIZE = 20;

	private int maxWidth = positionsPerLine * SQUARESIZE;
	private int maxHeight = positionsPerLine * SQUARESIZE;

	private int mouseLine;
	private int mouseColloumn;
	private Point mouseCLick;

	private Snake snake1;
	private Snake snake2;
	private LinkedList<Snake> snakes = new LinkedList<Snake>();

	private final static int PLAYERS = 2;
	private Random random = new Random();

	public Board() {

		setPreferredSize(new Dimension(maxWidth, maxHeight));

	//	snake1 = new Snake( new Point(1,random.nextInt(positionsPerLine-3)+1), Color.RED, this);
		snake1 = new Snake( new Point(3,10), Color.GREEN, this);
		snake1.addObserver(this);
		snake1.start();
		snakes.add(snake1);
		//snake1.setSnakeDestination(new Point(20,20));

/*		snake2 = new Snake(new Point(random.nextInt(positionsPerLine-3)+1,3), Color.BLUE, this);
		snake2.addObserver(this);
		snake2.start();
		snakes.add(snake2);*/

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

		mouseLine = (int) arg0.getX() / SQUARESIZE;
		mouseColloumn = (int) arg0.getY() / SQUARESIZE;
		mouseCLick = new Point(mouseLine, mouseColloumn);
		
			for(Snake s : snakes) {
			if (s.getSnake().contains(mouseCLick)) {
				s.pauseSnake();

			}

			else {
				
				s.setSnakeDestination(mouseCLick);

			}
		}

	}

	public void paint(Graphics g) {
		// para fazer reset a' window antes de recomeçar
		refreshGrid(g);
		drawGrid(g);
		drawSnake(g);
	}

	/*
	 * ############## # DRAWING # ##############
	 */

	private void drawGrid(Graphics g) {

		g.setColor(Color.black);
		for (int i = 0; i <= positionsPerLine; i++) {
			g.drawLine(SQUARESIZE + i * SQUARESIZE, SQUARESIZE, SQUARESIZE + i
					* SQUARESIZE, SQUARESIZE + positionsPerLine * SQUARESIZE);
			g.drawLine(SQUARESIZE, SQUARESIZE + i * SQUARESIZE, SQUARESIZE
					+ positionsPerLine * SQUARESIZE, SQUARESIZE + i * SQUARESIZE);
		}
	}

	private void refreshGrid(Graphics g) {

		g.clearRect(20, 20, (positionsPerLine * SQUARESIZE) + SQUARESIZE,
				(positionsPerLine * SQUARESIZE) + SQUARESIZE);
	}

	public void drawSnake(Graphics g) {

		for (int j = 0; j < snakes.size(); j++) {

			for (int i = 0; i < snakes.get(j).getSnake().size(); i++) {
				
				//Draw Border
				g.setColor(Color.BLACK);
				g.fillRect(snakes.get(j).getSnake().get(i).x * SQUARESIZE, snakes.get(j).getSnake().get(i).y * SQUARESIZE, SQUARESIZE, SQUARESIZE);
				//Draw Body
				g.setColor(snakes.get(j).getSnakeColor());
				g.fillRect(snakes.get(j).getSnake().get(i).x * SQUARESIZE +2, snakes.get(j).getSnake().get(i).y * SQUARESIZE+2, SQUARESIZE-4, SQUARESIZE-4);

			}			
		}
		
		drawSnakeHead(g);
	}

	public void drawSnakeHead(Graphics g) {

		for (int j = 0; j < snakes.size(); j++) {

			int x1,y1,x2,y2;
			x1 = snakes.get(j).getSnake().getFirst().x* SQUARESIZE;
			y1 = snakes.get(j).getSnake().getFirst().y * SQUARESIZE;
			x2 = SQUARESIZE;
			y2 = SQUARESIZE;

			g.setColor(Color.BLACK);
			g.fillRect(x1, y1, x2, y2);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x1 + 2, y1 + 2, x2 - 4, y2 - 4);
		}

	}

	public int getBoardHeight() {
		return maxHeight;
	}

	public int getBoardWidth() {
		return maxWidth;
	}

	public int getPositionsPerLine() {
		return positionsPerLine;
	}

	@Override
	public void update(Observable observable, Object obj) {
		synchronized (this) {
			repaint();
		}

	}

}
