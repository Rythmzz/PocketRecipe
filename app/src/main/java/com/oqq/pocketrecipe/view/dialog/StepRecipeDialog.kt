package com.oqq.pocketrecipe.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.oqq.pocketrecipe.databinding.StepRecipeDialogBinding
import com.oqq.pocketrecipe.data.model.recipe.Process
import com.oqq.pocketrecipe.listener.DialogResultStepListener

class StepRecipeDialog(context:Context, private val dialogResultStepListener: DialogResultStepListener, val position:Int) : Dialog(context) {
    init {
        setCancelable(false)
    }
    private lateinit var binding:StepRecipeDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = StepRecipeDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntialData()
        setBehavior()
    }

    private fun setIntialData() {
        binding.step.setText("Bước ${position+1}")
    }

    private fun setBehavior() {
        binding.btnYes.setOnClickListener {
          if (binding.editStepName.text.toString().isEmpty()){
              binding.editStepName.error = "Không để trống tên bước"
          }
            else if (binding.stepDescription.text.toString().isEmpty()){
                binding.stepDescription.error = "Không để trống mô tả bước"
          }
            else {
                val process = Process(binding.editStepName.text.toString(), binding.stepDescription.text.toString())
              dialogResultStepListener.onDialogResultStep(true,process)
              this.dismiss()
          }
        }
        binding.btnNo.setOnClickListener {
            dialogResultStepListener.onDialogResultStep(false,null)
            this.dismiss()
        }
    }
}