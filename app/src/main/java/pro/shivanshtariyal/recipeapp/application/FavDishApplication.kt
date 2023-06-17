package pro.shivanshtariyal.recipeapp.application

import android.app.Application
import pro.shivanshtariyal.recipeapp.models.database.FavDishRepository
import pro.shivanshtariyal.recipeapp.models.database.FavDishRoomDatabase

class FavDishApplication: Application() {

    private val database by lazy { FavDishRoomDatabase.getDatabase(this@FavDishApplication) }

    val repository by lazy { FavDishRepository(database.favDishDao()) }
}