# Fresco-ImageView

FrescoImageView is an image control for the HarmonyOS platform that can asynchronously load network pictures, project resources and local pictures, and supports two-finger zooming, basic image processing, and all the features of Fresco.

The control is based on Facebook's image loading library Fresco package, all methods and properties of Fresco can be used.

Fresco-ImageView itself inherits from DraweeView, so you can directly use it as DraweeView. In addition to using the loading method encapsulated by the control, you can also load pictures through Fresco's original ImageRequest.

## Characteristic
 * Directly inherit Fresco's DraweeView, which is essentially a View, and is compatible with all Fresco's parameters and methods
 * Only one step is required to load pictures, no complicated settings are required
 * Supports two-finger zoom, and supports click events, instead of PhotoView controls

## Usage

### Configure Fresco

Add network access permissions in the config.json of the project (depending on requirements).

```json
"reqPermissions": [
  {
    "name": "ohos.permission.INTERNET",
    "reason": "internet"
  }
]
```

Configure Fresco in the Application class of the project. This is actually the content of Fresco.

```java
public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();
        Fresco.initialize(this);
    }
}
```

### Adding controls to XML
Add components to the interface xml that needs to be added.

FrescoImageView provides two components, FrescoImageView (normal control) and FrescoZoomImageView (scalable control). Add controls as needed. Take FrescoZoomImageView as an example:

``` xml
<lib.lhh.fiv.library.FrescoImageView
    ohos:id="$+id:fiv"
    ohos:layout_alignment="horizontal_center"
    ohos:height="100vp"
    ohos:width="100vp"
    fresco:actualImageScaleType="fitCenter"/>
```

Find the component in the code.

```java
FrescoImageView frescoImageView = (FrescoImageView) findComponentById(ResourceTable.Id_fiv);
```

### Load network pictures

Call the void loadView(String url, int defaultResID) method.

url represents the address of the network image that needs to be loaded, and defaultResID represents the placeholder image (default image).

```java
frescoImageView.loadView(mImgUrl, ResourceTable.Media_icon);
```

If you need to display the default image first, then load and display a low-resolution image, and finally display the original image, you can use the void loadView(String lowUrl ,String url, int defaultResID) method, and lowUrl represents the low-resolution image address.

### Load network GIFs.

Call the void loadGifView(String url) method.

url represents the address of the network GIF that needs to be loaded.

```java
frescoImageView.loadGifView(mGifUrl);
```

### Load project resources

Choose any of the above methods and fill in the non-defaultResId parameter as null.

```java
frescoImageView.loadLocalImage(null,ResourceTable.Media_icon);
```

### Click to listen

If you are using FrescoZoomImageView, you need to call the setOnDraweeClickListener(OnClickListener l) method to set up the click event listener.

If you are using FrescoImageView, you can directly use setOnClicklistener(OnClickListener l).

### Turn on and off gif animation

If your picture is of gif type, we can control the Gif animation of FrescoImageView, which is controlled by setAnim(boolean anim) of FrescoImageView. By default, we turn on animation.

### Set the circle

If you need to turn the displayed picture into a circle, you can use the asCircle() method.

```java
frescoImageView.asCircle();
```

### Gif picture is set as a circle

Due to Fresco, if you need to set the gif to a circle, you need to use the setCircle(int overlay_color) method, and overlay_color is the background image color.

```java
frescoImageView.setCircle(Color.WHITE);
```

### Set rounded corners

Through the setCornerRadius(float radius) method, and pass in the required angle, the rounded corners and corners can be cut.

```java
frescoImageView.setCornerRadius(10);
```

### Set up the image processor

You may need to do additional processing on the picture, then you can write a Fresco PostProcessor and set a processor through the setPostProcessor(PostProcessor) method.

```java
frescoImageView.setPostProcessor(postProcessor);
```

### Set whether to click to retry loading

Sometimes the picture will fail to load. At this time, you can set whether to allow the user to click on the picture to retry the load. SetTapToRetryEnabled(boolean tapToRetryEnabled) method.

```java
frescoImageView.setTapToRetryEnabled(true);
```

### Set picture border

Set by the setBorder(int color, float width) method.

```java
frescoImageView.setBorder(Color.BLACK, 3.0f);
```

### Clear image fillet attributes

Maybe you have set the rounded corner properties of the picture, you can clear them through the clearRoundingParams() method to restore to the initial state.

```java
frescoImageView.clearRoundingParams();
```

### Set up loading monitor

Sometimes we want to monitor the picture loading situation, through the setControllerListener (ControllerListener controllerListener) method can add monitoring.

You can create a listener by ControllerListener controllerListener = new BaseControllerListener().

```java
frescoImageView.setControllerListener(controllerListener);
```

### Other

FrescoImageView is based on the Fresco package, so the usage of Fresco is also applicable to FrescoImageView.

Similarly, FrescoZoomImageView also supports the api and Fresco methods mentioned above.
