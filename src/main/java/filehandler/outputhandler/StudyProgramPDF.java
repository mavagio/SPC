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
import main.java.types.program.StudyProgram;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

	/**
	 *@author Martin Avagyan 
	 * */

	/**
	 * The following class generates a pdf document with provided study program
	 * The document consists of one chapters, overview of what courses it has 
	 * has two arguments @param program (student program) and the path to save it in
	 * */

public class StudyProgramPDF {	
	protected PDFStyle style;
	protected PDFGraphics graphics;
	private boolean successfullyCreated = true;	

	private static String headerImagePath = "/main/resources/img/reportHeader.jpg";
	

	
	private  String filePath;
		
	private String programName;	

	private static DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy,  hh:mm aaa");
	private static Calendar cal = Calendar.getInstance();
	private StudyProgram program;
	
	private HashMap<String,Course> alternativeCourses = new HashMap<String,Course>();

	private BaseColor color1 = WebColors.getRGBColor("#FFFFFF");
	private BaseColor color2 = WebColors.getRGBColor("#FAFAFA");
	private BaseColor color3 = WebColors.getRGBColor("#FFF59D");
	
	
	public StudyProgramPDF(String filePath) {
		this(null,filePath);
	}
	
	public StudyProgramPDF(StudyProgram program,String filePath) {
		style = new PDFStyle();
		graphics = new PDFGraphics();
		this.filePath = filePath;
		this.programName = program.getProgramName();
		loadResources();		
		this.program = program;
		
		try {
			Document document = new Document();			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(outputStream);
			
			PdfWriter writer = PdfWriter.getInstance(document, output);

			FileOutputStream outputStreamFile = new FileOutputStream(this.filePath+programName+"_"+program.getStudyYear()+".pdf");
						
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
		
	}
	

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("Study program checker program overview");
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
		addToPreface(new Paragraph("Study program\n" + program.getProgramName()+" " + program.getStudyYear(), style.getCatFont()),preface);
		
		// generated by
		addToPreface(new Paragraph("Generated by: " + System.getProperty("user.name"),style.getSmallBold()),preface);
		
		//generation date
		addToPreface(new Paragraph(	"Date: " + dateFormat.format(cal.getTime()), style.getSmallBold()),preface);

		
		
		// Add one empty line
		addEmptyLine(preface, 1);
		
	
		/**
		 * Add the preface to the document
		 * */
		document.add(preface);
				
		
	}
	
	/**
	 * Main content
	 * */
	private  void addContent(Document document) throws DocumentException {		
		addChapterOverview(document);
	}
	
	
	/**
	 * First Chapter
	 * */	
	private void addChapterOverview(Document document) throws DocumentException {				
		//Chapter heading
		Anchor anchor = new Anchor("Courses", style.getCatFont());
		anchor.setName("Courses");
		Chapter catPart = new Chapter(new Paragraph(anchor),1);
		
		Paragraph content = makeChapterNote("The courses that are highlighted have alternatives. See section two for listing.",true);
        catPart.add(content);
        
        

		// 1.1 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingOne(),catPart,program.getPropadeuic(),program.getAlternativeCourses());
		
		// 1.2 Sub chapter heading. Generate the post propaedeutic mandatory table
		//addToChapterOne(subHeadingTwo(),catPart,program.getPostPropadeuic(),program.getAlternativeCourses());

		// 1.3 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingThree(),catPart,program.getMinorElectives(),program.getAlternativeCourses());
		
		// 1.4 Sub chapter heading. Generate the post propaedeutic mandatory table
		addToChapterOne(subHeadingFour(),catPart,program.getBachelorProject(),program.getAlternativeCourses());
		
		//Add alternatives
		addAlternatives(alternativeHeading(),catPart,program.getAlternativeCourses());
		
		// Add all this to the document
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
	
	private void addToChapterOne(Paragraph par,Chapter chap,HashMap<String,Course> subProgram,HashMap<String,String> alternatives) throws DocumentException{
		par.setAlignment(Element.ALIGN_CENTER);
		Section sec = chap.addSection(par,0);
		addEmptyLine(par, 1);
		
		createOverviewSection(sec,subProgram,alternatives);
	}
	
	private void addAlternatives(Paragraph par,Chapter chap,HashMap<String,String> alternatives) throws DocumentException{
		par.setAlignment(Element.ALIGN_CENTER);
		Section sec = chap.addSection(par,0);
		addEmptyLine(par, 1);
		
		createTableForAlternatives(sec, alternatives);
	}
	
	//Mandatory subheading for children to extend
	
	// Sub 1.1
	protected Paragraph subHeadingOne() {
		return new Paragraph("Compulsory courses ", style.getMediumBold());
	}

	// Sub 1.2
	/*protected Paragraph subHeadingTwo() {
		return new Paragraph("Post-propaedeutic mandatory ", style.getMediumBold());
	}*/

	// Sub 1.3
	protected Paragraph subHeadingThree() {
		return new Paragraph("Electives / Minor ", style.getMediumBold());
	}

	// Sub 1.4
	protected Paragraph subHeadingFour() {
		return new Paragraph("Bachelor project including essay ", style.getMediumBold());
	}
	
	// Sub 1.5
	protected Paragraph subHeadingFive() {
		return new Paragraph("Extra curricular courses", style.getMediumBold());
	}	
	// Sub 1.6
	protected Paragraph alternativeHeading() {
		return new Paragraph("Alternative courses", style.getMediumBold());
	}
		
	/**
	 * Add subsection for second chapter
	 * */
	private void createOverviewSection(Section subCatPart,HashMap<String,Course> subProgram,HashMap<String,String> alternatives){
		if(subProgram.size()==0){
			Paragraph title =  new Paragraph("Section is empty", style.getBackFont());
			title.setAlignment(Element.ALIGN_CENTER);
			subCatPart.add(title);
		}else{
			try {
				createTableForOverview(subCatPart,subProgram, alternatives);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * generate an alternative table
	 * */
	private void createTableForAlternatives(Section subCatPart,HashMap<String,String> alternatives)	throws DocumentException {
		
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
		genAlternativeCourses(table,alternatives);

		subCatPart.add(table);
	}

	/**
	 * Add courses from a hashtable to a table as a content
	 * */
	private void genAlternativeCourses(PdfPTable table,HashMap<String,String> alternatvies){
		BaseColor tempCol = color2;
		
		for(String coruseCodes: alternatvies.keySet()){			
			
			//Add the course code to the table
			table.addCell(new PDFCellContent(new Phrase(coruseCodes,style.getSmallNormal()),tempCol));
			
			//Add the course name to the table
			table.addCell(new PDFCellContent(new Phrase(alternativeCourses.get(coruseCodes).getCourseName(),style.getSmallNormal()),tempCol));
						
			//Add the credit to the table
			table.addCell(new PDFCellContent(new Phrase(alternativeCourses.get(coruseCodes).getCourseCredits()+"",style.getSmallNormal()),tempCol));	
			
			PdfPCell cell;
	        cell = new PdfPCell(new PDFCellContent(new Phrase(alternatvies.get(coruseCodes).replaceAll("\t","    "),style.getSmallNormal()),color1));
	        cell.setColspan(3);
	        table.addCell(cell);
		}
	}
	
	
	
	/**
	 * Generate table for suggestions
	 * */
	/**
	 * generate a table
	 * */
	private void createTableForOverview(Section subCatPart,HashMap<String,Course> subProgram,HashMap<String,String> alternatives)	throws DocumentException {
		
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
		genCoursesForOverview(table, subProgram,alternatives);

		subCatPart.add(table);
	}
	/**
	 * Add courses from a hashtable to a table as a content
	 * */
	private void genCoursesForOverview(PdfPTable table,HashMap<String,Course> subProgram,HashMap<String,String> alternatives){
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
			//Highlight if has alternative
			if(alternatives.containsKey(coruseCodes)){
				alternativeCourses.put(coruseCodes, subProgram.get(coruseCodes));
				tempCol = color3; 
			}
			
			//Add the course code to the table
			table.addCell(new PDFCellContent(new Phrase(coruseCodes,style.getSmallNormal()),tempCol));
			
			//Add the course name to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseName(),style.getSmallNormal()),tempCol));
						
			//Add the credit to the table
			table.addCell(new PDFCellContent(new Phrase(subProgram.get(coruseCodes).getCourseCredits()+"",style.getSmallNormal()),tempCol));		
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