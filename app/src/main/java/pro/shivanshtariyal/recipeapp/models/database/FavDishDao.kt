package pro.shivanshtariyal.recipeapp.models.database

import androidx.room.Dao
import androidx.room.Insert
import pro.shivanshtariyal.recipeapp.models.entities.FavDish


@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishDetails(favDish:FavDish){

    }
}