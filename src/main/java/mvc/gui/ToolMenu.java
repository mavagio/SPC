package main.java.mvc.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Martin Avagyan 
 */

public class ToolMenu extends JMenuBar{	
	public ToolMenu(){
		
		this.setBackground(Color.decode("#FFFFFF"));
		 genToolMenu();
	}
	
	private void genToolMenu() {
		
		addMenuItem(new JMenu("File"),"Exit", "exit.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
	}
	
	private void addMenuItem(JMenu item,String itemName, String iconName, ActionListener newAction){
		ImageIcon icon = new ImageIcon(iconName);
		JMenuItem eMenuItem = new JMenuItem(itemName, icon);
		
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.addActionListener(newAction);
		
		item.add(eMenuItem);
		this.add(item);
	}
}