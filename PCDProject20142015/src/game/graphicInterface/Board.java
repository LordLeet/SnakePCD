package game.graphicInterface;

import game.gameObjects.SnakePart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

	private SnakePart snake1;
	private SnakePart snake2;
	private LinkedList<SnakePart> snakes = new LinkedList<SnakePart>();
	private final static int PLAYERS = 2;

	public Board() {

		setPreferredSize(new Dimension(maxWidth, maxHeight));
		
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
			public void mouseClicked(MouseEvent arg0) {
				getMouseLocation(arg0);	
				  if (snake1.getSnake().contains(arg0.getX()) || snake1.getSnake().contains(arg0.getY())) {
					  System.out.println("SNAKE");
				  }
				
			}
		});
		
		snake1 = new SnakePart(200, 200);
		snake1.addObserver(this);
		snake1.start();
		
		snake2 = new SnakePart(40, 60);
		snake2.addObserver(this);
		snake2.start();

	}

	private void getMouseLocation(MouseEvent arg0) {
		mouseLine = (int) arg0.getX() / 20;
		mouseColloumn = (int) arg0.getY() / 20;
		
		System.out.println(mouseLine + " " + mouseColloumn);
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
		g.setColor(Color.RED);
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
