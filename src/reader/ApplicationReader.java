package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import application.Application;
import application.ApplicationType;
import application.ApplicationStatus;
import application.ApplicationMgr;
import database.IDatabase;
import databasemgr.ProjectDatabaseMgr;
import databasemgr.UserDatabaseMgr;
import project.Project;
import user.User;

/**
 * Reads application data from a CSV file and populates a list of {@code Application} objects.
 * The file format is defined in the comments within the class.
 */
public class ApplicationReader implements IReader<Application>
{
	/*
	 * Applications are stored in the following format:
	 * applicationID, userID, projectID, applicationType, applicationStatus
	 */
	
	/**
	 * Constructs an {@code ApplicationReader}.
	 */
	public ApplicationReader()
	{
		// default constructor
	}
	
	/**
	 * Reads application data from the application file, creates {@code Application} objects,
	 * and associates them with the corresponding {@code User} and {@code Project} objects
	 * from the provided databases.
	 *
	 * @param userDatabase    The database containing {@code User} objects.
	 * @param projectDatabase The database containing {@code Project} objects.
	 * @return An {@code ArrayList} of {@code Application} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	// @Override
	public ArrayList<Application> read(IDatabase<User> userDatabase, IDatabase<Project> projectDatabase)
	{
		ArrayList<Application> applicationList = new ArrayList<>();
		User user;
		Project project;
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		ProjectDatabaseMgr projMgr = new ProjectDatabaseMgr();
		ApplicationMgr appMgr = new ApplicationMgr();
		Application application;

		String line;
        String[] data;

        try(BufferedReader reader = new BufferedReader(new FileReader(applicationFile)))
        {
	        while ((line = reader.readLine()) != null)
	        {
	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 5) {
	                // malformed line
	                continue;
	            }
	            
	            for(int i = 0; i < data.length; i++)
	            {
	            	data[i] = data[i].trim();
	            	if(data[i].isEmpty())
	            		data[i] = null;
	            }
	            
	            user = userMgr.getUser(userDatabase, data[1]);
	            project = projMgr.getData(projectDatabase, data[2]);
	            
	            if(user == null || project == null)
	            	continue;

	            application = appMgr.create(data[0], user, project,
	            		ApplicationType.valueOf(data[3]),
	            		ApplicationStatus.valueOf(data[4]));
	            
            	applicationList.add(application);
	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading application file: " + e.getMessage());
            return null;
        }

		return applicationList;
	}
}
