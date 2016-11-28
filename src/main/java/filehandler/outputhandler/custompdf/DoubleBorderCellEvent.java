package main.java.filehandler.outputhandler.custompdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author Martin Avagyan 
 */

public class DoubleBorderCellEvent implements PdfPCellEvent{
	 public void cellLayout(PdfPCell cell, Rectangle position,

	PdfContentByte[] canvases) {
		PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
		canvas.rectangle(position.getLeft(), position.getBottom(),
                position.getWidth(), position.getHeight());
		canvas.moveTo(position.getLeft(), position.getBottom());
		canvas.lineTo(position.getRight(), position.getBottom());
		// construct second line (4 user units lower):
		canvas.moveTo(position.getLeft(), position.getBottom() - 2);
		canvas.lineTo(position.getRight(), position.getBottom() - 2);
		// draw lines
		canvas.stroke();
	}
	
}
