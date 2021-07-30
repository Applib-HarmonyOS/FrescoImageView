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
 * FrescoImageViewAbilitySlice class.
 */
public class FrescoImageViewAbilitySlice extends AbilitySlice {

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_fresco_imageview);

        Text text = (Text) findComponentById(ResourceTable.Id_text);

        if (verifySelfPermission(SystemPermission.INTERNET) == IBundleManager.PERMISSION_GRANTED) {
            text.setText("Fresco Image View");
        } else {
            text.setText("Internet not granted!");
            if (canRequestPermission(SystemPermission.INTERNET)) {
                requestPermissionsFromUser(new String[]{SystemPermission.INTERNET}, 0);
            }
        }

        String imgUrl = "https://avatars1.githubusercontent.com/u/8758713?v=3&s=460";

        FrescoImageView frescoImageView = (FrescoImageView) findComponentById(ResourceTable.Id_fiv);
        frescoImageView.asCircle();
        frescoImageView.loadView(imgUrl, ResourceTable.Media_icon);
    }
}
