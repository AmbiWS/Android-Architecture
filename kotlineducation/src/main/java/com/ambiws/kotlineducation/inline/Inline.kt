package com.ambiws.kotlineducation.inline

/*
    Преимущество inline:
    Лямбда встраивается → нет создания объекта Function0 → меньше аллокаций и быстрее.
    Без inline — каждый вызов создал бы объект-лямбду. С inline — чистый код без overhead.
 */

inline fun <T> measureTimeMillis(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    val time = System.currentTimeMillis() - start
    return result to time
}

// Использование
fun main() {
    val (result, time) = measureTimeMillis {
        Thread.sleep(100)
        "Done"
    }
    println("Result: $result, took $time ms")
}
