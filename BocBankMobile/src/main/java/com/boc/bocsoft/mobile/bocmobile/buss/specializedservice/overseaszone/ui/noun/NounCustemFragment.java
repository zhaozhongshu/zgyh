package com.boc.bocsoft.mobile.bocmobile.buss.specializedservice.overseaszone.ui.noun;

import com.boc.bocsoft.mobile.bocmobile.R;
import com.boc.bocsoft.mobile.bocmobile.buss.specializedservice.overseaszone.base.BaseNounFragment;

/**
 * 作者：xwg on 16/11/1 10:48
 * 攻略-外派工作-客户宝典 界面
 */
public class NounCustemFragment extends BaseNounFragment{

    @Override
    public String[] getTitleArr() {
        return  getResources().getStringArray(R.array.boc_overseas_noun_custem_title);
    }

    @Override
    public String[] getContentArr() {
        return getResources().getStringArray(R.array.boc_overseas_noun_custem_content);
    }
}
