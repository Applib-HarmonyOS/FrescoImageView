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

package lib.lhh.fiv.library.gestures;

import ohos.multimodalinput.event.TouchEvent;

/**
 * Component that detects and tracks multiple pointers based on touch events.
 *
 * <p>Each time a pointer gets pressed or released, the current gesture (if any) will end, and a new
 * one will be started (if there are still pressed pointers left). It is guaranteed that the number
 * of pointers within the single gesture will remain the same during the whole gesture.
 */
public class MultiPointerGestureDetector {

    /** The listener for receiving notifications when gestures occur. */
    public interface Listener {
        /** Responds to the beginning of a gesture.
         *
         * @param detector Multi pointer gesture detector
         */
        void onGestureBegin(MultiPointerGestureDetector detector);

        /** Responds to the update of a gesture in progress.
         *
         * @param detector Multi pointer gesture detector
         */
        void onGestureUpdate(MultiPointerGestureDetector detector);

        /** Responds to the end of a gesture.
         *
         * @param detector Multi pointer gesture detector
         */
        void onGestureEnd(MultiPointerGestureDetector detector);
    }

    private static final int MAX_POINTERS = 2;

    private boolean mGestureInProgress;
    private int mCount;
    private final int[] mId = new int[MAX_POINTERS];
    private final float[] mStartX = new float[MAX_POINTERS];
    private final float[] mStartY = new float[MAX_POINTERS];
    private final float[] mCurrentX = new float[MAX_POINTERS];
    private final float[] mCurrentY = new float[MAX_POINTERS];

    private Listener mListener = null;

    public MultiPointerGestureDetector() {
        reset();
    }

    /** Factory method that creates a new instance of MultiPointerGestureDetector. */
    public static MultiPointerGestureDetector newInstance() {
        return new MultiPointerGestureDetector();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    /**
     * Resets the component to the initial state.
     */
    public void reset() {
        mGestureInProgress = false;
        mCount = 0;
        for (int i = 0; i < MAX_POINTERS; i++) {
            mId[i] = -1;
        }
    }

    protected boolean shouldStartGesture() {
        return true;
    }

    private void startGesture() {
        if (!mGestureInProgress) {
            mGestureInProgress = true;
            if (mListener != null) {
                mListener.onGestureBegin(this);
            }
        }
    }

    private void stopGesture() {
        if (mGestureInProgress) {
            mGestureInProgress = false;
            if (mListener != null) {
                mListener.onGestureEnd(this);
            }
        }
    }

    /**
     * Handles touch event.
     *
     * @param event Touch event
     * @return True if successfully handled
     */
    public boolean onTouchEvent(final TouchEvent event) {
        switch (event.getAction()) {
            case TouchEvent.POINT_MOVE: {
                // update pointers
                for (int i = 0; i < event.getPointerCount(); i++) {
                    mCurrentX[i] = event.getPointerPosition(i).getX();
                    mCurrentY[i] = event.getPointerPosition(i).getY();
                }
                // start a new gesture if not already started
                if (!mGestureInProgress && shouldStartGesture()) {
                    startGesture();
                }
                // notify listener
                if (mGestureInProgress && mListener != null) {
                    mListener.onGestureUpdate(this);
                }
                break;
            }

            case TouchEvent.PRIMARY_POINT_DOWN:
            case TouchEvent.OTHER_POINT_DOWN:
            case TouchEvent.PRIMARY_POINT_UP:
            case TouchEvent.OTHER_POINT_UP: {
                // we'll restart the current gesture (if any) whenever the number of pointers changes
                // NOTE: we only restart existing gestures here, new gestures are started in ACTION_MOVE
                final boolean wasGestureInProgress = mGestureInProgress;
                stopGesture();
                reset();
                // update pointers
                for (int i = 0; i < event.getPointerCount(); i++) {
                    mId[i] = event.getPointerId(i);
                    mCurrentX[i] = mStartX[i] = event.getPointerPosition(i).getX();
                    mCurrentY[i] = mStartY[i] = event.getPointerPosition(i).getY();
                    mCount++;
                }
                // restart the gesture (if any) if there are still pointers left
                if (wasGestureInProgress && mCount > 0) {
                    startGesture();
                }
                break;
            }

            case TouchEvent.CANCEL:
            default: {
                stopGesture();
                reset();
                break;
            }

        }
        return true;
    }

    /** Restarts the current gesture. */
    public void restartGesture() {
        if (!mGestureInProgress) {
            return;
        }
        stopGesture();
        for (int i = 0; i < MAX_POINTERS; i++) {
            mStartX[i] = mCurrentX[i];
            mStartY[i] = mCurrentY[i];
        }
        startGesture();
    }

    /** Gets whether gesture is in progress or not. */
    public boolean isGestureInProgress() {
        return mGestureInProgress;
    }

    /** Gets the number of pointers in the current gesture. */
    public int getCount() {
        return mCount;
    }

    /**
     * Gets the start X coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getStartX() {
        return mStartX;
    }

    /**
     * Gets the start Y coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getStartY() {
        return mStartY;
    }

    /**
     * Gets the current X coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getCurrentX() {
        return mCurrentX;
    }

    /**
     * Gets the current Y coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getCurrentY() {
        return mCurrentY;
    }
}
