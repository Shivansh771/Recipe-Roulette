package pro.shivanshtariyal.recipeapp.view.fragments
import android.app.Dialog
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.databinding.DialogCustomListBinding
import pro.shivanshtariyal.recipeapp.databinding.FragmentFridgeToRecipeBinding
import pro.shivanshtariyal.recipeapp.models.entities.Fridge
import pro.shivanshtariyal.recipeapp.utils.Constants
import pro.shivanshtariyal.recipeapp.utils.OnBackPressedListener
import pro.shivanshtariyal.recipeapp.view.adapter.CustomListAdapter
import pro.shivanshtariyal.recipeapp.view.adapter.FridgeAdapter
import pro.shivanshtariyal.recipeapp.viewmodel.FridgeDishViewModel

class FridgeToRecipeFragment : Fragment(),OnBackPressedListener {
    private lateinit var binding:FragmentFridgeToRecipeBinding
    private lateinit var adapter:FridgeAdapter
    private lateinit var fab:FloatingActionButton
    private var mProgressDialog: Dialog?=null
    private lateinit var mCustomListDialog:Dialog
    var cuisines=""
    var diet=""
    lateinit var retFridge:MutableLiveData<Fridge.fridge>
    private lateinit var ftrVM:FridgeDishViewModel
    lateinit var items:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
               ftrVM.getDishFromAPI(items,cuisines,diet)
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
    fun hideProgressDialog(){
        mProgressDialog?.dismiss()
    }

    private fun randomDishViewModelObserver() {
        ftrVM.fridgeDishResponse.observe(
            viewLifecycleOwner
        ) { randomDishResponse ->
            randomDishResponse?.let {
                Log.i("Random Dish Response", "${ftrVM.fridgeDishResponse.value?.results}")
                retFridge=ftrVM.fridgeDishResponse
                findNavController().navigate(R.id.action_fridge_to_fridgeDishViewFragment)
                Log.e(ftrVM.fridgeDishResponse.value?.results?.get(0)?.title,"title")
                Log.e("${ftrVM.fridgeDishResponse.value?.results?.get(0)?.cookingMinutes}","Cook")

                val ans=ftrVM.fridgeDishResponse.value?.results
                val analyzedInstruction= retFridge.value?.results?.get(0)?.analyzedInstructions.toString().replace("Step(step=","").drop(35)
                Log.e("aaa", analyzedInstruction)
                FridgeDishViewFragment().get11(ans,analyzedInstruction,items)





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






                }
            }
        })
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fridge_menu,menu)



        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.fridge_filter->{
                filterDishesListDialog()
                return true
            }
            R.id.diet->{
                dietDishDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun filterDishesListDialog(){
        mCustomListDialog=Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text=resources.getString(R.string.Cuisines)
        val dishTypes= Constants.cuisines()
        dishTypes.add(0, Constants.ANY_ITEMS)
        binding.rvList.layoutManager= LinearLayoutManager(requireActivity())
        val adapter=
            CustomListAdapter(requireActivity(),this@FridgeToRecipeFragment,dishTypes, Constants.FILTER_SELECTION)
        binding.rvList.adapter=adapter
        mCustomListDialog.show()
    }
    private fun dietDishDialog(){
        mCustomListDialog=Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text=resources.getString(R.string.diet)
        val diet= Constants.diet()
        diet.add(0, Constants.ANY_ITEMS)
        binding.rvList.layoutManager= LinearLayoutManager(requireActivity())
        val adapter=
            CustomListAdapter(requireActivity(),this@FridgeToRecipeFragment,diet, Constants.FILTER_SELECTION)
        binding.rvList.adapter=adapter
        mCustomListDialog.show()
    }


    fun filterSelection(filterItemSelection:String){
        mCustomListDialog.dismiss()
        Log.i("Filter Selection",filterItemSelection)
        Toast.makeText(requireActivity(),"Selected $filterItemSelection",Toast.LENGTH_SHORT).show()
        if(filterItemSelection!="Any" && filterItemSelection in Constants.cuisines()){

            cuisines=filterItemSelection
            Log.i("Cuisine",cuisines)
            Log.i("diet",diet)
        }

    }
    fun dietSelction(filterItemSelection:String){
        mCustomListDialog.dismiss()
        Log.i("Filter Selection",filterItemSelection)
        Toast.makeText(requireActivity(),"Selected $filterItemSelection",Toast.LENGTH_SHORT).show()
        if(filterItemSelection!="Any" && filterItemSelection in Constants.diet()){

            diet=filterItemSelection
            Log.i("Cuisine",cuisines)
            Log.i("Diet",diet)

        }

    }


    override fun onBackPressed() {
        val fragmentManager = requireActivity().supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.fridgeDishViewFragment)

        if (currentFragment == this) {
            fragmentManager.beginTransaction().remove(this).commit()
        }
    }

}