package pro.shivanshtariyal.recipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.models.network.FridgeAPIService
import pro.shivanshtariyal.recipeapp.view.fragments.FridgeDishViewFragment

class FridgeDishViewModel:ViewModel() {

    private val fridgeDishAPIService=FridgeAPIService()
    private val compositeDisposable=CompositeDisposable()

    val loadFridgeDish=MutableLiveData<Boolean>()
    val fridgeDishResponse=MutableLiveData<Fridge.fridge>()
    val fridgeDishLoadingError=MutableLiveData<Boolean>()
    fun getDishFromAPI(items:ArrayList<String>,cuisines:String){
        Log.e("in view model","$items")
        loadFridgeDish.value=true
        try {
                compositeDisposable.add(
                    fridgeDishAPIService.getDish(items, cuisines)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Fridge.fridge>() {
                            override fun onSuccess(t: Fridge.fridge) {
                                loadFridgeDish.value = false
                                fridgeDishResponse.value = t
                                fridgeDishLoadingError.value = false
                                Log.e("TAG", "${fridgeDishResponse.value}")


                            }

                            override fun onError(e: Throwable) {
                                loadFridgeDish.value = false
                                fridgeDishLoadingError.value = true
                                e!!.printStackTrace()
                                Log.e("Tag", "$e")
                            }
                        })
                )
            } catch (e: Throwable) {
                Log.e("TAG", e.toString())
            }
        }
    }
