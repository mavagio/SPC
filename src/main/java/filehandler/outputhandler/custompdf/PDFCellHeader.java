package main.java.filehandler.outputhandler.custompdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * @author Martin Avagyan 
 */

public class PDFCellHeader extends PdfPCell{

	public PDFCellHeader(Phrase prs){
		super(prs);
		initStyle();
	}
	
	private void initStyle(){
		this.setPadding(5);
		this.setHorizontalAlignment(Element.ALIGN_CENTER);
		this.setVerticalAlignment(Element.ALIGN_CENTER);
		this.setBorder(Rectangle.NO_BORDER );
		this.setCellEvent(new DoubleBorderCellEvent());
	}
}
