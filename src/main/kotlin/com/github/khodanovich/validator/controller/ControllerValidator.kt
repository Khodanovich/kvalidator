package com.github.khodanovich.validator.controller

import com.github.khodanovich.validator.Validator


class ControllerValidator<Value, Controller> : Validator<Value> {

    private var controller: Controller? = null
    override var predicate: ((Value)->Boolean)? = null
    var action: ((Controller, Value) -> Unit)? = null
    var error: ((Controller)-> Unit)? = null

    override fun check(value: Any?): Boolean{
        val checkedValue = value as Value
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
        this.controller = controller as Controller
    }
}