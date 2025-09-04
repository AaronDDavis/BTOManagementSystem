package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import application.Application;
import project.Project;
import user.HDBOfficer;

/**
 * Writes a list of {@code HDBOfficer} objects to a CSV file.
 * The data is saved in a specific format, including user details, applicant-like statuses,
 * and lists of joined projects, registered projects, and project registrations.
 * Project and application IDs within the lists are separated by semicolons.
 */
public class HDBOfficerWriter implements IUserWriter<HDBOfficer>
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
	 * Writes the provided list of HDB Officers to the HDB Officer data file.
	 * Each officer's information is written on a new line, with fields separated by commas.
	 * The format includes basic user details (userID, name, password, age, marital status),
	 * applicant-like statuses (appliedProject ID, projectApplication ID, withdrawalApplication ID,
	 * canApply, isWithdrawing, isReceiptReady), followed by semicolon-separated lists of
	 * joined project IDs, registered project IDs, and project registration application IDs.
	 *
	 * @param officerList The {@code ArrayList} of {@code HDBOfficer} objects to write.
	 */
	@Override
	public void write(List<HDBOfficer> officerList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(HDBOfficerFile)))
        {
    		for(HDBOfficer officer: officerList)
    		{
    			line = officer.getUserID() + ", "
    					+ officer.getName() + ", "
    					+ officer.getPassword() + ", "
    					+ officer.getAge() + ", "
    					+ officer.getMaritalStatus() + ", "
    					+ (officer.getAppliedProject() != null ?
    							officer.getAppliedProject().getID(): "") + ", "
						+ (officer.getProjectApplication() != null ?
    							officer.getProjectApplication().getID(): "") + ", "
						+ (officer.getWithdrawalApplication() != null ?
    							officer.getWithdrawalApplication().getID(): "") + ", "
    					+ officer.canApply() + ", "
    					+ officer.isWithdrawing() + ", "
    					+ officer.isReceiptReady() + ", ";
    			
    			if(officer.getJoinedProjects() != null)
	    			for(Project project: officer.getJoinedProjects())
	    			{
	    				if(!line.endsWith(", "))
	    					line += "; ";
	    				line += project.getID();
	    			}
    			line += ", ";

    			if(officer.getRegisteredProjects() != null)
	    			for(Project project: officer.getRegisteredProjects())
	    			{
	    				if(!line.endsWith(", "))
	    					line += "; ";
	    				line += project.getID();
	    			}
    			line += ", ";
    			
    			if(officer.getProjectRegistration() != null)
	    			for(Application application: officer.getProjectRegistration())
	    			{
	    				if(!line.endsWith(", "))
	    					line += "; ";
	    				line += application.getID();
	    			}

    			writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing HDBOfficer file: " + e.getMessage());
        }

	}

}
