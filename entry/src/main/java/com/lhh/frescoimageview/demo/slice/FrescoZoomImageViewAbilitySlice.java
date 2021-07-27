package com.lhh.frescoimageview.demo.slice;

import com.lhh.frescoimageview.demo.ResourceTable;
import lib.lhh.fiv.library.FrescoZoomImageView;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.bundle.IBundleManager;
import ohos.security.SystemPermission;

/**
 * FrescoZoomImageViewAbilitySlice class.
 */
public class FrescoZoomImageViewAbilitySlice extends AbilitySlice {

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_fresco_zoom_imageview);

        Text text = (Text) findComponentById(ResourceTable.Id_text);

        if (verifySelfPermission(SystemPermission.INTERNET) == IBundleManager.PERMISSION_GRANTED) {
            text.setText("Fresco Zoom Image View");
        } else {
            text.setText("Internet not granted!");
            if (canRequestPermission(SystemPermission.INTERNET)) {
                requestPermissionsFromUser(new String[]{SystemPermission.INTERNET}, 0);
            }
        }

        String imgUrl = "https://avatars1.githubusercontent.com/u/8758713?v=3&s=460";

        FrescoZoomImageView frescoZoomImageView = (FrescoZoomImageView) findComponentById(ResourceTable.Id_fiv);
        frescoZoomImageView.loadView(imgUrl, ResourceTable.Media_icon);
    }
}
