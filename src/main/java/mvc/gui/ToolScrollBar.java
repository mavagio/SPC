package main.java.mvc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * @author Martin Avagyan 
 */

public class ToolScrollBar extends BasicScrollBarUI   {

	private Image imageThumb, imageTrack;
	private JButton b = new JButton() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }
    };
	
	public ToolScrollBar() {
         imageThumb = FauxImage.create(10, 10, Color.lightGray );
         imageTrack = FauxImage.create(10, 10,Color.decode("#E0E0E0"));
     }
	
    @Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		((Graphics2D) g).drawImage(imageTrack, trackBounds.x, trackBounds.y,
				trackBounds.width, trackBounds.height, null);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

		((Graphics2D) g).drawImage(imageThumb, thumbBounds.x, thumbBounds.y,
				thumbBounds.width, thumbBounds.height, null);
	}
	
	 @Override
     protected JButton createDecreaseButton(int orientation) {
         return b;
     }
	 

     @Override
     protected JButton createIncreaseButton(int orientation) {
         return b;
     }
    
    private static class FauxImage {

        static public Image create(int w, int h, Color c) {
            BufferedImage bi = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setPaint(c);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
            return bi;
        }
    }    
 }




