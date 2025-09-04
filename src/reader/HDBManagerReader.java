package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import user.HDBManager;
import user.User.MARITAL_STATUS;
import userctrl.UserMgr;

/**
 * Reads HDB manager data from a CSV file and populates a list of {@code HDBManager} objects.
 * The file format is defined in the comments within the class.
 */
public class HDBManagerReader implements IUserReader<HDBManager>
{

	/*
	 * Saved in the following format:
	 * 
	 * User (all users have at least these):
	 * userID, name, password, age, mritalStatus
	 */

	/**
	 * Reads HDB manager data from the HDB manager file and creates a list of {@code HDBManager} objects.
	 *
	 * @return An {@code ArrayList} of {@code HDBManager} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	public ArrayList<HDBManager> read()
	{
		UserMgr mgr = new UserMgr();
		ArrayList<HDBManager> userList = new ArrayList<>();

		HDBManager manager;
		String userID, name, password;
		MARITAL_STATUS maritalStatus;
		int age;

		String line;
        String data[];

        try(BufferedReader reader = new BufferedReader(new FileReader(HDBManagerFile)))
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
	            
	            try
	            {
		            for(int i = 0; i < 5; i++)
		            {
		            	data[i] = data[i].trim();
		            	if(data[i].length() == 0)
		            		data[i] = null;
		            }
		            
		            userID = data[0];
		            name = data[1];
		            password = data[2];
		            age = Integer.parseInt(data[3]);
		            maritalStatus = MARITAL_STATUS.valueOf(data[4]);
		            
		            manager = mgr.createHDBManager(userID, name, password, age,
		            		maritalStatus);
		            if(manager != null)
		            	userList.add(manager);
	            }
	            catch (IllegalArgumentException | IndexOutOfBoundsException e) {
	                System.err.println("Error parsing manager line: " + line + " - " + e.getMessage());
	                continue;
	            }
	            catch (Exception e) { // Catch other potential issues like NullPointer if lookups fail
	                System.err.println("Unexpected error processing manager line: " + line + " - " + e.getMessage());
	                continue;
	            }
	        }
		}
        catch(IOException e)
        {
            System.err.println("Error reading manager file: " + e.getMessage());
            return null;
        }

		return userList;
	}

}
