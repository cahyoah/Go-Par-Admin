package com.example.asus.go_paradmin.ui.home.tips;

import com.example.asus.go_paradmin.data.model.Tips;

import java.util.List;

public interface TipsView {
    void onFailureShowAllTips(String message);

    void onSuccessShowAllTips(List<Tips> tipsList);
}
