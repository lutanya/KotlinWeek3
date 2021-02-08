package taxipark

import java.util.*

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
    val mapOfPassengers =
        flattenSet(driverToTrips[driver])?.filter { it.value > 1 }
    return mapToSet(mapOfPassengers)
}
fun flattenSet(setOfTrips: List<Trip>?)=setOfTrips?.map{it.passengers}?.flatten()?.groupingBy { it }?.eachCount()

fun mapToSet(mapOfPassengers:Map<Passenger,Int>?)=mapOfPassengers?.keys?.toSet()?: emptySet()
/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger>{
val (tripsWithDiscount, tripWithoutDiscount)=this.trips.partition { it.discount != null }
    val passengersWithoutDiscountTrip=flattenSet(tripWithoutDiscount)
    val mapOfPassengers = flattenSet(tripsWithDiscount)?.filter{ (key, value) -> !(passengersWithoutDiscountTrip?.containsKey(key)?:false)
            || (passengersWithoutDiscountTrip?.containsKey(key)?:true && passengersWithoutDiscountTrip?.getValue(key)?:0 < value)}
    return mapToSet(mapOfPassengers)
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    val frduration = this.trips.groupingBy { it.duration/10 }.eachCount().maxByOrNull { it.value }?.key ?: return null

    return frduration*10..frduration*10+9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(this.trips.isEmpty()) return false
    
    val driversWithTrip=this.trips.groupBy{ it.driver }
    val costsList= this.allDrivers.associateBy ({ it },{driversWithTrip[it]}).toList().sortedBy { (_, value) -> -(value?.size?:0)}.toMap().values.map { it?.sumByDouble{trip->trip.cost}?:0.0}

    val sumUpDriversCount= (this.allDrivers.size*0.2).toInt()
    var sumTwentyPercent = 0.0
    var sum = 0.0
    for(i in costsList.indices) {
        if(i<sumUpDriversCount) {
            sumTwentyPercent += costsList[i]
        }
        sum+=costsList[i]
    }
    return sumTwentyPercent/sum>=0.8
}