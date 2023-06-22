package pro.shivanshtariyal.recipeapp.models.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pro.shivanshtariyal.recipeapp.models.entities.FavDish

@Dao
interface FavDishDao {
    @Insert
    fun insertFavDishDetails(favDish: FavDish)
    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList(): Flow<List<FavDish>>

    @Update
     fun updateFaveDishDetails(favDish: FavDish)

     @Query("SELECT * FROM FAV_DISHES_TABLE WHERE favorite_dish=1")
     fun getFavoriteDishesList():Flow<List<FavDish>>

     @Delete
     fun deleteFavDishDetails(favDish:FavDish)

    @Query("SELECT * FROM fav_dishes_table WHERE type= :filterType")
    fun getFilteredDishesList(filterType:String):Flow<List<FavDish>>

}