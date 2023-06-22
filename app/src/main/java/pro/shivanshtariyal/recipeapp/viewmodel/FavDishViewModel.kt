package pro.shivanshtariyal.recipeapp.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.shivanshtariyal.recipeapp.models.database.FavDishRepository
import pro.shivanshtariyal.recipeapp.models.entities.FavDish

class FavDishViewModel(private val repository: FavDishRepository):ViewModel() {

    fun insert(dish:FavDish)=viewModelScope.launch {
        withContext(Dispatchers.IO){

        repository.insertFavDishData(dish)
    }}
    val allDishesList:LiveData<List<FavDish>> = repository.allDishesList.asLiveData()


    fun update(dish: FavDish)=viewModelScope.launch {
        withContext(Dispatchers.IO){
        repository.updateFaveDishData(dish)
    }}

    fun delete(dish: FavDish)=viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.deleteFavDishData(dish)

        }
    }
    fun getFilteredList(value:String):LiveData<List<FavDish>> = repository.filteredListDishes(value).asLiveData()

    val favoriteDishes:LiveData<List<FavDish>> =repository.favoriteDishes.asLiveData()
}

class FavDishViewModelFactory(private val repository: FavDishRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavDishViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavDishViewModel(repository) as T
    }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}