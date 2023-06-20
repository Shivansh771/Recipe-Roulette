package pro.shivanshtariyal.recipeapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.inflate
import android.widget.TextView
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.application.FavDishApplication
import pro.shivanshtariyal.recipeapp.databinding.FragmentAllDishesBinding
import pro.shivanshtariyal.recipeapp.view.activities.AddUpdateDishActivity
import pro.shivanshtariyal.recipeapp.view.adapter.FavDishAdapter
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModelFactory


import pro.shivanshtariyal.recipeapp.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {
    private lateinit var mBinding:FragmentAllDishesBinding
    private val mFavDishViewModel:FavDishViewModel by viewModels{
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding=FragmentAllDishesBinding.inflate(inflater,container,false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvDishesList.layoutManager=GridLayoutManager(requireActivity(),2)
        val favDishAdapter=FavDishAdapter(this@AllDishesFragment)
        mBinding.rvDishesList.adapter=favDishAdapter
        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes->
                dishes.let {

                    if(it.isNotEmpty()){
                        mBinding.rvDishesList.visibility=View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility=View.GONE

                        favDishAdapter.dishesList(it)
                    }else{
                        mBinding.rvDishesList.visibility=View.GONE
                        mBinding.tvNoDishesAddedYet.visibility=View.VISIBLE
                    }
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes,menu)



        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_dish->{
                startActivity(Intent(requireActivity(),AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}