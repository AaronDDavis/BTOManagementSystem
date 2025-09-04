package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import user.Applicant;

/**
 * Writes a list of {@code Applicant} objects to a CSV file.
 * The data is saved in a specific format, including user details and applicant-specific attributes.
 */
public class ApplicantWriter implements IUserWriter<Applicant>
{
	/*
	 * Saved in the following format:
	 * 
	 * User (all users have at least these):
	 * userID, name, password, age, isMarried
	 * 
	 * Applicants:
	 * appliedProject, projectApplication, withdrawalApplication,
	 * canApply, isWithdrawing, isReceiptReady (true/false)
	 * 
	 * HDBOfficers:
	 * [joinedProjects], [registeredProjects],
	 * [projectRegistration] (Individually separated by ;)
	 */

	/**
	 * Writes the provided list of applicants to the applicant data file.
	 * Each applicant's information is written on a new line, with fields separated by commas.
	 * The format includes basic user details (userID, name, password, age, marital status)
	 * and applicant-specific details (applied project ID, project application ID,
	 * withdrawal application ID, canApply status, isWithdrawing status, isReceiptReady status).
	 *
	 * @param applicantList The {@code ArrayList} of {@code Applicant} objects to write.
	 */
	@Override
	public void write(List<Applicant> applicantList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(applicantFile)))
        {
    		for(Applicant applicant: applicantList)
    		{
    			line = applicant.getUserID() + ", "
    					+ applicant.getName() + ", "
    					+ applicant.getPassword() + ", "
    					+ applicant.getAge() + ", "
    					+ applicant.getMaritalStatus() + ", "
    					+ (applicant.getAppliedProject() != null ?
    							applicant.getAppliedProject().getID(): "") + ", "
						+ (applicant.getProjectApplication() != null ?
    							applicant.getProjectApplication().getID(): "") + ", "
						+ (applicant.getWithdrawalApplication() != null ?
    							applicant.getWithdrawalApplication().getID(): "") + ", "
    					+ applicant.canApply() + ", "
    					+ applicant.isWithdrawing() + ", "
    					+ applicant.isReceiptReady();
        		writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing applicant file: " + e.getMessage());
        }

	}

}
