package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver ->
        trips.none { it.driver == driver }
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { trip -> trip.passengers.contains(passenger) } >= minTrips
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
    allPassengers.associateWith { passenger ->
        this.trips.filter { trip -> trip.passengers.contains(passenger) }.toList()
    }
        .filter { (_, passengerTrips) -> (passengerTrips.count { it.discount != null && it.discount > 0 } > (passengerTrips.size / 2)) }.keys.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null
    val maxDuration = trips.maxOf { it.duration }
    val minDuration = trips.minOf { it.duration }
    val tripsByPeriod: MutableMap<IntRange, MutableList<Trip>> = mutableMapOf();

    fun getIntRange(duration: Int): IntRange = IntRange((duration / 10) * 10, (duration / 10) * 10 + 9)

    for (duration in minDuration..maxDuration) {
        tripsByPeriod[getIntRange(duration)] = mutableListOf()
    }
    trips.forEach { trip -> tripsByPeriod[getIntRange(trip.duration)]?.add(trip) }

    return tripsByPeriod.maxBy { (_, value) -> value.size }.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val totalRevenue = trips.sumOf { trip -> trip.cost }
    val revenueByDriver = allDrivers.associateWith { driver ->
        trips.filter { trip -> trip.driver == driver }.sumOf { it.cost }
    }.values.sorted().reversed()
    return revenueByDriver.subList(0, (allDrivers.size * 0.2).toInt()).sum() >= (0.8 * totalRevenue)
}