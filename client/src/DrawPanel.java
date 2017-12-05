import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener {

    private int x1,x2,y1,y2;

    private Client client;
    private Color color;

    private String command;
    private String myCommand;

    private boolean first;


    public DrawPanel(Client client){

        this.color = Color.BLACK;
        this.client = client;
        this.myCommand = "line";

        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.setVisible(true);

        first = true;

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                dragged(e);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mousePressed(MouseEvent e) {
                pressed(e);
            }
        });

    }

    private void dragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();

        if (myCommand.equals("line")) {
            try {
                client.sendCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                processCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (myCommand.equals("brush")){
            try {
                client.sendCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                System.out.println("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                processCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        x1 = e.getX();
        y1 = e.getY();
    }

    private void pressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();

        x2 = e.getX();
        y2 = e.getY();

        if (myCommand.equals("line")) {
            try {
                client.sendCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                processCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (myCommand.equals("brush")){
            try {
                client.sendCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                System.out.println("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                processCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        if (first) {
            super.paintComponent(g);
            first = false;
        }
        if (command == null) {

        }else if (command.startsWith("line")) {
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
        }else if (command.startsWith("brush")) {
            String[] cmd = command.split(" ");
            int tempX1 = Integer.parseInt(cmd[1]);
            int tempY1 = Integer.parseInt(cmd[2]);
            int tempX2 = Integer.parseInt(cmd[3]);
            int tempY2 = Integer.parseInt(cmd[4]);
            int r = Integer.parseInt(cmd[5]);
            int gr = Integer.parseInt(cmd[6]);
            int b = Integer.parseInt(cmd[7]);
            g.setColor(new Color(r, gr, b));
            g.fillOval(tempX2, tempY2, 10, 10);
        }else if (command.equals("clear")) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public void setMyCommand(String command){
        myCommand = command;
    }

    public void processCommand(String command) {
        this.command = command;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        x2 = e.getX();
//        y2 = e.getY();
//
//        if (myCommand.equals("line")) {
//            try {
//                client.sendCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                processCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } else if (myCommand.equals("brush")){
//            try {
//                client.sendCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                System.out.println("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                processCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        x1 = e.getX();
//        y1 = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        x1 = e.getX();
//        y1 = e.getY();
//
//        x2 = e.getX();
//        y2 = e.getY();
//
//        if (myCommand.equals("line")) {
//            try {
//                client.sendCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                processCommand("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } else if (myCommand.equals("brush")){
//            try {
//                client.sendCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                System.out.println("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                processCommand("brush " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }


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
