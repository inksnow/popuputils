

[我的个人主页：www.inksnow.ink](www.inksnow.ink)


效果图：
![01](https://github.com/inksnow/popuputils/blob/master/11.jpg)  
![02](https://github.com/inksnow/popuputils/blob/master/22.jpg)  
![03](https://github.com/inksnow/popuputils/blob/master/001.gif)  
![04](https://github.com/inksnow/popuputils/blob/master/a1~3.gif)  

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

添加   implementation 'com.github.inksnow:popuputils:1.0.7'  

dependencies {  

    implementation fileTree(dir: 'libs', include: ['*.jar'])  
    
     ......  
     
    implementation 'com.github.inksnow:popuputils:1.0.7'  
    
}  

 //popupPrompt使用  

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
//PopupPrompt可设置的属性及其默认值  
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


//PopupSelect使用

```Java
                SelectSettings.Builder builder = new SelectSettings.Builder();
                SelectSettings selectSettings =
                        builder.selectListDataBean(selectListDataBeans)
                                .clickListener(selectBackListener)
                                .build();
                popupSelect.popupSelect(window,context,inflater,selectSettings,0);
                
                
                
                 
                builder = new SelectSettings.Builder();
                selectSettings =
                        builder.selectListDataBean(selectListDataBeans2)
                                .clickListener(selectBackListener)
                                .titleTextStr("请选择语言")
                                .titleIcon(getDrawable(R.drawable.l))
                                .titleTextPaddings(new int[]{10,20,0,20})
                                .showTitleIcon(true)
                                .multipleSelection(false)
                                .showListIcon(true)
                                .build();
                popupSelect.popupSelect(window,context,inflater,selectSettings,0);


  ```

 //popupSelect可设置的属性及其默认值  
  ```Java
          //确认按钮点击后的回调，或单选没设置确认按钮点击list选项的回调
        private  PopupSelect.onClickListener clickListener = null;
        //List 数据
        private List<SelectListDataBean> selectListDataBean = null;
        //是否可以多选
        private boolean multipleSelection = true;
 
        //PopupWindow的一些设置，默认点击外边PopupWindow消失
        //如果想设置点击外边不消失设置focusable = false ,outsideTouchable = false
        //在activity中拦截点击事件
        //     @Override
        //    public boolean dispatchTouchEvent(MotionEvent event){
        //        if (popupSelect.getpWindow()!= null && popupSelect.getpWindow().isShowing()){
        //            return false;
        //        }else{
        //            return super.dispatchTouchEvent(event);
        //        }
        //    }
        private boolean focusable = true;
        private boolean outsideTouchable = false;
        private boolean touchable = true;
        private int inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED;
        private int softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        //背景色，必须大于等于2个值，设置不同的多个值为渐变效果
        private int[] popupBg ={0XFFFFFFFF,0XFFFFFFFF};
        // 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
        private float[] popupRadius ={20,20,20,20,20,20,20,20};
        //Paddings 设置此值可以调节布局位置
        private int[] titleIconPaddings = {60,10,10,10};
        private int[] titleTextPaddings={80,20,20,20};
        private int[] listPaddings={20,10,20,10};
        private int[] buttonPaddings={20,20,20,20};
        //宽
        private int popupWidth =700;
        //高
        private int popupHeight=460;
        //标题图标
        private int titleIconWidth=140;
        private int titleIconHeight=100;
        //是否显示标题图标和文字布局
        private boolean showTitle = true;
        //标题布局背景
        private int[] titleBg ={0X00000000,0X00000000};
        //是否显示标题图标
        private boolean showTitleIcon = true;
        //标题图标资源
        private Drawable titleIcon = null;
        //是否显示标题文字
        private boolean showTitleText = true;
        //标题文字内容
        private String titleTextStr = "请设置标题";
        //标题文字颜色
        private int titleTextColor = 0XFF333333;
        //标题文字大小
        private int titleTextSize = 18;
        //标题文字位置
        private int titleTextGravity = Gravity.CENTER_VERTICAL;
        //是否显示右边滚动条
        private boolean scrollBarEnabled = true;
        //滚动条自动消失时间
        private int scrollBarFadeDuration = 5000;
        //滚动条大小
        private int scrollBarSize = 5;
        //滚动条style
        private int scrollBarStyle =View.SCROLLBARS_INSIDE_INSET ;
        //button1文字
        private String buttonTextStr1 = "取消";
        //button2文字
        private String buttonTextStr2 = "确定";
        //按钮字体大小
        private int buttonTextSize = 18;
        //按钮文字颜色
        private int buttonTextColor1=0XFF03a9f4;
        private int buttonTextColor2=0XFF03a9f4;
        //是否显示该按钮
        private boolean showButton1 = true;
        private boolean showButton2 = true;
        //标题下分割线颜色
        private int titleDividingColor = 0X33AAAAAA;
        //按钮分割线颜色
        private int buttonDividingColor= 0X33AAAAAA;
        //每一条list高度
        private int listHeight = 100;
        //list选框高宽
        private int listSelectImageHeight = 80;
        private int listSelectImageWidth = 110;
        //list 图标高宽
        private int listIconHeight = 80;
        private int listIconWidth = 80;
        //list文字大小
        private int listTextSize = 16;
        //paddings
        private int[] listSelectImagePaddings={60,10,10,10};
        private int[] listIconPaddings={20,10,10,10};
        private int[] listTextPaddings={10,10,10,10};
        //选中选框
        private Drawable listSelectImageOn = null;
        //未选中选框
        private Drawable listSelectImageOff = null;
        private Drawable listIcon = null;
        //list文字颜色
        private int listTextColor = 0XFF333333;
        //list选中后文字颜色
        private int listTextSelectColor = 0XFF03a9f4;
        //list文字位置
        private int listTextGravity = Gravity.CENTER_VERTICAL;
        //是否显示选框
        private boolean showListSelectImage = true;
        //是否显示图标
        private boolean showListIcon = true;
        //list分割线
        private  Drawable listDivider = null;
        //list分割线高度
        private  int listDividerHeight = 1;
        //背景透明度
        private float bgAlpha = 0.6f;

  ```
