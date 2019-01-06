package com.github.khodanovich.scope

import com.github.khodanovich.validator.Validator
import com.github.khodanovich.validator.controller.ControllerValidator

class ScopeValidator private constructor(){

    companion object {
        fun newInstance() = ScopeValidator()
    }

    private val validators = HashMap<String, List<Validator<*>>>()

    inner class ControllerValidatorBuilder<Value, Controller>(key: String){
        private val scopeValidator = this@ScopeValidator
        private val validators = mutableListOf<Validator<*>>()

        init {
            scopeValidator.addValidators(key, validators)
        }

        fun add(validator: ControllerValidator<Value, Controller>): ScopeValidator {
            validators.add(validator)
            return scopeValidator
        }
    }

    private fun addValidators(key: String, validators: List<Validator<*>>){
        if (this.validators.contains(key).not()){
            this.validators[key] = validators
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
    }

    fun <Value, Controller> with(key: String, scopeValidator: ControllerValidatorBuilder<Value, Controller>.() -> ScopeValidator): ScopeValidator {
        return scopeValidator(this.ControllerValidatorBuilder(key))
    }

    fun <Value> addValidators(key: String, vararg validator: Validator<Value>){
        if (validators.contains(key).not()){
            validators[key] = validator.toList()
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
    }

    fun attachControllerToValidator(key: String, controller: Any){
        validators[key]?.filter { it is ControllerValidator<*, *> }?.map { (it as ControllerValidator<*, *>).attachController(controller)}
    }

    fun attachController(controller: Any){
        for (validator in validators){
            validator.value.filter { it is ControllerValidator<*, *> }.map { (it as ControllerValidator<*, *>).attachController(controller)}
        }
    }

    fun checkByKey(key: String, value: Any?){
        validators[key]?.firstOrNull { it.check(value).not() }
    }

    fun checkGroup(values: HashMap<String, Any>, action: () -> Unit){
        var isValid = true
        values.forEach{ key, value -> validators[key]?.forEach { if(it.check(value).not()) isValid = false } }
        if (isValid) action.invoke()
    }
}