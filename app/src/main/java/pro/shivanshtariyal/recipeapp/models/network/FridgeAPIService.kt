package pro.shivanshtariyal.recipeapp.models.network

import io.reactivex.rxjava3.core.Single
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.utils.Constants
import pro.shivanshtariyal.recipeapp.view.adapter.FridgeAdapter
import pro.shivanshtariyal.recipeapp.view.fragments.FridgeToRecipeFragment
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FridgeAPIService {
    private lateinit var adapter:FridgeAdapter

    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(FridgeAPI::class.java)


    fun getDish(): Single<Fridge.fridge> {


        var ingridients=FridgeToRecipeFragment().returnList()
        val queryString=ingridients.joinToString(
            prefix = "",
            separator = ",",
            postfix = "",
            )
        return api.getRecipeFromFridge(Constants.API_KEY_VALUE,Constants.LIMIT_LICENSE_VALUE,Constants.NUMBER_VALUE,Constants.ADD_RECIPE_INFO_VALUE,queryString)
    }
}
