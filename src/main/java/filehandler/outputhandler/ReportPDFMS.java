package main.java.filehandler.outputhandler;

import com.itextpdf.text.Paragraph;

import main.java.types.program.StudentProgram;

/**
 * @author Martin Avagyan 
 */

	public class ReportPDFMS extends ReportPDF{

	public ReportPDFMS(String filePath) {
		this(null,filePath);
	}

	public ReportPDFMS(StudentProgram program,String filePath) {
		super(program,filePath);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Override the layout
	 * */
	// Sub1
	protected Paragraph subHeadingOne() {
		return new Paragraph("Mandatory courses ", style.getMediumBold());
	}

	// Sub2
	protected Paragraph subHeadingTwo() {
		return new Paragraph("Guided choice ", style.getMediumBold());
	}

	// Sub3
	protected Paragraph subHeadingThree() {
		return new Paragraph("Free choice ", style.getMediumBold());
	}

	// Sub4
	protected Paragraph subHeadingFour() {
		return new Paragraph("Master thesis ", style.getMediumBold());
	}
	
	// Sub 1.5
	protected Paragraph subHeadingFive() {
		return new Paragraph("Additional courses", style.getMediumBold());
	}
	
	// Sub 2.1
	protected Paragraph chTwoSubHeadingOne() {
		return new Paragraph("Possible alternatives for mandatory courses", style.getMediumBold());
	}
	
	// Sub 2.2
	protected Paragraph chTwoSubHeadingTwo() {
		return new Paragraph("Possible alternatives for guided choices", style.getMediumBold());
	}

	// Sub 2.3
	protected Paragraph chTwoSubHeadingThree() {
		return new Paragraph("Possible alternatives for free choices", style.getMediumBold());
	}

	// Sub 2.4
	protected Paragraph chTwoSubHeadingFour() {
		return new Paragraph("Possible alternatives for master thesis", style.getMediumBold());
	}
}
