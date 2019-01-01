package com.github.khodanovich.validator.simple

import com.github.khodanovich.validator.Validator


class SimpleValidator<Value> : Validator<Value> {

    override var predicate: ((Value)->Boolean)? = null
    var action: ((Value) -> Unit)? = null
    var error: (()-> Unit)? = null

    override fun check(value: Any?): Boolean{
        val checkedValue = value as Value
        predicate?.let {
            if (it.invoke(value)){
                action?.invoke(checkedValue)
                return true
            } else {
                error?.invoke()
                return false
            }
        }
    }
}