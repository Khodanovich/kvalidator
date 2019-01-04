package com.github.khodanovich

import com.github.khodanovich.creator.ScopeCreator
import com.github.khodanovich.scope.ScopeValidator
import java.lang.IllegalArgumentException

object KValidator {

    private val scopes = HashMap<String, ScopeValidator>()

    infix fun createScope(key: String): ScopeValidator {
        val scope = ScopeValidator.newInstance()
        if (scopes.contains(key).not()){
            scopes[key] = scope
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
        return scope
    }

    fun createScope(creator: ScopeCreator){
        creator.createScope()
    }

    fun getScope(key: String): ScopeValidator {
        if (scopes.contains(key).not()) throw IllegalArgumentException("there is not area with this key")
        return scopes[key]!!
    }
}








