package FONTS.CapaDePresentacion.Vistas;

import FONTS.CapaDePresentacion.Controladores.CtrlEditarPerfil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class VistaEditarPerfil {
    private JFrame frame = new JFrame();
    private JPanel NorthPanel = new JPanel();
    private JPanel SouthPanel = new JPanel();
    private JPanel WestPanel = new JPanel();
    private JPanel EastPanel = new JPanel();
    private JPanel CenterPanel = new JPanel();
    private JButton HomeButton = new JButton();
    private JPanel HomeButtonPanel = new JPanel();
    private JLabel UsernameLabel = new JLabel();
    private JLabel PasswordLabel = new JLabel();
    private JTextField UsernameTextField = new JTextField();
    private JTextField PasswordField = new JTextField();
    private JButton SaveBUtton = new JButton();
    private JLabel EditProfileLabel = new JLabel();
    private GridBagConstraints GBC = new GridBagConstraints();
    private JButton CancelBUtton = new JButton();
    CtrlEditarPerfil CEP = new CtrlEditarPerfil();
    private miActionListener ActionListener = new miActionListener();
    private miMouseListener MouseListener = new miMouseListener();
    private miWindowListener WindowListener = new miWindowListener();

    public void Inicializar_Componentes() {
        frame.setTitle("FilmFIB");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("../../FONTS/CapaDePresentacion/assets/Logo.PNG");
        frame.setIconImage(icon.getImage());
        frame.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        frame.setMinimumSize(new Dimension(720,500));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //-----------------HomeButton--------------------
        HomeButton.setText("FilmFIB");
        HomeButton.setFont(new Font("Dubai", Font.BOLD, 50));
        HomeButton.setForeground(new Color(0xD47210));
        HomeButton.setBackground(Color.darkGray);
        Border line = new LineBorder(Color.darkGray);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        HomeButton.setBorder(compound);
        HomeButton.setFocusable(false);
        HomeButton.addActionListener(ActionListener);
        HomeButton.setAlignmentX(JButton.WEST);
        HomeButton.setPreferredSize(new Dimension(200,50));
        HomeButton.addMouseListener(MouseListener);

        //-----------------North panel---------------------
        NorthPanel.setBackground(Color.darkGray);
        NorthPanel.setPreferredSize(new Dimension(100,100));
        NorthPanel.setLayout(new BorderLayout());

        //-----------------HomeButton panel---------------------
        HomeButtonPanel .setBackground(Color.darkGray);
        HomeButtonPanel.add(HomeButton);

        //-----------------South panel---------------------
        SouthPanel.setBackground(Color.darkGray);
        SouthPanel.setPreferredSize(new Dimension(100,100));

        //-----------------EastPanel---------------------
        EastPanel.setBackground(Color.darkGray);
        EastPanel.setPreferredSize(new Dimension(50,100));

        //-----------------WestPanel---------------------
        WestPanel.setBackground(Color.darkGray);
        WestPanel.setPreferredSize(new Dimension(50,100));

        //-----------------CenterPanel---------------------
        CenterPanel.setBackground(Color.darkGray);
        CenterPanel.setLayout(new GridBagLayout());

        //-----------------EditProfileLabel---------------------
        EditProfileLabel.setText("Edit Profile:");
        EditProfileLabel.setForeground(new Color(0xE0E0E0));
        EditProfileLabel.setFont(new Font("Dubai", Font.BOLD, 75));
        EditProfileLabel.setPreferredSize(new Dimension(400,60));
        EditProfileLabel.setBackground(Color.darkGray);
        EditProfileLabel.setHorizontalTextPosition(0);
        EditProfileLabel.setOpaque(true);
        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.anchor = GridBagConstraints.PAGE_END;
        GBC.weighty = 1.0;
        GBC.weightx = 1.0;
        GBC.gridwidth = 2;
        CenterPanel.add(EditProfileLabel, GBC);

        //-----------------UsernameLabel---------------------
        UsernameLabel.setText("Enter your new username:");
        UsernameLabel.setForeground(new Color(0xE0E0E0));
        UsernameLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        UsernameLabel.setBackground(Color.darkGray);
        UsernameLabel.setOpaque(true);
        UsernameLabel.setPreferredSize(new Dimension(220,20));
        GBC.gridwidth = 1;
        GBC.anchor = GridBagConstraints.LINE_END;
        GBC.gridx = 0;
        GBC.gridy = 1;
        CenterPanel.add(UsernameLabel, GBC);

        //-----------------PasswordLabel---------------------
        PasswordLabel.setText("Enter your new password:");
        PasswordLabel.setForeground(new Color(0xE0E0E0));
        PasswordLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        PasswordLabel.setPreferredSize(new Dimension(220,20));
        PasswordLabel.setBackground(Color.darkGray);
        PasswordLabel.setOpaque(true);
        GBC.anchor = GridBagConstraints.LINE_END;
        GBC.gridx = 0;
        GBC.gridy = 2;
        CenterPanel.add(PasswordLabel, GBC);

        //-----------------UsernameTextField---------------------
        UsernameTextField.setPreferredSize(new Dimension(180,30));
        UsernameTextField.setFont(new Font("Arial",Font.PLAIN,25));
        UsernameTextField.setForeground(Color.gray);
        String[] ActiveUser = CEP.getActiveUser();
        UsernameTextField.setText(ActiveUser[1]);
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.gridx = 1;
        GBC.gridy = 1;
        CenterPanel.add(UsernameTextField, GBC);

        //-----------------PasswordField---------------------
        PasswordField.setPreferredSize(new Dimension(180,30));
        PasswordField.setFont(new Font("Arial",Font.PLAIN,25));
        PasswordField.setForeground(Color.gray);
        PasswordField.setText(ActiveUser[2]);
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.gridx = 1;
        GBC.gridy = 2;
        CenterPanel.add(PasswordField, GBC);

        //-----------------SaveButton---------------------
        SaveBUtton.setText("Save Changes and Exit to Home");
        SaveBUtton.setForeground(Color.BLACK);
        SaveBUtton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        SaveBUtton.setBorder(compound);
        SaveBUtton.setFocusable(false);
        SaveBUtton.addActionListener(ActionListener);
        SaveBUtton.addMouseListener(MouseListener);
        GBC.insets = new Insets(0,0,0,30);
        GBC.anchor = GridBagConstraints.LINE_END;
        GBC.gridx = 0;
        GBC.gridy = 3;
        GBC.fill = GridBagConstraints.NONE;
        CenterPanel.add(SaveBUtton, GBC);

        //-----------------CancelButton---------------------
        CancelBUtton.setText("Cancel");
        CancelBUtton.setForeground(Color.BLACK);
        CancelBUtton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        CancelBUtton.setBorder(compound);
        CancelBUtton.setFocusable(false);
        CancelBUtton.addActionListener(ActionListener);
        CancelBUtton.addMouseListener(MouseListener);
        GBC.insets = new Insets(0,30,0,0);
        GBC.anchor = GridBagConstraints.LINE_START;
        GBC.gridx = 1;
        GBC.gridy = 3;
        CenterPanel.add(CancelBUtton, GBC);

        //----------------Añado itemos a los paneles---------------------
        NorthPanel.add(HomeButtonPanel, BorderLayout.WEST);

        //----------------Ultimos ajustes al frame---------------------
        frame.add(NorthPanel, BorderLayout.NORTH);
        frame.add(SouthPanel, BorderLayout.SOUTH);
        frame.add(WestPanel, BorderLayout.WEST);
        frame.add(EastPanel, BorderLayout.EAST);
        frame.add(CenterPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.addWindowListener(WindowListener);
    }

    public VistaEditarPerfil () {
        Inicializar_Componentes();
    }
    private class miActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == CancelBUtton) {
                int res = JOptionPane.showConfirmDialog(null,"You're about to exit, do you want to keep going?","Exiting",JOptionPane.YES_NO_OPTION);
                if(res == 0) {
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else if(e.getSource() == SaveBUtton) {
                //Retorna 0 si todo bien
                //Retorna 1 si el username o el password están vacios
                //Retorna 2 si no hay cambios
                int res = CEP.saveChanges(UsernameTextField.getText(), PasswordField.getText());
                if (res == 0) {
                    JOptionPane.showMessageDialog(null, "Your changes have been updated!", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(res == 1){
                    JOptionPane.showMessageDialog(null, "Username or password is empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if(res == 2) {
                    frame.dispose();
                    JOptionPane.showMessageDialog(null, "No changes detected", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else if(e.getSource() == HomeButton) {
                int res = JOptionPane.showConfirmDialog(null,"You're about to exit, changes made won't save, do you want to keep going?","Exiting",JOptionPane.YES_NO_OPTION);
                if(res == 0) {
                    frame.dispose();
                    try {
                        VistaHome home = new VistaHome();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
            if(e.getSource() == SaveBUtton) {
                SaveBUtton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));

            }
            else if(e.getSource() == CancelBUtton) {
                CancelBUtton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == HomeButton) {
                HomeButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() == SaveBUtton) {
                SaveBUtton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == CancelBUtton) {
                CancelBUtton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == HomeButton) {
                HomeButton.setBackground(Color.darkGray);
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private class miWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            int res2 = JOptionPane.showConfirmDialog(null,"You're about to exit, changes made won't save, do you want to keep going?","Exiting",JOptionPane.YES_NO_OPTION);
            if(res2 == 0) {
                frame.dispose();
                try {
                    VistaHome home = new VistaHome();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}
