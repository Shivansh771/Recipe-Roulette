package pro.shivanshtariyal.recipeapp.models.network

import androidx.core.content.contentValuesOf
import io.reactivex.rxjava3.core.Single
import pro.shivanshtariyal.recipeapp.models.entities.RandomDish
import pro.shivanshtariyal.recipeapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RandomDishApiService {

    private val api=Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishAPI::class.java)

    fun getRandomDish():Single<RandomDish.Recipe>{
        return api.getRandomDish(Constants.API_KEY_VALUE,Constants.LIMIT_LICENSE_VALUE,Constants.TAGS_VALUE,Constants.NUMBER_VALUE)

    }
}