import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

    private int x1,x2,y1,y2;

    private Client client;
    private Color color;

    private String command;

    private boolean first;

    private Graphics2D graphics2D;

    private java.util.List<String> commands;

    private javax.swing.Timer timer;

    private String myCommand;

    private static int brushSize;


    public DrawPanel(Client client){

        this.color = Color.BLACK;
        this.client = client;
        this.brushSize = 10;

        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.setVisible(true);

        first = true;

        myCommand = "line";

        commands = Collections.synchronizedList(new LinkedList<String>());

        timer = new javax.swing.Timer(5, this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        if (first) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            first = false;
        }

        if (commands.size() == 0) {
            return;
        }
        command = commands.get(0);
        commands.remove(0);
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
        }else if (command.startsWith("brush")) {
            String[] cmd = command.split(" ");
            int tempX1 = Integer.parseInt(cmd[1]);
            int tempY1 = Integer.parseInt(cmd[2]);
            int tempX2 = Integer.parseInt(cmd[3]);
            int tempY2 = Integer.parseInt(cmd[4]);
            int r = Integer.parseInt(cmd[5]);
            int gr = Integer.parseInt(cmd[6]);
            int b = Integer.parseInt(cmd[7]);
            int bSize = Integer.parseInt(cmd[8]);
            g.setColor(new Color(r, gr, b));
            g.fillOval(tempX1, tempY1, bSize, bSize);
            g.fillOval(tempX2, tempY2, bSize, bSize);
        }else if (command.equals("clear")) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    public static void setBrushSize(int size){
        brushSize = size;
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public void processCommand(String command) {
        //this.command = command;
//        System.out.println("adding command");
        commands.add(command);
        //repaint();
    }

    public void setMyCommand(String command){
        myCommand = command;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();

        try {
            client.sendCommand(myCommand + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + brushSize);
            //System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            processCommand(myCommand + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + brushSize);
        } catch (IOException e1) {
            //e1.printStackTrace();
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


        try {
            client.sendCommand(myCommand + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + brushSize);
            //System.out.println("line " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            processCommand(myCommand + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + brushSize);
        } catch (IOException e1) {
            //e1.printStackTrace();
        }

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}
