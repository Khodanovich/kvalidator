package scope

import validator.Validator
import validator.controller.ControllerValidator

class ScopeValidator private constructor(){

    companion object {
        fun newInstance() = ScopeValidator()
    }

    private var validators = HashMap<String, List<Validator<*>>>()

    //Билдер для построение скоупа валидаторов
    inner class Builder(key: String){
        private val scopeValidator = this@ScopeValidator
        private val validators = mutableListOf<Validator<*>>()

        init {
            scopeValidator.addValidators(key, validators)
        }

        fun add(validator: Validator<*>): ScopeValidator{
            validators.add(validator)
            return scopeValidator
        }
    }

    //Добавление валидаторов билдером
    fun with(key: String, scopeValidator: ScopeValidator.Builder.() -> ScopeValidator): ScopeValidator{
        return scopeValidator(this.Builder(key))
    }

    //Добавление валидаторов по ключу
    fun <T> addValidators(key: String, vararg validator: Validator<T>){
        if (validators.contains(key).not()){
            validators[key] = validator.toList()
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
    }

    //Добавление валидаторов по ключу
    private fun addValidators(key: String, validators: List<Validator<*>>){
        if (this.validators.contains(key).not()){
            this.validators[key] = validators
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
    }

    //Присоеденить контроллер
    fun attachControllerToValidator(key: String, controller: Any){
        validators[key]?.filter { it is ControllerValidator<*, *> }?.map { (it as ControllerValidator<*, *>).attachController(controller)}
    }

    //Валидация по ключу валидатора
    fun checkByKey(key: String, value: Any?){
        validators[key]?.forEach { it.check(value) }
    }

    //Валидация группы
    fun checkGroup(values: HashMap<String, Any>, action: () -> Unit){
        var isValid = true
        values.forEach{ key, value -> validators[key]?.forEach { if(it.check(value).not()) isValid = false } }
        if (isValid) action.invoke()
    }
}