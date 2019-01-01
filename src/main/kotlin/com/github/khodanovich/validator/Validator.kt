package com.github.khodanovich.validator

interface Validator<T> {

    var predicate: ((T)->Boolean)?

    fun check(value: Any?): Boolean
}