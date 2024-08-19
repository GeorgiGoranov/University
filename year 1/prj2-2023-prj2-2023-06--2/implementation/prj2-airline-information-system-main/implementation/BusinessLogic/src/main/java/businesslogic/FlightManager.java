package businesslogic;


import datarecords.FlightData;
import javafx.collections.ObservableList;
import persistence.FlightStorageService;
import persistence.GetInfoForCanceledFlights;
import persistence.GetInfoForFlights;
import persistence.GetInfoForRecurringFlights;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import java.sql.SQLException;


public class FlightManager {

        private FlightStorageService FlightStorageService;
        private List<Integer> selectedMonth = new ArrayList<>();



        public FlightManager(FlightStorageService flightStorageService) {
            this.FlightStorageService = flightStorageService;
        }

        public FlightData add(FlightData flightData) {
            return FlightStorageService.add(flightData);
        }

        public ObservableList<GetInfoForFlights> getDataForFlights() {
            return FlightStorageService.getDataForFlights();
        }

        public ObservableList<String> getHours() {
            return FlightStorageService.getTimeOptionsForHour();
        }

        public ObservableList<String> getMinutes() {
            return FlightStorageService.getTimeOptionsForMinutes();
        }

        public ObservableList<String> getAirports() {
            return FlightStorageService.getAirports();
        }

        public ObservableList<GetInfoForFlights> getDataForAllFlights() {
            return FlightStorageService.getDataForFlights();
        }


        public ObservableList<String> getPlaneIDs() {
            return FlightStorageService.getPlaneIDs();
        }


        public void deleteSelectedFlight(String id) {
            FlightStorageService.deleteFlight(id);
        }


        public void createRecurringFlights(String duplicatedFlightId, int dayInterval, LocalDate dateOfDeparture, LocalDate dateOfArrival, LocalDate limitDate) {

            GetInfoForRecurringFlights recurringFlightsData = new GetInfoForRecurringFlights(duplicatedFlightId, dayInterval, dateOfDeparture, dateOfArrival, limitDate);

            addPrefixToRecurringID(recurringFlightsData);
        }

        private void addPrefixToRecurringID(GetInfoForRecurringFlights recurringFlightsData) {

            int numIterations = 0;
            LocalDate departureDate = recurringFlightsData.getDateOfDeparture();
            LocalDate arrivalDate = recurringFlightsData.getDateOfArrival();

            numIterations = (int) (recurringFlightsData.getDayLimit().toEpochDay() - recurringFlightsData.getDateOfDeparture().toEpochDay()) / recurringFlightsData.getDayInterval();

            if (Math.floor(numIterations) > 0) {

                for (int i = 1; i <= numIterations; i++) {
                    departureDate = departureDate.plusDays(recurringFlightsData.getDayInterval());
                    if (!departureDate.isAfter(recurringFlightsData.getDayLimit())) {
                        arrivalDate = arrivalDate.plusDays(recurringFlightsData.getDayInterval());

                        recurringFlightsData.setDateOfDeparture(departureDate);
                        recurringFlightsData.setDateOfArrival(arrivalDate);

                        FlightStorageService.insertIntoRecurringFlights(recurringFlightsData);

                    } else {
                        break; // Exit the loop if the departureDate exceeds the limitDate
                    }
                }
            }
        }

        public void insertFlightsIntoDeletedTable(String flightId, String reason) {
            FlightStorageService.insertIntoDeletedTable(flightId, reason);
        }

        public ObservableList<GetInfoForCanceledFlights> getDataForDeletedFlights() {
            return FlightStorageService.getDataForDeletedFlights();
        }

        public void storeFlightData(String flightID, String planeId, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, LocalDate dateOfDeparture, LocalDate dateOfArrival, boolean statdiscount, boolean ddiscount, float price) {
            FlightData flightData = new FlightData(flightID, planeId, departureAirport, arrivalAirport, departureTime, arrivalTime, dateOfDeparture, dateOfArrival, statdiscount, ddiscount, price);

            FlightStorageService.insertFlightIntoFlightData(flightData);
        }

        public boolean checkDate(LocalDate date) {

            int month = date.getMonthValue() + 1; // Get the month value (Note: January is 0 in Java's Calendar class)

            if (this.selectedMonth.isEmpty()) {
                this.selectedMonth.add(0);
            }

            if (this.selectedMonth.contains(month)) {
                return true;
            }
            return false;
            // if you want to implement add also a remvoe from list
        }

        //TODO program works fine when you do and undo inside the same gridpane, but when you open a new grid pane the discount and the price gets all mixed up
        //TODO we want to display the sequence diagram of Dynamic Display/ Error throwing from one layer to the next
        public void changeDD(String IDLabel, boolean boolValue, boolean access, LocalTime arrivalTime, LocalTime sunsetTime, double discountParam) {

            //business people want to arrive at night so the closer it is to the morning the more the discount: 11.00 am 50 percent discount
            // 9.00 pm 0 percent discount, ideal time

            int discountPercentage = calculateSunsetPercentage(arrivalTime, sunsetTime);

            int finaldiscount = (int) (discountPercentage + discountParam);


//            List<GetInfoForFlights> list = FlightDataDao.getDataForFlights();
//            List<String> filteredList = list.stream()
//                    .filter(getInfo -> getInfo.getId().equals(IDLabel))
//                    .map(getInfo -> getInfo.getId() + " - " + getPrice(getInfo.getId()))
//                    .toList();
//
//
//            for (String item : filteredList) {
//
//                String[] parts = item.split(" - ");
//                String currentFlightID = parts[0];
//                Double currentPrice = Double.parseDouble(parts[1]);
//                double dpercent = getPercentage(IDLabel);
//
//
//                System.out.println(currentFlightID + currentPrice + dpercent);
//                double trueprice = 0.0;
//
//
//                if (access) {
//
//                    dpercent += finaldiscount;
//                    trueprice = discountedprice(finaldiscount, currentPrice);
//                    dao.changeDynamicDiscount(IDLabel, boolValue, trueprice, dpercent);
//
//                } else {
//
//                    trueprice = undiscountedprice(finaldiscount, currentPrice);
//                    dpercent -= finaldiscount;
//                    dao.changeDynamicDiscount(IDLabel, boolValue, trueprice, dpercent);
//
//                }
//
//
//            }
        }

        public int calculateSunsetPercentage(LocalTime arrivalTime, LocalTime sunsetTime) {

            if (arrivalTime.isAfter(sunsetTime) || arrivalTime.equals(sunsetTime.plusMinutes(1))) {
                return 0; // After sunset, 0% discount
            } else if (arrivalTime.isBefore(sunsetTime.minusHours(12))) {
                return 50; // Before sunrise, 50% discount
            } else {

                long totalMinutes = (sunsetTime.toSecondOfDay() - arrivalTime.toSecondOfDay()) / 60;
                //calculate the totalnumber of minutes, get all seconds of both values and divide each value by 60, then subtract the two values
                double proportion = (double) totalMinutes / 720; // 720 minutes in 12 hours
                //since the diffrence is going to be every 12 hours
                //this should return a less then 1 value
                return (int) (proportion * 50); // Proportional discount within the 12-hour period
                // since our max discount is not 100 but 50 we do times 50
            }

            //basically every half hour the increment is of 2 percent


        }

        public <T> boolean checkValue(Class<T> type, Object value) {
            return type.isInstance(value);
        }

        //needs to set static discount to true so you cant have multiple static discounts at a time
        public boolean applyStaticDiscount(int month, double discount) {

            this.selectedMonth.add(month);


            String extrazero;
            if (month < 10)
                extrazero = "0";
            else {
                extrazero = "";
            }

//            List<GetInfoForFlights> list = FlightDataDao.getDataForFlights();
//            List<String> filteredList = list.stream()
//                    .filter(getInfo -> Pattern.matches("^\\d{4}-" + extrazero + month + "-\\d{2}$", getInfo.getDepDate()))
//                    .map(getInfo -> getInfo.getId() + " - " + getPrice(getInfo.getId()))
//                    .toList();
////| selectedMonth.contains(month)
//            if (filteredList.isEmpty()) {
//
//                return false;
//            }
//
////        System.out.println(filteredList);
//
//            for (String item : filteredList) {
//                String[] parts = item.split(" - ");
//                String currentFlightID = parts[0];
//                Double currentPrice = Double.parseDouble(parts[1]);
//
//                dao.changeStaticDiscount(currentFlightID, discountedprice(discount, currentPrice), discount);
////            System.out.println(discountedprice(discount,currentPrice));
//            }

            return true;
        /*
        Create a gridpane pop up that asks for confermatioon of deletion of all static discounts of that month
         */


        }

        public double discountedprice(double discount, Double currentPrice) {
            double discountAmount = currentPrice * ((double) discount / 100);
            return currentPrice - discountAmount;
        }

        public <T> boolean checkifnotNull(T value) {
            if (value == "")
                return false;

            return value != null;
        }

        public double convertToDouble(String value) {

            try {

                if (Double.parseDouble(value) < 0) {
                    return -1;
                } else if (Double.parseDouble(value) >= 100) {
                    return -2;
                }
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return (-3);
            }
        }

        public boolean staticdiscountAlreadyExists() {
//            List<GetInfoForFlights> list = FlightDataDao.getDataForFlights();
//
//            List<Boolean> filteredList = list.stream()
//                    .map(getInfo -> getInfo.getSdiscount())
//                    .toList();
//
//            if (filteredList.contains(true)) {
//                return true;
//            }

            return false;
        }

        public boolean checkValidMonth(int selectedMonth) {
//            for (int value : this.nonAvailableMonths) {
//                if (value == selectedMonth) {
//                    return false;
//                }
//            }
            return true;
        }

        public boolean eliminateDiscountonFlights(String ddate, double ddiscount) {


//        System.out.println(ddiscount);
            LocalDate date = LocalDate.parse(ddate);
            int month = date.getMonthValue();

            double discount = ddiscount;


//        this.selectedMonth.remove(month);

            String extrazero;
            if (month < 10)
                extrazero = "0";
            else {
                extrazero = "";
            }

//            List<GetInfoForFlights> list = FlightDataDao.getDataForFlights();
//            List<String> filteredList = list.stream()
//                    .filter(getInfo -> Pattern.matches("^\\d{4}-" + extrazero + month + "-\\d{2}$", getInfo.getDepDate()))
//                    .map(getInfo -> getInfo.getId() + " - " + getPrice(getInfo.getId()))
//                    .toList();
//
//            if (filteredList.isEmpty() | discount == 0)
//                return false;
//
////        System.out.println(filteredList);
//
//            for (String item : filteredList) {
//
//                String[] parts = item.split(" - ");
//                String currentFlightID = parts[0];
//                Double currentPrice = Double.parseDouble(parts[1]);
//                dao.eliminateStaticDiscount(currentFlightID, undiscountedprice(discount, currentPrice), discount);
//
//            }

            return true;
        }

        public double undiscountedprice(double discount, Double currentPrice) {
            return currentPrice / (1 - ((double) discount / 100));
        }

        public double getPercentage(String id) {

            return FlightStorageService.discountFetcher(id);
        }

        public String checkYesOrNo(String string) {
            if (string.contains("true")) {
                return "Yes";
            }
            return "No";
        }

        public double getPrice(String id) {
            return FlightStorageService.priceFetcher(id);
        }

        public boolean checkIfStaticDiscountExists() {

//            int index = 0;
//
//            List<GetInfoForFlights> list = FlightDataDao.getDataForFlights();
//            for (GetInfoForFlights a : list) {
//
//                boolean check = list.get(index).getDdiscount();
//                if (check) {
//                    return true;
//                }
//                index++;
//
//            }


            return false;
        }


    public void updateFlightData(String value, String planeid,String value1, String value2, String s, String s1, LocalDate value3, LocalDate value4, boolean statdiscount, boolean b, float price,String flightid) throws SQLException {
        FlightData flightData = new FlightData(value,planeid,value1,value2, s, s1, value3, value4, statdiscount, b, price);
        FlightStorageService.updateFlightData(flightData,flightid);

    }

    //test for flightmanager

    public ObservableList<GetInfoForFlights> getFlightsById(List<String> flightID){return FlightStorageService.getFlightsbyId(flightID);}

}

