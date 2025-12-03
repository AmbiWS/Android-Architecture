package com.ambiws.kotlineducation.inline

inline fun runAsync(crossinline block: () -> Unit) {
    Thread {
        block() // Лямбда вызывается не в основном стеке
    }.start()
}

fun test() {
    runAsync {
        //return // ← ОШИБКА: non-local return не разрешён!
               // Можно использовать return@runAsync
    } // "return not allowed here"
    println("Это не выполнится без модификатора crossinline")
}

/*
    Non-local return ломает логику именно в таких случаях:
    inline fun runAsync(block: () -> Unit) {
        Thread {
            block()             // ← лямбда выполняется в другом потоке
        }.start()

        println("runAsync закончилась сразу")  // ← выполнится ДО block!!
    }

    Если разрешить return без crossinline:
    fun test() {
        runAsync {
           println("Начало")
            return              // ← non-local return → пытается выйти из test()!
        }
        println("Этот код НЕ ДОЛЖЕН выполняться")
    }

    Что происходит на самом деле:

    1. runAsync запускает поток и сразу выходит → печатает «runAsync закончилась сразу».
    2. В это время новый поток только начинает выполнять лямбду.
    3. Когда доходит до return — он пытается «вернуться» из функции test(), но стек вызовов test() уже давно ушёл (он в главном потоке, а лямбда — в другом).
    4. JVM падает или ведёт себя непредсказуемо.

    Поэтому Kotlin запрещает non-local return из лямбд, которые могут выполняться не в том же стеке → crossinline и заставляет писать только return@runAsync (local return).
    Без crossinline — компилятор просто не пустит такой код. Это защита от багов.
 */