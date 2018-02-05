# MockWEIBOLikeView
仿微博点赞动画
1.拇指滑动放缩效果
2.水波纹效果
3.+1文字弹射效果.
<br>
![sample.gif](https://github.com/zhaokidd/MockWEIBOLikeView/blob/master/sample.gif)
<br>
已上传到maven和jcenter,使用gradle集成该仓库请在 build.gradle文件中添加
```gradle
compile 'com.zy:weibolikeview+'
```
当前最新版本为 1.0.4

### 用法

#### xml文件中声明
在xml文件中声明：注意控件中各个主要view的宽高均取决于__layout_width__，而设置__layout_height__需要注意高度不能太低否则动画会无法完全显示
```xml
    <com.android.zy.weibolikeanimview.view.WeiboLikeAnimView
        android:id="@+id/weiboLikeAnimView"
        android:layout_width="35dp"
        android:layout_height="65dp"
        android:onClick="onClick"
        app:likeview_isLiked="false"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.5"/>
```

#### 代码中使用
只需要调用__startAnim()__方法即可.

```java
    private WeiboLikeAnimView mLikeAnimView;
```

```java
    mLikeAnimView.startAnim();
```