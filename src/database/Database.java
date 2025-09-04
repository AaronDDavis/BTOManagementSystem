package database;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic database class that stores a list of objects.
 * <p>
 * This class provides a basic in-memory data storage solution using an {@link ArrayList}.
 * It is designed to be flexible and can hold any type of object, as specified by the
 * generic type parameter {@code <T>}. It implements the {@link IDatabase} interface.
 * </p>
 *
 * @param <T> The type of objects to be stored in the database.
 */
public class Database<T> implements IDatabase<T> {
	
	/**
	 * The internal list used to store the data.
	 */
	private List<T> dataList;

	/**
	 * Constructs a new Database instance, initializing an empty {@link ArrayList}.
	 */
	public Database() {
		dataList = new ArrayList<>();
	}

	/**
	 * Retrieves the list of all data stored in the database.
	 *
	 * @return A {@link List} containing all the stored objects of type {@code T}.
	 */
	@Override
	public List<T> getDataList() {
		return dataList;
	}

	/**
	 * Sets the entire data list of the database.
	 * <p>
	 * This method replaces the current data list with a new one. It can be
	 * used for operations like loading data from a file.
	 * </p>
	 *
	 * @param dataList The new {@link List} of objects to be stored.
	 */
	@Override
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}