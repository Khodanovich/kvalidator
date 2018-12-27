import creator.ScopeCreator
import scope.ScopeValidator
import java.lang.IllegalArgumentException

object KValidator {
    private var scopes = HashMap<String, ScopeValidator>()

    infix fun createScope(key: String): ScopeValidator {
        val scope = ScopeValidator.newInstance()
        if (scopes.contains(key).not()){
            scopes[key] = scope
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
        return scope
    }

    fun getScope(key: String): ScopeValidator {
        if (scopes.contains(key).not()) throw IllegalArgumentException("")
        return scopes[key]!!
    }

    fun createScope(creator: ScopeCreator){
        creator.createScope()
    }

}








