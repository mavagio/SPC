package main.java.insertion;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import main.java.mvc.gui.ToolButton;

/**
 * @author Martin Avagyan 
 */

public class InsertionFooterPanel extends JPanel{
	
	private  int WIDTH;
	
	private ToolButton nextBtn = new ToolButton("Next");
	private ToolButton cancelBtn = new ToolButton("Cancel");
	private ToolButton backBtn = new ToolButton("Back");
	
	public InsertionFooterPanel(int width){
		this.WIDTH = width;
		this.setBackground(Color.decode("#EEEEEE"));
		this.setLayout(null);
		
		this.setMaximumSize(new Dimension(this.WIDTH, 100));
		
		setLocation();
		add(nextBtn);
		add(backBtn);
		add(cancelBtn);
		
	}
	
	private void setLocation(){
		nextBtn.setBounds(this.WIDTH/2 - 50, 15, 100, 25);
		cancelBtn.setBounds(this.WIDTH/2 +51, 15, 100, 25);
		backBtn.setBounds(this.WIDTH/2-151, 15, 100, 25);
	}
	
	public ToolButton getNextBtn() {
		return nextBtn;
	}

	public void setNextBtn(ToolButton nextBtn) {
		this.nextBtn = nextBtn;
	}

	public ToolButton getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(ToolButton cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public ToolButton getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(ToolButton backBtn) {
		this.backBtn = backBtn;
	}
}
