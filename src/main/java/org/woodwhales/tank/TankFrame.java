package org.woodwhales.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

	private static final long serialVersionUID = 1L;

	public TankFrame() {
		setVisible(true);
		setResizable(false);
		setTitle("tank war game");
		setSize(800, 600);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}

	@Override
	public void paint(Graphics g) {
		g.drawRect(200, 200, 50, 50);
	}
}
