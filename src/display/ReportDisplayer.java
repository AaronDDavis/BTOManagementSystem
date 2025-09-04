package display;

import user.Applicant;

/**
 * A class responsible for displaying a report for an {@link Applicant}.
 * <p>
 * This class extends {@link ItemDisplayer} and provides a specific implementation
 * for formatting and printing an applicant's report. It is designed to display
 * key information about the applicant and their application details in a clear
 * and structured format.
 * </p>
 */
public class ReportDisplayer extends ItemDisplayer<Applicant> {

	/**
	 * Displays a report for a single {@link Applicant} object to the standard output.
	 * <p>
	 * The report includes the applicant's name, the project they applied for,
	 * the room type of the applied project, their age, and their marital status.
	 * </p>
	 *
	 * @param applicant The {@link Applicant} object whose report is to be displayed.
	 */
	@Override
	public void display(Applicant applicant) {
		System.out.println("Applicant Name:\t" + applicant.getName());
		System.out.println("Project Name:\t" + applicant.getAppliedProject().getName());
		System.out.println("Room Type:\t" + applicant.getAppliedProject().getRoomType());
		System.out.println("Age:\t" + applicant.getAge());
		System.out.println("Marital Status:\t" + applicant.getMaritalStatus().toString().toLowerCase());
		System.out.println();
	}
}