package main.java.insertion.alternative;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.insertion.InsertionLabel;
import main.java.mvc.gui.ToolButton;

/**
 * @author Martin Avagyan 
 */

public class AlternativeFooterPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private  int WIDTH;
	private int EXPR_LENGTH = 500;
	
	private ToolButton nextBtn = new ToolButton("Submit");
	private ToolButton cancelBtn = new ToolButton("Cancel");
	private AlternativeTextField expressionFild;
	private InsertionLabel expressionLabel;
	
	public AlternativeFooterPanel(int width){
		this.WIDTH = width;
		this.setBackground(Color.decode("#EEEEEE"));
		this.setLayout(null);
		this.expressionFild = new AlternativeTextField(EXPR_LENGTH);
		this.expressionLabel = new InsertionLabel("Enter an expression");
		
		this.setMaximumSize(new Dimension(this.WIDTH, 100));
		
		setLocation();
		add(expressionLabel);
		add(expressionFild);
		add(nextBtn);
		add(cancelBtn);
		
	}
	
	private void setLocation(){
		expressionLabel.setBounds(this.WIDTH/2-100, 15, 200, 25);
		expressionFild.setBounds(50,50,this.WIDTH-100,30);
		nextBtn.setBounds(this.WIDTH/2 - 101, 125, 100, 25);
		cancelBtn.setBounds(this.WIDTH/2, 125, 100, 25);
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

	public String getExpressionFildText() {
		return expressionFild.getText();
	}

	public void setExpressionFildText(String text) {
		this.expressionFild.setText(text);;
	}
	
	
}
