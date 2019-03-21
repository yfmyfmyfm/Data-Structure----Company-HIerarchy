///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  CompanyHierarchyMain.java
// File:             Employee.java
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

/** The Employee class represents a single employee in the company that keeps 
 * track of employee details (as a String), id (as an integer), date of joining
 * (as a String), title (as a String)
 * 
 * DO NOT MODIFY THIS CLASS
 */

public class Employee {
	private String name;
	private int id;
	private String dateOfJoining;
	private String title;
	
	/** Constructor to initialize an Employee with the required details */
	public Employee(String name, int id, String dateOfJoining, String title) {
		this.name = name;
		this.id = id;
		this.dateOfJoining = dateOfJoining;
		this.title = title;
	}
	
	/** Return the name of the employee */
	public String getName() {
		return name;
	}
	
	/** Return the id of the employee */
	public int getId() {
		return id;
	}
	
	/** Return the date of joining of the employee */
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	
	/** Return the title of the employee */
	public String getTitle() {
		return title;
	}
}
