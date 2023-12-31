package pro.shivanshtariyal.recipeapp.view.activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import pro.shivanshtariyal.recipeapp.R
import pro.shivanshtariyal.recipeapp.application.FavDishApplication
import pro.shivanshtariyal.recipeapp.databinding.ActivityAddUpdateDishBinding
import pro.shivanshtariyal.recipeapp.databinding.DialogCustomImageSelectionBinding
import pro.shivanshtariyal.recipeapp.databinding.DialogCustomListBinding
import pro.shivanshtariyal.recipeapp.models.database.FavDishRepository
import pro.shivanshtariyal.recipeapp.models.entities.FavDish
import pro.shivanshtariyal.recipeapp.utils.Constants
import pro.shivanshtariyal.recipeapp.view.adapter.CustomListAdapter
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModel
import pro.shivanshtariyal.recipeapp.viewmodel.FavDishViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var mBinding:ActivityAddUpdateDishBinding
    private var mImagePath:String=""

    private lateinit var mCustomListDialog:Dialog
    private var mFavDishDetails:FavDish?=null
    private val mFavDishViewModel:FavDishViewModel by viewModels{
        FavDishViewModelFactory((application as FavDishApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         mBinding=ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        if(intent.hasExtra(Constants.EXTRA_DISH_DETAILS)){
            mFavDishDetails=intent.getParcelableExtra(Constants.EXTRA_DISH_DETAILS)


        }
        mFavDishDetails?.let {
            if(it.id!=0){
                mImagePath=it.image
                Glide.with(this@AddUpdateDishActivity)
                    .load(mImagePath)
                    .centerCrop().into(mBinding.ivDishImage)
                mBinding.etTitle.setText(it.title)
                mBinding.etType.setText(it.type)
                mBinding.etCategory.setText(it.category)
                mBinding.etIngredients.setText(it.ingredients)
                mBinding.etCookingTime.setText(it.cookingTime)
                mBinding.etDirectionToCook.setText(it.directionToCook)
                mBinding.btnAddDish.text=resources.getString(R.string.lbl_update_dish)
            }
        }


        mBinding.ivAddDishImage.setOnClickListener(this@AddUpdateDishActivity)
        setupActionBar()
        mBinding.etType.setOnClickListener(this@AddUpdateDishActivity)
        mBinding.etCategory.setOnClickListener(this@AddUpdateDishActivity)
        mBinding.etCookingTime.setOnClickListener(this@AddUpdateDishActivity)
        mBinding.btnAddDish.setOnClickListener(this@AddUpdateDishActivity)

    }


    override fun onClick(v: View) {

        when (v.id) {

            R.id.iv_add_dish_image -> {

                customImageSelectionDialog()
                return
            }
            R.id.et_type->{
                customItemsListDialog(resources.getString(R.string.title_select_dish_type),Constants.dishTypes(),Constants.DISH_TYPE)
                return
            }
            R.id.et_cooking_time->{
                customItemsListDialog(resources.getString(R.string.title_select_dish_cooking_time),Constants.dishCookTime(),Constants.DISH_COOKING_TIME)
                return
            }
            R.id.et_category->{
                customItemsListDialog(resources.getString(R.string.title_select_dish_category),Constants.dishCategories(),Constants.DISH_CATEGORY)
                return
            }
            R.id.btn_add_dish->{
                val title=mBinding.etTitle.text.toString().trim{ it <=' '}
                val type=mBinding.etType.text.toString().trim{it<=' '}
                val category=mBinding.etCategory.text.toString().trim{it<=' '}
                val ingredients=mBinding.etIngredients.text.toString().trim{it<=' '}
                val cookingTimeinMinutes=mBinding.etCookingTime.text.toString().trim{it<=' '}
                val cookingDirection=mBinding.etDirectionToCook.text.toString().trim{it<=' '}

                when{
                    TextUtils.isEmpty(mImagePath)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_image),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(title)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_title),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(type)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_type),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(category)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_category),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(ingredients)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_ingredients),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(cookingTimeinMinutes)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_time),Toast.LENGTH_SHORT).show()
                    }
                    TextUtils.isEmpty(mImagePath)->{
                        Toast.makeText(this@AddUpdateDishActivity,resources.getString(R.string.err_msg_directions),Toast.LENGTH_SHORT).show()
                    }else->{
                        var dishID=0
                        var imageSource=Constants.DISH_IMAGE_SOURCE_LOCAL
                    var favoriteDish=false
                    mFavDishDetails?.let {
                        if(it.id!=null){
                            dishID=it.id
                            imageSource=it.imageSource
                            favoriteDish=it.favoriteDish
                        }
                    }
                        val favDishDetails=FavDish(
                            mImagePath,
                            imageSource,
                            title,
                            type,
                            category,
                            ingredients,
                            cookingTimeinMinutes,
                            cookingDirection,
                            favoriteDish
                        ,dishID
                        )

                    if(dishID==0){
                        mFavDishViewModel.insert(favDishDetails)
                        Toast.makeText(
                            this@AddUpdateDishActivity,
                            "You have successfully added your favorite dish",
                            Toast.LENGTH_SHORT).show()
                        Log.e("Insertion","success")
                        Toast.makeText(this@AddUpdateDishActivity,"You have successfully updated your dish details",Toast.LENGTH_SHORT).show()
                    }else{
                        mFavDishViewModel.update(favDishDetails)
                        Toast.makeText(this@AddUpdateDishActivity,"You have successfully updated your dish details",Toast.LENGTH_SHORT).show()


                    }

                    finish()
                    }


                }

            }
        }
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */

    fun selectedItem(item:String,selection:String){
        when(selection){
            Constants.DISH_TYPE->{
                mCustomListDialog.dismiss()
                mBinding.etType.setText(item)

            }
            Constants.DISH_CATEGORY->{
                mCustomListDialog.dismiss()
                mBinding.etCategory.setText(item)

            }
            Constants.DISH_COOKING_TIME->{
                mCustomListDialog.dismiss()
                mBinding.etCookingTime.setText(item)

            }
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                data?.extras?.let {

                    val thumbnail: Bitmap =
                        data.extras!!.get("data") as Bitmap // Bitmap from camera
                    // TODO Step 2: Here we will replace the setImageBitmap using glide as below.
                    // START
                    // mBinding.ivDishImage.setImageBitmap(thumbnail) // Set to the imageView.

                    // Set Capture Image bitmap to the imageView using Glide
                    Glide.with(this@AddUpdateDishActivity)
                        .load(thumbnail)
                        .centerCrop()
                        .into(mBinding.ivDishImage)
                    // END
                    mImagePath=saveImageToInternalStorage(thumbnail)
                    Log.e("Image Path",mImagePath)
                    // Replace the add icon with edit icon once the image is loaded.
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@AddUpdateDishActivity,
                            R.drawable.ic_baseline_edit_24
                        )
                    )
                }
            } else if (requestCode == GALLERY) {

                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data

                    // TODO Step 3: Here we will replace the setImageURI using Glide as below.
                    // START
                    // mBinding.ivDishImage.setImageURI(selectedPhotoUri) // Set the selected image from GALLERY to imageView.

                    // Set Selected Image bitmap to the imageView using Glide
                    Glide.with(this@AddUpdateDishActivity)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object :RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("Tag","Error loading image",e)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap:Bitmap=resource.toBitmap()
                                    mImagePath=saveImageToInternalStorage(bitmap)
                                    Log.e("Image path",mImagePath)
                                }
                                return false
                            }

                        })
                        .into(mBinding.ivDishImage)
                    // END

                    // Replace the add icon with edit icon once the image is selected.
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@AddUpdateDishActivity,
                            R.drawable.ic_baseline_edit_24
                        )
                    )
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }

    /**
     * A function for ActionBar setup.
     */
    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        if(mFavDishDetails!=null && mFavDishDetails!!.id!=0){
            supportActionBar?.let {
                it.title=resources.getString(R.string.title_edit_dish)

            }

        }else{
            supportActionBar?.let {
                it.title=resources.getString(R.string.title_add_dish)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.google.android.material.R.drawable.ic_arrow_back_black_24)

        mBinding.toolbarAddDishActivity.setNavigationOnClickListener { onBackPressed() }
    }


    /**
     * A function to launch the custom image selection dialog.
     */
    private fun customImageSelectionDialog() {
        val dialog= Dialog(this@AddUpdateDishActivity)

        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.TIRAMISU) {
                Dexter.withContext(this@AddUpdateDishActivity)
                    .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,

                        )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                            report?.let {
                                // Here after all the permission are granted launch the CAMERA to capture an image.
                                if (report.areAllPermissionsGranted()) {

                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, CAMERA)
                                }
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()
                        }
                    }).onSameThread()
                    .check()
            }else{
                Dexter.withContext(this@AddUpdateDishActivity)
                    .withPermissions(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.CAMERA,

                        )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                            report?.let {
                                // Here after all the permission are granted launch the CAMERA to capture an image.
                                if (report.areAllPermissionsGranted()) {

                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, CAMERA)
                                }
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()
                        }
                    }).onSameThread()
                    .check()
            }
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.TIRAMISU) {
                Dexter.withContext(this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )

                            startActivityForResult(galleryIntent, GALLERY)
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                "You have denied the storage permission to select image.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest,
                            token: PermissionToken
                        ) {
                            showRationalDialogForPermissions()
                        }
                    })
                    .onSameThread()
                    .check()
            }else{
                Dexter.withContext(this)
                    .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )

                            startActivityForResult(galleryIntent, GALLERY)
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                "You have denied the storage permission to select image.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest,
                            token: PermissionToken
                        ) {
                            showRationalDialogForPermissions()
                        }
                    })
                    .onSameThread()
                    .check()

            }
            dialog.dismiss()
        }

        //Start the dialog and display it on screen.
        dialog.show()
    }


    /**
     * A function used to show the alert dialog when the permissions are denied and need to allow it from settings app info.
     */
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap):String{
        val wrapper=ContextWrapper(applicationContext)

        var file=wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        file= File(file,"${UUID.randomUUID()}.jpg")

        try{
            val stream:OutputStream=FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun customItemsListDialog(title:String,itemsList:List<String>,selection:String){
        mCustomListDialog=Dialog(this@AddUpdateDishActivity)
        val binding:DialogCustomListBinding= DialogCustomListBinding.inflate(layoutInflater)
        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text=title
        binding.rvList.layoutManager=LinearLayoutManager(this)
        val adapter=CustomListAdapter(this,null,itemsList,selection)
        binding.rvList.adapter=adapter
        mCustomListDialog.show()

    }
    companion object {
        private const val CAMERA = 1

        private const val GALLERY = 2
        private const val IMAGE_DIRECTORY="RecipeAppImages"

    }
}