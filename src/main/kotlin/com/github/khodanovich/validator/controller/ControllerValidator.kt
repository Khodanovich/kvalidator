package com.github.khodanovich.validator.controller

import com.github.khodanovich.validator.Validator


class ControllerValidator<V, C> () : Validator<V> {

    private var controller: C? = null
    override var predicate: ((V)->Boolean)? = null
    var action: ((C, V) -> Unit)? = null
    var error: ((C)-> Unit)? = null

    override fun check(value: Any?): Boolean{
        val checkedValue = value as V
        predicate?.let {
            controller?.let {
                if (predicate!!.invoke(checkedValue)){
                    action?.invoke(controller!!, checkedValue)
                    return true
                } else {
                    error?.invoke(controller!!)
                    return false
                }
            }
        }
        return false
    }

    fun attachController(controller: Any){
        this.controller = controller as C
    }
}