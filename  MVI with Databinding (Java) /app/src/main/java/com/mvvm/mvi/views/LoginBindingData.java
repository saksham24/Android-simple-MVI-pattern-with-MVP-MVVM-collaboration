package com.mvvm.mvi.views;

import androidx.databinding.BaseObservable;

import com.mvvm.mvi.BR;

public class LoginBindingData extends BaseObservable {
    public String infoText;
    public boolean isEnableMainFields;
    public boolean isEnableResetButton;
    public int progressBarVisibility;

    public LoginBindingData(String infoText, boolean isEnableMainFields, boolean isEnableResetButton, int progressBarVisibility) {
        this.infoText = infoText;
        this.isEnableMainFields = isEnableMainFields;
        this.isEnableResetButton = isEnableResetButton;
        this.progressBarVisibility = progressBarVisibility;
    }

    public void setData(String infoText, boolean isEnableMainFields, boolean isEnableResetButton, int progressBarVisibility) {
        this.infoText = infoText;
        this.isEnableMainFields = isEnableMainFields;
        this.isEnableResetButton = isEnableResetButton;
        this.progressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR._all);
    }
}
