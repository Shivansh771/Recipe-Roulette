package pro.shivanshtariyal.recipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import pro.shivanshtariyal.recipeapp.models.entities.RandomDish
import pro.shivanshtariyal.recipeapp.models.network.RandomDishApiService

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
class RandomDishViewModel:ViewModel() {

    private val randomRecipeApiService=RandomDishApiService()
    private val compositeDisposable=CompositeDisposable()


    val loadRandomDish=MutableLiveData<Boolean>()
    val randomDishResponse=MutableLiveData<RandomDish.Recipes>()
    val randomDishLoadingError=MutableLiveData<Boolean>()

    fun getRandomDishFromAPI() {
        // Define the value of the load random dish.
        loadRandomDish.value = true

        // Adds a Disposable to this container or disposes it if the container has been disposed.
        compositeDisposable.add(
            // Call the RandomDish method of RandomDishApiService class.
            randomRecipeApiService.getRandomDish()
                // Asynchronously subscribes SingleObserver to this Single on the specified Scheduler.
                /**
                 * Static factory methods for returning standard Scheduler instances.
                 *
                 * The initial and runtime values of the various scheduler types can be overridden via the
                 * {RxJavaPlugins.setInit(scheduler name)SchedulerHandler()} and
                 * {RxJavaPlugins.set(scheduler name)SchedulerHandler()} respectively.
                 */
                .subscribeOn(Schedulers.newThread())
                /**
                 * Signals the success item or the terminal signals of the current Single on the specified Scheduler,
                 * asynchronously.
                 *
                 * A Scheduler which executes actions on the Android main thread.
                 */
                .observeOn(AndroidSchedulers.mainThread())
                /**
                 * Subscribes a given SingleObserver (subclass) to this Single and returns the given
                 * SingleObserver as is.
                 */
                .subscribeWith(object : DisposableSingleObserver<RandomDish.Recipes>() {
                    override fun onSuccess(t: RandomDish.Recipes) {
                        loadRandomDish.value = false
                        randomDishResponse.value = t
                        randomDishLoadingError.value = false                    }

                    override fun onError(e: Throwable) {
                        loadRandomDish.value = false
                        randomDishLoadingError.value = true
                        e!!.printStackTrace()
                    }

                })
        )
    }
}



