package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers - trips.map { it.driver }

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { passenger in it.passengers } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { trip -> (trip.passengers.contains(passenger) && trip.driver == driver) } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.associateWith { passenger -> trips.filter { trip -> trip.passengers.contains(passenger) }.toList() }
        .filterValues { passengerTrips ->
            val (tripsWithDiscount, tripsWithoutDiscount) = passengerTrips.partition { pt -> pt.discount != null }
            tripsWithDiscount.size > tripsWithoutDiscount.size
        }.keys

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null
    return trips.groupBy { trip ->
        val start = trip.duration / 10 * 10
        val end = start + 9
        start..end
    }
        .maxBy { (_, trips) -> trips.size }.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumOf (Trip::cost)
    val sortedDriversIncome: List<Double> = trips
        .groupBy(Trip::driver)
        .map { (_, tripsByDriver) -> tripsByDriver.sumOf(Trip::cost) }
        .sortedDescending()

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
        .take(numberOfTopDrivers)
        .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}