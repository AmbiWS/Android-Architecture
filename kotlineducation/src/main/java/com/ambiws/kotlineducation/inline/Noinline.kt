package com.ambiws.kotlineducation.inline

/*
    Компилятор не знает, как создать Function0<Unit> из inline-лямбды: её просто нет в рантайме.
    Noinline говорит: «эту лямбду НЕ встраивай, а сделай обычный объект-функцию», тогда её можно сохранить.
    Коротко:
    inline-лямбда = только код → нельзя хранить
    noinline-лямбда = обычный объект → можно хранить
 */

val debugBlocks = java.util.concurrent.CopyOnWriteArrayList<() -> Unit>()

inline fun debug(
    noinline block: () -> Unit // ← Без noinline — ошибка
) {
    debugBlocks += block // Сохраняем лямбду в глобальный список
}
