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
import user.HDBOfficer;
import user.User;
import user.User.MARITAL_STATUS;
import userctrl.UserMgr;

/**
 * Reads HDB officer data from a CSV file and populates a list of {@code HDBOfficer} objects.
 * Also, updates the loaded officers with references to their joined projects, registered projects,
 * and project registrations by reading the file again and using IDs.
 * The file format is defined in the comments within the class.
 */
public class HDBOfficerReader implements IUserReader<HDBOfficer>
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
	 * 
	 * Total of 14 comma-separated fields. Empty fields are represented by null.
	 */
	
	/**
	 * Reads HDB officer data from the HDB officer file and creates a list of {@code HDBOfficer} objects.
	 *
	 * @return An {@code ArrayList} of {@code HDBOfficer} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	public ArrayList<HDBOfficer> read()
	{
		UserMgr mgr = new UserMgr();
		ArrayList<HDBOfficer> userList = new ArrayList<>();

		HDBOfficer officer;
		String userID, name, password;
		MARITAL_STATUS maritalStatus;
		int age;
		boolean canApply, isWithdrawing, isReceiptReady;

		String line;
        String data[];

        try(BufferedReader reader = new BufferedReader(new FileReader(HDBOfficerFile)))
        {
	        while ((line = reader.readLine()) != null)
	        {
	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 14) {
	                // malformed line
	                continue;
	            }
	            
	            try
	            {
		            for(int i = 0; i < 14; i++)
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
		            
		            officer = mgr.createHDBOfficer(userID, name, password, age,
		            		maritalStatus, canApply, isWithdrawing, isReceiptReady);
		            if(officer != null)
		            	userList.add(officer);
	            }
	            catch (IllegalArgumentException | IndexOutOfBoundsException e) {
	                System.err.println("Error parsing officer line: " + line + " - " + e.getMessage());
	                continue;
	            }
	            catch (Exception e) { // Catch other potential issues like NullPointer if lookups fail
	                System.err.println("Unexpected error processing officer line: " + line + " - " + e.getMessage() + " - " + e.getStackTrace().toString());
	                continue;
	            }
	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading officer file: " + e.getMessage());
            return null;
        }
		return userList;
	}

	/**
	 * Updates the loaded {@code HDBOfficer} objects with references to their joined projects,
	 * registered projects, and project registrations by reading the officer file again
	 * and matching based on User IDs.
	 *
	 * @param userDatabase      The database containing all loaded {@code User} objects.
	 * @param applicationDatabase The database containing all loaded {@code Application} objects.
	 * @param projectDatabase   The database containing all loaded {@code Project} objects.
	 */
	public void updateHDBOfficers(IDatabase<User> userDatabase, IDatabase<Application> applicationDatabase, IDatabase<Project> projectDatabase)
	{
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		ApplicationDatabaseMgr appMgr= new ApplicationDatabaseMgr();
		ProjectDatabaseMgr projMgr = new ProjectDatabaseMgr();

		String appliedProject, projectApplication, withdrawalApplication;
		String[] strJoinedProjects, strRegisteredProjects, strProjectRegistrations;
		HDBOfficer officer;

		String line;
        String[] data;

        try(BufferedReader reader = new BufferedReader(new FileReader(HDBOfficerFile)))
        {
	        while ((line = reader.readLine()) != null)
	        {
	        	ArrayList<Project> joinedProjects = new ArrayList<>();
	        	ArrayList<Project> registeredProjects = new ArrayList<>();
	        	ArrayList<Project> prohibitedProjects = new ArrayList<>();
	        	ArrayList<Application> projectRegistrations = new ArrayList<>();
	        	Project project;

	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 14) {
	                // malformed line
	                continue;
	            }
	            
	            try
	            {
		            for(int i = 0; i < 14; i++)
		            {
		            	if(data[i] != null)
		            	{
			            	data[i] = data[i].trim();
			            	if(data[i].length() == 0)
			            		data[i] = null;
		            	}
		            }
		            
		            appliedProject = data[5];
		            projectApplication = data[6];
		            withdrawalApplication = data[7];
		            
		            if(data[11] != null)
		            {
			            strJoinedProjects = data[11].split(";");            
			            for(String proj: strJoinedProjects)
			            {
			            	proj = proj.trim();
			            	if(!proj.isEmpty())
			            	{
			            		project = projMgr.getData(projectDatabase, proj);
			            		if(project == null)
					            	System.err.println("Project not loaded - " + proj);
			            		joinedProjects.add(project);
			            	}
			            }
		            }
		
		            if(data[12] != null)
		            {
				        strRegisteredProjects = data[12].split(";");
			            for(String proj: strRegisteredProjects)
			            {
			            	proj = proj.trim();
			            	if(!proj.isEmpty())
			            	{
			            		project = projMgr.getData(projectDatabase, proj);
			            		if(project == null)
					            	System.err.println("Project not loaded - " + proj);
			            		registeredProjects.add(project);
			            	}
			            }
		            }
		
		            if(data[13] != null)
		            {
				        strProjectRegistrations = data[13].split(";");
			            for(String app: strProjectRegistrations)
			            {
			            	app = app.trim();
			            	if(!app.isEmpty())
			            	projectRegistrations.add(appMgr.getData(applicationDatabase, app));
			            }
		            }
		            
		            if(data[11] != null)
		            	prohibitedProjects.addAll(joinedProjects);
		            if(data[12] != null)
	            		prohibitedProjects.addAll(registeredProjects);
		            
		            officer = (HDBOfficer) userMgr.getUser(userDatabase, data[0]);
		            if(officer == null)
		            	System.err.println("User not loaded - " + data[0]);
		            
		            if(data[5] != null)
		            	officer.setAppliedProject(projMgr.getData(projectDatabase, appliedProject));
		            if(data[6] != null)
		            	officer.setProjectApplication(appMgr.getData(applicationDatabase, projectApplication));
		            if(data[7] != null)
		            	officer.setWithdrawalApplication(appMgr.getData(applicationDatabase, withdrawalApplication));
		            if(data[11] != null)
		            	officer.setJoinedProjects(joinedProjects);
		            if(data[12] != null)
			            officer.setRegisteredProjects(registeredProjects);
		            if(data[11] != null || data[12] != null)
			            officer.setProhibitedProjects(prohibitedProjects);
		            if(data[13] != null)
			            officer.setProjectRegistration(projectRegistrations);
		        }
	            catch (Exception e) { // Catch other potential issues like NullPointer if lookups fail
	                System.err.println("Unexpected error processing officer line: " + line + " - " + e.getMessage());
	                continue;
	            }
	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading officer file: " + e.getMessage());
        }
	}
}
