
# Use Case Descriptions

## Table of Contents

- [Create_Flight](#create_flight)
- [Edit_Flight](#edit_flight)
- [Create recurring flight/s in the management board](#use_management_board)
- [Search_Flight](#search_flight)
- [View_Flight](#view_flight)
- [Add_Dynamic_Discount](#add_dynamic_discount)
- [Add_Static_Discount](#add_static_discount)
- [Log_In](#log_in)
- [Create_Booking](#create_booking)
- [View_Booking](#view_booking)
- [Edit_Booking](#edit_booking)
- [Cancel_Booking](#cancel_booking)
- [Search_Booking](#search_booking)
- [Choose_Seat](#choose_seat)

<br> 

<table>
  <tr>
   <td>
Name:
   </td>
   <td><a name="create_flight"></a>Create Flight
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>A Sales Employee wants to create a Flight.
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Employee is <span style="text-decoration:underline;">signed in</span>
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Employee selects create Flight

<li>System displays formular 
<ol>
 
<li>Airport departing
 
<li>Airport arriving
 
<li>Estimated time departing
 
<li>Estimated time arriving
 
<li>Day of departure
 
<li>Day of arrival
 
<li>Plane type (number of seats)
</li> 
</ol>

<li>Employee fills in the formular

<li>System displays formular with given information

<li>Employee selects done
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>New Flight has been created
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>3a. Employee doesnt fill in all the necessary information
<p>
3.1. System displays an error message. Go back to step 2.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="edit_flight"></a>Edit Flight
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>Sales Employee wants to change information of a flight
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Flight exists
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Employee selects <span style="text-decoration:underline;">view flights</span>

<li>System displays flights

<li>Employee selects a flight to be edited

<li>System displays formular with changeable information of the selected flight 
<ol>
 
<li>plane id
  
<li>departure
  
<li>arrival
  
<li>ETD
  
<li>ETA
 
<li>day of departure
 
<li>day of arrival
 
<li> price
 
</li> 
</ol>

<li>Employee edits information and confirms

<li>System saves changes
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Flight has been edited
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>4a. Employee fills in wrong information
<p>
4.1 System shows a warning. Go back to step 3.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="use_management_board"></a>Create recurring flight/s in the management board
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Manager 
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>Manager uses the management board 
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>The Actor needs to be logged in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>1. The Actor selects management board 
<p>
1. System shows all the flights
<p>
2. The Actor choses a fligh
<p>
3. System auto fills in the information of the chosen flight
<p>
4. The Actor add the additional inforation needed.
<p>
5. System calculates and populates the flight database.

</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Manager successfully used the management board 
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>none
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>none
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="search_flight"></a>Search flight
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>A Sales employee wants to look up a flight
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>The actor must be signed in.
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor navigates to search flights.

<li>System displays all flights.

<li>The Actor filters the list of flights, such as departure airport, arrival airport, departure date and arrival date, and price. 

<li>System displays matching flights.
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>The actor will search potential flights relevant to the information he gave the system.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>2a. No flights were found in the database.
<p>
2a.1 System doesn’t display any flights.
<p>
2a.2 Use Case ends here.
<p>
4a. No flights match information.
<p>
4a.1 System doesn’t display any flights.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>\
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="view_flight"></a>View flight
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>After searching for a flight the actor will view a list of potential flights
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Actor is logged in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor <ins>searched for flights</ins> and selects one.

<li>System shows information about flight available flights.
<li>The Actor choses a desired flight.
<li>System displays the information about the flight.
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>The actor sees only the selected flight. 
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>\
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>2b. There is no machking flights with the inputted information.
<p>
2.b Use Case ends here.
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_dynamic_discount"></a>Add Dynamic Discount
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Officer
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>The Sales Officer adds a new dynamic discount to a specific flight.
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Flight needs to exist.
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor <span style="text-decoration:underline;">looks up a fligh</span> and chooses one.

<li>System gives an opportunity to add a discount displaying discount menu.

<li>Actor selects to add a dynamic discount.

<li>System requires a discount percentage and confermation.

<li>Actor fills in discount percentage and gives confermation.

<li>System displays success alert and saves discount in database.

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor added a new dynamic discount.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5a. Actor chooses to save information but fills in the wrong information.
<p>
        5.1 System shows message that information was wrong. Go back to step 1.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>
<p>
5a. Actor selects to close discount menu. Return to step 1.
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_static_discount"></a>Add Static Discount
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Officer
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>The Sales Officer adds a new static discount to multiple flights.
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td> Flights needs to exist.
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor <span style="text-decoration:underline;"> chooses </span> add static discount.

<li>System displays static discount menu and gives an opportunity to add a discount percentage and month.

<li>Actor chooses a specific month and a specific percentage to apply.

<li>System requires confermation.

<li>Actor gives confermation.

<li>System displays success alert and saves discounts in database.

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor added new static discounts.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5a. Actor chooses to save information but fills in the wrong information.
<p>
        5.1 System shows message that information was wrong. Go back to step 2.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>5a
<p>
5a. Actor selects to close discount menu. Return to step 1.
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="log_in"></a>Log In
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Employee/s
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>a Employee wants to log into his account
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Employee has a account
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>user selects login

<li>system shows login page

<li>user enters passcode and confirms

<li>system logs the user in
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>a employee has logged into his account
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>3. user enters wrong passcode and confirms
<p>
3.1 system shows something is not correctly filled in. Go back to step 2.
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="create_booking"></a>Create Booking
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>sales employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>user wants to create a booking for a trip
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>The actor is logged in, at least one flight has been created
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>user selects create booking

<li>system asks to chose a flight(s)

<li>user chooses flight(s)

<li>system asks to chose a customer

<li>user selects a customer

<li>system asks to select a passenger(s)

<li>user selects passenger(s)

<li>system asks if user wants to save the booking

<li>user confirms

<li>system shows information related to the booking, such as price

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>user created a booking
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>4. There is no desired customer in the database
<p>
4.1 User creates new customer
<p>
6. There is no desired Passenger in the database
<p>
6.1 User creates new passenger
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
  <td>
    </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="view_booking"></a>View booking
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>User wants to view booking
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>User is logged in  
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>User clicks on bookings

<li>System displays a list of existing bookings

<li>User selects a desired booking

<li>System shows all the information related to the booking such as: 
<ol>
 
<li>Tickets (one or more)  
<ol>
  
<li>Airport departing
  
<li>Airport arriving
  
<li>Estimated time of departure
  
<li>Estimated time of arrival
  
<li>Day of departure 
  
<li>Day of arrival
  
<li>Plane type (number of seats)
  
<li>Packing (Extra luggage)
  
<li>Security check (recommendation on when to begin security check)
  
<li>Gate (usually displays about an hour before departure)
</li>  
</ol>
</li>  
</ol>
</li>  
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>User viewed a booking
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>3. User does not have any bookings
<p>
3.1 System displays no bookings
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="edit_booking"></a>Edit booking
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>User wants to edit a booking
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>User has to be signed in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>User <span style="text-decoration:underline;">searches booking</span> and clicks on a desired booking

<li>System asks whether the user wants to edit or cancel the booking.

<li>User selects edit booking

<li>System displays all the information that can be changed: 
<ol>
 
<li>Select seats
 
<li>Add luggage 
</li> 
</ol>

<li>User chooses to select seats.

<li>System displays all available seats through the seat overview.

<li>User selects desired available seats.

<li>System asks for confirmation.

<li>User confirms.

<li>System saves changed information.
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Booking information was edited.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>7a. User doesn’t confirm.
<p>
7.1 System doesn’t save changes.
<p>
51. User didn’t change anything.
<p>
5.1 System displays error message. Go back to step 4.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>5a. User chooses add luggage.
<p>
5.1 System displays luggage options.
<p>
5.2 User selects desired luggage option. Go back to step 8.
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="cancel_booking"></a>Cancel booking
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>Actor wants to cancel a booking
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Booking must exist and User must be logged in 
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>User <span style="text-decoration:underline;">searches booking</span> and clicks on a desired booking

<li>System asks whether the user wants to edit or cancel the booking.

<li>User selects cancel booking

<li>System asks for confirmation.

<li>User confirms.

<li>System removes the booking from the users account and cancels the users reservation 
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Booking is removed
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5a. User doesn’t confirm.
<p>
5.1 System stops process.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>N/A
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="search_booking"></a>Search Booking
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>Actor wants to search booking 
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Actor need to be logged in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>1. Actor goes to list of bookings in the system 
<p>
2. System shows all bookings in the system 
<p>
3. Actor types the name/id/number of the desired booking
<p>
4. System shows the desired result from the search
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor made a booking search
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>4a.1 System does not find the desired result from the search
<p>
4a.2 System shows invalid search
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
   </td>
  </tr>
</table>

<br> <br> 

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="choose_seat"></a>Choose Seat
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Employee
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>Actor wants to add a seat for a passanger. 
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Actor needs to be logged in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>1. Actor <span style="text-decoration:underline;">creates a booking</span>.
<p>
2. Actor <span style="text-decoration:underline;">searches a booking</span>.
<p>
3. System displays all bookings.
<p>
4. Actor chooses a specific booking.
<p>
5. System displays passangers in booking.
<p>
6. Actor chooses passanger and clicks button to add seat.
<p>
7. System displays flight information and seat options.
<p>
8. Actor chooses flight and seat.
<p>
9. System shows chosen information.
<p>
10. Actor presses button to save information.
<p>
11. System saves information.
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor added a seat to a booking.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>8a.1 Actor chooses flight in which passenger already booked a seat.
<p>
8a.2 System shows chosen seat. Go back to step 8.
<p>
8b.1 Actor chooses a seat that is already booked. 
<p>
8b.2 System shows warning that seat is already booked.Go back to step 8.
<p>
10a.1 Actor presses button to save information with out choosing a seat.
<p>
10a.2 System shows warning that no seat was chosen. Go back to step 8.
<p>
10b.1 Actor presses button to save information with out choosing a flight.
<p>
10b.2 System shows warning that no flight was chosen. Go back to step 8.
   </td>
 </tr>
  <tr>
   <td>Extension:
      
   </td>
   <td><p>
       8a.1 Actor chooses flight and clicks button to choose random seat.
       8a.2 System generates random seat and saves information.
   </td>
  </tr>
</table>

