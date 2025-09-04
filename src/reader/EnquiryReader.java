package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import database.IDatabase;
import databasemgr.ProjectDatabaseMgr;
import databasemgr.UserDatabaseMgr;
import enquiry.Enquiry;
import enquiry.EnquiryMgr;
import project.Project;
import user.User;
import user.Applicant;

/**
 * Reads enquiry data from a CSV file and populates a list of {@code Enquiry} objects.
 * The file format is defined in the comments within the class.
 */
public class EnquiryReader implements IReader<Enquiry>
{
	/*
	 * Make sure no comma in question or reply when inputting
	 * Follows this format:
	 * enquiryID, applicantFilerID, projectID, question, reply
	 */
	
	/**
	 * Constructs an {@code EnquiryReader}.
	 */
	public EnquiryReader()
	{
		// default constructor
	}

	/**
	 * Reads enquiry data from the enquiry file, creates {@code Enquiry} objects,
	 * and associates them with the corresponding {@code Applicant} and {@code Project} objects
	 * from the provided databases.
	 *
	 * @param userDatabase    The database containing {@code User} objects (including Applicants).
	 * @param projectDatabase The database containing {@code Project} objects.
	 * @return An {@code ArrayList} of {@code Enquiry} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	public ArrayList<Enquiry> read(IDatabase<User> userDatabase, IDatabase<Project> projectDatabase)
	{
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		ProjectDatabaseMgr projMgr = new ProjectDatabaseMgr();
		EnquiryMgr enqMgr = new EnquiryMgr();

		ArrayList<Enquiry> enquiryList = new ArrayList<>();
		Enquiry enquiry;
		Applicant enquiryFiler;
		Project project;
		String question, reply;

		String line;
        String data[];

        try(BufferedReader reader = new BufferedReader(new FileReader(enquiryFile)))
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
	            
	            for(int i = 0; i < 5; i++)
	            {
	            	data[i] = data[i].trim();
	            }
	
	            enquiryFiler = (Applicant) userMgr.getUser(userDatabase, data[1]);
	            project = projMgr.getData(projectDatabase, data[2]);
	            question = data[3];
	            reply = data[4];
	            
	            if(enquiryFiler == null || project == null)
	            	continue;
	            
	            enquiry = enqMgr.create(data[0], enquiryFiler, project, question, reply);

	            enquiryList.add(enquiry);
	        }
        }
        catch(IOException e)
        {
            System.err.println("Error reading enquiry file: " + e.getMessage());
            return null;
        }

        return enquiryList;
	}
}
