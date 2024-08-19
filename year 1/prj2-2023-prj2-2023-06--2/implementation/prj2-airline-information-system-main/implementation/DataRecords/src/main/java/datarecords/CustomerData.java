package datarecords;

/**
 * Data carrier for CustomerData.
 * A record is not mutable. Getter methods (e.g. firstName(), not getFirstName()), 
 * hashCode(), equals() end toString available for free.
 * 
 * @author Informatics Fontys Venlo
 */
public record CustomerData (String name, String lastName, String personalNumber) {}
