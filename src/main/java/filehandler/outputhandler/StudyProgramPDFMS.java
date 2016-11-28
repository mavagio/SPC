package main.java.filehandler.outputhandler;

import com.itextpdf.text.Paragraph;
import main.java.types.program.StudyProgram;
/**
 * @author Martin Avagyan 
 */

	public class StudyProgramPDFMS extends StudyProgramPDF{

	public StudyProgramPDFMS(String filePath) {
		this(null,filePath);
	}

	public StudyProgramPDFMS(StudyProgram program,String filePath) {
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
}
