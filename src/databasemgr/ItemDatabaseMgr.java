package databasemgr;

import database.IDatabase;
import java.util.List;
import user.User;

/**
 * An abstract base class for managing a database of a specific item type.
 * <p>
 * This class provides a common implementation for the {@code add} method,
 * which handles the process of adding an item to the database's data list.
 * Subclasses are responsible for providing the specific logic for retrieving
 * data, as defined in the {@link IItemDatabaseMgr} interface.
 * </p>
 */
public abstract class ItemDatabaseMgr<T> implements IItemDatabaseMgr<T> {

	/**
	 * Adds a new item to the database's data list.
	 * <p>
	 * This method checks if the item is not null and then adds it to the
	 * underlying data list of the provided database.
	 * </p>
	 *
	 * @param database The database to which the item will be added.
	 * @param item     The item of type {@code T} to be added.
	 * @return {@code true} if the item was successfully added, {@code false} if the item was null.
	 */
	@Override
	public boolean add(IDatabase<T> database, T item) {
		return (item != null) ? database.getDataList().add(item) : false;
	}

	/**
	 * Abstract method to retrieve a list of items from the database.
	 * <p>
	 * Subclasses must implement this method to define the specific logic
	 * for filtering and retrieving data based on a user's role or other criteria.
	 * </p>
	 *
	 * @param database The database containing the items.
	 * @param user     The user requesting the data.
	 * @return A {@link List} of items of type {@code T} relevant to the user.
	 */
	@Override
	public abstract List<T> getData(IDatabase<T> database, User user);
}