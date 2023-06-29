package pro.shivanshtariyal.recipeapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.databinding.FragmentAllDishesBinding
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeToRecipeBinding
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.view.adapter.FridgeAdapter
import pro.shivanshtariyal.recipeapp.viewmodel.FridgeDishViewModel

class FridgeToRecipeFragment : Fragment() {
    private lateinit var binding:FragmentFridgeToRecipeBinding
    private lateinit var adapter:FridgeAdapter
    private lateinit var fab:FloatingActionButton
    private var mProgressDialog: Dialog?=null
    lateinit var retFridge:MutableLiveData<Fridge.fridge>
    private lateinit var ftrVM:FridgeDishViewModel
    lateinit var items:ArrayList<String>
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
        ftrVM=ViewModelProvider(this).get(FridgeDishViewModel::class.java)

        fab=binding.fab

        fab.setOnClickListener{

            items= adapter.retList()
            Log.e("items" ,"$items")
               ftrVM.getDishFromAPI(items)
            randomDishViewModelObserver()


        }



    }
    private fun showCustomProgressDialog(){
        mProgressDialog= Dialog(requireActivity())
        mProgressDialog?.let {
            it.setContentView(R.layout.dialog_please_wait)
            it.show()
        }
    }
    private fun hideProgressDialog(){
        mProgressDialog?.let {
            it.dismiss()
        }
    }
    fun returnList():ArrayList<String>{
        adapter=FridgeAdapter(this)
        return adapter.itemSelected
    }
    private fun randomDishViewModelObserver() {

        ftrVM.fridgeDishResponse.observe(
            viewLifecycleOwner
        ) { randomDishResponse ->
            randomDishResponse?.let {
                Log.i("Random Dish Response", "${ftrVM.fridgeDishResponse.value}")
                retFridge=ftrVM.fridgeDishResponse



            }
        }

        ftrVM.fridgeDishLoadingError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {
                    Log.i("Random Dish API Error", "$dataError")

                }
            })

        ftrVM.loadFridgeDish.observe(viewLifecycleOwner, Observer {  loadRandomDish->
            loadRandomDish?.let {
                Log.i("Random Dish Loading", "$loadRandomDish")

                if(loadRandomDish){
                    showCustomProgressDialog()
                }else{
                    hideProgressDialog()
                    findNavController().navigate(R.id.action_fridge_to_fridgeDishViewFragment)


                }
            }
        })
    }

    fun retItems():ArrayList<String>{

        return items
    }



}