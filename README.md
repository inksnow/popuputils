

[我的个人主页：www.inksnow.ink](www.inksnow.ink)


效果图：
![01](https://github.com/inksnow/popuputils/blob/master/11.jpg)  
![02](https://github.com/inksnow/popuputils/blob/master/001.gif)  

使用方法：<br>  

build.gradle (Project)中  

添加   maven { url 'https://jitpack.io' }  

allprojects {  

    repositories {  
    
        jcenter()  
        
        maven { url 'https://jitpack.io' }  
        
    }  
    
}  

build.gradle (app)中  

添加   implementation 'com.github.inksnow:popuputils:1.0.6'  

dependencies {  

    implementation fileTree(dir: 'libs', include: ['*.jar'])  
    
     ......  
     
    implementation 'com.github.inksnow:popuputils:1.0.6'  
    
}  

 //使用  

```Java
  PromptSettings.Builder builder = new PromptSettings.Builder();  
  
  PromptSettings promptSettings = builder.build();  
  
  popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);  
  ```
  
  //自定义效果  
  ```Java
PromptSettings.Builder builder = new PromptSettings.Builder(); 
builder = new PromptSettings.Builder();
builder.bgAlpha(0.6f)
        .duration(2000)
        .bgColour(new int[]{0XFFFF0000,  0XFFFFFFFF})
        .showImage(true)
        .showMode(PromptSettings.MODE_SHOW_IMAGE)
        .image(getDrawable(R.drawable.ic_launcher_foreground))
        .imageWidth(160)
        .imageAnim(anim)
        .buttonColour(0XFFFF0000)
        .popupAnim(R.style.popup_bottom_top)
        .location(Gravity.BOTTOM);
 promptSettings = builder.build();
popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
```
//可设置的属性及其默认值  
  ```Java
        //宽
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        //高
        private int height = 160;
        //位置
        private int location = Gravity.TOP;
        private boolean focusable = false;
        private boolean outsideTouchable = false;
        private boolean touchable = true;
        private int inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED;
        private int softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        //弹窗miss监听
        private PopupWindow.OnDismissListener dismissListener = null;
        //右侧按键监听
        private PopupPrompt.onClickListener clickListener = null;
        //圆角 （必须是8个值）
        private  float[] radius={0,0,0,0,0,0,0,0};
        //背景色（渐变色数组，必须大于等于2个值，如果是单色就用2个一样的颜色）
        private int[] bgColour = {0XFF03a9f4,0XFF03a9f4};
        //ProgressBar 颜色
        private int proColour = 0XFFFFFFFF;
        //文字颜色
        private int textColour = 0XFFFFFFFF;
        //按钮文字颜色
        private int buttonColour = 0XFFFFFFFF;
        //文字和按钮字体大小
        private   float textSize = 18;
        //显示左边的图片
        private Drawable image = null;
        //显示左边的图片动画
        private Animation imageAnim = null;
        //提示文字
        private String text = "这是一个提示框!";
        //按钮文字
        private String buttonText = "好的";
        //是否显示文字
        private boolean showText = true;
        //是否显示按钮
        private boolean showButton = true;
        //是否显示左边Pro 或 图片
        private boolean showImage = false;
        //图片宽度
        private int imageWidth = 120;
        //Padding
        private int[] imagePaddings = {20, 20, 20, 20};
        private int[] buttonPaddings = {20, 20, 20, 20};
        private int[] textPaddings = {0, 0, 0, 0};
        //显示图片还是ProgressBar
        private int showMode = MODE_SHOW_PRO;
        //自动消失时间，小于等于0 不自动消失
        private int duration = 3000;
        //背景透明度
        private float bgAlpha = 1.0f;
        //text左  右  中
        private int textGravity = Gravity.CENTER_VERTICAL;
        //弹窗动画，默认style.popup_fade_in_out 写好的有 popup_top_down popup_bottom_top popup_left_right
        //可以传入自己的动画
        private int popupAnim = 0;
        //X  Y 坐标的偏移值，根据此值可以自定义位置
        private int gravityX = 0;
        private int gravityY = 0;

  ```


 

