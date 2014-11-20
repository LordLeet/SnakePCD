package game;

import game.graphicInterface.Board;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PCD Project 2014/2015 - SNAKE");
		setResizable(false);
		setSize(450, 475);
		init();
	}

	public void init() {
		setLayout(new BorderLayout());
		Board board = new Board();
		add(board, BorderLayout.CENTER);
		//pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Frame();
	}
}
