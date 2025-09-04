package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import application.Application;
import database.IDatabase;
import databasemgr.ApplicationDatabaseMgr;
import databasemgr.ProjectDatabaseMgr;
import databasemgr.UserDatabaseMgr;
import project.Project;
import user.Applicant;
import user.User;
import user.User.MARITAL_STATUS;
import userctrl.UserMgr;

/**
 * Reads applicant data from a CSV file and populates a list of {@code Applicant} objects.
 * Also, updates the loaded applicants with references to their applied project and applications
 * (project application and withdrawal application) by reading the file again and using IDs.
 * The file format is defined in the comments within the class.
 */
public class ApplicantReader implements IUserReader<Applicant>
{
	/*
	 * Saved in the following format:
	 * 
	 * User (all users have at least these):
	 * userID, name, password, age, maritalStatus
	 * 
	 * Applicants:
	 * appliedProject, projectApplication, withdrawalApplication,
	 * canApply, isWithdrawing, isReceiptReady (true/false)
	 *
	 * Total of 11 comma-separated fields. Empty fields are represented by null.
	 */
	
	/**
	 * Reads applicant data from the applicant file and creates a list of {@code Applicant} objects.
	 *
	 * @return An {@code ArrayList} of {@code Applicant} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	public ArrayList<Applicant> read()
	{
		UserMgr mgr = new UserMgr();
		ArrayList<Applicant> userList = new ArrayList<>();

		String userID, name, password;
		MARITAL_STATUS maritalStatus;
		
		int age;
		boolean canApply, isWithdrawing, isReceiptReady;
		Applicant applicant;

		String line;
        String data[];

        try(BufferedReader reader = new BufferedReader(new FileReader(applicantFile)))
        {
	        while ((line = reader.readLine()) != null)
	        {
	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 11) {
	                // malformed line
	            	System.err.println("Error parsing applicant line: " + line + " - " + data.length + " terms");
	                continue;
	            }
	            
	            try
	            {
		            for(int i = 0; i < 11; i++)
		            {
		            	if(data[i] != null)
		            	{
			            	data[i] = data[i].trim();
			            	if(data[i].length() == 0)
			            		data[i] = null;
		            	}
		            }
		            
		            userID = data[0];
		            name = data[1];
		            password = data[2];
		            age = Integer.parseInt(data[3]);
		            maritalStatus = MARITAL_STATUS.valueOf(data[4]);
		            
		            if(data[8] != null)
			            canApply = Boolean.parseBoolean(data[8]);
		            else
		            	canApply = true;

		            if(data[9] != null)
			            isWithdrawing = Boolean.parseBoolean(data[9]);
		            else
		            	isWithdrawing = false;
		            
		            if(data[10] != null)
			            isReceiptReady = Boolean.parseBoolean(data[10]);
		            else
		            	isReceiptReady = false;
		            
		            applicant = mgr.createApplicant(userID, name, password, age,
		            		maritalStatus, canApply, isWithdrawing, isReceiptReady);
		            if(applicant != null)
		            	userList.add(applicant);
	            }
	            catch (IllegalArgumentException | IndexOutOfBoundsException e) {
	                System.err.println("Error parsing applicant line: " + line + " - " + e.getMessage());
	                continue;
	            }
	            catch (Exception e) { // Catch other potential issues like NullPointer if lookups fail
	                System.err.println("Unexpected error processing applicant line: " + line + " - " + e.getMessage());
	                continue;
	            }

	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading applicant file: " + e.getMessage());
            return null;
        }
		return userList;
	}
	
	/**
	 * Updates the loaded {@code Applicant} objects with references to their applied project
	 * and associated applications (project and withdrawal) by reading the applicant file again
	 * and matching based on User IDs.
	 *
	 * @param userDatabase      The database containing all loaded {@code User} objects.
	 * @param applicationDatabase The database containing all loaded {@code Application} objects.
	 * @param projectDatabase   The database containing all loaded {@code Project} objects.
	 */
	public void updateApplicants(IDatabase<User> userDatabase, IDatabase<Application> applicationDatabase, IDatabase<Project> projectDatabase)
	{
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		ApplicationDatabaseMgr appMgr= new ApplicationDatabaseMgr();
		ProjectDatabaseMgr projMgr = new ProjectDatabaseMgr();

		String appliedProject, projectApplication, withdrawalApplication;
		Applicant applicant;

		String line;
        String data[];

        try(BufferedReader reader = new BufferedReader(new FileReader(applicantFile)))
        {
        while ((line = reader.readLine()) != null)
	        {
	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 11) {
	                // malformed line
	                continue;
	            }
	            
	            for(int i = 0; i < 11; i++)
	            {
	            	data[i] = data[i].trim();
	            	if(data[i].length() == 0)
	            		data[i] = null;
	            }
	            
	            appliedProject = data[5];
	            projectApplication = data[6];
	            withdrawalApplication = data[7];
	            
	            applicant = (Applicant) userMgr.getUser(userDatabase, data[0]);
	            if(data[5] != null)
	            	applicant.setAppliedProject(projMgr.getData(projectDatabase, appliedProject));
	            if(data[6] != null)
	            	applicant.setProjectApplication(appMgr.getData(applicationDatabase, projectApplication));
	            if(data[7] != null)
	            	applicant.setWithdrawalApplication(appMgr.getData(applicationDatabase, withdrawalApplication));
	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading applicant file: " + e.getMessage());
        }

	}
}
