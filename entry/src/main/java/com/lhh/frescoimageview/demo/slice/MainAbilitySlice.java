package com.lhh.frescoimageview.demo.slice;

import com.lhh.frescoimageview.demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

/**
 * MainAbilitySlice class.
 */
public class MainAbilitySlice extends AbilitySlice {

    Button mBtnFrescoNormal;
    Button mBtnFrescoZoom;
    Button mBtnFrescoGif;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        mBtnFrescoNormal = (Button) findComponentById(ResourceTable.Id_btn_fresco_normal);
        mBtnFrescoZoom = (Button) findComponentById(ResourceTable.Id_btn_fresco_zoom);
        mBtnFrescoGif = (Button) findComponentById(ResourceTable.Id_btn_fresco_gif);

        mBtnFrescoNormal.setClickedListener(component -> present(new FrescoImageViewAbilitySlice(), new Intent()));

        mBtnFrescoZoom.setClickedListener(component -> present(new FrescoZoomImageViewAbilitySlice(), new Intent()));

        mBtnFrescoGif.setClickedListener(component -> present(new FrescoGifImageViewAbilitySlice(), new Intent()));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
