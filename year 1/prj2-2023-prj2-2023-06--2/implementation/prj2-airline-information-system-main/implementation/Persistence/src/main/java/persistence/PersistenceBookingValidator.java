package persistence;

import persistence.exceptions.InvalidBookingDataException;
import persistence.exceptions.InvalidPersonalDataException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
/*
Validation class for BookingData, CustomerData and PassengerData
 */
public class PersistenceBookingValidator{
    Record data;
    String entityName;
//    PersistenceBookingValidator(Record data, String entityName){
//        this.data = data;
//        this.entityName = entityName;
//    }
    public boolean isInValidData(Record data, String entityName) {
        if(data == null){
            try {
                if (entityName.equals("Booking")){
                    throw new InvalidBookingDataException("Data is null");
                }
                throw new InvalidPersonalDataException("Data is null");

            }catch (InvalidPersonalDataException | InvalidBookingDataException e){
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }

        Method[] methods = data.getClass().getDeclaredMethods();
        for (Method method : methods) {
                String propertyName = method.getName();
                if (propertyName.equals("name")){
                    try {
                        Method getName = data.getClass().getMethod(propertyName);
                        Method getLastName = data.getClass().getMethod("lastName");
                        Method getPersonalNumber = data.getClass().getMethod("personalNumber");
                        String name = (String) getName.invoke(data);
                        String lastName = (String) getLastName.invoke(data);
                        String personalNumber = (String) getPersonalNumber.invoke(data);
                        try {
                            if (name.isEmpty() || lastName.isEmpty() || personalNumber.isEmpty()) {
                                throw new InvalidPersonalDataException("All fields must be filled in");
                            }
                            if (!name.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
                                throw new InvalidPersonalDataException(entityName + " name and last name should only contain letters");
                            }
                            if (!personalNumber.matches("[0-9]+")) {
                                throw new InvalidPersonalDataException("Personal number should only contain digits");
                            }
                            if (entityName.equals("Passenger")) {
                                Method getDate = data.getClass().getMethod("dateOfBirth");
                                String dateOfBirth = (String) getDate.invoke(data);
                                if (isInValidDate(dateOfBirth)) {
                                    throw new InvalidPersonalDataException("Invalid date of birth");
                                }
                            } else return false;
                        }catch (InvalidPersonalDataException e){
                            throw new InvalidPersonalDataException(e.getMessage(), e.getCause());
                        }

                    }catch (NoSuchMethodException nsme){
                        nsme.printStackTrace();
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (entityName.equals("Booking")){

                    try {
                        Method getPassengerIDs = data.getClass().getMethod("passengerIDs");
                        Method getFlightIDs = data.getClass().getMethod("flightIDs");
                        Method getCustomerID = data.getClass().getMethod("customerID");
                        Method getDate = data.getClass().getMethod("bookingDate");
                        List<String> passengerIDs = (List<String>) getPassengerIDs.invoke(data);
                        List<String> flightIDs = (List<String>) getFlightIDs.invoke(data);
                        String customerID = (String) getCustomerID.invoke(data);
                        LocalDate bookingDate = (LocalDate) getDate.invoke(data);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        try {
                            if (passengerIDs.isEmpty()) {
                                throw new InvalidBookingDataException("Passenger ID list is empty");

                            }
                            if (flightIDs.isEmpty()) {

                                throw new InvalidBookingDataException("Flight ID list is empty");
                            }

                            String dateAsString = bookingDate.format(formatter);
                            if (isInValidDate(dateAsString)){
                                throw new InvalidBookingDataException("Invalid date");
                            }
                            return false;
                        }catch (InvalidBookingDataException e){
                            throw new InvalidBookingDataException(e.getMessage(), e.getCause());
                        }
                    }
                    catch (NoSuchMethodException e){
                        e.printStackTrace();
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        }
        return true;
    }
    public boolean isInValidDate(String date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);

            try {
                // Check if the date is not a future date
                LocalDate currentDate = LocalDate.now();
                if (parsedDate.isAfter(currentDate)) {
                    throw new InvalidPersonalDataException("Date can not be future date");
                }
                // Check if the person is not more than 122 years old (oldest person's age)
                LocalDate oldestAllowedDate = currentDate.minusYears(122);
                if (parsedDate.isBefore(oldestAllowedDate)){
                    throw new InvalidPersonalDataException("Provided date is too old");
                }
                return false;
            }catch (InvalidPersonalDataException e){
                throw new InvalidPersonalDataException(e.getMessage(), e.getCause());
            }

            // All constraints are satisfied
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Invalid date format
        }
    }

}
