package utils

// Find the greatest common divisor of two numbers
fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

// Find the least common multiple of `n` numbers
fun lcm(n: LongArray): Long = n.fold(1L) { acc, next -> (acc * next) / gcd(acc, next) }