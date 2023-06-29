package pro.shivanshtariyal.recipeapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeDishViewBinding
import pro.shivanshtariyal.recipeapp.models.entities.Fridge

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FridgeDishViewFragment : Fragment() {

    private lateinit var binding:FragmentFridgeDishViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentFridgeDishViewBinding.inflate(layoutInflater,container,false)

        return binding.root

    }
fun setupINUI(item: Fridge.fridge?){

}


}