package org.example.project

import kotlin.math.max

class Polynome(val coef: MutableList<Double>) {
    var n = coef.size - 1
    fun upgradeDegree(m: Int) {
        if (m > n) {
            for (i in n + 1..m) {
                coef.add(0.0)
            }
            n = m
        }
    }

    fun zeros(n: Int) = Polynome(MutableList(n + 1) { 0.0 })
    operator fun plus(polynomial: Polynome): Polynome {
        val degree = max(n, polynomial.n)
        val result = zeros(degree)
        for (i in 0..degree) {
            var a = 0.0
            var b = 0.0
            if (i <= n) {
                a = coef[i]
            }
            if (i <= polynomial.n) {
                b = polynomial.coef[i]
            }
            result.coef[i] = a + b

        }
        return result
    }

    operator fun times(polynomial: Polynome): Polynome {
        var result = zeros(this.n + polynomial.n)
        for (i in 0..this.n) {
            for (j in 0..polynomial.n) {
                result.coef[i + j] += this.coef[i] * polynomial.coef[j]
            }
        }
        return result

    }

    override fun toString(): String {
        var result = ""
        for (i in n downTo 0) {
            if (coef[i] != 0.0) {
                if (result != "") {
                    if (coef[i] > 0) {
                        result += " + "
                    } else {
                        result += " - "
                    }
                } else {
                    if (coef[i] < 0) {
                        result += "-"
                    }
                }
                var value = kotlin.math.abs(coef[i])

                if (i == 0) {
                    result += value.toString()
                } else if (i == 1) {
                    result += value.toString() + "x"
                } else {
                    result += value.toString() + "x^" + i
                }
            }
        }
        if (result == "") {
            result = "0"
        }
        return result
    }
}

fun lagrangePolynome(points: List<CartesianPoint>): Polynome {
    var result = Polynome(mutableListOf(0.0))
    for (i in points.indices) {
        var basis = Polynome(mutableListOf(1.0))
        for (j in points.indices) {
            if (j in points.indices) {
                if (i != j) {
                    var denominator = points[i].x - points[j].x
                    var factor = Polynome(
                        mutableListOf(-points[j].x / denominator, 1.0 / denominator)
                    )
                    basis *= factor
                }
            }
        }
        for (k in basis.coef.indices) {
            basis.coef[k] *= points[i].y
        }
        result += basis
    }
    return result
}