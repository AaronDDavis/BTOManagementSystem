package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import user.HDBManager;
import user.HDBOfficer;

/**
 * Represents a project, such as a Build-To-Order (BTO) housing development.
 * <p>
 * This class encapsulates all the details of a project, including its unique ID,
 * name, housing unit count, location, selling price, application period,
 * and the HDB staff assigned to it. It provides methods for accessing and
 * modifying these properties.
 * </p>
 */
public class Project {

	/**
	 * The unique identifier for the project.
	 */
	private String id;
	
	/**
	 * The name of the project.
	 */
	private String name;
	
	/**
	 * The number of housing units available in the project.
	 */
	private int count;
	
	/**
	 * The neighbourhood or location of the project.
	 */
	private String neighbourhood;
	
	/**
	 * An enumeration of the available room types.
	 */
	public enum ROOM_TYPE { _2Room, _3Room };
	
	/**
	 * The room type available in this project.
	 */
	private ROOM_TYPE roomType;
	
	/**
	 * The selling price of the housing units.
	 */
	private double sellingPrice;
	
	/**
	 * The date when the application period starts.
	 */
	private LocalDate applicationStartDate;
	
	/**
	 * The date when the application period ends.
	 */
	private LocalDate applicationEndDate;
	
	/**
	 * The HDB manager responsible for the project.
	 */
	private HDBManager manager;
	
	/**
	 * The number of officer slots for the project.
	 */
	private int officerSlot;
	
	/**
	 * A list of HDB officers assigned to the project.
	 */
	private List<HDBOfficer> officers;
	
	/**
	 * A flag indicating whether the project is visible to applicants.
	 */
	private boolean isVisible;
	
	/**
	 * Constructs a new Project instance with all its details.
	 *
	 * @param id                   The unique identifier for the project.
	 * @param name                 The name of the project.
	 * @param count                The number of housing units.
	 * @param neighbourhood        The neighbourhood of the project.
	 * @param roomType             The room type available.
	 * @param sellingPrice         The selling price.
	 * @param applicationStartDate The start date of the application period.
	 * @param applicationEndDate   The end date of the application period.
	 * @param manager              The HDB manager for the project.
	 * @param officerSlot          The number of officer slots.
	 * @param officers             A list of assigned HDB officers.
	 * @param isVisible            A flag for visibility to applicants.
	 */
	public Project(String id, String name, int count, String neighbourhood, ROOM_TYPE roomType, double sellingPrice,
			LocalDate applicationStartDate, LocalDate applicationEndDate, HDBManager manager, int officerSlot,
			List<HDBOfficer> officers, boolean isVisibile) {
		this.id = id;
		this.name = name;
		this.count = count;
		this.neighbourhood = neighbourhood;
		this.roomType = roomType;
		this.sellingPrice = sellingPrice;
		this.applicationStartDate = applicationStartDate;
		this.applicationEndDate = applicationEndDate;
		this.manager = manager;
		this.officerSlot = officerSlot;
		this.officers = officers;
		this.isVisible = isVisibile;
	}

	/**
	 * Retrieves the unique ID of the project.
	 *
	 * @return The project's ID.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Retrieves the name of the project.
	 *
	 * @return The project's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the number of units in the project.
	 *
	 * @return The project's unit count.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Retrieves the neighbourhood of the project.
	 *
	 * @return The project's neighbourhood.
	 */
	public String getNeighbourhood() {
		return neighbourhood;
	}

	/**
	 * Retrieves the room type of the project.
	 *
	 * @return The project's {@link ROOM_TYPE}.
	 */
	public ROOM_TYPE getRoomType() {
		return roomType;
	}

	/**
	 * Retrieves the selling price of the units.
	 *
	 * @return The selling price.
	 */
	public double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * Retrieves the application start date.
	 *
	 * @return The start date as a {@link LocalDate}.
	 */
	public LocalDate getApplicationStartDate() {
		return applicationStartDate;
	}

	/**
	 * Retrieves the application end date.
	 *
	 * @return The end date as a {@link LocalDate}.
	 */
	public LocalDate getApplicationEndDate() {
		return applicationEndDate;
	}

	/**
	 * Retrieves the HDB manager of the project.
	 *
	 * @return The {@link HDBManager} object.
	 */
	public HDBManager getManager() {
		return manager;
	}

	/**
	 * Retrieves the number of officer slots.
	 *
	 * @return The number of officer slots.
	 */
	public int getOfficerSlot() {
		return officerSlot;
	}

	/**
	 * Retrieves the list of HDB officers assigned to the project.
	 *
	 * @return A {@link List} of {@link HDBOfficer} objects.
	 */
	public List<HDBOfficer> getOfficers() {
		return officers;
	}

	/**
	 * Checks if the project is currently visible to applicants.
	 *
	 * @return {@code true} if visible, {@code false} otherwise.
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the number of units in the project.
	 *
	 * @param count The new unit count.
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Retrieves the unique ID of the project.
	 *
	 * @return The project's ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique ID of the project.
	 *
	 * @param id The new project ID.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the visibility of the project.
	 *
	 * @param isVisible The new visibility status.
	 */
	public void setVisibility(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Sets the name of the project.
	 *
	 * @param name The new project name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the neighbourhood of the project.
	 *
	 * @param neighbourhood The new neighbourhood.
	 */
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	/**
	 * Sets the room type for the project.
	 *
	 * @param roomType The new {@link ROOM_TYPE}.
	 */
	public void setRoomType(ROOM_TYPE roomType) {
		this.roomType = roomType;
	}

	/**
	 * Sets the selling price of the units.
	 *
	 * @param sellingPrice The new selling price.
	 */
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * Sets the application start date.
	 *
	 * @param applicationStartDate The new start date.
	 */
	public void setApplicationStartDate(LocalDate applicationStartDate) {
		this.applicationStartDate = applicationStartDate;
	}

	/**
	 * Sets the application end date.
	 *
	 * @param applicationEndDate The new end date.
	 */
	public void setApplicationEndDate(LocalDate applicationEndDate) {
		this.applicationEndDate = applicationEndDate;
	}

	/**
	 * Sets the number of officer slots.
	 *
	 * @param officerSlot The new number of slots.
	 */
	public void setOfficerSlot(int officerSlot) {
		this.officerSlot = officerSlot;
	}

	/**
	 * Sets the list of HDB officers for the project.
	 *
	 * @param officers An {@link ArrayList} of {@link HDBOfficer} objects.
	 */
	public void setOfficers(ArrayList<HDBOfficer> officers) {
		this.officers = officers;
	}
	
	/**
	 * Compares this project to another project based on their unique IDs.
	 *
	 * @param project The other project to compare with.
	 * @return {@code true} if the projects have the same ID, {@code false} otherwise.
	 */
	public boolean equals(Project project) {
		return this.getID().compareTo(project.getID()) == 0;
	}
}