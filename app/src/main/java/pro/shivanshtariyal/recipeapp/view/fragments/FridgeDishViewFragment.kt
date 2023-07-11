package pro.shivanshtariyal.recipeapp.view.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.application.FavDishApplication
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeDishViewBinding
import pro.shivanshtariyal.recipeapp.models.entities.FavDish
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.utils.Constants
import pro.shivanshtariyal.recipeapp.utils.OnBackPressedListener
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModelFactory

private  var  res: List<Fridge.Result>?=null
private lateinit var DirectionsToCook: String
private lateinit var items:String
class FridgeDishViewFragment : Fragment() ,OnBackPressedListener{

    private lateinit var binding:FragmentFridgeDishViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Fridge to Recipe"


        binding=FragmentFridgeDishViewBinding.inflate(layoutInflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentFridgeDishViewBinding.bind(view)
        binding.tvTitle.text= res?.get(0)?.title.toString()
        Glide.with(requireActivity())
            .load(res?.get(0)?.image)
            .centerCrop()
            .into(binding.ivDishImage)
        var dishType: String = "other"

        if (res?.get(0)?.dishTypes?.isNotEmpty() == true) {
            dishType = res?.get(0)!!.dishTypes[0]
            binding!!.tvType.text = dishType
        }
        binding!!.tvCategory.text = "Other"
        binding!!.tvIngredients.text= items
        var n= DirectionsToCook.length
        var ddc=DirectionsToCook.substring(0,n-5)

            binding!!.tvCookingDirection.text = ddc

        binding!!.tvCookingTime.text =
            resources.getString(
                R.string.lbl_estimate_cooking_time,
                res?.get(0)!!.readyInMinutes.toString()
            )

        var rim=res?.get(0)!!.readyInMinutes.toString()

        var addedToFavorite=false
        binding!!.ivFavoriteDish.setOnClickListener{
            if(addedToFavorite){
                Toast.makeText(
                    requireActivity(),
                    "You have already added the dish to favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                var instructions=ddc




                val randomDishDetails= res?.get(0)?.image?.let { it1 ->
                    res?.get(0)?.title?.let { it2 ->
                        FavDish(
                            it1,
                            Constants.DISH_IMAGE_SOURCE_ONLINE,
                            it2,
                            dishType,
                            "Others",
                            items,
                            rim,
                            instructions,
                            true
                        )
                    }
                }
                val mFavDishViewModel: FavDishViewModel by viewModels {
                    FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
                }
                if (randomDishDetails != null) {
                    mFavDishViewModel.insert(randomDishDetails)
                }
                addedToFavorite=true
                binding!!.ivFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_selected
                    )
                )
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_added_to_facorites),
                    Toast.LENGTH_SHORT
                ).show()
            }}








    }



fun get11(item: List<Fridge.Result>?,an:String,li:List<String>){
    Log.e("90","$item")
    res=item
    DirectionsToCook=an

    items= li.toString().drop(1).dropLast(1)

}

    override fun onBackPressed() {
        DirectionsToCook=""
        items=""

        val fragmentManager = requireActivity().supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.fridgeDishViewFragment)

        if (currentFragment == this) {
            fragmentManager.beginTransaction().remove(this).commit()
        }
    }



}


