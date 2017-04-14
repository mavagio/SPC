package main.java.filehandler.outputhandler;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import main.java.filehandler.outputhandler.custompdf.PDFCellContent;
import main.java.filehandler.outputhandler.custompdf.PDFCellHeader;
import main.java.filehandler.outputhandler.custompdf.PDFGraphics;
import main.java.filehandler.outputhandler.custompdf.PDFStyle;
import main.java.types.Course;
import main.java.types.program.StudentProgram;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

	/**
	 *@author Martin Avagyan 
	 * */

	/**
	 * The following class generates a pdf document with provided student program
	 * The document consists of two chapters, overview of what student has chosen
	 * and suggestions
	 * has one argument @param program (student program)
	 * */

public class ReportPDF {	
	protected PDFStyle style;
	protected PDFGraphics graphics;
	private boolean successfullyCreated = true;	

	private static String headerImagePath = "/main/resources/img/reportHeader.jpg";
	private static String matchedImagePath = "/main/resources/img/matched.png";
	private static String alternativeMatchedImagePath = "/main/resources/img/matchedalt.png";
	private static String notMatchedImagePath = "/main/resources/img/not-matched.png";
	
	private Image matchedTableImage;
	private Image alterMatchedTableImage;
	private Image notMatchedTableImage;
	
	private  String filePath = "C:/Users/Martin/Desktop/testExample/";
		
	private String studentName;	

	private static DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy,  hh:mm aaa");
	private static Calendar cal = Calendar.getInstance();

	private StudentProgram program;
	
	

	private BaseColor color1 = WebColors.getRGBColor("#FFFFFF");
	private BaseColor color2 = WebColors.getRGBColor("#FAFAFA");
	
	public ReportPDF(String filePath) {
		this(null,filePath);
	}
	
	public ReportPDF(StudentProgram program,String filePath) {
		style = new PDFStyle();
		graphics = new PDFGraphics();
		this.filePath = filePath;
		this.studentName = program.getStudentSurnameName();
		loadResources();		
		this.program = program;
		
		try {
			Document document = new Document();			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(outputStream);
			
			PdfWriter writer = PdfWriter.getInstance(document, output);

			FileOutputStream outputStreamFile = new FileOutputStream(this.filePath+studentName+" - " + program.getMatchedProgramName() + " report.pdf");
						
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			
			addMetaData(document);	
			graphics.getHeaderImage(document,headerImagePath);
			addTitlePage(document);
			addContent(document);
			document.close();
			writer.close();
			
			//TODO
			
			//Start
			int pageCount = writer.getPageNumber();
			
			byte[] output2 = outputStream.toByteArray();
			PdfReader reader = new PdfReader(output2);
			//reset the output
			outputStream = new ByteArrayOutputStream();
			output = new DataOutputStream(outputStream);
			document = new Document();
			writer = PdfWriter.getInstance(document, output);
			document.open();
			PdfStamper stamper = new PdfStamper(reader, outputStream);
			//add the pages
			for (int i = 1; i <= pageCount; i++)
			{
				 ColumnText.showTextAligned(stamper.getOverContent(i), 
						    Element.ALIGN_CENTER, new Phrase(i+"/" + pageCount, style.getSmallNormal()), 550, 30, 0);  // my own paragraph font
			}
			stamper.close();	
			
			//Finally save the file
			outputStream.writeTo(outputStreamFile);
			//TODO			
			outputStream.close();
			//document.close();
			outputStreamFile.close();
		} catch (Exception e) {
			successfullyCreated = false;
			e.printStackTrace();
		}
	}
	
	private void loadResources(){
		matchedTableImage = graphics.getImage(matchedImagePath,12);
		alterMatchedTableImage = graphics.getImage(alternativeMatchedImagePath,12);		
		notMatchedTableImage = graphics.getImage(notMatchedImagePath,12);
		
	}
	

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("Study program checker");
		document.addSubject("Report");
		document.addKeywords("Java, PDF, RUG,Student, Study program");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator(System.getProperty("user.name"));
	}
	
	private void addToPreface(Paragraph par,Paragraph preface){		
		par.setAlignment(Element.ALIGN_CENTER);
		preface.add(par);
		addEmptyLine(preface, 1);
	}

	/**
	 * Title page
	 * */
	private void addTitlePage(Document document)throws DocumentException {
		/**
		 * Create the first page of the document
		 * */
		Paragraph preface = new Paragraph();
		preface.setAlignment(Element.ALIGN_CENTER);
		
		// Add one empty line
		addEmptyLine(preface, 1);
		
		// Header title
		addToPreface(new Paragraph("Student program report", style.getCatFont()),preface);
		
		// generated by
		addToPreface(new Paragraph("Generated by: " + System.getProperty("user.name"),style.getSmallBold()),preface);
		
		//generation date
		addToPreface(new Paragraph(	"Date: " + dateFormat.format(cal.getTime()), style.getSmallBold()),preface);

		//add student
		addToPreface(new Paragraph("Student name: " + program.getStudentName() + " (s"+ program.getStudentNumber()+")", style.getSmallBold()),preface);
				
		//add remarks title
		addToPreface(new Paragraph("Student remark", style.getSmallBold()),preface);	
		
		//add remarks content		
        Paragraph remarkPar = new Paragraph(program.getRemarks(), style.getSmallNormal());
        remarkPar.setIndentationLeft(75);
        remarkPar.setIndentationRight(75);
        remarkPar.setAlignment(Element.ALIGN_LEFT);
        preface.add(remarkPar);
		//addToPreface(new Paragraph(program.getRemarks(), style.getSmallBoldGray()),preface);
		
		// Add one empty line
		addEmptyLine(preface, 1);
		
		//Add status		
		addToPreface(
				new Paragraph("Selected program: " + program.getVariant()
						+ "\nChecked program: "
						+ program.getMatchedProgramName() + "\nStatus: "
						+ program.getNotMatchedCount() + " not matched",
						style.getSmallBold()), preface);
	
		/**
		 * Add the preface to the document
		 * */
		document.add(preface);
				
		//Add status image
		if(program.isMatched()){
			document.add(matchedTableImage);
		}else{
			document.add(notMatchedTableImage);
		}		
		
		
		// Start a new page
		document.newPage();
	}
	
	/**
	 * Main content
	 * */
	private  void addContent(Document document) throws DocumentException {		
		addChapterOverview(document);	
		addChapterSummary(document);
	}
	
	
	/**
	 * First Chapter
	 * */	
	private void addChapterOverview(Document document) throws DocumentException {				
		//Chapter heading
		Anchor anchor = new Anchor("Student programs overview", style.getCatFont());
		anchor.setName("Student programs overview");
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		
		//Adding the remark of the paragraph
	
		//Introduction to remark
		Paragraph content = makeChapterNote("This section gives an overview of all the"
											+ " courses that the student has chosen. The"
											+ " status column gives a positive or negative "
											+ "remark about the particular course. Here are"
											+ " the explanations for each color:",true);

		//Explanation for green status icon
		Paragraph greenStatus = makeChapterNote("",false);		
		greenStatus.add(new Phrase("Green: "));
		greenStatus.add(new Chunk(matchedTableImage, 0, 0));
		greenStatus.add(new Phrase(", means a positive remark. The course"
								  + " that the student has chosen matches "
								  + "the curriculum."));
		
		//Explanation for blue status icon
		Paragraph blueStatus = makeChapterNote("",false);		
		blueStatus.add(new Phrase("Blue:   "));
		blueStatus.add(new Chunk(alterMatchedTableImage, 0, 0));
		blueStatus.add(new Phrase(", means a positive remark. The course"
								  + " that the student has chosen matches "
								  + "an accepted alternative of a course in "
								  + "the curriculum."));
		
		//Explanation for red status icon
		Paragraph redStatus = makeChapterNote("",false);		
		redStatus.add(new Phrase("Red:    "));
		redStatus.add(new Chunk(notMatchedTableImage, 0, 0));
		redStatus.add(new Phrase(", means a negative remark. The course"
								  + " that the student has chosen does "
								  + "NOT match the curriculum. Please "
								  + "refer to section 2 (Suggestions) to "
								  + "find possible alternative courses that"
								  + " the student could choose to fix the problem."));
		
		addEmptyLine(redStatus,1);
		
		catPart.add(content);
		catPart.add(greenStatus);
		catPart.add(blueStatus);
		catPart.add(redStatus);
		
		
		// 1 Sub chapter heading. Generate the propaedeutic mandatory table
		addToChapterOne( subHeadingOne(),catPart,program.getPropadeuic(), program.getMatchedStudyProgram().getPropadeuic());			

		// 2 Sub chapter heading. Generate the post propaedeutic mandatory table	
		addToChapterOne(subHeadingTwo(),catPart,program.getPostPropadeuic(),  program.getMatchedStudyProgram().getPostPropadeuic());

		// 3 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingThree(),catPart,program.getMinorElectives(), program.getMatchedStudyProgram().getMinorElectives());
		
		// 4 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingFour(),catPart,program.getBachelorProject(),  program.getMatchedStudyProgram().getBachelorProject());
		
		// 5 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingFive(),catPart,program.getExtraCourses(),  program.getMatchedStudyProgram().getExtraCourses());
		
		document.add(catPart);
	}
	
	/**
	 * First argument is the next of the note
	 * Second argument is for new paragraph, if the boolean is true, new line will be added
	 * */
	private Paragraph makeChapterNote(String text,boolean newPar){
		Paragraph content = new Paragraph("",style.getSmallNormal());
		content.setAlignment(Element.ALIGN_CENTER);
		if(newPar)addEmptyLine(content,1);
		Paragraph remarkPar = new Paragraph(text, style.getSmallNormal());
		
		content.setIndentationLeft(25);
		content.setIndentationRight(25);
        remarkPar.setAlignment(Element.ALIGN_LEFT);
        content.add(remarkPar);
        return content;
	}
	
	
	/**
	 * Second Chapter
	 * */
	private void addChapterSummary(Document document) throws DocumentException {
		//Chapter heading
		Anchor anchor = new Anchor("Suggestions", style.getCatFont());
		anchor.setName("Suggestions");
		Chapter catPart = new Chapter(new Paragraph(anchor),2);
		
		Paragraph content = makeChapterNote("This section gives an overview of alternative course units, that "
											+ "you could take to fix problems reported in section 1 "
											+ "of this report.",true);
        catPart.add(content);
        
        

		// 2.1 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterTwo(chTwoSubHeadingOne(),catPart,program.filterAccpeted(program.getMatchedStudyProgram().getPropadeuic()));
		
		// 2.2 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterTwo(chTwoSubHeadingTwo(),catPart,program.filterAccpeted(program.getMatchedStudyProgram().getPostPropadeuic()));

		// 2.3 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterTwo(chTwoSubHeadingThree(),catPart,program.filterAccpeted(program.getMatchedStudyProgram().getMinorElectives()));
		
		// 2.4 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterTwo(chTwoSubHeadingFour(),catPart,program.filterAccpeted(program.getMatchedStudyProgram().getBachelorProject()));
		
		// Add all this to the document
		document.add(catPart);
	}
	

	private void addToChapterOne(Paragraph par,Chapter chap,HashMap<String,Course> subProgram,HashMap<String,Course> subStudyProgram) throws DocumentException{
		par.setAlignment(Element.ALIGN_CENTER);
		Section sec = chap.addSection(par,0);
		
		par.add(new Chunk(isComplete(subProgram)?matchedTableImage:notMatchedTableImage, 0, 0));
		addEmptyLine(par,1);
		createTable(sec,subProgram);
	}
	
	private void addToChapterTwo(Paragraph par,Chapter chap,HashMap<String,Course> subProgram) throws DocumentException{
		par.setAlignment(Element.ALIGN_CENTER);
		Section sec = chap.addSection(par,0);
		addEmptyLine(par, 1);
		
		creatSummarySection(sec,subProgram);
	}
	
	//Mandatory subheading for children to extend
	
	// Sub 1.1
	protected Paragraph subHeadingOne() {
		return new Paragraph("Propaedeutic mandatory ", style.getMediumBold());
	}

	// Sub 1.2
	protected Paragraph subHeadingTwo() {
		return new Paragraph("Post-propaedeutic mandatory ", style.getMediumBold());
	}

	// Sub 1.3
	protected Paragraph subHeadingThree() {
		return new Paragraph("Minor and electives ", style.getMediumBold());
	}

	// Sub 1.4
	protected Paragraph subHeadingFour() {
		return new Paragraph("Bachelor's project ", style.getMediumBold());
	}
	
	// Sub 1.5
	protected Paragraph subHeadingFive() {
		return new Paragraph("Extra curricular courses", style.getMediumBold());
	}
	
	
	// Sub 2.1
	protected Paragraph chTwoSubHeadingOne() {
		return new Paragraph("Possible alternatives for propaedeutic mandatory", style.getMediumBold());
	}
	
	// Sub 2.2
	protected Paragraph chTwoSubHeadingTwo() {
		return new Paragraph("Possible alternatives for post-propaedeutic mandatory", style.getMediumBold());
	}

	// Sub 2.3
	protected Paragraph chTwoSubHeadingThree() {
		return new Paragraph("Possible alternatives for minor and electives", style.getMediumBold());
	}

	// Sub 2.4
	protected Paragraph chTwoSubHeadingFour() {
		return new Paragraph("Possible alternatives for bachelor's project", style.getMediumBold());
	}
		
	/**
	 * Add subsection for second chapter
	 * */
	private void creatSummarySection(Section subCatPart,HashMap<String,Course> subProgram){
		if(subProgram.size()==0){
			Paragraph title =  new Paragraph("Section is complete, no suggestion.", style.getBackFont());
			title.setAlignment(Element.ALIGN_CENTER);
			subCatPart.add(title);
		}else{
			try {
				createTableForSuggestions(subCatPart,subProgram);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * generate a table
	 * */
	private void createTable(Section subCatPart,HashMap<String,Course> subProgram)	throws DocumentException {
		
		PdfPTable table = new PdfPTable(6);	
		
		//set table properties
		float[] columnWidths = new float[] {20f, 50f, 15f, 20f,15f,15f};
		table.setWidths(columnWidths);
		
		table.setTotalWidth(PageSize.A4.getWidth()*9/10);
	    table.setLockedWidth(true);
		table.setPaddingTop(4);		
		table.setHeaderRows(1);
		
		//Table columns header
		PDFCellHeader c1 = new PDFCellHeader(new Phrase("Code",style.getSmallBold()));
		table.addCell(c1);

		c1 = new PDFCellHeader(new Phrase("Name",style.getSmallBold()));
		table.addCell(c1);

		c1 = new PDFCellHeader(new Phrase("Grade",style.getSmallBold()));
		table.addCell(c1);
		
		c1 = new PDFCellHeader(new Phrase("Status",style.getSmallBold()));
		table.addCell(c1);
		
		c1 = new PDFCellHeader(new Phrase("Credits",style.getSmallBold()));
		table.addCell(c1);
		
		c1 = new PDFCellHeader(new Phrase("Check",style.getSmallBold()));
		table.addCell(c1);
		
		//Set the table content
		genSectionCourses(table, subProgram);

		subCatPart.add(table);
	}
	
	/**
	 * The following function checks if the section is correctly chosen
	 * */
	private boolean isComplete(HashMap<String,Course> subProgram){
		for(String coruseCodes: subProgram.keySet()){	
			System.out.println(subProgram.get(coruseCodes).isMatched());
			if(subProgram.get(coruseCodes).isMatched() == 0){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Add courses from a hashtable to a table as a content
	 * */
	private void genSectionCourses(PdfPTable table,HashMap<String,Course> subProgram){
		boolean colorcheck = false;
		BaseColor tempCol = color1;
		
		for(String coruseCodes: subProgram.keySet()){			
			if(colorcheck){
				tempCol  = color1;
				colorcheck = false;
			}else{
				tempCol = color2;
				colorcheck = true;				
			}
				
			//Add the course code to the table
			table.addCell(new PDFCellContent(new Phrase(coruseCodes,style.getSmallNormal()),tempCol));
			
			//Add the course name to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseName(),style.getSmallNormal()),tempCol));
			
			//Add the grade to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getGrade()+"",style.getSmallNormal()),tempCol));
			

			//Status chacker if grade is > 5.5 && is credit count is != 0
			if( subProgram.get(coruseCodes).getCourseCredits()!=0){
				table.addCell(new PDFCellContent(new Phrase("Complete",style.getSmallNormal()),tempCol));
			}else{
				table.addCell(new PDFCellContent(new Phrase("Not complete",style.getSmallNormal()),tempCol));
			}			
			
			//Add the credit to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseCredits()+"",style.getSmallNormal()),tempCol));
			
			PDFCellContent cTemp = new PDFCellContent(new Phrase("match",style.getSmallNormal()),tempCol);
			cTemp.addElement(setCellImage(subProgram,coruseCodes));
			table.addCell(cTemp);			
		}
	}
	
	/**
	 * Generate table for suggestions
	 * */
	/**
	 * generate a table
	 * */
	private void createTableForSuggestions(Section subCatPart,HashMap<String,Course> subProgram)	throws DocumentException {
		
		PdfPTable table = new PdfPTable(3);	
		
		//set table properties
		float[] columnWidths = new float[] {20f, 50f,15f};
		table.setWidths(columnWidths);
		
		table.setTotalWidth(PageSize.A4.getWidth()*9/10);
	    table.setLockedWidth(true);
		table.setPaddingTop(4);		
		table.setHeaderRows(1);
		
		//Table columns header
		PDFCellHeader c1 = new PDFCellHeader(new Phrase("Code",style.getSmallBold()));
		table.addCell(c1);

		c1 = new PDFCellHeader(new Phrase("Name",style.getSmallBold()));
		table.addCell(c1);
		
		c1 = new PDFCellHeader(new Phrase("Credits",style.getSmallBold()));
		table.addCell(c1);
		

		//Set the table content
		genSectionCoursesSuggestions(table, subProgram);

		subCatPart.add(table);
	}
	/**
	 * Add courses from a hashtable to a table as a content
	 * */
	private void genSectionCoursesSuggestions(PdfPTable table,HashMap<String,Course> subProgram){
		boolean colorcheck = false;
		BaseColor tempCol = color1;
		
		for(String coruseCodes: subProgram.keySet()){			
			if(colorcheck){
				tempCol  = color1;
				colorcheck = false;
			}else{
				tempCol = color2;
				colorcheck = true;				
			}
				
			//Add the course code to the table
			table.addCell(new PDFCellContent(new Phrase(coruseCodes,style.getSmallNormal()),tempCol));
			
			//Add the course name to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseName(),style.getSmallNormal()),tempCol));
						
			//Add the credit to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseCredits()+"",style.getSmallNormal()),tempCol));		
		}
	}

	private Image setCellImage(HashMap<String, Course> subProgram, String coruseCodes) {
		// Match checker: set the image				
		if(subProgram.get(coruseCodes).isMatched()==1){
			return matchedTableImage;				
		}else if (subProgram.get(coruseCodes).isMatched()==2){
			return alterMatchedTableImage;		
		}else{
			return notMatchedTableImage;
		}
	}
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}	

	public boolean isSuccessfullyCreated() {
		return successfullyCreated;
	}

}