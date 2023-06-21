package pro.shivanshtariyal.recipeapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import pro.shivanshtariyal.recipeapp.application.FavDishApplication
import pro.shivanshtariyal.recipeapp.databinding.FragmentFavoriteDishesBinding
import pro.shivanshtariyal.recipeapp.models.entities.FavDish
import pro.shivanshtariyal.recipeapp.view.activities.MainActivity
import pro.shivanshtariyal.recipeapp.view.adapter.FavDishAdapter
import pro.shivanshtariyal.recipeapp.viewmodel.DashboardViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModelFactory

class FavoriteDishesFragment : Fragment() {

    private var mBinding: FragmentFavoriteDishesBinding? = null
    private val mFavDishViewModel:FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository )
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentFavoriteDishesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mFavDishViewModel.favoriteDishes.observe(viewLifecycleOwner){
            dishes->
            dishes.let {
                mBinding!!.rvFavoriteDishesList.layoutManager=GridLayoutManager(
                    requireActivity(),2)

                val adapter=FavDishAdapter(this)
                mBinding!!.rvFavoriteDishesList.adapter=adapter
                if(it.isNotEmpty()){

                mBinding!!.rvFavoriteDishesList.visibility=View.VISIBLE
                    mBinding!!.tvNoFavoriteDishesAvailable.visibility=View.GONE
                    adapter.dishesList(it)


                }else{
                    mBinding!!.rvFavoriteDishesList.visibility=View.GONE
                    mBinding!!.tvNoFavoriteDishesAvailable.visibility=View.VISIBLE
                }
            }
        }
    }

    fun dishDetails(favDish: FavDish){
        findNavController().navigate(FavoriteDishesFragmentDirections.actionNavigationFavDishesToNavigationDishDetails(favDish))

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity).showBottomNavigationView()
        }
    }
}