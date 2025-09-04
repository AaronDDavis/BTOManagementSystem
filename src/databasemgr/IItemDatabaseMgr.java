package databasemgr;

import java.util.List;

import database.IDatabase;
import user.User;

/**
 * A generic interface for managing a database of a specific item type.
 * <p>
 * This interface extends {@link IDatabaseMgr} and defines common operations
 * for retrieving and adding items to a database. It ensures a consistent
 * API for different types of item managers, such as those for applications,
 * projects, or users.
 * </p>
 */
public interface IItemDatabaseMgr<T> extends IDatabaseMgr<T> {

	/**
	 * Retrieves a filtered list of items from the database based on the provided user.
	 * <p>
	 * The implementation of this method will vary depending on the specific
	 * item type and the role of the user. For example, a user might only be
	 * able to see items they own or are associated with.
	 * </p>
	 *
	 * @param database The database containing the items.
	 * @param user     The user who is requesting the data.
	 * @return A {@link List} of items of type {@code T} that are relevant to the user.
	 */
	public List<T> getData(IDatabase<T> database, User user);

	/**
	 * Adds a new item to the database.
	 *
	 * @param database The database to which the item will be added.
	 * @param item     The item of type {@code T} to be added.
	 * @return {@code true} if the item was successfully added, {@code false} otherwise.
	 */
	public boolean add(IDatabase<T> database, T item);
}