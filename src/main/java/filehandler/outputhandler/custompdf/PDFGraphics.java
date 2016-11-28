package main.java.filehandler.outputhandler.custompdf;

import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

/**
 * @author Martin Avagyan 
 */

public class PDFGraphics {
	/**
	 * Read the header image for the pdf
	 * */
	public void getHeaderImage(Document document,String headerImagePath){		
		URL input =  getClass().getResource(headerImagePath);
		if(input!=null){
			try {			
				Image img = Image.getInstance(input);
				
				int indentation = 0;
				float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
			               - document.rightMargin() - indentation) / img.getWidth()) * 100;

				img.scalePercent(scaler*7/10);
				
				img.setAlignment(Image.MIDDLE);
				document.add(img);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * Read an image given the string path and size
	 * */
	public Image getImage(String imageLocation, int size){		
		URL input =  getClass().getResource(imageLocation);
		if(input!=null){
			try {			
				Image img = Image.getInstance(input);
				
				img.scalePercent(size);
				
				img.setAlignment(Image.MIDDLE);
				return img;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}	
	
}
