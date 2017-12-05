import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ClientGUI extends JFrame{

    private JButton hostBtn;
    private JButton connectBtn;
    private JButton clearBtn;
    private JButton colorBtn;

    private Color color;

    private DrawPanel drawPanel;

    private Client client;

    private Server server;

    public ClientGUI(){

        color = Color.BLACK;
        client = new Client(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000,500);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        drawPanel = new DrawPanel(client);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = .8;
        c.weighty = 1;
        contentPane.add(drawPanel, c);

        hostBtn = new JButton("Host");
        connectBtn = new JButton("Connect");
        clearBtn = new JButton("Clear");
        colorBtn = new JButton("Choose Color");

        buttonPanel.add(hostBtn);
        buttonPanel.add(connectBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(colorBtn);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = .2;
        c.weighty = 1;
        contentPane.add(buttonPanel, c);

        hostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //create text fields
                //JTextField ipText = new JTextField();
                JTextField portText = new JTextField();

                //create message for optionpane
                Object[] message = {"Server Port: ", portText
                };

                //open optionpane and set result to n
                int n = JOptionPane.showConfirmDialog(null,
                        message, "Create Server",
                        JOptionPane.OK_CANCEL_OPTION);

                //create serverInfo object if OK is pressed
                if (n == JOptionPane.OK_OPTION) {
                    //String ip = ipText.getText();
                    int port = Integer.parseInt(portText.getText());
                    server = new Server(port);
                    server.start();
                }
            }
        });

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //create text fields
                JTextField ipText = new JTextField();
                JTextField portText = new JTextField();

                //create message for optionpane
                Object[] message = {"Server ip: ", ipText,
                        "Server Port: ", portText
                };

                //open optionpane and set result to n
                int n = JOptionPane.showConfirmDialog(null,
                        message, "Join Server",
                        JOptionPane.OK_CANCEL_OPTION);

                //create serverInfo object if OK is pressed
                if (n == JOptionPane.OK_OPTION) {
                    String ip = ipText.getText();
                    int port = Integer.parseInt(portText.getText());

                    try {
                        client.connect(ip, port);
                        connectBtn.setEnabled(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        colorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = JColorChooser.showDialog(null, "Choose a color", color);
                drawPanel.updateColor(color);
            }
        });


        this.add(contentPane);
        this.setVisible(true);






    }

    public void process(String command) {
        drawPanel.processCommand(command);
    }


    public static void main(String[] args){
        ClientGUI gui = new ClientGUI();
    }
}