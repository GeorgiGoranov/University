## Data Dictionary

Entities: `Customer`,`Flight`,`Airplane`,`Company`(Admin),`Ticket`

### Table

| Data name | Explanation |
| --- | ---|
<<<<<<< Updated upstream
|<a name = "airline-company"> </a> `Airline Company`|An airline company is a business that has multiple [airplanes](#airplane) with the intention to sell seats on these airplanes.|
|<a name = "airplane" ></a> `Airplane`|An Airplane is a vehicle which has [seats](#seat) and is used for the traveling of long distances through [flights](#flight).|
|<a name = "Trip" ></a>`Flight`|A flight is a long distance travel that takes a [route](#route) through the sky utilizing an [airplane](#airplane) accessible via the purchase of [tickets](#ticket).|
|<a name = "ticket" ></a>`Ticket`|A ticket is used to access a [seats](#seat) on the [flight](#flight) and is bought by a [customer](#customer).|
|<a name = "customer" ></a>`Customer`|A customer is an individual that pays for the booking, but does not necessarily have to be a [passenger](#passenger).|
|<a name = "Flight" ></a>`Trip`|Travel journey to get from starting point to ending point that includes one or more [Trips](#flight).|
|<a name = "luggage" ></a>`Luggage`|A container of personal items that can either be a registered luggage or a non-registered luggage.|
|<a name = "dynamic-discount" ></a>`Dynamic discount`|A dynamic discount is a price reduction that depends on the sunset time of the arrival destination, the discount is proportional to said sunset time.|
|<a name = "static-discount" ></a>`Static discount`|A static discount is price reduction of a [flight](#flight) that incorporates the whole month selected made by [sales officer](#sales-officer) that is just a flat percentage discount on all flights inside that month.|
|<a name = "sales-officer" ></a>`Sales officer`|Sales officer is an employee of the [airline company](#airline-company) who is making [flights](#flight) for the [customer](#customer) and the [sales employee](#sales-employee) and making price reduction|
|<a name = "sales-manager" ></a>`Sales manager`| Sales manager is an employee of the [airline company](#airline-company) who is monitoring [key performance indicators](#key-p-i)|
|<a name = "sales-employee" ></a>`Sales employee`|Sales employee is an employee who is working for the [airline company](#airline-company) in the [airport](#airport) and is making [booking](#booking) reservations for the [customers](#customer)|
|<a name = "eta" ></a>`ETA`|Estimated time of arrival.|
|<a name = "etd" ></a>`ETD`|Estimated time of departure.|
|<a name = "management-dashboard" ></a>`Management dashboard`|A dashboard accessable only to [sales managers](#sales-manager) that displays the original [flight/s](#flight) and their [recurrences](#recurrence).|
|<a name = "recurrence"></a>`Recurrence`|The act or pattern of something happening repeatedly or occurring at regular intervals.|
|<a name = "booking" ></a>`Booking`|The reservation for one or more desired [flights](#flight), payed by one [customer](#customer) and containing one or more [passengers](#passenger).|
|<a name = "airport" ></a>`Airport`|A complex of runways and buildings for the take-off, landing, and maintenance of civil aircraft, with facilities for [passengers](#passenger).|
|<a name = "key-p-i" ></a>`Key performance indicators`|Indicates total revenue, number of [tickets](#ticket) sold, statistics.|
|<a name = "seat"></a>`Seat`|A specific location inside an [airplane](#airplane) where individual can plump oneself statically|
|<a name = "Standby Ticket"></a>`Standby Ticket`| To be added|
|<a name = "passenger"></a> `Passenger` |A person who is traveling with an [airplane](#airplane) but is not necessarily a [customer](#customer)|

[flights](#flight)
