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
		
		g.drawRect(x, y, 50, 50);
		
	}

	class MyKeyListener extends KeyAdapter {
		
		// 使用标识变量记录键盘按键的情况，之后再计算方块应该移动的方向
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_UP:
					bU = true;
					break;
				case KeyEvent.VK_RIGHT:
					bR = true;
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					break;
			}
			
			// x += 20;
			// repaint(); // 每调用一次，就会调用 paint() 方法一次，因此可以重画Frame
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
			}
		}
	}
}
