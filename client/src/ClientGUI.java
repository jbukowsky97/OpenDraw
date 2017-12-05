import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {


    JButton hostBtn;

    JButton connectBtn;



    public ClientGUI(){


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,500);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        JPanel buttonPanel = new JPanel();
        DrawPanel draw = new DrawPanel();

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 1;







    }

    public void process(String command) {

    }


    public static void main(String[] args){
        ClientGUI gui = new ClientGUI();
    }





}