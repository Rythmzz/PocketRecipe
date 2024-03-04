package com.oqq.pocketrecipe.listener

import com.oqq.pocketrecipe.data.model.recipe.Process
interface DialogResultStepListener {
    fun onDialogResultStep(result:Boolean, process: Process?)
}