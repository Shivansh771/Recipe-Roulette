package pro.shivanshtariyal.recipeapp.models.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.shivanshtariyal.recipeapp.models.entities.FavDish


@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishDetails(favDish:FavDish){

    }

    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList(): Flow<List<FavDish>>
}