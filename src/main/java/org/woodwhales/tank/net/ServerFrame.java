package org.woodwhales.tank.net;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();

    TextArea taLeft = new TextArea();
    TextArea taRight = new TextArea();

    Server server = new Server();

    public ServerFrame() {
        this.setTitle("this is server");
        this.setSize(1000, 600);
        this.setLocation(200, 30);
        Panel panel = new Panel(new GridLayout(1, 2));
        panel.add(taLeft);
        panel.add(taRight);
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.start();
    }

    public void updateServerMsg(String msg) {
        this.taLeft.setText(this.taLeft.getText() + msg + System.getProperty("line.separator"));
    }

    public void updateClientMsg(String msg) {
        this.taRight.setText(this.taRight.getText() + msg + System.getProperty("line.separator"));
    }
}
