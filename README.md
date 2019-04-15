

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
  

