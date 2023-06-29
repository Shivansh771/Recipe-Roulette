package pro.shivanshtariyal.recipeapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        var name=ingredient[position]

        when(name){
            "flour"->  Glide.with(fragment)
                .load(R.drawable.flour)
                .into(holder.ivIngridentImage)
            "sugar"->  Glide.with(fragment)
                .load(R.drawable.sugar)
                .into(holder.ivIngridentImage)
            "vinegar"->  Glide.with(fragment)
                .load(R.drawable.vinegar)
                .into(holder.ivIngridentImage)
            "honey"->  Glide.with(fragment)
            .load(R.drawable.honey)
            .into(holder.ivIngridentImage)
            "butter"->  Glide.with(fragment)
                .load(R.drawable.butter)
                .into(holder.ivIngridentImage)
            "milk"->  Glide.with(fragment)
                .load(R.drawable.milk)
                .into(holder.ivIngridentImage)
            "eggs"->  Glide.with(fragment)
                .load(R.drawable.eggs)
                .into(holder.ivIngridentImage)
            "cheese"->  Glide.with(fragment)
                .load(R.drawable.cheese)
                .into(holder.ivIngridentImage)
            "chicken"->  Glide.with(fragment)
                .load(R.drawable.chicken)
                .into(holder.ivIngridentImage)
            "tomato"->  Glide.with(fragment)
                .load(R.drawable.tomato)
                .into(holder.ivIngridentImage)
            "rice"->  Glide.with(fragment)
                .load(R.drawable.rice)
                .into(holder.ivIngridentImage)
            "pasta"->  Glide.with(fragment)
                .load(R.drawable.pasta)
                .into(holder.ivIngridentImage)
            "potatoes"->  Glide.with(fragment)
                .load(R.drawable.potatoes)
                .into(holder.ivIngridentImage)
            "onions"->  Glide.with(fragment)
                .load(R.drawable.onions)
                .into(holder.ivIngridentImage)
            "garlic"->  Glide.with(fragment)
                .load(R.drawable.garlic)
                .into(holder.ivIngridentImage)
            "carrots"->  Glide.with(fragment)
                .load(R.drawable.carrots)
                .into(holder.ivIngridentImage)
            "peppers"->  Glide.with(fragment)
                .load(R.drawable.peppers)
                .into(holder.ivIngridentImage)
            "lemons"->  Glide.with(fragment)
                .load(R.drawable.lemons)
                .into(holder.ivIngridentImage)
            "almonds"->  Glide.with(fragment)
                .load(R.drawable.almonds)
                .into(holder.ivIngridentImage)
            "peanuts"->  Glide.with(fragment)
                .load(R.drawable.peanuts)
                .into(holder.ivIngridentImage)
            "ketchup"->  Glide.with(fragment)
                .load(R.drawable.ketchup)
                .into(holder.ivIngridentImage)
            "oats"->  Glide.with(fragment)
                .load(R.drawable.oats)
                .into(holder.ivIngridentImage)
            "raisins"->  Glide.with(fragment)
                .load(R.drawable.raisins)
                .into(holder.ivIngridentImage)
            "beans"->  Glide.with(fragment)
                .load(R.drawable.beans)
                .into(holder.ivIngridentImage)
            "lentils"->  Glide.with(fragment)
                .load(R.drawable.lentils)
                .into(holder.ivIngridentImage)

        }

        var selected=false


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

    fun retList():ArrayList<String>{

        return itemSelected
    }




}