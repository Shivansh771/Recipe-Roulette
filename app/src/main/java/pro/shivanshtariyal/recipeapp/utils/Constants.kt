package pro.shivanshtariyal.recipeapp.utils

object Constants {
    const val DISH_TYPE:String="DishType"
    const val DISH_CATEGORY:String="DishCategory"
    const val DISH_COOKING_TIME:String="DishCookingTime"
    const val DISH_IMAGE_SOURCE_LOCAL:String="Local"
    const val DISH_IMAGE_SOURCE_ONLINE:String="Online"
const val ALL_ITEMS:String="All"
    const val ANY_ITEMS:String="Any"
    const val FILTER_SELECTION:String="FilterSelection"
    const val DIET:String="diet"
    const val CUISINE:String="cuisine"

    const val API_ENDPOINT:String="recipes/random"

    const val API_FRIDGE_ENDPOINT:String="recipes/complexSearch"

    const val API_KEY:String="apiKey"
    const val LIMIT_LICENSE:String="limitLicense"
    const val TAGS:String="tags"
    const val NUMBER:String="number"
    const val EXTRA_DISH_DETAILS="DishDetails"
    const val ADD_RECIPE_INFORMATION="addRecipeInformation"
    const val INCLUDE_INGRIDIENTS="includeIngredients"

    const val BASE_URL="https://api.spoonacular.com/"

    const val API_KEY_VALUE:String="d25d01fa39c945fd8eb47516ddd8ffce"
    const val LIMIT_LICENSE_VALUE:Boolean=true
    const val TAGS_VALUE:String=""
    const val NUMBER_VALUE:Int=1
    const val ADD_RECIPE_INFO_VALUE=true

    fun dishTypes(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("breakfast")
        list.add("lunch")
        list.add("snacks")
        list.add("dinner")
        list.add("salad")
        list.add("side dish")
        list.add("dessert")
        list.add("other")
        return list
    }
    fun diet():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("Vegetarian")
        list.add("Lacto-Vegetarian")
        list.add("Ovo-Vegetarian")
        list.add("Ketogenic")
        list.add("Vegan")
        list.add("Pescetarian")
        list.add("Paleo")
        list.add("Low FODMAP")
        list.add("Whole30")
        list.add("Gluten Free")
        return list

    }
    fun cuisines():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("Indian")
        list.add("Chinese")
        list.add("Italian")
        list.add("Korean")
        list.add("Southern")
        list.add("Mexican")
        list.add("Greek")
        list.add("Japanese")
        list.add("Mediterranean")
        list.add("Latin American")
        return list

    }
    // END

    // TODO Step 3: Define the Dish Category list items.
    // START
    /**
     *  This function will return the Dish Category list items.
     */
    fun dishCategories(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Pizza")
        list.add("BBQ")
        list.add("Bakery")
        list.add("Burger")
        list.add("Cafe")
        list.add("Chicken")
        list.add("Dessert")
        list.add("Drinks")
        list.add("Hot Dogs")
        list.add("Juices")
        list.add("Sandwich")
        list.add("Tea & Coffee")
        list.add("Wraps")
        list.add("Other")
        return list
    }
    // END


    // TODO Step 4: Define the Dish Cooking Time list items.
    // START
    /**
     *  This function will return the Dish Cooking Time list items. The time added is in Minutes.
     */
    fun dishCookTime(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("10")
        list.add("15")
        list.add("20")
        list.add("30")
        list.add("45")
        list.add("50")
        list.add("60")
        list.add("90")
        list.add("120")
        list.add("150")
        list.add("180")
        return list
    }

    fun ingridents():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("flour")
        list.add("sugar")
        list.add("vinegar")
        list.add("honey")
        list.add("butter")
        list.add("milk")
        list.add("eggs")
        list.add("cheese")
        list.add("chicken")
        list.add("tomato")
        list.add("rice")
        list.add("pasta")
        list.add("potatoes")
        list.add("onions")
        list.add("garlic")
        list.add("carrots")
        list.add("peppers")
        list.add("lemons")
        list.add("almonds")
        list.add("peanuts")
        list.add("ketchup")
        list.add("oats")
        list.add("raisins")
        list.add("beans")
        list.add("lentils")
        return list
    }



    // END
}

