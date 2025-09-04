package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import application.Application;

/**
 * Writes a list of {@code Application} objects to a CSV file.
 * The data is saved in a specific format, including application ID, user ID,
 * project ID, application type, and application status.
 */
public class ApplicationWriter implements IWriter<Application>
{
	/*
	 * Applications are stored in the following format:
	 * applicationID, userID, projectID, applicationType, applicationStatus
	 */

	/**
	 * Writes the provided list of applications to the application data file.
	 * Each application's information is written on a new line, with fields separated by commas.
	 * The format includes application ID, user ID, project ID, application type, and application status.
	 *
	 * @param applicationList The {@code ArrayList} of {@code Application} objects to write.
	 */
	@Override
	public void write(List<Application> applicationList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(applicationFile)))
        {
    		for(Application application: applicationList)
    		{
    			line = application.getID() + ", "
    			 + application.getUser().getUserID() + ", "
    			 + application.getProject().getID() + ", "
    			 + application.getApplicationType().name() + ", "
    			 + application.getStatus().name();
        		writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing application file: " + e.getMessage());
        }

	}
}
