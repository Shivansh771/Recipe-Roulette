package pro.shivanshtariyal.recipeapp.view.adapter

import android.app.Activity
import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pro.shivanshtariyal.recipeapp.databinding.ItemCustomListBinding
import pro.shivanshtariyal.recipeapp.view.activities.AddUpdateDishActivity
import pro.shivanshtariyal.recipeapp.view.fragments.AllDishesFragment
import pro.shivanshtariyal.recipeapp.view.fragments.FridgeToRecipeFragment

class CustomListAdapter(
    private val activity: Activity,
    private val fragment: Fragment?,
    private val listItems:List<String>,
    private val selection:String
):RecyclerView.Adapter<CustomListAdapter.ViewHolder>() {

    class ViewHolder(view:ItemCustomListBinding):RecyclerView.ViewHolder(view.root){
        val tvText=view.tvText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemCustomListBinding=ItemCustomListBinding
            .inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item=listItems[position]
        holder.tvText.text=item

        holder.itemView.setOnClickListener{
            if(activity is AddUpdateDishActivity){
                activity.selectedItem(item,selection)
            }
            if(fragment is AllDishesFragment){
                fragment.filterSelection(item)
            }
            if(fragment is FridgeToRecipeFragment){

                fragment.filterSelection(item)
                fragment.dietSelction(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}