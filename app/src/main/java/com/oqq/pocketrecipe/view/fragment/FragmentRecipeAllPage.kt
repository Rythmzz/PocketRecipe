package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentRecipeAllPageBinding
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class FragmentRecipeAllPage: Fragment() {
    private lateinit var binding:FragmentRecipeAllPageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val navOptions:NavOptions by inject()

    private var firstThread: Job? = null

    private val recipeViewModel:RecipeViewModel by viewModel()

    private lateinit var listLayout:MutableList<FrameLayout>

    private var quotes:MutableList<String> = mutableListOf(
        "Không ai giỏi ngay từ lần đầu, không trải qua thất bại thì chẳng biết thành công mùi vị ngọt ngào ra sao",
        "Bạn cần xây dựng cho mình một sự tự tin, nấu ăn là một hành trình, nó cũng giống như ngành y vậy",
        "Khi yêu một cái gì đó, bạn sẽ không thấy nhàm chán, dù công việc cứ lặp đi lặp lại hàng ngày",
        "Sáng tạo, kiên trì và chăm chỉ là những yếu tố tạo nên sự thành công của người thợ làm bánh",
        "Người yêu thích ăn uống luôn luôn là những người tốt nhất",
        "Nhân tố quan trọng nhất dẫn đến thành công đó chính là kiên nhẫn. Không bao giờ bỏ cuộc"
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeAllPageBinding.inflate(inflater,container,false)

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
                            binding.countRecipeMain.setText("Hơn +${recipeViewModel.countMainRecipe} ý tưởng công thức")
        binding.countRecipeSub.setText("Hơn +${recipeViewModel.countSubRecipe} ý tưởng công thức")
        binding.countRecipeOther.setText("Hơn +${recipeViewModel.countOther} ý tưởng công thức")
                }
                }
            }
        }
    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        for (i in 0 until listLayout.size){
            listLayout[i].setOnClickListener {
                val action = FragmentRecipeAllPageDirections.actionFragmentRecipeAllDestToFragmentRecipeAllDetailDest(i)
                findNavController().navigate(action,navOptions)

            }
        }

    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        listLayout = mutableListOf(
            binding.layoutMainMeal,binding.layoutSubMeal,binding.layoutOtherMeal
        )

        val randomQuote:Int = Random.nextInt(0, quotes.size)
        binding.textQuote.setText(quotes[randomQuote])

        recipeViewModel.getListRecipeMeal("Chính")
        recipeViewModel.getListRecipeMeal("Phụ")
        recipeViewModel.getListNotContainRecipeMeal("Chính")










    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}