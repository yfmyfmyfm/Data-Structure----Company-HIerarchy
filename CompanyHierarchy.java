///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  CompanyHierarchyMain.java
// File:             CompanyHierarchy.java
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

/** The CompanyHierarchy class implements the TreeNode and Employee classes,
 * and represents the whole company tree. Within the CompanyHierarchy class, we 
 * construct methods to get: the CEO, number of people in the company, level of 
 * employee, employee(s) by their id and name, list of supervisors for one given
 * employee, list of employees with the same supervisor or title or date of 
 * joining, and we can replace the employee by another, check whether there is 
 * this employee in this company by using their id and name, add employee(s),
 * and remove employee(s).
 */

/**
 * when a null parameter value is passed, or <0 value is passed for id, 
 * throw a java.lang.IllegalArgumentException. 
 * If root of the CompanyHierarchy tree is null, then return an appropriate value.
 * 
 * When you run into ParseException while using SimpleDateFormat, 
 * catch the exception and throw a CompanyHierarchyException with this message: 
 * "Date parsing failed!".
 */

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CompanyHierarchy {
	private TreeNode root;
	//Used for recursive search of node
	private TreeNode nodeForSearching;
	//Used for recursive search of lists of nodes
	private List<Employee> nodeList;
	//Used for recursive search to find level of person
	private int levelOfEmployee;
	private int numOfPeople;

        /** Constructs a CompanyHierarchy tree */
	public CompanyHierarchy(){
		root = null;
		nodeForSearching = new TreeNode(null, null);
		nodeList = null;
		levelOfEmployee = 0;
		numOfPeople = 0;
	}

	/** Get the name of the CEO in this company tree */	
	public String getCEO(){
		if (root == null) 
			return null;
		return root.getEmployee().getName();
	}
	
	/** Return the number of employees in this company tree*/
	public int getNumEmployees() {
		if (root == null)
			return 0;
		return numOfPeople;
	}
	
	/** Return the number of levels in the tree : 0+ values*/
	public int getMaxLevels() {
		if (root == null)
			return 0;
		return 0+levelOfEmployee;
		
	}

	
	/**Return the employee details of given employee id and name 
	Return null if no such employee was found 
	If you found a match with employee id, but the name is different, 
	then throw a CompanyHierarchyException with this message: "Incorrect employee name for id!"
	 * @throws CompanyHierarchyException 
	*/
	public Employee getEmployee(int id, String name) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		if (getTreeNode(root, id, name, "Incorrect employee name for id!") == null)
			return null;
		else
			return getTreeNode(root, id, name, "Incorrect employee name for id!").getEmployee();
	}
		
	
	

	/**
	 * Adds employee as a child to the given supervisor node if supervisor exists on tree 
	 * When the root and supervisorName are both null, 
	 * you should set the employee as the root and return true. 
	 * Do not throw an IllegalArgumentException for this circumstance alone. 
	 * If there is no such supervisor, return false 
	 * If the company hierarchy tree already has this employee, 
	 * don't do anything and return false; otherwise add the employee and return true 
	 * If you found that another employee in the CompanyHierarchy tree 
	 * that has this same id (that is your employee instance will have an ID) 
	 * then throw CompanyHierarchyException with this message: "Id already used!" 
	 * If you found a match with supervisor id, but the supervisor name is different, 
	 * then throw a CompanyHierarchyException with this message: "Incorrect supervisor name for id!"
	 * @param employee
	 * @param supervisorId
	 * @param supervisorName
	 * @return true or false
	 */
	public boolean addEmployee(Employee employee, int supervisorId, String supervisorName) {
		if (root == null && supervisorName == null) {
			root = new TreeNode(employee, null);
			numOfPeople++;
			levelOfEmployee++;
			return true;
		}
		if (!contains(supervisorId, supervisorName, "Incorrect supervisor name for id")) {
			return false;
		}
		else {
			if (contains(employee.getId(),employee.getName(), "Id already used!")) {
			return false;
		}
			else {
				TreeNode supervisornode = getTreeNode(root, supervisorId, 
						supervisorName, "Incorrect supervisor name for id!");
				TreeNode newEmployee = new TreeNode(employee, supervisornode);
				supervisornode.addWorker(newEmployee);
				numOfPeople++;	
				levelOfEmployee = height(root);
				return true;
			}
			
		}
		
	}


	/**
	 * Return true if the company hierarchy tree contains employee with given name, otherwise return false 
	 * If the employee id matches, but name doesn't match, 
	 * then throw a CompanyHierarchyException with the message as passed by argument exceptionMessage
	 * @param id
	 * @param name
	 * @param exceptionMessage
	 * @return true or false
	 */
	public boolean contains(int id, String name, String exceptionMessage) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		if (getTreeNode(root, id, name, exceptionMessage) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/** 
	 * Removes the given employee(if found on the tree) 
	 * and updates all the workers to report to the given employee's supervisor 
	 * Make sure to update all the required fields due to this supervisor-worker relationship change 
	 * Returns true or false accordingly 
	 * If you found a match with employee id, but the name is different, 
	 * then throw a CompanyHierarchyException with this message: "Incorrect employee name for id!" 
	 * If asked to remove the root, then throw a CompanyHierarchyException with this message: 
	 * "Cannot remove CEO of the company!" 
	 * You may use any methods in the Java's ArrayList<E> class          
     */
	public boolean removeEmployee(int id, String name) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		TreeNode rmE = getTreeNode(root, id, name, "Incorrect employee name for id!");
		if (rmE == null) {
			return false;
		}
		if (rmE == root) {
			String msg = "Cannot remove CEO of the company!";
			throw new CompanyHierarchyException(msg);
		}
		while (rmE.getWorkers().iterator().hasNext()) {
			TreeNode rmEwk = rmE.getWorkers().iterator().next();
			rmE.getSupervisor().addWorker(rmEwk);
			rmEwk.updateSupervisor(rmE.getSupervisor());
			rmE.getWorkers().remove(rmEwk);
		}
		rmE.getSupervisor().getWorkers().remove(rmE);
		rmE.updateSupervisor(null);
		numOfPeople--;
		levelOfEmployee = height(root);
		return true;
	}

	/** 
	 * Replaces the given employee(if found on the tree) 
	 * and if the new employee title matches with old employee 
	 * Returns true or false accordingly 
	 * If the new employee id (in this case consider only full employee details match) 
	 * already exists on the company tree, 
	 * then throw a CompanyHierarchyException with this message: 
	 * "Replacing employee already exists on the Company Tree!" 
	 * If you found that another employee in the CompanyHierarchy tree that has this same id 
	 * (that is your newEmployee instance will have an ID) 
	 * then throw CompanyHierarchyException with this message: "Id already used!" 
	 * If the new employee title doesn't match with the old employee's title, 
	 * then throw a CompanyHierarchyException with this message: 
	 * "Replacement title does not match existing title!" 
	 */	    
	public boolean replaceEmployee(int id, String name, Employee newEmployee) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		if (contains(newEmployee.getId(), newEmployee.getName(), "Id already used!" )) {
			String msg = "Replacing employee already exists on the Company Tree!";
			throw new CompanyHierarchyException(msg);
		}
		
		TreeNode oldEmployee =getTreeNode(root, id, name, "Incorrect employee name for id!");
		if (oldEmployee != null) {
			if (oldEmployee.getEmployee().getTitle().equals(newEmployee.getTitle())) {
				oldEmployee.updateEmployee(newEmployee);
				return true;
			}
			else {
				String msg = "Replacement title does not match existing title!";
				throw new CompanyHierarchyException(msg);
			}
		}
		return false;
			
		
	}

	/** Search and return the list of employees with the provided title; if none 
        *  found return null */
	public List<Employee> getEmployeeWithTitle(String title) {
		if (title == null) throw new IllegalArgumentException();
		List<Employee> newge = new ArrayList<Employee>(); 
		List<Employee> gt = gEWT(newge, root, title);
		if (gt.size() == 0)
			return null;
		else 
			return gt;
	}
	


	/**
	 * Search and return the list of employees with date of joing within the 
	 * provided range; If none found return null 
	 * Assume that mm/dd/yyyy is the format which you have to expect. 
	 * Use the SimpleDateFormat to parse the date and once parsed use any relevant functions 
	 * from Date to perform comparison.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Employee> getEmployeeInJoiningDateRange(String startDate, String endDate) {
		if (startDate == null || endDate == null) throw new IllegalArgumentException();
		List<Employee> newged = new ArrayList<Employee>(); 
		
		String sr ="";
		String er = "";
		//make sure that the date type is MM/dd/yyyy
		if (startDate.length() == 10) {
			String rangem = startDate.substring(0, 2);
			String ranged = startDate.substring(3, 5);
			String rangey = startDate.substring(6, 10);
			sr = rangem + "/" + ranged + "/" + rangey;
		}
		if (endDate.length() == 10) {
			String rangem = endDate.substring(0, 2);
			String ranged = endDate.substring(3, 5);
			String rangey = endDate.substring(6, 10);
			er = rangem + "/" + ranged + "/" + rangey;
		}		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 	
		Date std = new Date();
		Date ed = new Date();
		try {
			std = df.parse(sr);
			ed = df.parse(er);
			List<Employee> gd = gEWD(newged, root, std, ed);
			if (gd.size() == 0)
				return null;
			else
				return gd;	
		} catch (ParseException e) {
			System.out.println("Date parsing failed!");
			return null;
		}
	}

	/** Return the list of employees who are in the same level as the given 
        *  employee sharing the same supervisor */
	/**
	 * Return the list of employees who are in the same level as the given 
	 * employee sharing the same supervisor 
	 * If you found a match with employee id, but the name is different, 
	 * then throw a CompanyHierarchyException with this message: "Incorrect employee name for id!" 
	 * @param id
	 * @param name
	 * @return
	 */
	public List<Employee> getCoWorkers(int id, String name) {
		if (id <0 || name ==null) throw new IllegalArgumentException();
		List<Employee> gcw = new ArrayList<Employee>();
		TreeNode thisEp = getTreeNode(root, id, name, "Incorrect employee name for id!" );
		if (thisEp.getSupervisor() == null)
			return null;
		List<TreeNode> gcwTree = thisEp.getSupervisor().getWorkers();
		for (int i = 0; i < gcwTree.size(); i++) {
			if (gcwTree.get(i) != thisEp) {
				gcw.add(gcwTree.get(i).getEmployee());
			}
		}
		if (gcw.size() == 0)
			return null;
		else
			return gcw;
	}


	/** 
	 * Returns the supervisor list(till CEO) for a given employee 
	 * Returns null if employee is not found 
	 * If you found a match with employee id, but the name is different, 
	 * then throw a CompanyHierarchyException with this message: "Incorrect employee name for id!" 
	 * If asked to find supervisor chain of the root, 
	 * then throw a CompanyHierarchyException with this message: 
	 * "No Supervisor Chain found for that employee!" 
	 * @param id
	 * @param name
	 * @return
	 */
	public List<Employee> getSupervisorChain(int id, String name) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		List<Employee> gsc = new ArrayList<Employee>();
		TreeNode gsct = getTreeNode(root, id, name, "Incorrect employee name for id!" );
		if (gsct == root) {
			String msg = "No Supervisor Chain found for that employee!";
			throw new CompanyHierarchyException(msg);
		}
		while (gsct.getSupervisor() != null) {
			gsct = gsct.getSupervisor();
			gsc.add(gsct.getEmployee());	
		}
		return gsc;
	}

	/////////////////////////////////////////////
	// Private helper methods below

	private void checkParam(Object parameter) {
		
	}
	
	private void checkIntParam(int parameter) {
		
	}
	
	// use id and name to find the corresponding TreeNode
	// the most frequent used function in the whole file
	private TreeNode getTreeNode(TreeNode nodeForSearching, int id, String name, String exceptionName) {
		if (id <0 || name == null) throw new IllegalArgumentException();
		if (nodeForSearching.getEmployee().getId() == id) {		
			if (nodeForSearching.getEmployee().getName().equals(name)) {
				return nodeForSearching;
			}
			else {
				String msg = exceptionName;
				throw new CompanyHierarchyException(msg);
			}
		}
		TreeNode temp = null;
		Iterator<TreeNode> itr= nodeForSearching.getWorkers().iterator();
		while (itr.hasNext() && nodeForSearching.getWorkers().size() != 0) {
			TreeNode worker = itr.next();
			temp = getTreeNode(worker, id, name,exceptionName);
			if (temp != null)
				break;
		}	    	
		return temp;
	}
	
	//search for the list of employee(s) with the same title. Adding a List<Employee> named
	//ge to represent the return value
	private List<Employee> gEWT(List<Employee> ge, TreeNode nodeForSearching, String title) {
		if (title == null) throw new IllegalArgumentException();
		String ot = nodeForSearching.getEmployee().getTitle();
		boolean judge = (ot.equals(title));
		if (judge == true) {	
			ge.add(nodeForSearching.getEmployee());	
		}
		Iterator<TreeNode> itr = nodeForSearching.getWorkers().iterator();
		while (itr.hasNext() 
				&& nodeForSearching.getWorkers().size() != 0) {
			TreeNode temp = itr.next();
			gEWT(ge, temp, title);	
		}	    	
		return ge;
	}
	
	
	//adding one more parameter ge, which is a List of Employee, to be the return value
	private List<Employee> gEWD(List<Employee> ge, TreeNode nodeForSearching, Date start, Date end) {
		String ds = nodeForSearching.getEmployee().getDateOfJoining();
		String dr = "";
		//for the date that is exactly the 10 digits, make sure their type is xx/xx/xxxx
		if (ds.length() == 10) {
			String rangem = ds.substring(0, 2);
			String ranged = ds.substring(3, 5);
			String rangey = ds.substring(6, 10);
			dr = rangem + "/" + ranged + "/" + rangey;
		}
				
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = new Date();	
		try {
			//change String type to Date type
			date = df.parse(dr);
			boolean judge = date.before(end) && date.after(start);
			if (judge == true) {	
				ge.add(nodeForSearching.getEmployee());		
			}
			for (int i = 0; i < nodeForSearching.getWorkers().size(); i++) {
				TreeNode temp = nodeForSearching.getWorkers().get(i);	
				gEWD(ge, temp, start, end);	
			}    	
			return ge;	
		} catch (ParseException e) {
			System.out.println("Date parsing failed!");
			return null;
		}
	}
	
	//the codes that is given in class to calculate the level of a tree 
	private int height(TreeNode n){  
		if(n == null) return 0;
		if(n.getWorkers().isEmpty()) return 1;
		int maxHeight = 0;
		Iterator<TreeNode> itr = n.getWorkers().iterator();
		while(itr.hasNext()){
			int childHeight = height(itr.next());
			if(childHeight > maxHeight) {maxHeight = childHeight;}
		}
		return 1 + maxHeight;
	}

}
