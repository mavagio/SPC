package main.java.filehandler.outputhandler.custompdf;

import com.itextpdf.text.Font;

/**
 * @author Martin Avagyan 
 */

public class PDFStyle {
	/**
	 * Font types
	 * */
	private  Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.BOLD);
	protected  Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	protected  Font backFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private  Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);	
	private  Font mediumBold = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);	
	private  Font smallBoldGray = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);	
	private  Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 9,
			Font.NORMAL);
	
	
	public PDFStyle(){
		backFont.setColor(150,150,150);
		smallBoldGray.setColor(100,100,100);
	}
	
	/**
	 * Getters
	 * */
	public  Font getCatFont() {
		return catFont;
	}
	public  Font getSubFont() {
		return subFont;
	}
	public  Font getBackFont() {
		return backFont;
	}
	public  Font getSmallBold() {
		return smallBold;
	}
	public  Font getSmallBoldGray() {
		return smallBoldGray;
	}
	public  Font getSmallNormal() {
		return smallNormal;
	}

	public Font getMediumBold() {
		return mediumBold;
	}
	
}
