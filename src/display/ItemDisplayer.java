package display;

import java.util.List;

/**
 * An abstract base class for displaying a single item or a list of items.
 * <p>
 * This class implements the {@link IDisplayer} interface and provides a
 * common, reusable method for iterating through a list of objects and
 * displaying each one. Subclasses must implement the abstract {@code display(T ob)}
 * method to define how a single item is presented.
 * </p>
 */
public abstract class ItemDisplayer<T> implements IDisplayer<T> {

	/**
	 * Displays a list of items.
	 * <p>
	 * This method iterates through the provided list and calls the abstract
	 * {@code display(T ob)} method for each item. It also adds a numbered
	 * prefix for each item to improve readability. If the list is empty,
	 * it prints "NA".
	 * </p>
	 *
	 * @param itemList The {@link List} of items to be displayed.
	 */
	public void display(List<T> itemList) {
		if (itemList.isEmpty()) {
			System.out.println("NA");
		} else {
			int i = 0;
			for (T listItem : itemList) {
				System.out.print(++i + ". ");
				display(listItem);
				System.out.println("\n");
			}
		}
	}
	
	/**
	 * Abstract method to display a single item.
	 * <p>
	 * Subclasses must provide their own implementation of this method to
	 * define how an object of type {@code T} is formatted for display.
	 * </p>
	 *
	 * @param ob The object of type {@code T} to be displayed.
	 */
	@Override
	public abstract void display(T ob);
}