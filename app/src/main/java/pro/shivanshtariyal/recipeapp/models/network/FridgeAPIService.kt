package pro.shivanshtariyal.recipeapp.models.network

import android.util.Log
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


    fun getDish(ingredients:ArrayList<String>,cuisines:String): Single<Fridge.fridge> {



        Log.e("Items recieved","$ingredients")
        val queryString=ingredients.joinToString(
            prefix = "",
            separator = ",",
            postfix = "",
            )
        Log.e("TAG","$queryString")
        return api.getRecipeFromFridge(Constants.API_KEY_VALUE,Constants.LIMIT_LICENSE_VALUE,Constants.NUMBER_VALUE,Constants.ADD_RECIPE_INFO_VALUE,queryString,cuisines)
    }
}
