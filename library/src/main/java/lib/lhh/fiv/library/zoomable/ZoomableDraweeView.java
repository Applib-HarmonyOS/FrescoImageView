/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package lib.lhh.fiv.library.zoomable;

/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.Animatable;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.GenericDraweeView;
import com.oszc.bbhmlibrary.wrapper.RectF;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.Point;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

/**
 * DraweeView that has zoomable capabilities.
 *
 * <p>Once the image loads, pinch-to-zoom and translation gestures are enabled.
 *
 */
public class ZoomableDraweeView extends GenericDraweeView
        implements ZoomableController.Listener, Component.DrawTask {

    private static final float HUGE_IMAGE_SCALE_FACTOR_THRESHOLD = 1.1f;
    private static final int TOUCH_TIME = 250; //触摸间隔时间

    private final RectF mImageBounds = new RectF();
    private final RectF mViewBounds = new RectF();

    private final ControllerListener<Object> mControllerListener = new BaseControllerListener<Object>() {

        @Override
        public void onFinalImageSet(
                String id,
                Object imageInfo,
                Animatable animatable) {
            onFinalImageSet();
        }

        private void onFinalImageSet() {
            if (!mZoomableController.isEnabled()) {
                updateZoomableControllerBounds();
                mZoomableController.setEnabled(true);
            }
        }

        private void updateZoomableControllerBounds() {
            getHierarchy().getActualImageBounds(mImageBounds);
            mViewBounds.set(0, 0, getWidth(), getHeight());
            mZoomableController.setImageBounds(mImageBounds);
            mZoomableController.setViewBounds(mViewBounds);
        }

        private void onRelease() {
            mZoomableController.setEnabled(false);
        }

        @Override
        public void onRelease(String id) {
            onRelease();
        }
    };

    private DraweeController mHugeImageController;
    private ZoomableController mZoomableController = DefaultZoomableController.newInstance();

    public ZoomableDraweeView(Context context) {
        super(context);
        init();
    }

    public ZoomableDraweeView(Context context, AttrSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableDraweeView(Context context, AttrSet attrs, String defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mZoomableController.setListener(this);
    }

    /**
     * function zoomableController.
     *
     * @param zoomableController ZoomableController
     */
    public void setZoomableController(ZoomableController zoomableController) {
        Preconditions.checkNotNull(zoomableController);
        mZoomableController.setListener(null);
        mZoomableController = zoomableController;
        mZoomableController.setListener(this);
    }

    @Override
    public void setController(DraweeController controller) {
        setControllers(controller, null);
    }

    private void setControllersInternal(
            DraweeController controller,
            DraweeController hugeImageController) {
        removeControllerListener(getController());
        addControllerListener(controller);
        mHugeImageController = hugeImageController;
        super.setController(controller);
    }

    /**
     * Sets the controllers for the normal and huge image.
     *
     * <p>IMPORTANT: in order to avoid a flicker when switching to the huge image, the huge image
     * controller should have the normal-image-uri set as its low-res-uri.
     *
     * @param controller controller to be initially used
     * @param hugeImageController controller to be used after the client starts zooming-in
     */
    public void setControllers(
            DraweeController controller,
            DraweeController hugeImageController) {
        setControllersInternal(null, null);
        mZoomableController.setEnabled(false);
        setControllersInternal(controller, hugeImageController);
    }

    private void maybeSetHugeImageController() {
        if (mHugeImageController != null
                && mZoomableController.getScaleFactor() > HUGE_IMAGE_SCALE_FACTOR_THRESHOLD) {
            setControllersInternal(mHugeImageController, null);
        }
    }

    private void removeControllerListener(DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController) controller)
                    .removeControllerListener(mControllerListener);
        }
    }

    private void addControllerListener(DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController) controller)
                    .addControllerListener(mControllerListener);
        }
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(mZoomableController.getTransform());
        canvas.restoreToCount(saveCount);
    }

    private ClickedListener mOnClickListener;

    public void setOnDraweeClickListener(ClickedListener l) {
        mOnClickListener = l;
    }

    private long mCurrDownTime = 0;

    @Override
    public boolean onTouchEvent(Component component, TouchEvent event) {

        if (event.getAction() == TouchEvent.PRIMARY_POINT_DOWN) {
            mCurrDownTime = event.getOccurredTime();
        }

        if (event.getAction() == TouchEvent.PRIMARY_POINT_UP
                && event.getOccurredTime() - mCurrDownTime <= TOUCH_TIME
                && mOnClickListener != null) {
            //点击
            mOnClickListener.onClick(this);

        }

        if (mZoomableController.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(component, event);
    }

    /**
     * function clearZoom.
     */
    public void clearZoom() {
        if (mZoomableController instanceof DefaultZoomableController) {
            Point imagePoint = new Point((float) getWidth() / 2, (float) getHeight() / 2);
            ((DefaultZoomableController) mZoomableController).zoomToImagePoint(1, imagePoint);
        }
    }





    @Override
    public void onTransformChanged(Matrix transform) {
        maybeSetHugeImageController();
        invalidate();
    }



}