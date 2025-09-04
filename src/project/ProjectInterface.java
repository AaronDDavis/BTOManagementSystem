package project;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.IDatabase;
import databasemgr.UserDatabaseMgr;
import misc.DateConvertor;
import user.HDBManager;
import user.HDBOfficer;
import user.User;

/**
 * Manages the user interface for creating and editing {@link Project} objects.
 * <p>
 * This class handles all user interactions related to project management,
 * including collecting input for creating a new project and providing options
 * for editing an existing one. It validates user input to ensure data
 * integrity before passing the information to the business logic layer.
 * </p>
 */
public class ProjectInterface {
	
	/**
	 * Guides an HDB Manager through the process of creating a new project.
	 * <p>
	 * This method prompts the user for all necessary project details,
	 * validates the input (e.g., dates, number of officers), and then
	 * uses a {@link ProjectMgr} to create the new {@link Project} object.
	 * </p>
	 *
	 * @param manager      The HDB Manager creating the project.
	 * @param userDatabase The database containing all user data, used to find HDB Officers.
	 * @return The newly created {@link Project} object.
	 */
	public Project createProject(HDBManager manager, IDatabase<User> userDatabase) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		ProjectMgr mgr = new ProjectMgr();
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		
		String name;
		int count;
		String neighbourhood;
		double sellingPrice;
		LocalDate applicationStartDate, applicationEndDate;
		Project.ROOM_TYPE roomType;
		int officerSlot;
		List<HDBOfficer> officers = new ArrayList<>();
		boolean isVisible;
		
		int roomTypeChoice;
		int choice;
		int visibilityChoice;
		HDBOfficer officer;

		System.out.println("Enter Project details:");
		
		System.out.println("\nEnter Project name:");
		name = sc.nextLine();
		
		System.out.println("\nEnter Project count:");
		count = sc.nextInt();
		sc.nextLine();
		
		System.out.println("\nEnter Neighbourhood:");
		neighbourhood = sc.nextLine();
		
		System.out.println("\nEnter Selling Price:");
		sellingPrice = sc.nextDouble();
		sc.nextLine();
		
		while (true) {
			System.out.println("\nEnter Application Start Date (in the following format DD-MM-YYYY):");
			try {
				applicationStartDate = DateConvertor.parseToLocalDate(sc.nextLine());
			} catch (DateTimeParseException e) {
				System.out.println("Invalid input. Incorrect format.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.println("\nEnter Application End Date (in the following format DD-MM-YYYY):");
			try {
				applicationEndDate = DateConvertor.parseToLocalDate(sc.nextLine());
			} catch (DateTimeParseException e) {
				System.out.println("Invalid input. Incorrect format.");
				continue;
			}
			
			if (mgr.isValidApplicationEndDate(applicationStartDate, applicationEndDate)) {
				break;
			} else {
				System.out.println("Please enter a valid end date.");
			}
		}
		
		while (true) {
			System.out.println("\nEnter Room Type choice:");
			System.out.println("2. 2 Room");
			System.out.println("3. 3 Room");
			roomTypeChoice = sc.nextInt();
			
			if (roomTypeChoice == 2) {
				roomType = Project.ROOM_TYPE._2Room;
			} else if (roomTypeChoice == 3) {
				roomType = Project.ROOM_TYPE._3Room;
			} else {
				System.out.println("Invalid input. Please try again.");
				continue;
			}
			break;
		}

		while (true) {
			System.out.println("\nEnter number of officers:");
			officerSlot = sc.nextInt();
			sc.nextLine();
			
			if (mgr.isValidOfficerSlot(officerSlot)) {
				break;
			} else {
				System.out.println("Please enter number of officers within the range: " + mgr.getOfficerSlotValidRange());
			}
		}
		
		System.out.println("\nPlease add officers:");
		while (true) {
			System.out.println("Enter choice:");
			System.out.println("1. Add Officer");
			System.out.println("2. Continue adding other details");
			choice = sc.nextInt();
			sc.nextLine();
			
			if (choice == 1) {
				System.out.println("Enter officer's userID:");
				officer = (HDBOfficer) userMgr.getUser(userDatabase, sc.nextLine());
				if (officer != null) {
					officers.add(officer);
					System.out.println("HDB Officer successfully added.\n");
				} else {
					System.out.println("HDB Officer does not exist.\nPlease try again.\n");
					continue;
				}
			} else if (choice == 2) {
				break;
			} else {
				System.out.println("Invalid choice.\nPlease try again.");
			}
		}
		
		while (true) {
			System.out.println("\nEnter choice for visibiliy to applicants:");
			System.out.println("1. Visible");
			System.out.println("2. Not Visible");

			visibilityChoice = sc.nextInt();
			sc.nextLine();

			if (visibilityChoice == 1) {
				isVisible = true;
			} else if (visibilityChoice == 2) {
				isVisible = false;
			} else {
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
			break;
		}
		
		return mgr.create(name, count, neighbourhood, roomType, sellingPrice, applicationStartDate, applicationEndDate, officerSlot, officers, isVisible, manager);
	}
	
	/**
	 * Guides an HDB Manager through the process of editing an existing project.
	 * <p>
	 * This method prompts the user for each attribute of the project and allows
	 * them to decide whether to edit it. It includes validation for certain
	 * inputs, such as dates and officer slots.
	 * </p>
	 *
	 * @param project The {@link Project} object to be edited.
	 */
	public void editProject(Project project) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		ProjectMgr mgr = new ProjectMgr();
		
		int roomTypeChoice;

		System.out.println("Enter Project details:");
		
		if (wantToEdit("name")) {
			System.out.println("Enter Project name:");
			project.setName(sc.nextLine());
		}

		if (wantToEdit("count")) {
			System.out.println("Enter Project count:");
			project.setCount(sc.nextInt());
			sc.nextLine();
		}

		if (wantToEdit("neighbourhood")) {
			System.out.println("Enter Neighbourhood:");
			project.setNeighbourhood(sc.nextLine());
		}

		if (wantToEdit("Selling Price")) {
			System.out.println("Enter Selling Price:");
			project.setSellingPrice(sc.nextDouble());
			sc.nextLine();
		}

		if (wantToEdit("Application Start Date")) {
			System.out.println("Enter Application Start Date (in the following format DD-MM-YYYY):");
			project.setApplicationStartDate(DateConvertor.parseToLocalDate(sc.nextLine()));
		}

		if (wantToEdit("Application End Date")) {
			while (true) {
				System.out.println("Enter Application End Date (in the following format MM-DD-YYYY):");
				LocalDate appEndDate = DateConvertor.parseToLocalDate(sc.nextLine());
				
				if (mgr.isValidApplicationEndDate(project.getApplicationStartDate(), appEndDate)) {
					project.setApplicationEndDate(appEndDate);
					break;
				} else {
					System.out.println("Please enter valid Application End Date. Please try again.");
				}
			}
		}

		if (wantToEdit("Room Type")) {
			while (true) {
				System.out.println("Enter Room Type choice:");
				System.out.println("2. 2 Room");
				System.out.println("3. 3 Room");
				roomTypeChoice = sc.nextInt();
				
				if (roomTypeChoice == 2) {
					project.setRoomType(Project.ROOM_TYPE._2Room);
				} else if (roomTypeChoice == 3) {
					project.setRoomType(Project.ROOM_TYPE._3Room);
				} else {
					System.out.println("Invalid input. Please try again.");
					continue;
				}
				
				System.out.println("Room type successfully set to " + roomTypeChoice + " Room");
				break;
			}
		}
		
		if (wantToEdit("Number of Officers")) {
			while (true) {
				System.out.println("Enter number of officers:");
				int officerSlot = sc.nextInt();
				sc.nextLine();
				
				if (mgr.isValidOfficerSlot(officerSlot)) {
					project.setOfficerSlot(officerSlot);
					break;
				} else {
					System.out.println("Please enter valid Number of Officers. Please try again.");
				}
			}
		}
		
		if (wantToEdit("Visibility")) {
			while (true) {
				System.out.println("Enter choice for visibiliy to applicants:");
				System.out.println("1. Visible");
				System.out.println("2. Not Visible");
				System.out.println("3. Toggle Visibility");
				
				int visibilityChoice = sc.nextInt();
				sc.nextLine();

				if (0 < visibilityChoice && visibilityChoice < 4) {
					project.setVisibility(visibilityChoice == 1 ? true : visibilityChoice == 2 ? false : !project.isVisible());
					break;
				} else {
					System.out.println("Invalid input. Please try again.");
				}
			}
		}
	}
	
	/**
	 * A private helper method to ask the user if they want to edit a specific term.
	 *
	 * @param term The attribute or term to be edited (e.g., "name", "count").
	 * @return {@code true} if the user wants to edit, {@code false} otherwise.
	 */
	private boolean wantToEdit(String term) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		char choice;
		
		while (true) {
			System.out.println("\nDo you want to edit the " + term + "?"
					+ "\n(Enter Y/N)");
			choice = Character.toUpperCase(sc.nextLine().charAt(0));
			
			if (choice == 'Y') {
				return true;
			} else if (choice == 'N') {
				return false;
			} else {
				System.out.println("Invalid input. Please try again.");
			}
		}
	}
}