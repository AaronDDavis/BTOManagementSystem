package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import database.IDatabase;
import databasemgr.UserDatabaseMgr;
import misc.DateConvertor;
import project.Project;
import project.ProjectMgr;
import user.HDBManager;
import user.HDBOfficer;
import user.User;

/**
 * Reads project data from a CSV file and populates a list of {@code Project} objects.
 * The file format is defined in the comments within the class.
 */
public class ProjectReader implements IReader<Project>
{
	/*
	 * Data is stored as follows:
	 * projectID,
	 * name,
	 * count(int),
	 * neighbourhood,
	 * roomType,
	 * sellingPrice(double),
	 * applicationStartDate,
	 * applicationEndDate,
	 * ManagerID,
	 * officerSlot(int),
	 * [officerIDs separated by ;],
	 * visibility(true/false),
	 */
	public ProjectReader()
	{
		
	}

	/**
	 * Reads project data from the project file, creates {@code Project} objects,
	 * and associates them with the corresponding {@code HDBManager} and {@code HDBOfficer} objects
	 * from the provided user database.
	 *
	 * @param userDatabase The database containing {@code User} objects (including HDBManagers and HDBOfficers).
	 * @return An {@code ArrayList} of {@code Project} objects loaded from the file,
	 * or {@code null} if an error occurs during file reading.
	 */
	public ArrayList<Project> read(IDatabase<User> userDatabase)
	{
		UserDatabaseMgr userMgr = new UserDatabaseMgr();
		ProjectMgr projMgr = new ProjectMgr();

		ArrayList<Project> projectList = new ArrayList<>();
		// User enquiryFiler, projectManager;
		String projectID, name, neighbourhood, managerID;
		LocalDate startDate, endDate;
		int count, officerSlot;
		double sellingPrice;
		boolean visibility;
		Project.ROOM_TYPE roomType;

		String line = null;
        String data[], officerIDs[];

        try(BufferedReader reader = new BufferedReader(new FileReader(projectFile)))
        {
	        while ((line = reader.readLine()) != null)
	        {
	    		ArrayList<HDBOfficer> projectOfficers = new ArrayList<>();
	        	line = line.trim();
	            if (line.isEmpty()) {
	                continue; // skip blank lines
	            }
	
	            data = line.split(",", -1);
	            if (data.length != 12) {
	                // malformed line
	                continue;
	            }
	            
	            try
	            {
		            for(int i = 0; i < 12; i++)
		            {
		            	data[i] = data[i].trim();
		            }
		            
		            projectID = data[0];
		            name = data[1];
		            count = Integer.parseInt(data[2]);
		            neighbourhood = data[3];
		            roomType = Project.ROOM_TYPE.valueOf(data[4]);
		            sellingPrice = Double.parseDouble(data[5]);
		            startDate = DateConvertor.parseToLocalDate(data[6]);
		            endDate = DateConvertor.parseToLocalDate(data[7]);
		            managerID = data[8];
		            officerSlot = Integer.parseInt(data[9]);
		            officerIDs = data[10].split(";");
		            visibility = Boolean.parseBoolean(data[11]);
		            
		            for(String officerID: officerIDs)
		            {
		            	officerID = officerID.trim();
		            	if(officerID.length() == 0)
		            		officerID = null;
		            	projectOfficers.add((HDBOfficer)userMgr.getUser(userDatabase, officerID));
		            }
		            
		            projectList.add(projMgr.create(projectID, name, count, neighbourhood, roomType, sellingPrice,
		            		startDate, endDate, officerSlot, projectOfficers, visibility,
		            		(HDBManager)userMgr.getUser(userDatabase, managerID)));

	            }
	            catch (DateTimeParseException | IllegalArgumentException | IndexOutOfBoundsException e) {
	                System.err.println("Error parsing project line: " + line + " - " + e.getMessage());
	                continue;
	            }
	            catch (Exception e) { // Catch other potential issues like NullPointer if lookups fail
	                System.err.println("Unexpected error processing project line: " + line + " - " + e.getMessage());
	                continue;
	            }
	        }
        }
        catch (IOException e) {
            System.err.println("Error reading project file: " + e.getMessage());
            return null;
        }
		return projectList;
	}
	
}
