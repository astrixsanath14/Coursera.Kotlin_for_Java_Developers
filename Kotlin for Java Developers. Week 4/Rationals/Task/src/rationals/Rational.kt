package rationals

import java.math.BigInteger

fun gcd(a: BigInteger, b: BigInteger): BigInteger {
    return a.gcd(b)
}

class Rational(n: BigInteger, d: BigInteger) : Comparable<Rational> {
    val n: BigInteger
    val d: BigInteger

    init {
        require(d != BigInteger.ZERO) { "Denominator cannot be zero" }

        // Ensure the sign is always carried by the numerator
        val sign = if (d < BigInteger.ZERO) -BigInteger.ONE else BigInteger.ONE
        val common = n.gcd(d)

        this.n = (n / common) * sign
        this.d = (d / common).abs()
    }

    operator fun plus(other: Rational): Rational = Rational(n * other.d + other.n * d, d * other.d)

    operator fun minus(other: Rational): Rational = Rational(n * other.d - other.n * d, d * other.d)

    operator fun times(other: Rational): Rational = Rational(n * other.n, d * other.d)

    operator fun div(other: Rational): Rational = Rational(n * other.d, d * other.n)

    operator fun unaryMinus(): Rational = Rational(-n, d)

    operator fun unaryPlus(): Rational = Rational(n, d)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false
        return n == other.n && d == other.d
    }

    operator fun rangeTo(endInclusive: Rational) = RationalRange(this, endInclusive)

    override fun compareTo(other: Rational): Int = (n * other.d).compareTo(other.n * d)

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + d.hashCode()
        return result
    }

    override fun toString(): String {
        if (d == BigInteger.ONE) return "$n"
        return "$n/$d"
    }
}

// Extension functions and Helpers
infix fun Number.divBy(other: Number) =
    Rational(this.toString().toBigInteger(), other.toString().toBigInteger())

infix fun BigInteger.divBy(other: BigInteger) = Rational(this, other)

fun String.toRational(): Rational {
    return if (this.contains('/')) {
        val (num, den) = this.split('/')
        Rational(num.toBigInteger(), den.toBigInteger())
    } else {
        Rational(this.toBigInteger(), BigInteger.ONE)
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}

class RationalRange(
    override val start: Rational,
    override val endInclusive: Rational
) : ClosedRange<Rational>