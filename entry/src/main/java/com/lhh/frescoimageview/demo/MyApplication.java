package com.lhh.frescoimageview.demo;

import com.facebook.drawee.backends.pipeline.Fresco;
import ohos.aafwk.ability.AbilityPackage;

/**
 * MyApplication class.
 */
public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();

        Fresco.initialize(this);
    }
}
