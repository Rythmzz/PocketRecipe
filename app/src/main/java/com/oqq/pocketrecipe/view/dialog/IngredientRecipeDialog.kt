package com.oqq.pocketrecipe.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.oqq.pocketrecipe.databinding.IngredientRecipeDialogBinding
import com.oqq.pocketrecipe.listener.DialogResultAddIngredientListener

class IngredientRecipeDialog(context:Context, private val dialogResultAddIngredientListener:DialogResultAddIngredientListener) : Dialog(context) {
    init {
        setCancelable(false)
    }
    private lateinit var binding:IngredientRecipeDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = IngredientRecipeDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntialData()
        setBehavior()
    }

    private fun setIntialData() {
    }

    private fun setBehavior() {
        binding.btnYes.setOnClickListener {
          if (binding.textIngredient.text.toString().isEmpty()){
              binding.textIngredient.error = "Không để trống nguyên liệu"
          }

            else {
              dialogResultAddIngredientListener.onDialogResult(true,binding.textIngredient.text.toString())
              this.dismiss()
          }
        }
        binding.btnNo.setOnClickListener {
            dialogResultAddIngredientListener.onDialogResult(false,"")
            this.dismiss()
        }
    }
}