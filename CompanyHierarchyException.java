///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  CompanyHierarchyMain.java
// File:             CompanyHierarchyException.java
// Semester:         (course) summer 2017
//
// Author:           (your name and email address) yme8@wisc.edu
// CS Login:         (your login name) yifanmei
// Lecturer's Name:  (name of your lecturer) Meena
// Lab Section:      (your lab section number)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     (name of your pair programming partner) Yuran Liu
// Email:            (email address of your programming partner) yuran.liu@wisc.edu
// CS Login:         (partner's login name) yuran
// Lecturer's Name:  (name of your partner's lecturer) Meena
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////


/** The CompanyHierarchyException class throws a kind of RuntimeException
 * When throwing this kind of Exception, we can directly throw it or throw a 
 * message with this Exception 
 */
public class CompanyHierarchyException extends RuntimeException {

	public CompanyHierarchyException() {
		super();
	}
	public CompanyHierarchyException(String msg) {
		super(msg);
	}

}
