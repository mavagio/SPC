package main.java.filehandler.outputhandler.custompdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.PdfPCell;

/**
 * @author Martin Avagyan 
 */

public class PDFPCellContent extends PdfPCell{

	private BaseColor col;
	
	public PDFPCellContent(Phrase prs,BaseColor col){
		super(prs);
		this.col = col;

		initStyle();
	}
	
	private void initStyle(){
		this.setPadding(5);
		this.setHorizontalAlignment(Element.ALIGN_CENTER);
		this.setVerticalAlignment(Element.ALIGN_CENTER);
		this.setBackgroundColor(col);
	}
}
