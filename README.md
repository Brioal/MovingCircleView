# MovingCircleView\
##TC App加速效果
![](https://github.com/Brioal/MovingCircleView/blob/master/art/2.gif)
##本组件实现效果:
![](https://github.com/Brioal/MovingCircleView/blob/master/art/1.gif)
#写在前面:
##如果觉得效果还行请顺手点个sta支持一下r,谢谢
##欢迎加入我创建的QQ交流群,群号:375276053
##另外感谢群友李通同学的建议,写这个自定义View也学到了一些东西
##另外的开源库:
##[多达288种动画效果定制的侧滑菜单库](https://github.com/Brioal/SwipeMenuDemo)
##[仿360底部动画菜单布局](https://github.com/Brioal/BottomTabLayout/)
##欢迎查看与star
##下面介绍本项目
##使用方法:
##1.xml布局中添加组件
###注:至少给宽或者高设置确定的值,不能同时设置为wrap_content,否则报错
```
    <com.brioal.movingview.view.MovingDotView
        android:id="@+id/main_movingView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />

```
##2.Activity中获取实例,设置初始的进度,和点击时候要变到的进度
```
 	mMovingDotView = (MovingDotView) findViewById(R.id.main_movingView);
        mMovingDotView.setProgress(50);
        mMovingDotView.setToProgress(10);
```
###如果需要对动画的执行过程设置监听
```
mMovingDotView.setChangeListener(new OnAnimatorChangeListener() {
            @Override
            public void onProgressChanged(float progress) {
                //progress 范围从 0 ~ 1.0f
            }
        });
```
###可以在合适的地方设置返回效果,例如当点击返回键的时候
###注:如果返回的时候不设置progress,则返回的是刚开始设置的progress
###比如原本50 , 执行动画变化到10 ,如果返回的时候不设置progress,则返回动画执行后会显示50
```
@Override
    public void onBackPressed() {
        if (mMovingDotView.isCleaned()) {
            mMovingDotView.backClean();
        } else {
            super.onBackPressed();
        }
    }
```
##很多可定制的属性还有很多,包括设置动画持续时间,文字颜色,背景颜色,小圆点数量等等,如下表
代码内方法|xml属性|效果
:--|:--|:--
`void setDotsCount(int dotsCount)`|`md_dot_count`|设置屏幕上小圆点总数
`void setCenterDotRadius(int centerDotRadius)`|`md_center_dot_radius`|中心大圆点的半径
`void setCenterDotRes(Drawable centerDotRes)`|`md_center_dot_back`|中心大圆点的背景资源文件
`void setDotColor(int dotColor)`|`md_dot_color`|小圆点的颜色
`void setMaxDotRadius(int maxDotRadius)`|`md_dot_max_radius`|小圆点的最大半径
`void setMinDotRadius(int minDotRadius)`|`md_dot_min_radius`|最小小圆点半径
`void setMaxDotSpeed(int maxDotSpeed)`|`md_dot_max_speed`|小圆点的最大速度(1~10)
`void setMinDotSpeed(int minDotSpeed)`|`md_dot_min_speed`|小圆点的最小速度(1~10)
`void setTextSize(int textSize)`|`md_text_size`|显示进度的文字大小
`void setTextColor(int textColor)`|`md_text_color`|显示进度的文字颜色
`void setBtnTextColor(int btnTextColor)`|`md_btn_text_color`|按钮文字颜色
`void setAnimatorDuration(long animatorDuration)`|`md_animator_duration`|动画持续的时间

##另外一点需要注意的地方(希望都读完,自定义效果的时候比较重要):
###1.小圆点的最小速度即为不加速时候的移动速度,建议设置为1
###2.小圆点的最大速度为加速动画结束后的小圆点的移动速度,建议一般为7左右
###3.中心大圆点的半径默认为组件宽度的1/3,默认看起来还是比较协调的,可不设置
###4.默认的组件背景为白色,需要设置的话只要像给其他组件设置背景颜色那样设置即可,图片讲道理也是可以的,不过我没试过
###5.中心圆点的背景资源如果要设置的话尽量用shapedrawable做成圆角角度与宽高一样的效果,意思就是中心的组件其实是正方形的,只是设置的资源文件是圆形而已,这一点自定义的时候不是很好控制,如果需要改变中心颜色的同学得注意.




#在项目中添加此组件的方式:
##Step 1. 项目的build.gradle文件做如下修改
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
	
```

##Step 2. 添加依赖
```
	dependencies {
	        compile 'com.github.Brioal:MovingCircleView:1.0'
	}
	
```
