package pro.shivanshtariyal.recipeapp.models.network

import io.reactivex.rxjava3.core.Single
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.models.entities.RandomDish
import pro.shivanshtariyal.recipeapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface FridgeAPI {
    @GET(Constants.API_FRIDGE_ENDPOINT)
    fun getRecipeFromFridge(
        @Query(Constants.API_KEY) apiKey:String,
        @Query(Constants.LIMIT_LICENSE) limitLicense:Boolean,
        @Query(Constants.NUMBER) number: Int,
        @Query(Constants.ADD_RECIPE_INFORMATION) add:Boolean,
        @Query(Constants.INCLUDE_INGRIDIENTS) include:String


    ): Single<Fridge.fridge>

}