///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            program 2
// Files:            CompanyHierarchyMain.java; CompanyHierarchy.java; 
//                   CompanyHierarchyException.java; Employee.java; TreeNode.java
// Semester:         (course) Summer 2017
//
// Author:           (your name) Yifan Mei
// Email:            (your email address) ymei8@wisc.edu
// CS Login:         (your login name) yifanmei
// Lecturer's Name:  (name of your lecturer) Meena
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner) Yuran Liu
// Email:            (email address of your programming partner) yuran.liu@wisc.edu
// CS Login:         (partner's login name) yuran
// Lecturer's Name:  (name of your partner's lecturer) Meena
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////


/** CompanyHierarchyMain implementation 
 * CompanyHierarchyMain.java is the main class to add or remove employee(s), 
 * search employee(s) by their combination of id and name, replace employee by other one, 
 * get the list of employee(s) with the same supervisor or title or date of joining. 
 * In this main class, it include all methods in CompanyHierarchy.java, 
 * CompanyHierarchyException.java, TreeNode.java, Employee.java. Besides, all of the 
 * operations in this main class can be simply achieved by using the menu, say:
 *  a, s, d, etc.
 */

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;


public class CompanyHierarchyMain {

	private static CompanyHierarchy checkInputAndReturnTree (String [] args) {
		// *** Step 1: Check whether exactly one command-line argument is given *** 
		if (args.length != 1) {
            System.out.println("Usage: java -cp . CompanyHierarchyMain FileName");
            System.exit(0);
        }

        // *** Step 2: Check whether the input file exists and is readable ***
		File f = new File(args[0]);
		if (!f.exists() || !f.canRead()) {
			System.out.println("Error: Cannot access input file");
			System.exit(0);
		}
        /* Step 3: Load the data from the input file and use it to 
		 *  construct a company tree. Note: people are to be added to the 
		 *  company tree in the order in which they appear in the text file. 
		 */
		try {	
			CompanyHierarchy ceoc = new CompanyHierarchy();
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				String line = sc.nextLine().trim();
				String[] slines = line.split(",");
				for (int i = 0; i < slines.length; i++)
					slines[i] = slines[i].trim();
				Employee emp = new Employee(slines[0],Integer.parseInt(slines[1]),
						slines[2],slines[3]);
				//add CEO
				if (slines.length<5) {
					ceoc.addEmployee(emp, 0, null);
				}
				else {
					//add other employee(s)
					ceoc.addEmployee(emp, Integer.parseInt(slines[5]), slines[4]);
				}
			}
			return ceoc;
		} catch (Exception e) {
			System.out.println(e.getMessage());    
			return null;
		} 
		
	}
		
	

	/**use the file that read by the method private static CompanyHierarchy 
	 * checkInputAndReturnTree (String [] args) to implement the methods in CompanyHierarchy.java
	 * @param args
	 */
	public static void main(String[] args) {

		CompanyHierarchy tree = checkInputAndReturnTree(args);

		/* Step 4: Prompt the user to enter command options and 
		 *  process them until the user types x for exit. 
		 */
		boolean stop = false;

		Scanner stdin = new Scanner(System.in);
		while (!stop) {

			System.out.println("\nEnter Command: ");
			String input = stdin.nextLine();
			String remainder = null;
			//if the user enters more than one letter, the extra command is set as remainder
			if (input.length() > 0) {
				char option = input.charAt(0);
				if (input.length() > 1) {
					remainder = input.substring(1).trim();
				}

				//switch statement for the letter/command entered from the user
				switch (option) {

					/** a newid,newname,DOJ,title,supervisorId,supervisorName
					 * Add a new employee with given details to the company tree. 
                     * Display "Employee added" if the addition was successful. 
					 * If there is no such supervisor in the company tree, 
                     * display "Cannot add employee as supervisor was not found!"
                     */
					case 'a': {
						try {
							//for every method except the method e, we use the split function
							//for String to separate values for different parameters 
							String ss[] = remainder.split(",");
							Employee newe = new Employee(ss[1],Integer.parseInt(ss[0]),ss[2],ss[3]);
							if (tree.addEmployee(newe, Integer.parseInt(ss[4]), ss[5]) == false) {
								if(tree.getEmployee(Integer.parseInt(ss[0]),ss[1]) != null) 
									System.out.println("Employee already exists!");	
								else {
									if (tree.getEmployee(Integer.parseInt(ss[4]), ss[5]) == null) 
										System.out.println("Cannot add employee as supervisor was not found!");
									}	
							}
							else {
								tree.addEmployee(newe, Integer.parseInt(ss[4]), ss[5]);
								System.out.println("Employee added");		
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());    
						} 
						break;	
					}

						 /** s id name		Print the name(s) of all the 
                          * supervisors in the supervisor chain of the given 
                          * employee. Print the names on separate lines. 
                          * If no such employee is found, display 
                          * "Employee not found!"*/	
					case 's':{			
						try {
							String ss[] = remainder.split(",");
							if (tree.getEmployee(Integer.parseInt(ss[0]),ss[1]) == null)
								System.out.println("Employee not found!");
							else {
								Iterator<Employee> itr = tree.getSupervisorChain(
										Integer.parseInt(ss[0]),ss[1]).iterator();
								while (itr.hasNext()) 
									System.out.println(itr.next().getName());
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					}

						 /** d		Display information about the company tree 
                          * by doing the following:
						  * Display on a line: "# of employees in company tree: integer"
						  * This is the number of employees in this company tree.
						  *
						  * Display on a line: "max levels in company tree: integer"
						  * This is the maximum number of levels in the company tree.
						  *
						  * Display on a line: "CEO: name"
						  * This is the CEO in the company tree*/
					case 'd': {
						try {
							System.out.println("# of employees in company tree: "
						    		+ tree.getNumEmployees());
						    System.out.println("max levels in company tree: "
						    		+ tree.getMaxLevels());
						    System.out.println("CEO: " + tree.getCEO());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;	
					}

						 /** e title		Print the name(s) of the employee(s) 
                          *  that has the given title. Print the names on 
                          *  separate lines. If no such employee is found, 
                          *  display "Employee not found!" */
					case 'e': {
						try {
							if (tree.getEmployeeWithTitle(remainder) == null) 
								System.out.println("Employee not found!");
							else {
								Iterator<Employee> itr =tree.getEmployeeWithTitle(remainder).iterator();
								while (itr.hasNext())
									System.out.println(itr.next().getName());
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;	
					}

						 /** r id name		Remove the employee with given id 
                          * and name from the company tree and re-assign the 
                          * worker's to the removed employee's supervisor. 
                          * Display "Employee removed" after the removal. 
                          * If there is no such employee in the company tree, 
                          * display "Employee not found!" */
					case 'r': {
						try {
						
							String ss[] = remainder.split(",");
							if (tree.getEmployee(Integer.parseInt(ss[0]), ss[1]) == null)
							System.out.println("Employee not found!");
							else {
							tree.removeEmployee(Integer.parseInt(ss[0]), ss[1]);
							System.out.println("Employee removed");
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;	
					}

						 /** c id name		Print the name(s) of the 
                          * co-employees(sharing the same supervisor) of the 
                          * employee with given id and name. Print the names on 
                          * separate lines. If no such employee is found, 
                          * display "Employee not found!". If the employee has 
                          * no co-employee under the same supervisor, display 
                          * "The employee has no co-workers." */
					case 'c': {
						try {
							String ss[] = remainder.split(",");
							if (tree.getEmployee(Integer.parseInt(ss[0]),ss[1]) == null) 
								System.out.println("Employee not found!");
							else {
								List<Employee> gc = tree.getCoWorkers(
										Integer.parseInt(ss[0]),ss[1]);
								if (gc == null)
									System.out.println("The employee has no co-workers.");
								else {
									Iterator<Employee> itr =gc.iterator();
									while (itr.hasNext())
									System.out.println(itr.next().getName());	 
								}
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					}

						 /** u id name newid newname DOJ title		Replace the 
                          * employee with give id and name from the company tree 
                          * with the provided employee details. 
                          * Display "Employee replaced" after the removal. If 
                          * there is no such employee in the company tree, 
                          * display "Employee not found!" */
					case 'u': {
						try {
							String ss[] = remainder.split(",");
							Employee newE = new Employee(ss[3],Integer.parseInt(ss[2]),ss[4],ss[5]);
							if (tree.getEmployee(Integer.parseInt(ss[0]), ss[1]) == null)
									System.out.println("Employee not found!");
							else {
								tree.replaceEmployee(Integer.parseInt(ss[0]), ss[1], newE);
								System.out.println("Employee replaced");
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;	
					}

						 /** j startDate endDate		Print the name(s) of the 
                          * employee(s) whose date of joining are between 
                          * startDate and endDate(you may assume that startDate 
                          * is equal to or before end date). Print the names on 
                          * separate lines. If no such employee is found, 
                          * display "Employee not found!" */
					case 'j': {	 
						try {
							String ss[] = remainder.split(",");	
							if (ss[0].length() != 10 || ss[1].length()!=10)
								tree.getEmployeeInJoiningDateRange(ss[0], ss[1]);
							else {
								if (tree.getEmployeeInJoiningDateRange(ss[0], ss[1]) == null)
									System.out.println("Employee not found!");	
								else {
						    		Iterator<Employee> itr =tree.getEmployeeInJoiningDateRange(
											ss[0], ss[1]).iterator();	
						    		while (itr.hasNext())
										System.out.println(itr.next().getName());					    		
								}
							}
						} catch (Exception e) {
							System.out.println("Date parsing failed!");
						}
						break;	
					}

						 //***exits program***
					case 'x':{
							 stop = true;
							 System.out.println("exit");
							 break;
						 }
					default:
						 break;
				}

			}
		}
	}
}
