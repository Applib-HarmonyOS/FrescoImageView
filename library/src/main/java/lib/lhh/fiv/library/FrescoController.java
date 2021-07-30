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
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Created by Linhh on 16/3/2.
 */
public interface FrescoController {

    String HTTP_PREFIX = "http://";
    String HTTPS_PREFIX = "https://";
    String FILE_PREFIX = "file://";

    /**
     * 加载网络图片.
     *
     * @param lowUrl 低分辨率图片
     * @param url 网络图片
     * @param defaultResID 默认图
     */
    void loadView(String lowUrl, String url, int defaultResID);

    /**
     * 加载网络图片.
     *
     * @param url 网络图片
     * @param defaultResID 默认图
     */
    void loadView(String url, int defaultResID);

    /**
     * 加载本地图片.
     *
     * @param path 图片路劲
     * @param defaultRes 默认图
     */
    void loadLocalImage(String path, int defaultRes);

    /**
     * 将该Fresco处理为圆形.
     */
    void asCircle();

    /**
     * 用一种颜色来遮挡View以实现圆形，在一些内存较低的机器上推荐使用.
     *
     * @param overlayColor int
     */
    void setCircle(int overlayColor);

    /**
     * 设置圆角.
     *
     * @param radius float
     */
    void setCornerRadius(float radius);

    /**
     * 用一种颜色来遮挡View以实现圆角，在一些内存较低的机器上推荐使用.
     *
     * @param radius float
     * @param overlayColor int
     */
    void setCornerRadius(float radius, int overlayColor);

    /**
     * 设置边框.
     *
     * @param color int
     * @param width float
     */
    void setBorder(int color, float width);

    /**
     * 清除所使用的RoundingParams.
     */
    void clearRoundingParams();

    /**
     * 设置RoundingParams.
     *
     * @param roundingParmas RoundingParams
     */
    void setRoundingParmas(RoundingParams roundingParmas);

    /**
     * 设置下载监听器.
     *
     * @param controllerListener ControllerListener
     */
    void setControllerListener(ControllerListener<Object> controllerListener);


    /**
     * 设置后处理.
     *
     * @param postProcessor Postprocessor
     */
    void setPostProcessor(Postprocessor postProcessor);


    /**
     * 是否开启动画.
     *
     * @param anim boolean
     */
    void setAnim(boolean anim);

    /**
     * 是否可以点击重试.
     *
     * @param tapToRetryEnabled boolean
     */
    void setTapToRetryEnabled(boolean tapToRetryEnabled);

    /**
     * 是否自动旋转.
     *
     * @param autoRotateEnabled boolean
     */
    void setAutoRotateEnabled(boolean autoRotateEnabled);

    /**
     * 设置图片缩放type.
     *
     * @param scaleType ScaleType
     */
    void setActualImageScaleType(ScalingUtils.ScaleType scaleType);

}
