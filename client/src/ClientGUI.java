import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientGUI extends JFrame{

    private JButton hostBtn;
    private JButton connectBtn;
    private JButton clearBtn;
    private JButton colorBtn;
    private JButton penBtn;
    private JButton brushBtn;
    private JSlider slider;

    private Color color;

    private DrawPanel drawPanel;

    private Client client;

    private Server server;

    public ClientGUI(){

        color = Color.BLACK;
        client = new Client(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,500);
        this.setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new GridLayout(7, 1));
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        drawPanel = new DrawPanel(client);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = .95;
        c.weighty = 1;
        contentPane.add(drawPanel, c);

        hostBtn = new JButton("Host");
        connectBtn = new JButton("Connect");
        clearBtn = new JButton("Clear");
        colorBtn = new JButton("Choose Color");
        penBtn = new JButton("Pen");
        brushBtn = new JButton("Brush");
        slider = new JSlider();



        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        buttonPanel.add(hostBtn, gbc);
        gbc.gridy = 1;
        buttonPanel.add(connectBtn, gbc);
        gbc.gridy = 2;
        buttonPanel.add(clearBtn, gbc);
        gbc.gridy = 3;
        buttonPanel.add(colorBtn, gbc);
        gbc.gridy = 4;
        buttonPanel.add(penBtn, gbc);
        gbc.gridy = 5;
        buttonPanel.add(brushBtn, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 6;
        slider.setSize(100, 20);
        slider.setValue(10);
        slider.setMaximum(100);
        slider.setMinimum(10);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        buttonPanel.add(slider, gbc);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = .05;
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
                try {
                    client.sendCommand("clear");
                    drawPanel.processCommand("clear");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        colorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = JColorChooser.showDialog(null, "Choose a color", color);
                drawPanel.updateColor(color);
            }
        });

        penBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.setMyCommand("line");

            }
        });
        brushBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.setMyCommand("brush");

            }
        });

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DrawPanel.setBrushSize(slider.getValue());
            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Notify parent of close attempt
                try {
                    client.sendCommand("quit");
                    if (server != null) {
                        server.quit();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowOpened(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowIconified(WindowEvent e) {
                // Do nothing
            }
        });


        this.add(contentPane);

        this.setLocationRelativeTo(null);
        this.setVisible(true);






    }

    public void process(String command) {
        if (command.equals("quit")) {
            System.exit(0);
        }
        drawPanel.processCommand(command);
    }


    public static void main(String[] args){
        ClientGUI gui = new ClientGUI();
    }
}