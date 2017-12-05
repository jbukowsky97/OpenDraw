import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener {

    private int x1,x2,y1,y2;

    private Client client;
    private Color color;

    private String command;


    public DrawPanel(Client client){

        this.color = Color.BLACK;
        this.client = client;

        this.setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.setVisible(true);



    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (command.startsWith("line")) {
            String[] cmd = command.split(" ");
            int tempX1 = Integer.parseInt(cmd[1]);
            int tempY1 = Integer.parseInt(cmd[2]);
            int tempX2 = Integer.parseInt(cmd[3]);
            int tempY2 = Integer.parseInt(cmd[4]);
            int r = Integer.parseInt(cmd[5]);
            int gr = Integer.parseInt(cmd[6]);
            int b = Integer.parseInt(cmd[7]);
            g.setColor(new Color(r, gr, b));
            g.drawLine(tempX1, tempY1, tempX2, tempY2);
        }else if (command.equals("clear")) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public void processCommand(String command) {
        this.command = command;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();

        try {
            client.sendCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();

        x2 = e.getX();
        y2 = e.getY();


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
