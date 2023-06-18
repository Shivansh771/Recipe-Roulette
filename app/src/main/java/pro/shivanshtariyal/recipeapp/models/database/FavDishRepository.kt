package pro.shivanshtariyal.recipeapp.models.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pro.shivanshtariyal.recipeapp.models.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
       favDishDao.insertFavDishDetails(favDish)
    }


    val allDishesList:Flow<List<FavDish>> = favDishDao.getAllDishesList()
}