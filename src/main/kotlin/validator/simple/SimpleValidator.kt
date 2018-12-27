package validator.simple

import validator.Validator


class SimpleValidator<V>: Validator<V> {

    override var predicate: ((V)->Boolean)? = null
    var action: ((V) -> Unit)? = null
    var error: (()-> Unit)? = null

    override fun check(value: Any?): Boolean{
        val checkedValue = value as V
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