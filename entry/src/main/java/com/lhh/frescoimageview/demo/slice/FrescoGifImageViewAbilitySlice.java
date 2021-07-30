/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
