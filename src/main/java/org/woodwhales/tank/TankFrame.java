package org.woodwhales.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

	private static final long serialVersionUID = 1L;

	int x = 200, y = 200;
	
	public TankFrame() {
		setVisible(true);
		setResizable(false);
		setTitle("tank war game");
		setSize(800, 600);
		
		addKeyListener(new MyKeyListener());
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}

	// 每次窗口在显示器上重新显示出来就会调用一次paint()方法
	// 因此想要方框能够移动，那么就需要不停地调用paint()方法，重新画方框的位置
	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
		g.drawRect(x, y, 50, 50);
		x += 20;
	}

	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("key pressed");
			// x += 20;
			// repaint(); // 每调用一次，就会调用 paint() 方法一次，因此可以重画Frame
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("key released");
		}
	}
}
