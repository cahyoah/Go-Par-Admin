package com.example.asus.go_paradmin.ui.detailtips;

import com.example.asus.go_paradmin.data.model.Tips;

public interface DetailTipsView {
    void onSuccessShowDetailTips(Tips tips);

    void onFailureShowDetailTips(String message);

    void onFailureDeleteTips(String message);

    void onSuccessDeleteTips();

    void onFailureUpdateTips(String message);

    void onSuccessUpdateTips(Tips tips);
}
