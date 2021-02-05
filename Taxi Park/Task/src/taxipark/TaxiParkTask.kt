package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver>
    {
        val driversWithTrip=this.trips.map { it.driver.name }
        return this.allDrivers.filter { driver->!driversWithTrip.any { it == driver.name } }.toSet()
    }



/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger>
    {
        val passengers = this.trips.map { it.passengers.map{passenger->passenger.name} }
        val count: (String)->Int = {passengerName->passengers.count{ passengers-> passengers.any { it == passengerName } }}
        return this.allPassengers.filter { count(it.name)>= minTrips}.toSet()
    }

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger>? {
    val driverToTrips = this.trips.groupBy { it.driver }
    val listOfPassengers =
        driverToTrips[driver]?.map { it.passengers }?.flatten()?.groupingBy { it }?.eachCount()?.filter { it.value > 1 }
    return listOfPassengers?.keys?.toSet()?: emptySet()
}


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger>{

}


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return TODO()
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    TODO()
}