package lib.lhh.fiv.library;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Created by Linhh on 16/3/2.
 */
public interface BaseFrescoImageView {

    /**
     * 获得当前监听.
     *
     * @return ControllerListener
     */
    ControllerListener<Object> getControllerListener();

    /**
     * 获得当前使用的DraweeController.
     *
     * @return DraweeController
     */
    DraweeController getDraweeController();

    /**
     * 获得低级别ImageRequest.
     *
     * @return ImageRequest
     */
    ImageRequest getLowImageRequest();

    /**
     * 获得当前使用的ImageRequest.
     *
     * @return ImageRequest
     */
    ImageRequest getImageRequest();

    /**
     * 获得当前使用的RoundingParams.
     */
    RoundingParams getRoundingParams();

    /**
     * 是否开启动画.
     *
     * @return boolean
     */
    boolean isAnim();

    /**
     * 获得当前后处理.
     *
     * @return Postprocessor
     */
    Postprocessor getPostProcessor();

    /**
     * 获得当前使用的默认图.
     *
     * @return int
     */
    int getDefaultResID();

    /**
     * 获得当前加载的图片.
     *
     * @return String
     */
    String getThumbnailUrl();

    /**
     * 获得当前低分辨率图片.
     *
     * @return String
     */
    String getLowThumbnailUrl();

    /**
     * 获得加载的本地图片.
     *
     * @return String
     */
    String getThumbnailPath();

    /**
     * 是否可以点击重试,默认false.
     *
     * @return boolean
     */
    boolean getTapToRetryEnabled();

    /**
     * 是否自动旋转.
     *
     * @return boolean
     */
    boolean getAutoRotateEnabled();
}
