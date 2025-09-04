package userinterface;

import java.util.List;
import java.util.Scanner;

/**
 * A helper class for handling common user interface operations.
 * This class provides utility methods for tasks such as validating user input
 * and interacting with the console to get user preferences.
 */
public class UserInterfaceHelper {
	/**
	 * Checks if a given index is valid for a list.
	 * This method safely checks if an index is within the bounds of a list without
	 * causing an {@link IndexOutOfBoundsException} if the index is invalid.
	 * 
	 * @param <T>      The type of objects in the list.
	 * @param itemList The list to check the index against.
	 * @param index    The index to validate.
	 * @return {@code true} if the index is within the bounds of the list, {@code false} otherwise.
	 */
	public <T> boolean isValidIndex(List<T> itemList, int index) {
		try {
			itemList.get(index);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * Prompts the user for the sorting order (ascending or descending).
	 *
	 * @return {@code true} for ascending, {@code false} for descending.
	 */
	public boolean getSortOrder(Scanner sc) {
		int input = 1;
		while(true) {
			System.out.println("Input Sort Order:");
			System.out.println("1. Ascending");
			System.out.println("2. Descending");
			input = sc.nextInt();
			sc.nextLine();
			
			if(input != 1 || input != 2) {
				System.out.println("Invalid input. Please try again.");
			}
			else {
				return (input == 1) ? true : false;
			}
		}
	}
}
