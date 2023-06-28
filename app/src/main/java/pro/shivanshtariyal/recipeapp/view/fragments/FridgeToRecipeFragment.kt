package pro.shivanshtariyal.recipeapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.databinding.FragmentAllDishesBinding
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeToRecipeBinding
import pro.shivanshtariyal.recipeapp.view.adapter.FridgeAdapter

class FridgeToRecipeFragment : Fragment() {
    private lateinit var binding:FragmentFridgeToRecipeBinding
    private lateinit var adapter:FridgeAdapter
    private lateinit var fab:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFridgeToRecipeBinding.inflate(inflater,container,false)

        (activity as AppCompatActivity).supportActionBar?.title = "Fridge to Recipe"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDishesList.layoutManager=GridLayoutManager(requireActivity(),2)
        adapter=FridgeAdapter(this)
        binding.rvDishesList.adapter=adapter

        fab=binding.fab

        fab.setOnClickListener{
            for(items in adapter.itemSelected){

                Log.e("item","$items")

            }
        }



    }



}