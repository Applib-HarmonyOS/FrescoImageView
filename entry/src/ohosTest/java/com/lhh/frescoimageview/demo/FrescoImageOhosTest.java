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

package com.lhh.frescoimageview.demo;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.imagepipeline.request.Postprocessor;
import lib.lhh.fiv.library.FrescoImageView;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class FrescoImageOhosTest {
    private final AttrSet attrSet = new AttrSet() {
        @Override
        public Optional<String> getStyle() {
            return Optional.empty();
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public Optional<Attr> getAttr(int i) {
            return Optional.empty();
        }

        @Override
        public Optional<Attr> getAttr(String s) {
            return Optional.empty();
        }
    };
    private FrescoImageView frescoImageView;
    private static final String IMG_URL = "https://avatars1.githubusercontent.com/u/8758713?v=3&s=460";

    @Before
    public void setup() {
        Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        frescoImageView = new FrescoImageView(context, attrSet);
    }

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.lhh.frescoimageview.demo", actualBundleName);
    }

    @Test
    public void testAnim() {
        assertTrue(frescoImageView.isAnim());
        frescoImageView.setAnim(false);
        assertFalse(frescoImageView.isAnim());
    }

    @Test
    public void testAutoRotateEnabled() {
        assertFalse(frescoImageView.getAutoRotateEnabled());
        frescoImageView.setAutoRotateEnabled(true);
        assertTrue(frescoImageView.getAutoRotateEnabled());
    }

    @Test
    public void testTapToRetryEnabled() {
        assertFalse(frescoImageView.getTapToRetryEnabled());
        frescoImageView.setTapToRetryEnabled(true);
        assertTrue(frescoImageView.getTapToRetryEnabled());
    }

    @Test
    public void testDefaultResID() {
        frescoImageView.loadView(IMG_URL, 5);
        assertEquals(5, frescoImageView.getDefaultResID());
    }

    @Test
    public void testPostprocessor() {
        Postprocessor postProcessor = null;
        frescoImageView.setPostProcessor(postProcessor);
        assertEquals(postProcessor, frescoImageView.getPostProcessor());
    }

    @Test
    public void testControllerListener() {
        ControllerListener controllerListener = null;
        frescoImageView.setControllerListener(controllerListener);
        assertEquals(controllerListener, frescoImageView.getControllerListener());
    }

    @Test
    public void testThumbnailUrl() {
        frescoImageView.loadView(IMG_URL, IMG_URL, 0);
        assertEquals(IMG_URL, frescoImageView.getThumbnailUrl());
    }

    @Test
    public void testLowThumbnailUrl() {
        frescoImageView.loadView(IMG_URL, IMG_URL, 0);
        assertEquals(IMG_URL, frescoImageView.getLowThumbnailUrl());
    }

    @Test
    public void testAsCircle() {
        RoundingParams roundingParams = frescoImageView.getRoundingParams();
        roundingParams.setRoundAsCircle(true);
        frescoImageView.asCircle();
        assertEquals(roundingParams, frescoImageView.getRoundingParams());
    }
}