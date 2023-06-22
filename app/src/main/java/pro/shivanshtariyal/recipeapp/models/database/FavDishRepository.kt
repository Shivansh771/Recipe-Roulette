package pro.shivanshtariyal.recipeapp.models.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pro.shivanshtariyal.recipeapp.models.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work
     * off the main thread.
     */
    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish) {
        favDishDao.insertFavDishDetails(favDish)
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishesList()

    @WorkerThread
    suspend fun updateFaveDishData(favDish: FavDish){
        favDishDao.updateFaveDishDetails(favDish)
    }
    @WorkerThread
     suspend fun deleteFavDishData(favDish: FavDish){
        favDishDao.deleteFavDishDetails(favDish)
    }

    fun filteredListDishes(value:String):Flow<List<FavDish>> = favDishDao.getFilteredDishesList(value)

    val favoriteDishes: Flow<List<FavDish>> =favDishDao.getFavoriteDishesList()
}

