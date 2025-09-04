package database;

import java.util.List;

/**
 * A generic interface for a database or data repository.
 * <p>
 * This interface defines the fundamental contract for any class that
 * acts as a data store for a list of objects. It provides methods to
 * retrieve the entire data list and to set the list.
 * </p>
 */
public interface IDatabase<T> {
	
	/**
	 * Retrieves the list of all data stored in the database.
	 *
	 * @return A {@link List} containing all the stored objects of type {@code T}.
	 */
	public List<T> getDataList();
	
	/**
	 * Sets the entire data list of the database.
	 *
	 * @param list The new {@link List} of objects to be stored.
	 */
	public void setDataList(List<T> list);
}