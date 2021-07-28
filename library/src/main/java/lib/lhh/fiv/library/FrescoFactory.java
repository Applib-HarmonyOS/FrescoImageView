package lib.lhh.fiv.library;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.oszc.bbhmlibrary.wrapper.TextUtils;
import ohos.utils.net.Uri;

/**
 * Created by Linhh on 16/3/2.
 */
public class FrescoFactory {

    private FrescoFactory() {
        throw new IllegalStateException("Fresco Factory class");
    }

    /**
     * function buildDraweeController.
     *
     * @param fresco BaseFrescoImageView
     * @return DraweeController
     */
    public static DraweeController buildDraweeController(BaseFrescoImageView fresco) {
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(fresco.getImageRequest())
                .setAutoPlayAnimations(fresco.isAnim())
                .setTapToRetryEnabled(fresco.getTapToRetryEnabled())
                .setLowResImageRequest(fresco.getLowImageRequest())
                .setControllerListener(fresco.getControllerListener())
                .setOldController(fresco.getDraweeController())
                .build();
    }

    /**
     * function buildImageRequestWithResource.
     *
     * @param fresco BaseFrescoImageView
     * @return ImageRequest
     */
    public static ImageRequest buildImageRequestWithResource(BaseFrescoImageView fresco) {
        return  ImageRequestBuilder.newBuilderWithResourceId(fresco.getDefaultResID())
                .setPostprocessor(fresco.getPostProcessor())
                .setLocalThumbnailPreviewsEnabled(true)
                .build();
    }

    /**
     * function buildImageRequestWithSource.
     *
     * @param fresco BaseFrescoImageView
     * @return ImageRequest
     */
    public static ImageRequest buildImageRequestWithSource(BaseFrescoImageView fresco) {
        String thumbnail;
        if (TextUtils.isEmpty(fresco.getThumbnailUrl())) {
            thumbnail = fresco.getThumbnailPath();
        } else {
            thumbnail = fresco.getThumbnailUrl();
        }
        Uri uri = Uri.parse(thumbnail);
        return  ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(fresco.getPostProcessor())
                .setLocalThumbnailPreviewsEnabled(true)
                .build();
    }

    /**
     * function buildLowImageRequest.
     *
     * @param fresco BaseFrescoImageView
     * @return ImageRequest
     */
    public static ImageRequest buildLowImageRequest(BaseFrescoImageView fresco) {
        String lowThumbnail;
        if (TextUtils.isEmpty(fresco.getLowThumbnailUrl())) {
            return null;
        }
        lowThumbnail = fresco.getLowThumbnailUrl();
        Uri uri = Uri.parse(lowThumbnail);
        return ImageRequest.fromUri(uri);
    }
}