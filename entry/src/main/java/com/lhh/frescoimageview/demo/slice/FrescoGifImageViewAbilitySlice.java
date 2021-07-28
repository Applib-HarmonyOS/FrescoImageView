package com.lhh.frescoimageview.demo.slice;

import com.lhh.frescoimageview.demo.ResourceTable;
import lib.lhh.fiv.library.FrescoImageView;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.bundle.IBundleManager;
import ohos.security.SystemPermission;

/**
 * FrescoGifImageViewAbilitySlice class.
 */
public class FrescoGifImageViewAbilitySlice extends AbilitySlice {

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_fresco_gif_imageview);

        Text text = (Text) findComponentById(ResourceTable.Id_text);

        if (verifySelfPermission(SystemPermission.INTERNET) == IBundleManager.PERMISSION_GRANTED) {
            text.setText("Fresco Gif Image View");
        } else {
            text.setText("Internet not granted!");
            if (canRequestPermission(SystemPermission.INTERNET)) {
                requestPermissionsFromUser(new String[]{SystemPermission.INTERNET}, 0);
            }
        }

        String gifUrl = "https://frescolib.org/static/sample-images/fresco_logo_anim_full_frames_s.gif";

        FrescoImageView frescoImageView = (FrescoImageView) findComponentById(ResourceTable.Id_fiv);
        frescoImageView.loadGifView(gifUrl);
    }
}
