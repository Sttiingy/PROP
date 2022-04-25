package FONTS.CapaDePresentacion.Vistas;

import FONTS.CapaDePresentacion.Controladores.CtrlPerfilItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class VistaPerfilItem {
    private CtrlPerfilItem CPI = new CtrlPerfilItem();
    private JFrame frame = new JFrame();
    private JButton homeButton = new JButton();
    private JPanel NorthPanel = new JPanel();
    private JPanel SouthPanel = new JPanel();
    private JPanel EastPanel = new JPanel();
    private JPanel WestPanel= new JPanel();
    private JPanel CenterPanel = new JPanel();
    private JPanel homeButtonPanel = new JPanel();
    private miActionListener actionListener = new miActionListener();
    private miMouseListener mouseListener = new miMouseListener();
    private JMenuBar barraMenu = new JMenuBar();
    private JMenu options = new JMenu("Options");
    private JMenuItem logOutMenuItem = new JMenuItem("Log Out");
    private JMenuItem editarPerfilMenuItem = new JMenuItem("Edit profile");
    private JMenuItem exportMenuItem = new JMenuItem("Export");
    private JFileChooser fileChooser = new JFileChooser();
    private GridBagConstraints GBC = new GridBagConstraints();
    private JLabel imageLabel = new JLabel();
    private JTextField myScoreTextField = new JTextField();
    private JButton rateButton = new JButton();
    private JLabel rellenaEspacioLabel = new JLabel();
    private JScrollPane scrollPaneTexto;
    private JScrollPane scrollPaneCentral;
    private JScrollPane scrollPaneLateral;
    private String[] item_info;
    private ArrayList<JButton> items_list = new ArrayList<JButton>();
    private JButton itemButton;
    private JLabel atributeName;
    private JLabel atributeBody;
    private JLabel myScoreLabel = new JLabel("MY SCORE: ");
    private Vector<String[]> similarItems;
    private JButton deleteRatingButton = new JButton("Delete rating");
    private JMenuItem misItemsMenuItem = new JMenuItem("My rated items");

    public String str2html (String text){
        return "<html><p>" + text + "</p></html>";
    }

    public JButton display_Item(String[] datos) throws IOException {
        itemButton = new JButton();
        String title = datos[0];
        URL url = new URL("https://aprende-a-programar.com/img/noImage.jpg");
        Image image = ImageIO.read(url);
        boolean found = false;
        for(int i = 0; i < datos.length && !found; ++i) {
            String[] atributo = datos[i].split(";;;");
            if(atributo[0].equals("img_url")) {
                url = new URL(atributo[2]);
                image = ImageIO.read(url);
                found = true;
            }
        }
        if(title != null) itemButton.setText(str2html(title));
        if(image != null )itemButton.setIcon(new ImageIcon(image));
        Border line = new LineBorder(Color.darkGray);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        itemButton.setBorder(compound);
        itemButton.setFocusable(false);
        itemButton.setBackground(Color.darkGray);
        itemButton.setForeground(new Color(0xE0E0E0));
        itemButton.setHorizontalTextPosition(JLabel.CENTER);
        itemButton.setVerticalTextPosition(JLabel.BOTTOM);
        itemButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        itemButton.setPreferredSize(new Dimension(230,440));
        return itemButton;
    }

    public void Inicializar_Componentes(String[] item) throws IOException {
        item_info = item;
        //-----------------Creo frame principal---------------------
        frame.setTitle("FilmFIB");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("../../FONTS/CapaDePresentacion/assets/Logo.PNG");
        frame.setIconImage(icon.getImage());
        frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        frame.setMinimumSize(new Dimension(1400, 900));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //-----------------homeButton--------------------
        homeButton.setText("FilmFIB");
        homeButton.setFont(new Font("Dubai", Font.BOLD, 50));
        homeButton.setForeground(new Color(0xD47210));
        homeButton.setBackground(Color.darkGray);
        Border line = new LineBorder(Color.darkGray);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        homeButton.setBorder(compound);
        homeButton.setFocusable(false);
        homeButton.addActionListener(actionListener);
        homeButton.setAlignmentX(JButton.WEST);
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.addMouseListener(mouseListener);

        //-----------------North panel---------------------
        NorthPanel.setBackground(Color.darkGray);
        NorthPanel.setPreferredSize(new Dimension(100, 100));
        NorthPanel.setLayout(new BorderLayout());

        //-----------------homeButton panel---------------------
        homeButtonPanel.setBackground(Color.darkGray);
        homeButtonPanel.add(homeButton);

        //-----------------South panel---------------------
        SouthPanel.setBackground(Color.darkGray);
        SouthPanel.setPreferredSize(new Dimension(100, 20));

        //-----------------EastPanel---------------------
        EastPanel.setBackground(Color.darkGray);
        similarItems = CPI.getKNNItems(item_info[0], 10);
        for (int i = 0; i < similarItems.size(); ++i) {
            String[] item_info = similarItems.get(i);
            items_list.add(display_Item(item_info));
            items_list.get(i).addActionListener(actionListener);
            items_list.get(i).addMouseListener(mouseListener);
            EastPanel.add(items_list.get(i));
        }
        EastPanel.setPreferredSize(new Dimension(300, items_list.size() * 445));
        scrollPaneLateral = new JScrollPane(EastPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneLateral.getVerticalScrollBar().setUnitIncrement(16);
        line = new LineBorder(Color.darkGray);
        margin = new EmptyBorder(0, 0, 0, 0);
        compound = new CompoundBorder(line, margin);
        scrollPaneLateral.getVerticalScrollBar().setBorder(compound);
        scrollPaneLateral.getVerticalScrollBar().setBackground(Color.darkGray);
        line = new LineBorder(Color.darkGray);
        margin = new EmptyBorder(0, 0, 0, 0);
        compound = new CompoundBorder(line, margin);
        scrollPaneLateral.setBorder(compound);


        //-----------------WestPanel---------------------
        WestPanel.setBackground(Color.darkGray);
        WestPanel.setPreferredSize(new Dimension(100, 100));

        //-----------------CenterPanel---------------------
        CenterPanel.setBackground(Color.darkGray);
        CenterPanel.setPreferredSize(new Dimension(100, item_info.length * 50 + 100));
        CenterPanel.setLayout(new GridBagLayout());

        //-----------------LabelImagen---------------------
        Image image = null;
        URL url = null;
        boolean found = false;
        for(int i = 0; i < item_info.length && !found; ++i) {
            String[] atributo = item_info[i].split(";;;");
            if(atributo[0].equals("img_url")) {
                url = new URL(atributo[2]);
                image = ImageIO.read(url);
                found = true;
            }
            else if(!found){
                url = new URL("https://aprende-a-programar.com/img/noImage.jpg");
                image = ImageIO.read(url);
            }
        }
        imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setPreferredSize(new Dimension(230,440));
        GBC.gridy = 0;
        GBC.gridx = 0;
        GBC.gridheight = item_info.length * 2;
        GBC.anchor = GridBagConstraints.FIRST_LINE_START;
        CenterPanel.add(imageLabel,GBC);

        //-----------------item_info---------------------
        int position = 0;
        for(int i = 1; i < item_info.length; ++i) {
            String[] atributo = item_info[i].split(";;;");
            boolean equal = true;
            String body = "";
            if(atributo.length > 2 ) body = atributo[2];
            int j;
            for(j = i + 1; equal && j < item_info.length; ++j) {
                String[] next_atributo = item_info[j].split(";;;");
                if(atributo[0].equals(next_atributo[0])) {
                    body = (body + ", " + next_atributo[2]);
                }
                else {
                    equal = false;
                    i = j - 1;
                }
            }
            if(j == item_info.length) i = j - 1;
            if(atributo.length > 2) {
                if(body.length() > 100) {
                    atributeName = new JLabel((atributo[0] + ": ").toUpperCase());
                    atributeBody = new JLabel(str2html(body));
                    atributeName.setForeground(new Color(0xE0E0E0));
                    atributeBody.setForeground(new Color(0xE0E0E0));
                    atributeBody.setBackground(Color.darkGray);
                    atributeBody.setOpaque(true);
                    atributeName.setFont(new Font("Dubai", Font.BOLD, 18));
                    atributeBody.setFont(new Font("Dubai", Font.PLAIN, 16));
                    atributeBody.setPreferredSize(new Dimension(400, atributo[2].length() / 100 * 50));
                    scrollPaneTexto = new JScrollPane(atributeBody, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPaneTexto.setPreferredSize(new Dimension(450,90));
                    scrollPaneTexto.getVerticalScrollBar().setUnitIncrement(16);
                    scrollPaneTexto.getVerticalScrollBar().setBackground(Color.darkGray);
                    line = new LineBorder(Color.darkGray);
                    margin = new EmptyBorder(0, 0, 0, 0);
                    compound = new CompoundBorder(line, margin);
                    scrollPaneTexto.setBorder(compound);
                }
                else {
                    atributeName = new JLabel((atributo[0] + ": ").toUpperCase());
                    atributeBody = new JLabel(body);
                    atributeName.setForeground(new Color(0xE0E0E0));
                    atributeBody.setForeground(new Color(0xE0E0E0));
                    atributeName.setFont(new Font("Dubai", Font.BOLD, 18));
                    atributeBody.setFont(new Font("Dubai", Font.PLAIN, 16));
                }
            }
            GBC.gridheight = 1;
            GBC.gridy = position;
            GBC.gridx = 2;
            GBC.anchor = GridBagConstraints.LINE_START;
            CenterPanel.add(atributeName,GBC);
            GBC.gridy = (position + 1);
            GBC.gridx = 2;
            position += 2;
            if(body.length() > 100) CenterPanel.add(scrollPaneTexto,GBC);
            else CenterPanel.add(atributeBody, GBC);
        }

        //----------------rellenaEspacioLabel---------------------
        rellenaEspacioLabel.setText("XD");
        rellenaEspacioLabel.setForeground(Color.darkGray);
        GBC.gridy = 0;
        GBC.gridx = 1;
        GBC.gridheight = item_info.length * 2;
        CenterPanel.add(rellenaEspacioLabel,GBC);

        //-----------------MyScoreLabel---------------------
        myScoreLabel.setFont(new Font("Dubai", Font.BOLD, 18));
        myScoreLabel.setForeground(new Color(0xE0E0E0));
        GBC.gridheight = 1;
        GBC.gridy = 0;
        GBC.gridx = 3;
        CenterPanel.add(myScoreLabel,GBC);

        //-----------------ScoreTextField---------------------
        myScoreTextField.setPreferredSize(new Dimension(80,20));
        if(CPI.isRated(item_info[0])) {
            myScoreTextField.setText(CPI.getRating(item_info[0]));
        }
        myScoreTextField.setForeground(Color.darkGray);
        GBC.gridheight = 1;
        GBC.gridy = 1;
        GBC.gridx = 3;
        CenterPanel.add(myScoreTextField,GBC);

        //-----------------rateButton---------------------
        rateButton.setText("Rate");
        rateButton.setForeground(Color.BLACK);
        rateButton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        rateButton.setBorder(compound);
        rateButton.addActionListener(actionListener);
        rateButton.addMouseListener(mouseListener);
        rateButton.setFocusable(false);
        GBC.gridy = 1;
        GBC.gridx = 4;
        GBC.insets = new Insets(0,10,0,0);
        CenterPanel.add(rateButton,GBC);

        //-----------------deleteRatingButton---------------------
        deleteRatingButton.setForeground(Color.BLACK);
        deleteRatingButton.setBackground(new Color(0xE0E0E0));
        line = new LineBorder(Color.BLACK);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        deleteRatingButton.setBorder(compound);
        deleteRatingButton.addActionListener(actionListener);
        deleteRatingButton.addMouseListener(mouseListener);
        deleteRatingButton.setFocusable(false);
        GBC.gridy = 2;
        GBC.gridx = 3;
        GBC.gridwidth = 2;
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.insets = new Insets(10,0,0,0);
        CenterPanel.add(deleteRatingButton,GBC);

        //----------------ScrollpaneCentral---------------------
        scrollPaneCentral = new JScrollPane(CenterPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCentral.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneCentral.getVerticalScrollBar().setBackground(Color.darkGray);
        line = new LineBorder(Color.darkGray);
        margin = new EmptyBorder(0, 0, 0, 0);
        compound = new CompoundBorder(line, margin);
        scrollPaneCentral.getVerticalScrollBar().setBorder(compound);
        scrollPaneCentral.getVerticalScrollBar().setBackground(Color.darkGray);
        line = new LineBorder(Color.darkGray);
        margin = new EmptyBorder(0, 0, 0, 0);
        compound = new CompoundBorder(line, margin);
        scrollPaneCentral.setBorder(compound);

        //----------------Añado items a los paneles---------------------
        NorthPanel.add(homeButtonPanel, BorderLayout.WEST);

        //----------------Menu---------------------
        barraMenu.setAlignmentX(JMenuBar.RIGHT_ALIGNMENT);
        barraMenu.setForeground(new Color(0xE0E0E0));
        barraMenu.add(options);
        barraMenu.setBackground(Color.darkGray);
        options.add(editarPerfilMenuItem);
        options.add(logOutMenuItem);
        options.setAlignmentX(JMenu.EAST);
        options.setBackground(Color.darkGray);
        options.setForeground(new Color(0xE0E0E0));
        options.addMouseListener(mouseListener);

        logOutMenuItem.addActionListener(actionListener);
        logOutMenuItem.setBackground(Color.darkGray);
        logOutMenuItem.setForeground(new Color(0xE0E0E0));

        editarPerfilMenuItem.setBackground(Color.darkGray);
        editarPerfilMenuItem.setForeground(new Color(0xE0E0E0));
        editarPerfilMenuItem.addActionListener(actionListener);

        exportMenuItem.setBackground(Color.darkGray);
        exportMenuItem.setForeground(new Color(0xE0E0E0));
        exportMenuItem.addActionListener(actionListener);

        misItemsMenuItem.setBackground(Color.darkGray);
        misItemsMenuItem.setForeground(new Color(0xE0E0E0));
        misItemsMenuItem.addActionListener(actionListener);

        options.add(editarPerfilMenuItem);
        options.add(exportMenuItem);
        options.add(misItemsMenuItem);
        options.add(logOutMenuItem);

        //-----------------UltimosAjustesFrame---------------------
        frame.setJMenuBar(barraMenu);
        frame.add(NorthPanel, BorderLayout.NORTH);
        frame.add(SouthPanel, BorderLayout.SOUTH);
        frame.add(WestPanel, BorderLayout.WEST);
        frame.add(scrollPaneLateral, BorderLayout.EAST);
        frame.add(scrollPaneCentral, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public String Str2html (String text){
        return "<html><p>" + text + "</p></html>";
    }

    public VistaPerfilItem(String[] item) throws IOException {
        Inicializar_Componentes(item);
    }

    private class miActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == logOutMenuItem) {
                int res = JOptionPane.showConfirmDialog(null,"You're about to logout, do you want to export?","Logging out",JOptionPane.YES_NO_OPTION);
                if(res == 0) {
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int response = fileChooser.showSaveDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        String pathFolder = fileChooser.getSelectedFile().getAbsolutePath();
                        int result = CPI.exportFiles(pathFolder);
                        if (result == 0) {
                            JOptionPane.showMessageDialog(null, "Your changes have been saved in the selected directory!", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if (result == 1) {
                            JOptionPane.showMessageDialog(null, "Your items couldn't be saved!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (result == 2) {
                            JOptionPane.showMessageDialog(null, "Files ratings.db.csv or  items.db.csv not found in this directory!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    frame.dispose();
                    VistaMenuInicial menuInicial = new VistaMenuInicial();
                }
                else if(res == 1) {
                    frame.dispose();
                    VistaMenuInicial menuInicial = new VistaMenuInicial();
                }
            }
            else if(e.getSource() == editarPerfilMenuItem) {
                frame.dispose();
                VistaEditarPerfil EditarPerfil = new VistaEditarPerfil();
            }
            else if(e.getSource() == homeButton) {
                frame.dispose();
                try {
                    VistaHome home = new VistaHome();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getSource() == rateButton) {
                int idItem = Integer.parseInt(item_info[0]);
                double score = -1;
                String aux = myScoreTextField.getText();
                if(!aux.equals("")) score = Double.parseDouble(aux);
                int res = CPI.rateItem(idItem,score);
                if(res == 0) {
                    JOptionPane.showMessageDialog(null, "This item has been rated!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(res == 1){
                    JOptionPane.showMessageDialog(null,"Rating value must be between 0 and 10!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == deleteRatingButton) {
                if(CPI.deleteRating(item_info[0])) {
                    JOptionPane.showMessageDialog(null,"Your rating has been removed!","SUCCES",JOptionPane.INFORMATION_MESSAGE);
                    myScoreTextField.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null,"You haven't rated this item!","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(e.getSource() == exportMenuItem) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    String pathFolder = fileChooser.getSelectedFile().getAbsolutePath();
                    int res = CPI.exportFiles(pathFolder);
                    if (res == 0) {
                        JOptionPane.showMessageDialog(null, "Your changes have been saved in the selected directory!", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (res == 1) {
                        JOptionPane.showMessageDialog(null, "Tus items o usuarios no se han podido guardar en la BD!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (res == 2) {
                        JOptionPane.showMessageDialog(null, "Los archivos ratings.db.csv o items.db.csv no existen en el directorio!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else if(e.getSource() == misItemsMenuItem) {
                frame.dispose();
                try {
                    VistaMisItems myItems = new VistaMisItems();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(!items_list.isEmpty()){
                for(int i = 0; i < items_list.size(); ++i) {
                    if(e.getSource() == items_list.get(i)) {
                        items_list.get(i).setBackground(Color.gray);
                        items_list.get(i).setCursor(new Cursor(Cursor.HAND_CURSOR));
                        String[] ITEM = similarItems.elementAt(i);
                        frame.dispose();
                        try {
                            VistaPerfilItem perfilitem = new VistaPerfilItem(ITEM);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
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
            if(e.getSource() == homeButton) {
                homeButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == rateButton) {
                rateButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(e.getSource() == deleteRatingButton) {
                deleteRatingButton.setBackground(Color.gray);
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if(!items_list.isEmpty()){
                for(int i = 0; i < items_list.size(); ++i) {
                    if(e.getSource() == items_list.get(i)) {
                        items_list.get(i).setBackground(Color.gray);
                        items_list.get(i).setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() == homeButton) {
                homeButton.setBackground(Color.darkGray);
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == rateButton) {
                rateButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(e.getSource() == deleteRatingButton) {
                deleteRatingButton.setBackground(new Color(0xE0E0E0));
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(!items_list.isEmpty()){
                for(int i = 0; i < items_list.size(); ++i) {
                    if(e.getSource() == items_list.get(i)) {
                        items_list.get(i).setBackground(Color.darkGray);
                        items_list.get(i).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        }
    }
}
