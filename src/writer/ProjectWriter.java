package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import misc.DateConvertor;
import project.Project;
import user.User;

/**
 * Writes a list of {@code Project} objects to a CSV file.
 * The data is stored in the following format:
 * projectID, name, count, neighbourhood, roomType, sellingPrice,
 * applicationStartDate, applicationEndDate, ManagerID, officerSlot,
 * [officerIDs separated by ;], visibility.
 */
public class ProjectWriter implements IWriter<Project>
{
	/*
	 * Data is stored as follows:
	 * projectID,
	 * name,
	 * count(int),
	 * neighbourhood,
	 * roomType(int),
	 * sellingPrice(double),
	 * applicationStartDate,
	 * applicationEndDate,
	 * ManagerID,
	 * officerSlot(int),
	 * [officerIDs separated by ;],
	 * visibility(true/false),
	 * userGroup
	 */

	/**
	 * Writes the provided list of projects to the project data file.
	 * Each project's information is written on a new line, with fields separated by commas.
	 * Officer IDs are separated by semicolons within their field. Dates are formatted
	 * using the {@code DateConvertor}.
	 *
	 * @param projectList The {@code ArrayList} of {@code Project} objects to write.
	 */
	@Override
	public void write(List<Project> projectList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile)))
        {
    		for(Project project: projectList)
    		{
    			line = project.getID() + ", "
    					+ project.getName() + ", "
    					+ project.getCount() + ", "
    					+ project.getNeighbourhood() + ", "
    					+ project.getRoomType() + ", "
    					+ project.getSellingPrice() + ", "
    					+ DateConvertor.formatLocalDate(project.getApplicationStartDate()) + ", "
    					+ DateConvertor.formatLocalDate(project.getApplicationEndDate()) + ", "
    					+ project.getManager().getUserID() + ", "
    					+ project.getOfficerSlot() + ", ";
    			
    			for(User user: project.getOfficers())
    			{
    				if(!line.endsWith(", "))
    					line += "; ";
    				if(user != null)
    					line += user.getUserID();
    			}
    			line += ", ";
    			
    			line += project.isVisible();
    			
        		writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing project file: " + e.getMessage());
        }
	}
}
