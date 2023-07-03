package pro.shivanshtariyal.recipeapp.utils

import androidx.fragment.app.Fragment

interface OnBackPressedListener {
    fun onBackPressed()
}

class MyFragment : Fragment(), OnBackPressedListener {

    override fun onBackPressed() {
        // Remove the fragment from the activity.
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().remove(this).commit()
    }
}