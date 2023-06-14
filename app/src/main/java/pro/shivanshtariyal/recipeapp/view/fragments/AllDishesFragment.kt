package pro.shivanshtariyal.recipeapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.inflate
import android.widget.TextView
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.databinding.FragmentAllDishesBinding
import pro.shivanshtariyal.recipeapp.databinding.FragmentAllDishesBinding.inflate
import pro.shivanshtariyal.recipeapp.databinding.FragmentDashboardBinding.inflate
import pro.shivanshtariyal.recipeapp.view.activities.AddUpdateDishActivity


import pro.shivanshtariyal.recipeapp.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {

    private var _binding: FragmentAllDishesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentAllDishesBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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