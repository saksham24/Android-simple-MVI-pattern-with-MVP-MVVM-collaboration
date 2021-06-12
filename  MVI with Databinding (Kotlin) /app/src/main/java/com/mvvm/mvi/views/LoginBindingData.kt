package com.mvvm.mvi.views

import androidx.databinding.BaseObservable
import com.mvvm.mvi.BR

class LoginBindingData(var infoText: String, var isEnableMainFields: Boolean,
                       var isEnableResetButton: Boolean, var progressBarVisibility: Int) : BaseObservable() {
    fun setData(infoText: String, isEnableMainFields: Boolean, isEnableResetButton: Boolean, progressBarVisibility: Int) {
        this.infoText = infoText
        this.isEnableMainFields = isEnableMainFields
        this.isEnableResetButton = isEnableResetButton
        this.progressBarVisibility = progressBarVisibility
        notifyPropertyChanged(BR._all)
    }

}
