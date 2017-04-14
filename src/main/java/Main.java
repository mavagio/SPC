package main.java;



import javax.swing.UIManager;

import main.java.mvc.ToolController;
import main.java.mvc.ToolModel;
import main.java.mvc.gui.ToolView;
/**
 *@author Martin Avagyan 
 * */

public class Main {
	public static void main(String [ ] args)
	{
		UIManager.put("ScrollBarUI", "main.java.mvc.gui.ToolScrollBar");
		UIManager.put("MetalScrollBarUI", "main.java.mvc.gui.ToolScrollBar");
	    ToolView view = new ToolView();
	    ToolModel model = new ToolModel();	
	    new ToolController(view,model);
	}
}
