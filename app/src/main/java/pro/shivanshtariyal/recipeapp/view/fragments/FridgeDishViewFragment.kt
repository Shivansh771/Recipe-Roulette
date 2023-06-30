package pro.shivanshtariyal.recipeapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeDishViewBinding
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
private  var  res: List<Fridge.Result>?=null
private lateinit var DirectionsToCook: String
private lateinit var items:String
class FridgeDishViewFragment : Fragment() {

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
        binding!!.tvIngredients.text=res?.get(0)!!.analyzedInstructions[0].toString()
            DirectionsToCook.dropLast(4)

            binding!!.tvCookingDirection.text = DirectionsToCook
        binding!!.tvIngredients.text= items







    }

fun get11(item: List<Fridge.Result>?,an:String,li:List<String>){
    Log.e("90","$item")
    res=item
    DirectionsToCook=an

    items= li.toString().drop(1).dropLast(1)

}}


