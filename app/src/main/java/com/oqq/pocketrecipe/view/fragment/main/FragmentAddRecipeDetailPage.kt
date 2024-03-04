package com.oqq.pocketrecipe.view.fragment.main

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.IngredientRecipeAdapter
import com.oqq.pocketrecipe.adapter.StepRecipeAdapter
import com.oqq.pocketrecipe.data.model.recipe.AttributesRecipe
import com.oqq.pocketrecipe.data.model.recipe.Process
import com.oqq.pocketrecipe.data.model.recipe.RecipeRequest
import com.oqq.pocketrecipe.databinding.FragmentAddRecipeDetailPageBinding
import com.oqq.pocketrecipe.di.MyApplication
import com.oqq.pocketrecipe.listener.DialogResultAddIngredientListener
import com.oqq.pocketrecipe.listener.DialogResultStepListener
import com.oqq.pocketrecipe.view.dialog.IngredientRecipeDialog
import com.oqq.pocketrecipe.view.dialog.StepRecipeDialog
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream

class FragmentAddRecipeDetailPage: Fragment(), DialogResultStepListener, DialogResultAddIngredientListener {
    private lateinit var binding:FragmentAddRecipeDetailPageBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private var currentListProcess:MutableList<Process> = mutableListOf()
    private var currentListIngredient:MutableList<String> = mutableListOf()

    private val recipeViewModel:RecipeViewModel by viewModel()


    private lateinit var stepRecipeAdapter:StepRecipeAdapter
    private lateinit var ingredientRecipeAdapter:IngredientRecipeAdapter

    private var currentImage:MultipartBody.Part? = null

    private var currentCategory:String = "Khai vị"

    private var firstThread: Job? = null

    private var listMeal:List<String> = arrayListOf(
            "Khai vị" ,
            "Chính" ,
            "Ăn kèm" ,
            "Tráng miệng" ,
            "Phụ" ,
            "Dinh dưỡng" ,
            "Đồ uống"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecipeDetailPageBinding.inflate(inflater,container,false)

        setIntialData()
        setStatus()
        setBehavior()
        return binding.root
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                recipeViewModel.success.collect{
                    result -> if (result is Boolean && result){
                    val attrtibuteRecipe = AttributesRecipe(null, binding.editName.text.toString(),
                        binding.editAbout.text.toString(),binding.editDescription.text.toString(),
                        (binding.editTime.text.toString()).toInt(),(binding.editKcal.text.toString()).toInt(),
                        (binding.editNumber.text.toString()).toInt(),0,currentCategory,recipeViewModel.currentUrlImageRecipe,
                    currentListIngredient,currentListProcess,null)
                    val recipeRequest = RecipeRequest(attrtibuteRecipe)
                    recipeViewModel.createRecipe(recipeRequest)

                }
                    else {
                        println("UPLOAD HINH THAT BAI :$result")
                }
                }
            }

            this.launch {
                recipeViewModel.success2.collect{
                    result -> if (result is Boolean && result){
                    Toast.makeText(activity,"Công thức được tạo thành công!!",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_add_recipe_detail_dest,null)

                }
                    else {
                    Toast.makeText(activity,"$result",Toast.LENGTH_SHORT).show()
                }
                }
            }
        }
    }

    private fun setBehavior() {
        binding.addIngredient.setOnClickListener {
            val dialog = IngredientRecipeDialog(activity!!,this)
            dialog.show()
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_add_recipe_detail_dest,null)
        }

        binding.chipAddStep.setOnClickListener {
            val dialog = StepRecipeDialog(activity!!,this,currentListProcess.size)
            dialog.show()
        }

        binding.categoryMeal.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currentCategory = listMeal[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.addPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                MyApplication.PICK_IMAGE
            )
        }

        binding.btnYes.setOnClickListener {
            if (binding.editName.text.toString().isEmpty()){
                binding.editName.error = "Không được để trống phần tên món"
            }
            else if (binding.editAbout.text.toString().isEmpty()){
                binding.editAbout.error = "Không được để trống phần giới thiệu món"
            }
            else if (binding.editDescription.text.toString().isEmpty()){
                binding.editDescription.error = "Không được để trống phần mô tả món"
            }
            else if (binding.editTime.text.toString().isEmpty()){
                binding.editTime.error = "Không được để trống thời gian nấu"
            }
            else if (binding.editKcal.text.toString().isEmpty()){
                binding.editKcal.error = "Không được để trống phần calories"
            }
            else if (binding.editNumber.text.toString().isEmpty()){
                binding.editNumber.error = "Không được để trống phần số người ăn"
            }
            else if (currentListIngredient.isEmpty()){
                binding.addIngredient.error = "Chưa có nguyên liệu nào được thêm vào"
            }
            else if (binding.editName.text.toString().isEmpty()){
                binding.chipAddStep.error = "Chưa có quy trình nấu được thêm vào"
            }
            else if (currentImage == null){
                Toast.makeText(activity,"Vui lòng thêm hình ảnh cho công thức",Toast.LENGTH_SHORT).show()
            }
            else {
                recipeViewModel.uploadImageRecipe(currentImage!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MyApplication.PICK_IMAGE && resultCode == RESULT_OK){
            val imageUri = data!!.data
            binding.imageRecipe.setImageURI(imageUri)
            val inputStream: InputStream = activity!!.contentResolver.openInputStream(imageUri!!)!!
            val imageData = inputStream.readBytes()
            val requestFile = RequestBody.create(
                MediaType.parse(activity!!.contentResolver.getType(imageUri)!!),
                imageData
            )
            val image = MultipartBody.Part.createFormData("files", "image.jpg", requestFile)
            currentImage = image

        }
    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        stepRecipeAdapter = StepRecipeAdapter(currentListProcess)

        binding.layoutListStep.adapter = stepRecipeAdapter

        val layoutManagerStep = LinearLayoutManager(activity)
        layoutManagerStep.orientation = LinearLayoutManager.VERTICAL

        binding.layoutListStep.layoutManager = layoutManagerStep

        val listMealAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, listMeal)
        listMealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoryMeal.adapter = listMealAdapter

        ingredientRecipeAdapter = IngredientRecipeAdapter(currentListIngredient)

        binding.layoutListIngredient.adapter = ingredientRecipeAdapter

        val layoutManagerIngredient = GridLayoutManager(activity,3)
        binding.layoutListIngredient.layoutManager = layoutManagerIngredient








    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onDialogResultStep(result: Boolean, process: Process?) {
        if (result){
            currentListProcess.add(process!!)
            stepRecipeAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDialogResult(result: Boolean, text: String) {
        if (result){
            currentListIngredient.add(text)
            ingredientRecipeAdapter.notifyDataSetChanged()
        }
    }
}