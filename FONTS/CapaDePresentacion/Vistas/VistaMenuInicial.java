package FONTS.CapaDePresentacion.Vistas;

import FONTS.CapaDePresentacion.Controladores.CtrlMenuInicial;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class VistaMenuInicial {
    private CtrlMenuInicial CMI = new CtrlMenuInicial();
    private JFrame frame = new JFrame();
    private JLabel titulo = new JLabel();
    private JLabel usernameLabel = new JLabel();
    private JLabel passwordLabel = new JLabel();
    private JTextField usernameTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel= new JPanel();
    private JPanel centerPanel = new JPanel();
    private JButton logInBUtton = new JButton();
    private JButton signUpButton = new JButton();
    private JLabel signUpLabel = new JLabel();
    private JPanel signUpPanel= new JPanel();
    private GridBagConstraints GBC = new GridBagConstraints();
    private miActionListener actionListener = new miActionListener();
    private miMouseListener mouseListener = new miMouseListener();
    private miKeyListener KeyListener = new miKeyListener();
    private JButton importButton = new JButton();
    private JLabel rellenaEspacioLabel = new JLabel();
    private JFileChooser fileChooser = new JFileChooser();
    private JMenu Gestores = new JMenu("Gestores");
    private JMenuItem Gestor;
    private ArrayList<JMenuItem> gestores_list = new ArrayList<JMenuItem>();
    private JMenuBar barraMenu = new JMenuBar();


    public void inicializarComponentes() {
        //-----------------frame---------------------
        frame.setTitle("FilmFIB");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("../../FONTS/CapaDePresentacion/assets/Logo.PNG");
        frame.setIconImage(icon.getImage());
        frame.setSize(720,500);
        frame.setMinimumSize(new Dimension(720,500));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //-----------------Label titulo---------------------
        titulo.setText("FilmFIB");
        titulo.setForeground(new Color(0xD47210));
        titulo.setFont(new Font("Dubai", Font.BOLD, 70));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.TOP);

        //-----------------North panel---------------------
        northPanel.setBackground(Color.darkGray);
        northPanel.setPreferredSize(new Dimension(100,100));
        northPanel.add(titulo);

        //-----------------Label Sign Up ---------------------
        signUpLabel.setText("You can sign up if you don't have an account already:");
        signUpLabel.setForeground(new Color(0xE0E0E0));
        signUpLabel.setHorizontalAlignment(JLabel.CENTER);
        signUpLabel.setVerticalAlignment(JLabel.BOTTOM);

        //-----------------SignUpButton---------------------
        signUpButton.setText("Sign Up");
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setBackground(new Color(0xE0E0E0));
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        signUpButton.setBorder(compound);
        signUpButton.setFocusable(false);
        signUpButton.addActionListener(actionListener);
        signUpButton.addMouseListener(mouseListener);

        //-----------------South panel---------------------
        southPanel.setBackground(Color.darkGray);
        southPanel.setPreferredSize(new Dimension(100,100));
        southPanel.setLayout(new BorderLayout());
        //-----------------Register Panel---------------------
        southPanel.add(signUpPanel, BorderLayout.SOUTH);
        signUpPanel.setBackground(Color.darkGray);
        signUpPanel.add(signUpLabel);
        signUpPanel.add(signUpButton);

        //-----------------eastPanel---------------------
        eastPanel.setBackground(Color.darkGray);
        eastPanel.setPreferredSize(new Dimension(50,100));

        //-----------------westPanel---------------------
        westPanel.setBackground(Color.darkGray);
        westPanel.setPreferredSize(new Dimension(50,100));

        //-----------------centerPanel---------------------
        centerPanel.setBackground(Color.darkGray );
        centerPanel.setPreferredSize(new Dimension(200,100));
        centerPanel.setLayout(new GridBagLayout());

        //-----------------UsernameLabel---------------------
        usernameLabel.setText("Username:");
        usernameLabel.setForeground(new Color(0xE0E0E0));
        usernameLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        GBC.weighty = 0;
        GBC.weightx = 0;
        GBC.anchor = GridBagConstraints.NORTHWEST;
        GBC.gridx = 0;
        GBC.gridy = 0;
        centerPanel.add(usernameLabel,GBC);

        //-----------------PasswordLabel---------------------
        passwordLabel.setText("Password:");
        passwordLabel.setForeground(new Color(0xE0E0E0));
        passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.gridx = 0;
        GBC.gridy = 2;
        GBC.insets = new Insets(10,0,0,0);
        centerPanel.add(passwordLabel,GBC);

        //-----------------UsernameTextField---------------------
        usernameTextField.setFont(new Font("Arial",Font.PLAIN,12));
        usernameTextField.addKeyListener(KeyListener);
        GBC.gridx = 0;
        GBC.gridy = 1;
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.insets = new Insets(0,0,0,0);
        centerPanel.add(usernameTextField,GBC);

        //-----------------PasswordTextField---------------------
        passwordField.setPreferredSize(new Dimension(120,20));
        passwordField.setFont(new Font("Arial",Font.PLAIN,12));
        passwordField.addKeyListener(KeyListener);
        GBC.gridx = 0;
        GBC.gridy = 3;
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.insets = new Insets(0,0,0,0);
        centerPanel.add(passwordField,GBC);

        //-----------------logInBUtton---------------------
        logInBUtton.setText("Log In");
        logInBUtton.setForeground(Color.BLACK);
        logInBUtton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        logInBUtton.setBorder(compound);
        logInBUtton.setFocusable(false);
        logInBUtton.addActionListener(actionListener);
        logInBUtton.addMouseListener(mouseListener);
        GBC.gridx = 0;
        GBC.gridy = 4;
        GBC.insets = new Insets(10,0,0,0);
        GBC.anchor = GridBagConstraints.PAGE_END;
        GBC.fill = GridBagConstraints.NONE;
        centerPanel.add(logInBUtton,GBC);

        //-----------------rellenaEspacioLabel---------------------
        rellenaEspacioLabel = new JLabel();
        rellenaEspacioLabel.setFont(new Font("Dubai", Font.BOLD, 18));
        rellenaEspacioLabel.setForeground(Color.darkGray);
        rellenaEspacioLabel.setText("Aux");
        GBC.gridy = 5;
        GBC.gridx = 0;
        centerPanel.add(rellenaEspacioLabel,GBC);

        //-----------------importButton---------------------
        importButton.setText("Import");
        importButton.setForeground(Color.BLACK);
        importButton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        importButton.setBorder(compound);
        importButton.setFocusable(false);
        importButton.addActionListener(actionListener);
        importButton.addMouseListener(mouseListener);
        GBC.gridx = 0;
        GBC.gridy = 6;
        GBC.insets = new Insets(10,0,0,0);
        GBC.anchor = GridBagConstraints.PAGE_END;
        GBC.fill = GridBagConstraints.NONE;
        centerPanel.add(importButton,GBC);

        //----------------Menu Gestores---------------------
        barraMenu.setAlignmentX(JMenuBar.RIGHT_ALIGNMENT);
        barraMenu.setForeground(new Color(0xE0E0E0));
        barraMenu.add(Gestores);
        barraMenu.setBackground(Color.darkGray);

        Gestores.setAlignmentX(JMenu.EAST);
        Gestores.setBackground(Color.darkGray);
        Gestores.setForeground(new Color(0xE0E0E0));
        Gestores.addMouseListener(mouseListener);

        for(int k = 0; k < Integer.parseInt(CMI.getTotalGestor()); ++k) {
            Gestor = new JMenuItem("Tipo Datos " + (k + 1));
            Gestor.setBackground(Color.darkGray);
            Gestor.setForeground(new Color(0xE0E0E0));
            gestores_list.add(Gestor);
            gestores_list.get(k).addActionListener(actionListener);
            Gestores.add(gestores_list.get(k));
        }

        //-----------------UltimosAjustesFrame---------------------
        frame.setVisible(true);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(westPanel, BorderLayout.WEST);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setJMenuBar(barraMenu);
    }

    public VistaMenuInicial() {
        inicializarComponentes();
    }

    private class miActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == logInBUtton) {
                if(Integer.parseInt(CMI.getCurrentGestor()) != -1) {
                    String username = usernameTextField.getText(), password = passwordField.getText();
                    int res = CMI.login(username, password);
                    if (res == 0) {
                        frame.dispose();
                        try {
                            VistaHome home = new VistaHome();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else if (res == 1) {
                        JOptionPane.showMessageDialog(null,"Password incorrect!","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                    else if (res == 2) {
                        JOptionPane.showMessageDialog(null,"Username not registered!","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                    else if (res == 3) {
                        JOptionPane.showMessageDialog(null,"Username or password are empty!","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"You must import before logging in!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == signUpButton) {
                if(Integer.parseInt(CMI.getCurrentGestor()) != -1) {
                    frame.dispose();
                    VistaCreaUsuario CreaUsuario = new VistaCreaUsuario();
                }
                else {
                    JOptionPane.showMessageDialog(null,"You must import before singning Up!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == importButton) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    Object [] ratings = {"Ratings among 5", "Ratings among 10"};
                    Object opcion = JOptionPane.showInputDialog(null,"Select rating values:", "Select Ratings",JOptionPane.QUESTION_MESSAGE,null,ratings, ratings[0]);
                    int multiplier = 0;
                    if(opcion.toString().equalsIgnoreCase("Ratings among 5")) {
                        multiplier = 2;
                    }
                    else {
                        multiplier = 1;
                    }
                    String pathFolder = fileChooser.getSelectedFile().getAbsolutePath();
                    int res = 0;
                    try {
                        res = CMI.importFiles(pathFolder, multiplier);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (res == 0) {
                        JOptionPane.showMessageDialog(null, "Items have been added correctly!!", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        VistaMenuInicial MenuInicial = new VistaMenuInicial();
                    }
                    else if (res == 1) {
                        JOptionPane.showMessageDialog(null, "Los archivos ratings.db.csv, items.db.csv, ratings.test.known.csv o ratings.test.unknown.csv no existen en el directorio!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (res == 2) {
                        JOptionPane.showMessageDialog(null, "Los archivos ratings.db.csv, items.db.csv, ratings.test.known.csv o ratings.test.unknown.csv no tienen el formato correcto!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else if(!gestores_list.isEmpty()) {
                for(int i = 0; i < gestores_list.size(); ++i) {
                    if(e.getSource() == gestores_list.get(i)) {
                        CMI.swapCurrentGestor(String.valueOf(i));
                    }
                }
            }
        }
    }

    private class miMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() == logInBUtton) {
                logInBUtton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == signUpButton) {
                signUpButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == importButton) {
                importButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() == logInBUtton) {
                logInBUtton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == signUpButton) {
                signUpButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == importButton) {
                importButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private class miKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == 10) {
                CtrlMenuInicial CMI = new CtrlMenuInicial();
                String username = usernameTextField.getText(), password = passwordField.getText();
                int res = CMI.login(username, password);
                if (res == 0) {
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if (res == 1) {
                    JOptionPane.showMessageDialog(null,"Password incorrect!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else if (res == 2) {
                    JOptionPane.showMessageDialog(null,"Username not registered!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else if (res == 3) {
                    JOptionPane.showMessageDialog(null,"Username or password are empty!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
