# kvalidator
[![](https://jitpack.io/v/khodanovich/kvalidator.svg)](https://jitpack.io/#khodanovich/kvalidator)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)


## Create scope
```java
object MainActivityValidator : ScopeCreator {
    const val MAIN_ACTIVITY_SCOPE_VALIDATOR = "MAIN_ACTIVITY_SCOPE_VALIDATOR"
    const val NAME_VALIDATOR = "NAME_VALIDATOR"
    const val AGE_VALIDATOR = "AGE_VALIDATOR"

    @ExperimentalContracts
    override fun createScope() {
        KValidator createScope MAIN_ACTIVITY_SCOPE_VALIDATOR apply {

            with<String, MainActivityView>(key = NAME_VALIDATOR) {
                add(ControllerValidator<String, MainActivityView>()
                        predicate { it.isNotEmpty() }
                        action { viewState, value -> viewState.hideNameError() }
                        error { it.showNameError("field name is empty") }
                )
                add(ControllerValidator<String, MainActivityView>()
                        predicate { it.length < 5 }
                        action { viewState, value -> viewState.hideNameError() }
                        error { it.showNameError("length must be less 5") }
                )
            }

            with<Int, MainActivityView>(key = AGE_VALIDATOR) {
                add(ControllerValidator<Int, MainActivityView>()
                        predicate { it > 0 }
                        action { viewState, value -> viewState.hideAgeError() }
                        error { it.showAgeError("age must be >0") }
                )
            }
        }
    }
}
```
### Presenter
```java
@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    private var validators: ScopeValidator

    init {
        KValidator.createScope(MainActivityValidator)
        validators = KValidator.getScope(MAIN_ACTIVITY_SCOPE_VALIDATOR)
    }

    override fun onFirstViewAttach() {
        KValidator.getScope(MAIN_ACTIVITY_SCOPE_VALIDATOR).attachController(viewState)
    }

    fun onCheckName(name: String){
        validators.checkByKey(NAME_VALIDATOR, name)
    }

    fun onCheckAge(age: Int){
        validators.checkByKey(AGE_VALIDATOR, age)
    }

    fun checkGroup(values: HashMap<String, Any>){
        validators.checkGroup(values) { println("success") }
    }

}
```

## Integration
```
allprojects {
    repositories {
        ...
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    implementation 'com.github.khodanovich:kvalidator:0.0.7'
}
```
