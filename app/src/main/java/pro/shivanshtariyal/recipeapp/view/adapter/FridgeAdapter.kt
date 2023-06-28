package pro.shivanshtariyal.recipeapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.databinding.ItemDishLayoutBinding
import pro.shivanshtariyal.recipeapp.utils.Constants

class FridgeAdapter(private val fragment: Fragment) : RecyclerView.Adapter<FridgeAdapter.ViewHolder>(){
    private var ingredient = Constants.ingridents()
    var itemSelected=ArrayList<String>()
    class ViewHolder(view: ItemDishLayoutBinding):RecyclerView.ViewHolder(view.root){
        val ivIngridentImage=view.ivDishImage
        val tvtitle=view.tvDishTitle


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeAdapter.ViewHolder {
        val binding:ItemDishLayoutBinding=ItemDishLayoutBinding.inflate(
            LayoutInflater.from(fragment.context),parent,false)
        return FridgeAdapter.ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return ingredient.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtitle.text=ingredient[position]
        var selected:Boolean=false
        holder.itemView.setOnClickListener{
            if(!selected){
            holder.itemView.setBackgroundResource(R.drawable.selected_background)
                selected=true
                itemSelected.add(holder.tvtitle.text.toString())


        }else{
            holder.itemView.setBackgroundResource(R.drawable.unselected_background)
                selected=false
                itemSelected.remove(holder.tvtitle.text.toString())
            }
        }

    }


}