package pro.shivanshtariyal.recipeapp.view.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.application.FavDishApplication
import pro.shivanshtariyal.recipeapp.databinding.FragmentDishDetailsBinding
import pro.shivanshtariyal.recipeapp.models.entities.FavDish
import pro.shivanshtariyal.recipeapp.utils.Constants
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModelFactory
import java.io.IOException
import java.util.*


class DishDetailsFragment : Fragment() {

    private var mFavDishDetails:FavDish?=null
    private var mBinding:FragmentDishDetailsBinding?=null
    private val mFaveDishViewModel:FavDishViewModel by viewModels {
        FavDishViewModelFactory(((requireActivity().application)as FavDishApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_share->{
                val type="text/plain"
                val subject="Checkout This dish recipe"
                var extraText=""
                val shareWith="Share With"

                mFavDishDetails?.let {
                    var image=""
                    if(it.imageSource==Constants.DISH_IMAGE_SOURCE_ONLINE){
                        image=it.image
                    }
                    var cookingInstructions=""
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                        cookingInstructions= Html.fromHtml(
                            it.directionToCook,
                            Html.FROM_HTML_MODE_COMPACT
                        ).toString()

                    }else{
                        @Suppress("DEPRICATION")
                        cookingInstructions=Html.fromHtml(it.directionToCook).toString()

                    }
                    extraText="$image\n"+"\nTitle: ${it.title} \n\n Type:${it.type}\n\n"+
                            "Category: ${it.category}"+"\n\n Ingredients: \n ${it.ingredients}\n\nInstructions"+
                            "To Cook: \n ${cookingInstructions}"+
                            "\n\n Time required to cook the dish approx"+
                            "${it.cookingTime} minutes."

                }
                val intent=Intent(Intent.ACTION_SEND)
                intent.type=type
                intent.putExtra(Intent.EXTRA_SUBJECT,subject)
                intent.putExtra(Intent.EXTRA_TEXT,extraText)
                startActivity(Intent.createChooser(intent,shareWith))
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentDishDetailsBinding.inflate(inflater,container,false)


        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args:DishDetailsFragmentArgs by navArgs()

        mFavDishDetails=args.dishDetails
        args.let {
            try{
                Glide.with(requireActivity())
                    .load(it.dishDetails.image)
                    .listener(object:RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                        Log.e("TAG","errr",e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.let {
                                Palette.from(resource!!.toBitmap()).generate(){
                                        palette->
                                    val intColor=palette?.vibrantSwatch?.rgb ?:0
                                    mBinding!!.rlDishDetailMain.setBackgroundColor(intColor)

                                }
                            }
                            return false
                        }

                    })
                    .centerCrop().into(mBinding!!.ivDishImage)


            }catch (e:IOException){
                e.printStackTrace()
            }
            mBinding!!.tvTitle.text=it.dishDetails.title
            mBinding!!.tvType.text=it.dishDetails.type.capitalize(Locale.ROOT)
            mBinding!!.tvCategory.text=it.dishDetails.category
            mBinding!!.tvIngredients.text=it.dishDetails.ingredients

            mBinding!!.tvCookingDirection.text=it.dishDetails.directionToCook
            mBinding!!.tvCookingTime.text=resources.getString(R.string.lbl_estimate_cooking_time,it.dishDetails.cookingTime)
            if(args.dishDetails.favoriteDish){
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_selected
                ))

            }else{
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_unselected))

            }

            }


        mBinding!!.ivFavoriteDish.setOnClickListener{
            args.dishDetails.favoriteDish=!args.dishDetails.favoriteDish
            mFaveDishViewModel.update(args.dishDetails)
            if(args.dishDetails.favoriteDish){
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_selected
                ))

            }else{
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_unselected))

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding=null
    }

}