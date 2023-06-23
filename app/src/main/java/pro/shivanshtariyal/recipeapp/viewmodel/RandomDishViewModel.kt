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
    val randomDishResponse=MutableLiveData<RandomDish.Recipe>()
    val randomDishLoadingError=MutableLiveData<Boolean>()

    fun getRandomRecipeFromAPI(){
        loadRandomDish.value=true
        compositeDisposable.add(
            randomRecipeApiService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<RandomDish.Recipe>(){
                    override fun onSuccess(value: RandomDish.Recipe) {
                      loadRandomDish.value=false
                      randomDishResponse.value=value
                      randomDishLoadingError.value=false

                    }

                    override fun onError(e: Throwable) {
                        loadRandomDish.value=false
                        randomDishLoadingError.value=true
                        e!!.printStackTrace()
                    }

                })

        )
    }

}