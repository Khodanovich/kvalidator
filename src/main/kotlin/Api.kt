import scope.ScopeValidator
import validator.controller.ControllerValidator
import validator.simple.SimpleValidator

//Controller Validator Api
infix fun <V, C> ControllerValidator<V, C>.predicate(predicate: (V) -> Boolean): ControllerValidator<V, C> {
    return this.apply { this.predicate = predicate }
}

infix fun <V, C> ControllerValidator<V, C>.action(action: (C, V) -> Unit): ControllerValidator<V, C> {
    return this.apply { this.action = action }
}

infix fun <V, C> ControllerValidator<V, C>.error(error: (C) -> Unit): ControllerValidator<V, C> {
    return this.apply { this.error = error }
}



//Simple Validator Api
infix fun <T> SimpleValidator<T>.predicate(predicate: (T)->Boolean): SimpleValidator<T> {
    return this.apply { this.predicate = predicate }
}

infix fun <V> SimpleValidator<V>.action(action: (V) -> Unit): SimpleValidator<V> {
    return this.apply { this.action = action }
}

infix fun <V> SimpleValidator<V>.error(error: () -> Unit): SimpleValidator<V> {
    return this.apply { this.error = error }
}


//Scope Validator Api
infix fun ScopeValidator.apply(scopeValidator: ScopeValidator.() -> Unit): ScopeValidator {
    scopeValidator()
    return this
}