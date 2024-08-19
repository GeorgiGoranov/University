<!-----

Yay, no errors, warnings, or alerts!

Conversion time: 1.765 seconds.


Using this Markdown file:

1. Paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β34
* Wed Mar 08 2023 07:30:12 GMT-0800 (PST)
* Source doc: Test Scenarios
* Tables are currently converted to HTML tables.
----->
## Table of contets
- [Create flight](#create_flight)
- [Edit Flight](#edit_flight)
- [Search flight](#search_flight)
- [View flight](#view_flight)
- [Create recurring flight/s in the management board](#management_board_tickets)
- [Sign up](#sign_up)
- [Add dynamic_discount](#add_dynamic_discount)
- [Add static_discount](#add_static_discount)
- [Edit discount](#edit_discount)
- [Remove discount](#remove_discount)
- [Log in](#log_in)
- [Create booking](#create_booking)
- [View booking](#view_booking)
- [Cancel booking](#cancel_booking)
- [Select seats](#select_seats)
- [Add baggage](#add_baggage)
- [Choose Seat](#choose_seat)

---

**Test Scenarios**





* include is basically when something includes another use case (e.g sign up)
* extend: e.g options for a booking aren't mandatory so it's just an extension

<table>
  <tr>
   <td>
Name:
   </td>
   <td><a name = "create_flight"></a> Create Flight EIN - BER
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
   <td>The Actor needs to be logged in
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Employee selects create Flight

<li>System displays formula 
<ol>
 
<li>Airport departing
 
<li>Airport arriving
 
<li>Estimated time departing
 
<li>Estimated time arriving
 
<li>Day of departure (select)
 
<li>Day of arrival (select)
 
<li>Plane type (number of seats) (select)
</li> 
</ol>

<li>Employee fills in the formula 
<ol>
 
<li>Eindhoven (EIN)
 
<li>Berlin (BER)
 
<li>05:30 pm
 
<li>07:20 pm
 
<li>05.03.2023
 
<li>05.03.2023
 
<li>Boeing 707 (selectable)
</li> 
</ol>

<li>System displays formula with given information

<li>Employee selects done
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Sales Employee successfully created the flight EIN - BER
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name = "edit_flight"></a>Edit Flight EIN - BER
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
   <td>Sales Employee wants to change the airplane type of the flight EIN - BER
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

<li>Employee selects <span style="text-decoration:underline;">edit flights</span>

<li>System displays flights in a table.

<li>Employee selects the flight EIN - BER to be edited

<li>System displays formula with changeable information of the selected flight 
<ol>
 
<li>plane id: PL1
   
<li>Departure: MUC/Munich  
   
<li>Arrival: FRA/Frankfurt
 
<li>arrival time: 07:20
 
<li>departure time: 17:30

<li>Day of Departure: 09.06.2023

<li>Day of Arrival: 09.06.2023
   
<li>Price: 123.0

</li> 
</ol>

<li>Employee edits information 
<ol>
 
<li>PL2
 
<li>arrival time: 07:20
 
<li>departure time: 17:30
</li> 
</ol>

<li>System displays changed flights information in the formular

<li>Employee selects Save Flight
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Sales employee changed airplane type for the flight EIN - BER
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name = "management_board_tickets"></a>Create recurring flight/s in the management board
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
   <td>Manager views the management board and creates recurrence for flight/s
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
2. System shows a table of all the abailable flights
<p>
3.The Actor selects <strong>flight</strong> from the table of all flights.
<p>
4.The System fills in automatically the flight number, date of departure and date of arrival
<p>
5.The Actor fills in the interval of recurrence and the limit date of recurrence.
<p>
6.The Actor selects the set up function to finalise the recurrance.
<p>
7.The System calculates and inserts the newly made recurrences into database and displays them onto the table with flights 
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Manager successfully creates recurrence/s of a selected flight
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>none
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
    <td> <a name = "search_flight"></a> Search flight
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
   <td>Employee wants to find a flight from Amsterdam to London
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

<li>System shows a table of all the available flights (AMS/Amsterdam - LHR/London, VNO/Vilnius - FRA/Frankfurt, FCO/Rome - BCN/Barcelona).

<li>Actor filters from the searchbar the arrival and departure locatioins (AMS/Amsterdam - LHR/London).

<li>System filters the table based on each character typed.

<li>System shows a list of available flights in the table only from the desired departure to the desired arival locations.

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
   <td>4b. System did not find any information that maches the inputt of the actor.
   <p>
   4b.1 Use case ends here.
   </td>

  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>\
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name = "view_flight"></a> View flight 
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
   <td>After searching the actor will view a desired flights
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

<li>Actor <ins>searched for flights</ins>.

<li>System shows all the available flights that will travel from and to the selected destinations.

<li>Actor choses a desired flight from the outcome of the search.

<li>System shows only the chosen flight and additional information.
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
   <td>2b System did not find any information that maches the inputt of the actor.
   <p>
   2b.1 Use case ends here.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td> 
   <td>\
   </td>
  </tr>
</table>


Rares: 


<table>
  <tr>
   <td>Name:
   </td>
   <td><a name = "sign_up"></a>Sign Up
   </td>
  </tr>
  <tr>
   <td>Actor:
   </td>
   <td>Sales Manager, Sales Employee, Sales Officer
   </td>
  </tr>
  <tr>
   <td>Description:
   </td>
   <td>User creates a new account
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>none
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>System shows list of requested Name, Birthdate, E-mail, Adresse

<li>Actor fills in necessary <strong>Jeremy Jefferson, 03.06.2000, jeremy@email.com, Moonpark 7 Huntsville</strong>

<li>System wants Actor to confirm 

<li>Actor confirms

<li>System safes information
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor has created a new account.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>2a. Actor fills in <strong>03.06.2023 as birthdate</strong>
<p>
2a.1 System shows message that <strong>birthday cannot be in future</strong>. Go back to step 2.
   </td>
  </tr>
  <tr>
   <td>Extensions:
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>




<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_dynamic_discount"></a>Add Dynamic Discount (Success)
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

<li>Actor looks up a flights and <strong>clicks on</strong> one.

<li>System gives an opportunity to add a discount displaying discount menu.

<li>Actor <strong>clicks on</strong> add a dynamic discount.

<li>System displays dynamic discount menu.

<li>Actor <strong>writes</strong> "10" discount percentage and <strong>clicks</strong> "Add" confermation.

<li> System displays success alert "Dynamic discount of 10% has been succefully added"and saves discount in database.

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
   <td>
<p>
       
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>
<p>

   </td>
  </tr>
</table>

<br> <br> 
<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_dynamic_discount"></a>Add Dynamic Discount (Fail)
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
   <td>The Sales Officer fails to add a new dynamic discount to a specific flight.
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

<li>Actor looks up a flights and <strong>clicks on</strong> one.

<li>System gives an opportunity to add a discount displaying discount menu.

<li>Actor <strong>clicks on</strong> add a dynamic discount.

<li>System displays dynamic discount menu.

<li>Actor <strong>writes</strong> "I am hungry " discount percentage and <strong>clicks</strong> "Add" confermation.

<li> System displays error alert "Only discounts of type INT are accepted" and exits all discount menus.

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor failed to add a new dynamic discount.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>
<p>
       
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>
<p>

   </td>
  </tr>
</table>

<br> <br> 
<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_static_discount"></a>Add Static Discount (Success)
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
   
<li>Actor <strong>clicks on</strong> add static discount.

<li>System displays static discount menu and gives an opportunity to add a discount percentage and month.

<li>Actor <strong>clicks on drop down menu and selects<strong> "5" and <strong>writes</strong> a "20" percentage to apply.

<li>System requires confermation.

<li>Actor <strong>clicks on</strong> "Save" confermation.

<li>System displays success alert " A static discount has been succefully added" and applys a 20 percent discount on all flights that depart in the 5th month (may).

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
   <td>/
<p>
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
<p>
   </td>
  </tr>
</table>

<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_static_discount_f"></a> Add Static Discount (Fail)
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
   <td>The Sales Officer trys to add a new static discount to multiple flights but fails.
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td> Flights do not exist.
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor <strong>clicks on</strong> add static discount.

<li>System displays static discount menu and gives an opportunity to add a discount percentage and month.

<li>Actor <strong>clicks on drop down menu and selects<strong> "8" and <strong>writes</strong> a "25" percentage to apply.

<li>System requires confermation.

<li>Actor <strong>clicks on</strong> "Save" confermation.

<li>System displays error alert "There are no flights present for the month selected".

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor failed to add new static discounts.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td> 
<p>
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
<p>
   </td>
  </tr>
</table>

   <table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_static_discount_f"></a> Add Static Discount (Fail)
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
   <td>The Sales Officer trys to add a new static discount to multiple flights but fails.
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

<li>Actor <strong>clicks on</strong> add static discount.

<li>System displays static discount menu and gives an opportunity to add a discount percentage and month.

<li>Actor <strong>clicks on drop down menu and selects<strong> "6" and <strong>writes</strong> a "125" percentage to apply.

<li>System requires confermation.

<li>Actor <strong>clicks on</strong> "Save" confermation.

<li>System displays error alert "Discount percentage cant be more than 99%".

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor failed to add new static discounts.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td> 
<p>
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
<p>
   </td>
  </tr>
</table>
 <table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_static_discount_f"></a> Add Static Discount (Fail)
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
   <td>The Sales Officer trys to add a new static discount to multiple flights but fails.
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

<li>Actor <strong>clicks on</strong> add static discount.

<li>System displays static discount menu and gives an opportunity to add a discount percentage and month.

<li>Actor <strong>clicks on drop down menu and selects<strong> " " and <strong>writes</strong> a " " percentage to apply.

<li>System requires confermation.

<li>Actor <strong>clicks on</strong> "Save" confermation.

<li>System displays error alert "Values cannot be null".

</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor failed to add new static discounts.
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td> 
<p>
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>/
<p>
   </td>
  </tr>
</table>  
   
   
   
   
   
   
   
   
<table>
  <tr>
   <td>Name:
   </td>
    <td><a name="edit_discount"></a> Edit discount
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
   <td>Actor accesses the Edit discount 
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Actor needs to be logged in and discount must exist
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor selects discounted flight <strong>EIN->SOF</strong>.

<li>System gives opportunity to edit current discount.

<li>Actor selects edit discount.

<li>System displays:

<li>Modify discount description

<li>Modify functionality

<li>Remove current discount

<li>Cancel

<li>Actor<strong> selects Modify discount description</strong>

<li>System showcases the discount description of selected flight

<li><strong>Actor writes or deletes information about the discount and clicks save</strong>

<li>System saves changes and returns to Discount table

<li><strong>Actor clicks cancel </strong>

<li>System closes Discount table and returns to Flights
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Actor accessed the Edit discount table
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>N/A
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>N/A
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td> <a name="remove_discount"></a> Remove discount
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
   <td>User wants to remove a specific discount
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>Actor needs to be logged in and discount must exist
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor chooses discounted flight <strong>EIN->SOF</strong>

<li>System gives opportunity to edit current discount.

<li>Actor <strong>selects edit discount</strong>.

<li>System displays:

<li>Modify discount description

<li>Modify functionality

<li>Remove current discount

<li>Cancel

<li>Actor<strong> selects “Remove current discount”</strong>

<li>System removes discount placed on flight selected
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Discount is removed from selected flight
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>N/A
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>N/A
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="log_in"><a> Log in
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
   <td>Employee has to log into his account
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td>he has a account
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>user selects login

<li>system shows login page

<li>user enters <strong>test@gmail.com</strong> and <strong>123456</strong> and confirms

<li>system logs the user in
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Employee has logged into his account
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>3. user enters <strong>32424 </strong>instead of 123456 and confirms
<p>
3.1 system shows something is not correctly filled in
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="create_booking">Create booking
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
   <td>user is logged in, at least one flight has been created
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>user selects create booking

<li>system asks to chose the desired flight

<li>user chooses flight from Amsterdam to Sofia with flight id "FL1"

<li>system displays the selected flight in the list of selected flights.

<li>user requests to proceed with customer data

<li>system asks user to choose or create a customer

<li>user selects an existing customer

<li>system displays the selected customer in the list of selected customers

<li>user requests to proceed with passenger data.

<li>system asks user to choose or create a passenger
   
<li>user selects an existing passenger
   
<li>system displays passenger in the list of selected passenger
   
<li>user requests to save the booking
   
<li>system asks for confirmation
   
<li>user aproves
   
<li>system saves the booking and displays the price
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
   <td>2.1 User wants to proceed to customer selection without choosing a flight
<p>
2.2 System warns the user that no flights were selected
<p>
7.1 user selects to create a new customer
<p>
7.2 system asks for name, last name and personal number of the customer
<p>
7.3. user enters "John", "Doe" and "123456789" accordingly and confirms
<p>
7.4 system displays success message
<p>
7.3.1 user enters "John", "Doe" and "123456BTDE" accordingly and confirms
<p>
7.3.2 System displays error messsage due to personal number containing letters
<p>
11.1 User selects to create a new passenger
<p>
11.2 System asks for name, last name, personal number and birth date of the customer
<p>
11.3 User user enters "John", "Doe", "123456789" and "01-10-2024" accordingly and confirms
<p>
11.4 System displays error message due to birth date being a future date
<p>
11.3.1  User user enters "John", "Doe", "123456789" and "01-10-1999" accordingly and confirms
<p>
11.3.2 System displays succes message
   </td>
  </tr>
</table>

 


<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="view_booking">View booking VIE-DXB
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

<li>User selects the booking VIE-DXB

<li>System shows all the information related to the booking: 
<ol>
 
<li>Ticket  
<ol>
  
<li>Airport departing - VIE Vienna
  
<li>Airport arriving - SAW Istanbul Sabiha
  
<li>Estimated time of departure - 15:00
  
<li>Estimated time of arrival - 19:15
  
<li>Day of departure - Tue, 7 Mar 2023
  
<li>Day of arrival - Tue, 7 Mar 2023
  
<li>Plane type (number of seats) - 220
  
<li>Packing (Extra luggage) - none
  
<li>Security check - Recommended to start security check no later than 13:30.
  
<li>Gate (usually displays about an hour before departure) - A1
</li>  
</ol>
 
<li>Ticket  
<ol>
  
<li>Airport departing - SAW Istanbul Sabiha
  
<li>Airport arriving - DXB Dubai
  
<li>Estimated time of departure - 20:30
  
<li>Estimated time of arrival - 01:55
  
<li>Day of departure - Tue, 7 Mar 2023
  
<li>Day of arrival - Wed, 8 Mar 2023
  
<li>Plane type (number of seats) - 220
  
<li>Packing (Extra luggage) - none
  
<li>Security check - Recommended to start security check no later than 19:00.
  
<li>Gate (usually displays about an hour before departure) - A9
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



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="cancel_booking">Edit booking: cancel booking VIE-DXB
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
   <td>User wants to cancel the booking
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

<li>User selects the booking VIE-DXB

<li>System asks whether the user wants to edit or cancel the booking.

<li>User selects cancel booking

<li>System asks for confirmation.

<li>User clicks confirm.

<li>System closes the dialog and cancels the booking.
</li>
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Booking was canceled
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5a. User doesn’t confirm.
<p>
7.1 System doesn’t cancel the booking
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="select_seats">Edit booking: Select seats for booking VIE-DXB
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
   <td>User wants to selects seats for a booking
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

<li>User selects the booking VIE-DXB

<li>System asks whether the user wants to edit or cancel the booking.

<li>User selects edit booking

<li>System displays all the information that can be changed: 
<ol>
 
<li>Select seats
 
<li>Add baggage
      3. User chooses to select seats for the flight(s).
<p>
      4. System displays the list of available seats.
<p>
      5. User selects 1A seat.
<p>
      6.  System asks for confirmation.
<ol>

<li>User selects confirm.

<li>System closes the dialog and saves the seat.
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
   <td>Seats were chosen
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5. User clicks cancel.
<p>
5.1 System redirects the user back to the booking info.
<p>
7. User does not confirm
<p>
7.1 System closes the dialog and does not save the selected seats
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>8. System closes the dialog and asks the user to choose seats for the next flight in the booking. Back to step 4.
   </td>
  </tr>
</table>



<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="add_baggage">Edit booking: Add baggage for booking VIE-DXB
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
   <td>User wants to add baggage
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

<li>User selects the booking VIE-DXB

<li>System displays all the information that can be changed: 
<ol>
 
<li>Select seats
 
<li>Add baggage
 
<li>Cancel booking
      3. User chooses to add baggage for the flight(s).
<p>
      4. System displays a list of available baggage options.
<p>
      5. User selects to add priority luggage.
<p>
      6. System asks for confirmation.
<p>
      7. User selects confirm.
<p>
      8. System closes the dialog and adds priority luggage.
</li> 
</ol>
</li> 
</ol>
   </td>
  </tr>
  <tr>
   <td>Result:
   </td>
   <td>Baggage was added
   </td>
  </tr>
  <tr>
   <td>Exceptions:
   </td>
   <td>5. User clicks cancel.
<p>
5.1 System redirects the user back to the list of available baggage.
<p>
7. User does not confirm
<p>
7.1 System closes the dialog and does not add the baggage.
   </td>
  </tr>
  <tr>
   <td>Extension:
   </td>
   <td>8. System closes the dialog and asks the user to choose preferred baggage for the next flight in the booking. Back to step 4.
<p>
5. User selects to add registered luggage.
   </td>
  </tr>
</table>
   
   
  
<table>
  <tr>
   <td>Name:
   </td>
   <td><a name="choose_seat">Choose Seat
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
   <td>Actor wants to choose a sea for a booking.
   </td>
  </tr>
  <tr>
   <td>Pre-condition:
   </td>
   <td> Actor is logged in.
   </td>
  </tr>
  <tr>
   <td>Scenario:
   </td>
   <td>
<ol>

<li>Actor <span style="text-decoration:underline;">created a booking.</span>

<li>Actor <span style="text-decoration:underline;">searches a booking</span>.

<li>System displays all bookings.

<li>Actor chooses a booking with the booking ID "B2".

<li>System displays passangers in booking.

<li>Actor chooses passanger with the ID "P2" and clicks button to add seat.

<li>System displays flight information and seat options.

<li> Actor chooses flight wit the ID "FL1" and seat "A12".

<li>System shows chosen information.

<li>Actor presses button to save information.
   
<li>System saves information.

</li>
</ol>
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
   <td>8a.1 Actor chooses flight"FL1" in which passenger already booked the seat.
<p>
8a.2 System shows chosen seat. Go back to step 8.
<p>
8b.1 Actor chooses a seat "A12", that is already booked. 
<p>
8b.2 System shows "Seat is already choosen".Go back to step 8.
<p>
10a.1 Actor presses button to save information with out choosing a seat.
<p>
10a.2 System shows "Please choose a seat". Go back to step 8.
<p>
10b.1 Actor presses button to save information with out choosing a flight.
<p>
10b.2 System shows "Please choose a flight". Go back to step 8.
   </td>
  </tr>
   <td>Exceptions:
   </td>
   <td> 8a.1 Actor chooses flight "FL1" and clicks random seat button.
<p>
8a.2 System generates seat "A20" and saves information.
   </td>
</table>

