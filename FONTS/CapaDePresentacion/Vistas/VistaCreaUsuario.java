package FONTS.CapaDePresentacion.Vistas;

import FONTS.CapaDePresentacion.Controladores.CtrlCreaUsuario;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class VistaCreaUsuario {
    private JFrame frame = new JFrame();
    private JPanel norhtPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel= new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel usernameLabel = new JLabel();
    private JLabel passwordLabel = new JLabel();
    private JTextField usernametextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton singUpButton = new JButton();
    private JLabel tituloLabel = new JLabel();
    private JButton cancelButton = new JButton();
    private JPanel cancelButtonPanel = new JPanel();
    private JPanel panelRellenaEspacio = new JPanel();
    private CtrlCreaUsuario CCU = new CtrlCreaUsuario();
    private GridBagConstraints GBC = new GridBagConstraints();
    private miMouseListener mouseListener = new miMouseListener();
    private miActionListener actionListener = new miActionListener();
    private miKeyListener keyListener = new miKeyListener();


    public void Inicializar_Componentes() {
        //-----------------TituloLabel---------------------
        tituloLabel.setText("FilmFIB");
        tituloLabel.setForeground(new Color(0xD47210));
        tituloLabel.setFont(new Font("Dubai", Font.BOLD, 70));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setVerticalAlignment(JLabel.TOP);

        //-----------------Creo frame principal---------------------
        frame.setTitle("FilmFIB");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("../../FONTS/CapaDePresentacion/assets/Logo.PNG");
        frame.setIconImage(icon.getImage());
        frame.setSize(720,500);
        frame.setMinimumSize(new Dimension(720,500));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //-----------------North panel---------------------
        norhtPanel.setBackground(Color.darkGray);
        norhtPanel.setPreferredSize(new Dimension(100,100));
        norhtPanel.add(tituloLabel);

        //-----------------South panel---------------------
        southPanel.setBackground(Color.darkGray);
        southPanel.setPreferredSize(new Dimension(100,100));
        southPanel.setLayout(new GridLayout(3,1));

        //-----------------eastPanel---------------------
        eastPanel.setBackground(Color.darkGray);
        eastPanel.setPreferredSize(new Dimension(50,100));


        //-----------------westPanel---------------------
        westPanel.setBackground(Color.darkGray);
        westPanel.setPreferredSize(new Dimension(50,100));

        //-----------------centerPanel---------------------
        centerPanel.setBackground(Color.darkGray);
        //centerPanel.setPreferredSize(new Dimension(400,100));
        centerPanel.setLayout(new GridBagLayout());

        //-----------------usernameLabel---------------------
        usernameLabel.setText("Enter your new username:");
        usernameLabel.setForeground(new Color(0xE0E0E0));
        usernameLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        GBC.gridx = 0;
        GBC.gridy = 0;
        centerPanel.add(usernameLabel,GBC);

        //-----------------passwordLabel---------------------
        passwordLabel.setText("Enter your new password:");
        passwordLabel.setForeground(new Color(0xE0E0E0));
        passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        GBC.gridx = 0;
        GBC.gridy = 2;
        GBC.insets = new Insets(20,0,0,0);
        centerPanel.add(passwordLabel,GBC);

        //-----------------usernametextField---------------------
        usernametextField.setHorizontalAlignment(JTextField.CENTER);
        usernametextField.setPreferredSize(new Dimension(120,20));
        usernametextField.setFont(new Font("Arial",Font.PLAIN,12));
        GBC.gridx = 0;
        GBC.gridy = 1;
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.insets = new Insets(0,0,0,0);
        centerPanel.add(usernametextField,GBC);
        usernametextField.addKeyListener(keyListener);

        //-----------------PasswordTextField---------------------
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setPreferredSize(new Dimension(120,20));
        passwordField.setFont(new Font("Arial",Font.PLAIN,12));
        GBC.gridx = 0;
        GBC.gridy = 3;
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.insets = new Insets(0,0,0,0);
        centerPanel.add(passwordField,GBC);
        passwordField.addKeyListener(keyListener);

        //-----------------RegisterButton---------------------
        singUpButton.setText("Register");
        singUpButton.setForeground(Color.BLACK);
        singUpButton.setBackground(new Color(0xE0E0E0));
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        singUpButton.setBorder(compound);
        singUpButton.setFocusable(false);
        singUpButton.addActionListener(actionListener);
        singUpButton.addMouseListener(mouseListener);
        GBC.gridx = 0;
        GBC.gridy = 4;
        GBC.insets = new Insets(10,0,0,0);
        GBC.anchor = GridBagConstraints.PAGE_END;
        GBC.fill = GridBagConstraints.NONE;
        centerPanel.add(singUpButton,GBC);

        //-----------------cancelButton--------------------
        cancelButton.setText("Cancel");
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        cancelButton.setBorder(compound);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(actionListener);
        cancelButton.addMouseListener(mouseListener);

        //-----------------cancelButtonPanel--------------------
        cancelButtonPanel.setBackground(Color.darkGray);
        cancelButtonPanel.add(cancelButton);

        panelRellenaEspacio.setBackground(Color.darkGray);
        southPanel.add(panelRellenaEspacio);
        southPanel.add(cancelButtonPanel);

        //-----------------Ultimos ajustes al frame---------------------
        frame.setVisible(true);
        frame.add(norhtPanel, BorderLayout.NORTH);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(westPanel, BorderLayout.WEST);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);
    }

    VistaCreaUsuario() {
        Inicializar_Componentes();
    }

    private class miActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == singUpButton) {
                String username = usernametextField.getText(), password = passwordField.getText();
                int res = CCU.signup(username, password);
                if(res == 0) {
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(res == 1){
                    JOptionPane.showMessageDialog(null,"Username already exists","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else if(res == 2){
                    JOptionPane.showMessageDialog(null,"User or Password is incorrect","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (e.getSource() == cancelButton) {
                frame.dispose();
                VistaMenuInicial menuInicial = new VistaMenuInicial();
            }
        }
    }

    private class miMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() == singUpButton) {
                singUpButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == cancelButton) {
                cancelButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() == singUpButton) {
                singUpButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == cancelButton) {
                cancelButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private class miKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == 10) {
                String username = usernametextField.getText(), password = passwordField.getText();
                int res = CCU.signup(username, password);
                if(res == 0) {
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(res == 1){
                    JOptionPane.showMessageDialog(null,"Username already exists","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else if(res == 2){
                    JOptionPane.showMessageDialog(null,"User cannot be added","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                else if(res == 3){
                    JOptionPane.showMessageDialog(null,"User or Password is incorrect","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
