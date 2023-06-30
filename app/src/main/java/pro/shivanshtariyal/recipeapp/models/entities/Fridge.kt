package pro.shivanshtariyal.recipeapp.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object Fridge {
    @Parcelize
    data class fridge(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
):Parcelable
@Parcelize
data class Result(
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val cheap: Boolean,
    val cookingMinutes: Int,
    val creditsText: String,
    val cuisines: List<String>,
    val dairyFree: Boolean,
    val diets: List<String>,
    val dishTypes: List<String>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Int,
    val id: Int,
    val image: String,
    val imageType: String,
    val lowFodmap: Boolean,
    val occasions: List<String>,
    val preparationMinutes: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int
):Parcelable
@Parcelize
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
):Parcelable
@Parcelize
data class Step(

    val step: String
):Parcelable
@Parcelize
data class Equipment(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
):Parcelable
@Parcelize
data class Ingredient(
    val name: String
):Parcelable
@Parcelize
data class Length(
    val number: Int,
    val unit: String
):Parcelable
}