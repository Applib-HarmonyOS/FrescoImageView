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

package lib.lhh.fiv.library;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.oszc.bbhmlibrary.wrapper.TextUtils;
import lib.lhh.fiv.library.zoomable.ZoomableDraweeView;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


/**
 * Created by Linhh on 16/2/18.
 */
public class FrescoZoomImageView extends ZoomableDraweeView implements FrescoController, BaseFrescoImageView {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "FrescoZoomImageView");

    private String mThumbnailUrl = null;

    private String mLowThumbnailUrl = null; //低分辨率Url

    private int  mDefaultResID = 0;

    private ImageRequest mRequest;

    private String mThumbnailPath = null;

    private boolean mAnim = true; //默认开启动画

    private ImageRequest mLowResRequest;

    private ControllerListener<Object> mControllerListener;

    private Postprocessor mPostProcessor;

    private DraweeController mController;

    private boolean mTapToRetry = false;

    private boolean mAutoRotateEnabled = false;

    public FrescoZoomImageView(Context context) {
        this(context, null);
    }

    public FrescoZoomImageView(Context context, AttrSet attrs) {
        super(context, attrs);
    }

    public FrescoZoomImageView(Context context, AttrSet attrs, String defStyle) {
        super(context, attrs, defStyle);
    }

    private void setResourceController() {

        mRequest = FrescoFactory.buildImageRequestWithResource(this);

        mController = FrescoFactory.buildDraweeController(this);

        this.setController(mController);
    }

    private void setSourceController() {

        mRequest = FrescoFactory.buildImageRequestWithSource(this);

        mLowResRequest = FrescoFactory.buildLowImageRequest(this);

        mController = FrescoFactory.buildDraweeController(this);

        this.setController(mController);
    }

    @Override
    public int getDefaultResID() {
        return this.mDefaultResID;
    }

    @Override
    public void loadView(String lowUrl, String url, int defaultResID) {
        try {
            mThumbnailPath = null;
            mThumbnailUrl = url;
            mLowThumbnailUrl = url;
            mDefaultResID = defaultResID;
            if (!TextUtils.isEmpty(mThumbnailUrl)
                    && (mThumbnailUrl.startsWith(FrescoController.HTTP_PREFIX)
                    || mThumbnailUrl.startsWith(FrescoController.HTTPS_PREFIX))) {

                this.getHierarchy().setPlaceholderImage(defaultResID);

                this.setSourceController();

                return;
            }

            this.getHierarchy().setPlaceholderImage(defaultResID);
            this.setResourceController();

        } catch (OutOfMemoryError e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }
    }

    @Override
    public void loadView(String url, int defaultResID) {
        this.loadView(null, url, defaultResID);
    }

    @Override
    public void loadLocalImage(String path, int defaultRes) {
        try {
            mThumbnailPath = path;
            mDefaultResID = defaultRes;
            mThumbnailUrl = null;
            mLowThumbnailUrl = null;

            this.getHierarchy().setPlaceholderImage(mDefaultResID);

            if (TextUtils.isEmpty(mThumbnailPath)) {
                this.setResourceController();
                return;
            }
            if (!mThumbnailPath.startsWith(FrescoController.FILE_PREFIX)) {
                mThumbnailPath = FrescoController.FILE_PREFIX + mThumbnailPath;
            }
            this.setSourceController();
        } catch (OutOfMemoryError e) {
            HiLog.error(LABEL_LOG, e.getMessage());
        }
    }

    @Override
    public Postprocessor getPostProcessor() {
        return this.mPostProcessor;
    }

    @Override
    public void setPostProcessor(Postprocessor postProcessor) {
        this.mPostProcessor = postProcessor;
    }

    @Override
    public String getThumbnailUrl() {
        return this.mThumbnailUrl;
    }

    @Override
    public String getLowThumbnailUrl() {
        return this.mLowThumbnailUrl;
    }

    @Override
    public String getThumbnailPath() {
        return this.mThumbnailPath;
    }

    @Override
    public boolean getTapToRetryEnabled() {
        return this.mTapToRetry;
    }

    @Override
    public boolean getAutoRotateEnabled() {
        return this.mAutoRotateEnabled;
    }

    @Override
    public void asCircle() {
        setRoundingParmas(getRoundingParams().setRoundAsCircle(true));
    }

    @Override
    public void setBorder(int color, float width) {
        setRoundingParmas(getRoundingParams().setBorder(color, width));
    }

    @Override
    public void clearRoundingParams() {
        setRoundingParmas(null);
    }

    @Override
    public ControllerListener<Object> getControllerListener() {
        return this.mControllerListener;
    }

    @Override
    public DraweeController getDraweeController() {
        return this.getController();
    }

    @Override
    public ImageRequest getLowImageRequest() {
        return this.mLowResRequest;
    }

    @Override
    public ImageRequest getImageRequest() {
        return this.mRequest;
    }

    @Override
    public RoundingParams getRoundingParams() {
        RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
        if (roundingParams == null) {
            roundingParams = new RoundingParams();
        }
        return roundingParams;
    }

    @Override
    public void setRoundingParmas(RoundingParams roundingParmas) {
        this.getHierarchy().setRoundingParams(roundingParmas);
    }

    @Override
    public void setControllerListener(ControllerListener<Object> controllerListener) {
        this.mControllerListener = controllerListener;
    }

    @Override
    public void setCircle(int overlayColor) {
        setRoundingParmas(getRoundingParams().setRoundAsCircle(true)
                .setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR)
                .setOverlayColor(overlayColor));
    }

    @Override
    public void setCornerRadius(float radius) {
        setRoundingParmas(getRoundingParams().setCornersRadius(radius));
    }

    @Override
    public void setCornerRadius(float radius, int overlayColor) {
        setRoundingParmas(getRoundingParams().setCornersRadius(radius)
                .setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR)
                .setOverlayColor(overlayColor));
    }

    @Override
    public boolean isAnim() {
        return mAnim;
    }

    @Override
    public void setAnim(boolean anim) {
        mAnim = anim;
    }

    @Override
    public void setTapToRetryEnabled(boolean tapToRetryEnabled) {
        this.mTapToRetry = tapToRetryEnabled;
    }

    @Override
    public void setAutoRotateEnabled(boolean autoRotateEnabled) {
        this.mAutoRotateEnabled = autoRotateEnabled;
    }

    @Override
    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        this.getHierarchy().setActualImageScaleType(scaleType);
    }
}
