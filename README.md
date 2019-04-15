

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

添加   implementation 'com.github.inksnow:Inkslibrary:1.0.2'  

dependencies {  

    implementation fileTree(dir: 'libs', include: ['*.jar'])  
    
     ......  
     
    implementation 'com.github.inksnow:Inkslibrary:1.0.2'  
    
}  
