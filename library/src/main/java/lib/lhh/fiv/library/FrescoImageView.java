package lib.lhh.fiv.library;

import com.facebook.animated.giflite.GifDecoder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.oszc.bbhmlibrary.wrapper.TextUtils;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

/**
 * Created by Linhh on 16/2/18.
 */
public class FrescoImageView extends SimpleDraweeView implements FrescoController, BaseFrescoImageView {

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "FrescoImageView");

    private String mThumbnailUrl = null;

    private String mLowThumbnailUrl = null; //低分辨率Url

    private int  mDefaultResID = 0;

    private ImageRequest mRequest;

    private boolean mAnim = true; //默认开启动画

    private String mThumbnailPath = null;

    private ControllerListener<Object> mControllerListener;

    private Postprocessor mPostProcessor;

    private DraweeController mController;

    private ImageRequest mLowResRequest;

    private boolean mTapToRetry = false;

    private boolean mAutoRotateEnabled = false;

    public FrescoImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FrescoImageView(Context context) {
        super(context);
    }

    public FrescoImageView(Context context, AttrSet attrs) {
        super(context, attrs);
    }

    public FrescoImageView(Context context, AttrSet attrs, String defStyle) {
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

    /**
     * Function to load Gif from Url.
     *
     * @param url Gif url
     */
    public void loadGifView(String url) {
        Uri uri = Uri.parse(url);
        final PipelineDraweeControllerBuilder controllerBuilder =
                Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setOldController(this.getController());
        final ImageDecodeOptionsBuilder<?> optionsBuilder =
                ImageDecodeOptions.newBuilder().setMaxDimensionPx(4000);

        GifDecoder mGifDecoder = new GifDecoder();
        optionsBuilder.setCustomImageDecoder(mGifDecoder);

        controllerBuilder.setImageRequest(
                ImageRequestBuilder.newBuilderWithSource(uri)
                        .setImageDecodeOptions(optionsBuilder.build())
                        .build());

        this.getHierarchy().setOverlayImage(null);

        this.setController(controllerBuilder.build());

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

