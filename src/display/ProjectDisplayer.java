package display;

import java.util.List;

import misc.DateConvertor;
import project.Project;
import user.User;

/**
 * A class responsible for displaying information about a {@link Project} object.
 * <p>
 * This class extends {@link ItemDisplayer} and provides specific implementations
 * for formatting and printing the details of a project to the console. It includes
 * methods to display a single project or a list of projects, with options to
 * handle different user roles (e.g., applicants).
 * </p>
 */
public class ProjectDisplayer extends ItemDisplayer<Project> {
	
	/**
	 * Displays the complete details of a single {@link Project} object.
	 * <p>
	 * This method prints all attributes of a project, including its name, count,
	 * neighborhood, selling price, application dates, room type, and assigned officers.
	 * </p>
	 *
	 * @param project The {@link Project} object to be displayed.
	 * @throws IllegalArgumentException if the provided project is null.
	 */
	@Override
	public void display(Project project) throws IllegalArgumentException {
		if (project != null) {
			System.out.println("Project details:");
			
			System.out.println("Project name:");
			System.out.println("\t" + project.getName());
	
			System.out.println("Project count:");
			System.out.println("\t" + project.getCount());
			
			System.out.println("Neighbourhood:");
			System.out.println("\t" + project.getNeighbourhood());
			
			System.out.println("Selling Price:");
			System.out.println("\t" + project.getSellingPrice());
			
			System.out.println("Application Start Date (in the following format DD-MM-YYYY):");
			System.out.println("\t" + DateConvertor.formatLocalDate(project.getApplicationStartDate()));
			
			System.out.println("Application End Date (in the following format DD-MM-YYYY):");
			System.out.println("\t" + DateConvertor.formatLocalDate(project.getApplicationEndDate()));
			
			System.out.println("Room type choice:");
			System.out.println("\t" + project.getRoomType());
			
			System.out.println("Number of officers:");
			System.out.println("\t" + project.getOfficerSlot());
	
			System.out.println("Officers:");
			for (User officer : project.getOfficers()) {
				System.out.println("\t" + officer.getName());
			}
		} else {
			throw new IllegalArgumentException("No Project");
		}
	}
	
	/**
	 * Displays a list of projects, with a numbered format.
	 * <p>
	 * This method iterates through a list of projects and calls the overloaded
	 * {@link #display(Project, User, boolean)} method for each one, providing
	 * a user context. It adds a numbered prefix for each project.
	 * </p>
	 *
	 * @param projectList The {@link List} of projects to display.
	 * @param user        The user viewing the projects.
	 * @param asApplicant A flag indicating if the user is viewing the projects as an applicant.
	 */
	public void display(List<Project> projectList, User user, boolean asApplicant) {
		int i = 0;
		for (Project project : projectList) {
			System.out.print(++i + ". ");
			display(project, user, asApplicant);
			System.out.println("\n");
		}
	}
	
	/**
	 * Displays a single project, considering the user's role.
	 * <p>
	 * If the user is an applicant and the project is not visible, this method
	 * does not display the project's details. Otherwise, it calls the main
	 * {@link #display(Project)} method to print the project information.
	 * </p>
	 *
	 * @param project     The project to display.
	 * @param user        The user viewing the project.
	 * @param asApplicant A flag indicating if the user is an applicant.
	 * @throws IllegalArgumentException if the provided project is null.
	 */
	public void display(Project project, User user, boolean asApplicant) throws IllegalArgumentException {
		if (asApplicant && !project.isVisible()) {
			return;
		}
		display(project);
		System.out.println("\n");
	}
}