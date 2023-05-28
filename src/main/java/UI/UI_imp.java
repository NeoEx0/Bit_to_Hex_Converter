package UI;
import Engin.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicArrowButton;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.*;
import java.awt.event.*;
public class UI_imp implements UI{
    Engin backend;
    //Main Window
    JFrame frame ;
    //Taskbar Menu
    JMenuBar menubar;
    JMenu file_menu;
    JMenuItem save;
    JMenuItem save_as;
    JMenuItem load;
    JMenuItem export;
    JMenuItem Import;
    JButton add_Bit;
    JButton help;
    //add_bit menu
    JDialog add_Bit_Dialog;
    JTextField bit_Name;
    boolean current_Visability;
    JButton bit_Confirm_Button;
    //help menu
    JDialog help_Dialog;
    JLabel help_text;
    //Main Functions
    ArrayList<Bit> bitlist;
    JScrollPane scrolepane;
    JPanel function_Panel;
    JPanel bit_hex_test;
    JTextField bitfield;
    JTextField hexfield;
    //rightclick menue
    JPopupMenu rightclick_menu;
    JMenuItem remove_Bit;
    JMenuItem change_name;
    
    JDialog change_Name_Dialog;
    JTextField new_Name;
    JButton change_Confirm_Button;
    Object clicked_bit;
    //File system
    JFileChooser file_Chooser;
    String file_path = null;
    public UI_imp(int current_version){
        //Assign Variables
        backend = new Engin_imp(current_version);
        frame = new JFrame("Logisim Hex Converter");
        menubar = new JMenuBar();
        file_menu = new JMenu("File");
        save = new JMenuItem("Save");
        save_as = new JMenuItem("Save as");
        load = new JMenuItem("Load");
        export = new JMenuItem("Export(WIP)");
        Import = new JMenuItem("Import(WIP)");
        add_Bit = new JButton("Add");
        help = new JButton("Help");
        add_Bit_Dialog = new JDialog();
        bit_Name = new JTextField();
        current_Visability = false;
        bit_Confirm_Button = new JButton("Confirm");
        help_Dialog = new JDialog(frame, "Help");
        help_text = new JLabel();
        function_Panel= new JPanel();
        bitlist = new ArrayList<Bit>();
        bit_hex_test = new JPanel(new FlowLayout());
        bitfield = new JTextField();
        hexfield = new JTextField();
        scrolepane = new JScrollPane(function_Panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightclick_menu = new JPopupMenu();
        remove_Bit = new JMenuItem("Remove");
        change_name = new JMenuItem("Change Name");
        change_Confirm_Button = new JButton("Confirm");
        new_Name = new JTextField();
        change_Name_Dialog = new JDialog();
        clicked_bit = new Object();
        file_Chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //Main Window
        frame.setSize(800,600);
        frame.isResizable();
        frame.setVisible(true);
        //Taskbar Menu
        menubar.add(file_menu);
        menubar.add(add_Bit);
        menubar.add(help);
        frame.setJMenuBar(menubar);
        //file_menu Options
        file_menu.add(save);
        file_menu.add(save_as);
        file_menu.add(load);
        file_menu.add(export);
        file_menu.add(Import);
        //add bit menu
        add_Bit_Dialog.add(bit_Name);
        add_Bit_Dialog.add(bit_Confirm_Button,BorderLayout.PAGE_END);
        add_Bit_Dialog.setVisible(current_Visability );
        add_Bit_Dialog.setSize(200,150);
        add_Bit_Dialog.setLocationRelativeTo(frame);
        //Help Menu
        help_text.setText("WIP");
        help_Dialog.add(help_text);
        help_Dialog.setSize(400,400);
        help_Dialog.setVisible(false);
        help_Dialog.setLocationRelativeTo(frame);
        //Main function
        function_Panel.setLayout(new BoxLayout(function_Panel, BoxLayout.PAGE_AXIS));
        function_Panel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.getContentPane().add(scrolepane,BorderLayout.CENTER);
        frame.getContentPane().add(bit_hex_test,BorderLayout.SOUTH);
        bit_hex_test.setMinimumSize(new Dimension(800,70));
        bit_hex_test.add(hexfield);
        bit_hex_test.add(bitfield);
        hexfield.setPreferredSize(new Dimension(200,35));
        hexfield.setMinimumSize(hexfield.getPreferredSize());
        bitfield.setPreferredSize(new Dimension(200,35));
        bitfield.setMinimumSize(bitfield.getPreferredSize());
        frame.revalidate();
        //rightclick menu
        rightclick_menu.add(remove_Bit);
        rightclick_menu.add(change_name);
        change_Name_Dialog.setVisible(false);
        change_Name_Dialog.setSize(200,150);
        change_Name_Dialog.add(new_Name);
        change_Name_Dialog.add(change_Confirm_Button,BorderLayout.PAGE_END);
        //file System
        file_Chooser.setVisible(false);
        //add to ActionListeners
        add_Bit.addActionListener(add);
        bit_Confirm_Button.addActionListener(add);
        help.addActionListener(al);
        save.addActionListener(al);
        save_as.addActionListener(al);
        load.addActionListener(al);
        export.addActionListener(al);
        Import.addActionListener(al);
        remove_Bit.addActionListener(rm_Listener);
        change_name.addActionListener(rm_Listener);
        change_Confirm_Button.addActionListener(rm_Listener);




    }
    
    
    ActionListener rm_Listener = new ActionListener() {
        public void actionPerformed(ActionEvent ev){
            String tmp = ((JLabel) ((JPanel) clicked_bit).getComponent(0)).getText();

            switch(ev.getActionCommand()){
                case "Remove":
                    backend.deleteBit(Integer.parseInt(tmp));
                    Bitlists();
                    break;
                case "Change Name":
                change_Name_Dialog.setVisible(true);
                    break;
                case "Confirm":
                    backend.changeBitName(new_Name.getText(), Integer.parseInt(tmp));
                    change_Name_Dialog.setVisible(false);
                    Bitlists();
                    function_Panel.revalidate();
                    break;
            }
        }
    };

    ActionListener al = new ActionListener() {
        public void actionPerformed(ActionEvent ev){
            switch(ev.getActionCommand()){
                case "Help":
                    help_Dialog.setVisible(true);
                    break;
                case "Save":
                    if(file_path != null){
                        backend.saveas(file_path);
                        break;
                    }
                case "Save as":
                    file_Chooser.setVisible(true);
                    if(file_Chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                        file_path = file_Chooser.getSelectedFile().getAbsolutePath();
                        backend.saveas(file_path);
                        file_Chooser.setVisible(false);
                    }else{
                        System.err.println("No File selected");
                    }
                    break;
                case "Load":
                    file_Chooser.setVisible(true);
                  if(file_Chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                        file_path = file_Chooser.getSelectedFile().getAbsolutePath();
                        bitlist = backend.load(file_path);
                        Bitlists();
                        file_Chooser.setVisible(false);
                    }else{
                     System.err.println("No File selected"+ file_path);
                    }
                break;

            }
        }
    };
    ActionListener add = new ActionListener() {
        public void actionPerformed(ActionEvent ev){
            switch(ev.getActionCommand()){
                case "Add":
                    current_Visability = true;
                    add_Bit_Dialog.setVisible(current_Visability );
                    break;
                case "Confirm":
                    bitlist = backend.addBit(bit_Name.getText());
                    Bitlists();
                    current_Visability = false;
                    add_Bit_Dialog.setVisible(current_Visability );
                    break;
            }
        }
    };
    void Bitlists(){
        JPanel tmp;
        JToggleButton tmp_button;
        function_Panel.removeAll();
        String pos = "";
        if(bitlist == null){
            return;
        }
        for(Bit bit : bitlist){
            tmp_button = new JToggleButton(String.valueOf(bit.getState()));
            tmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pos = String.valueOf(bit.getPosition());
            if(pos.length() < 4){
                while(pos.length() < 4){        
                    pos = "0"+ pos;
                }
            }
            tmp.add(new JLabel(pos));
            tmp.add(tmp_button);
            tmp.add(new JLabel(bit.getName()));
            tmp.setBorder(BorderFactory.createLineBorder(Color.black));
            tmp.setPreferredSize(new Dimension(200,40));
            tmp.setMaximumSize(new Dimension(800, 40));
            tmp.setMinimumSize(tmp.getPreferredSize());

            tmp.add(new BasicArrowButton(BasicArrowButton.NORTH), BorderLayout.NORTH);
            tmp.add(new BasicArrowButton(BasicArrowButton.SOUTH), BorderLayout.SOUTH);
            ((BasicArrowButton) tmp.getComponent(3)).addActionListener(button);
            ((BasicArrowButton) tmp.getComponent(4)).addActionListener(button);

            tmp_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev){
                    backend.changeBitState(Integer.parseInt(((JLabel) ((JPanel) ((JToggleButton) ev.getSource()).getParent()).getComponent(0)).getText()));
                    Bitlists();
                }
            });
            tmp.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
                }

                @Override
                public void mouseEntered(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
                }

                @Override
                public void mouseExited(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
                }

                @Override
                public void mousePressed(MouseEvent ev) {
                        if( ev.isPopupTrigger()){
                            rightclick_menu.show(ev.getComponent(),ev.getX(),ev.getY());
                            clicked_bit = ev.getSource();
                        }
                }

                @Override
                public void mouseReleased(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
                }
                
            });
            function_Panel.add(tmp);

        }
        bitfield.setText(backend.getInBits());
        hexfield.setText(backend.getInHex());
        function_Panel.revalidate();
        

        
    }

    ActionListener button = new ActionListener() {
        public void actionPerformed(ActionEvent ev){
            String tmp;
            switch(((BasicArrowButton) ev.getSource()).getDirection()){
                case BasicArrowButton.NORTH:
                    tmp = ((JLabel) ((BasicArrowButton) ev.getSource()).getParent().getComponent(0)).getText();
                    if(Integer.parseInt(tmp) != 0){
                        backend.changeBitPosition(Integer.parseInt(tmp), Integer.parseInt(tmp)-1);
                        Bitlists();
                    }
                    break;
                case BasicArrowButton.SOUTH:
                tmp = ((JLabel) ((BasicArrowButton) ev.getSource()).getParent().getComponent(0)).getText();
                if(Integer.parseInt(tmp) != backend.getList().size() -1){
                    backend.changeBitPosition(Integer.parseInt(tmp), Integer.parseInt(tmp)+1);
                    Bitlists();
                }
                break;

            }
        }
    };

}
