package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import user.HDBManager;

/**
 * Writes a list of {@code HDBManager} objects to a CSV file.
 * The data is saved in a format including basic user details.
 */
public class HDBManagerWriter implements IUserWriter<HDBManager>
{
	/**
	 * Writes the provided list of HDB Managers to the HDB Manager data file.
	 * Each manager's information is written on a new line, with fields separated by commas.
	 * The format includes userID, name, password, age, and marital status.
	 *
	 * @param managerList The {@code ArrayList} of {@code HDBManager} objects to write.
	 */
	@Override
	public void write(List<HDBManager> managerList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(HDBManagerFile)))
        {
    		for(HDBManager manager: managerList)
    		{
    			line = manager.getUserID() + ", "
    					+ manager.getName() + ", "
    					+ manager.getPassword() + ", "
    					+ manager.getAge() + ", "
    					+ manager.getMaritalStatus();
        		writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing HDBManager file: " + e.getMessage());
        }
	}
}
