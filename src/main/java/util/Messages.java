package main.java.util;

import main.java.mvc.ToolModel;

/**
 * @author Martin Avagyan 
 */

/**
 * This class contains all the string that are
 * outputted in the form of message dialogs  
 * */
public class Messages {
	
		/****************************************************************************
		 ******************************* Selection messages**************************
		 ****************************************************************************/

		/**
		 * Error messages for student selection
		 * */		
		public static String getWrongDegreeStudentMessage() {
			return "\nIt seems like you chose a wrong degree." +
			       "\nChoose a student folder with " + getDegree() + "." +
			 	   "\nIf you wish to proceed, change the setting to " + getDegreeOpposite() + ".";
		}
		public static String getWrongFolderStudentMessage() {
			return "\nIt seems like you chose a curriculum folder for " + getDegree() +
		 		   ".\nChoose instead a student folder for " + getDegree() + ".";
		}

		/**
		 * Error messages for curriculum selection
		 * */	
		public static String getWrongDegreeCurriculumMessage() {
			return "\nIt seems like you chose a wrong degree." +
			          "\nChoose a curriculum folder with " + getDegree() + "." +
			 	      "\nIf you wish to proceed, change the setting to " + getDegreeOpposite() + ".";
		}
		public static String getWrongFolderCurriculumMessage() {
			return "\nIt seems like you chose a student folder for " + getDegree() +
		 			 ".\nChoose instead a curriculum folder for " + getDegree() + ".";
		}
		public static String getInterpretError() {
			return "An error occured while interpreating a program.\n"
					+ "Check the folder files and try again.\n";
		}
		public static String getCheckError() {
			return "An error occured while checking students.\n"
					+ "Check the curriculum folder files and try again.\n"
					+ "Possibly the alternative section is incorrect.\n";
		}
		
		/**
		 * Empty folder warnings
		 * */
		public static String getEmpltyFolderError(){
			return "\nThere is a folder that not contain SPC files.";
		}
		
		/**
		 * Selection warnings
		 * */
		public static String getSelectFodersMessage() {
			return "Please select student folder and curriculum folder.";
		}
		public static String getSelectStudentMessage() {
			return "Please select at least one student.";
		}
		public static String getSelectStudyProgramEditMessage() {
			return "Please select a study program to edit.";
		}
		public static String getNoSelectionMessage() {
			return "No Selection";
		}
		public static String getSelectSaveLocationMessage() {
			return "Please select the save location again.";
		}
		public static String getWrongFolderHintMessage() {
			return "Oops! Wrong folder.\nHint:";
		}
		public static String getCheckErrorMessage() {
			return "Error: Check cant be performed due to wrong input.";
		}
		public static String getChooseCurriculumMessage() {
			return "Please select at least one student.";
		}
		public static String getSingleFolderMessage() {
			return "Please select one curriculum.";
		}
		public static String getSuccessSavedProgramMessage() {
			return "The program is successfully saved.";
		}
		public static String getSelectRowMessage() {
			return "Please select a table row.";
		}
		public static String getOnProgressMessage() {
			return "This function is on development.";
		}
				
		/**
		 * NA HINT
		 * */
		public static String getNA() {
			return " Not available :(";
		}
		/**
		 * Success message
		 * */
		public static String getSuccessSaveMessage() {
			return "Successfully saved!";
		}
		
		/****************************************************************************
		 ***************************** Local dynamic values**************************
		 ****************************************************************************/
		private static String getDegree(){
			return (ToolModel.degree.equals("BS")?"Bachelor":"Masters")+" degree";
		}		
		private static String getDegreeOpposite(){
			return (!ToolModel.degree.equals("BS")?"Bachelor":"Masters")+" degree";
		}
		
}
