package com.wind.arch.ioc.runtime;

import android.app.Activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created By wind
 * on 2020-02-21
 */
public class Injects {


    public static void inject(Activity activity) {

        injectLayout(activity);

        injectViews(activity);
        injectEvents(activity);
    }

    private static void injectEvents(Activity activity) {
        try {
            Class<?> activityClass = activity.getClass();
            //获取所有public的方法，包括父类的
            Method [] methods=activityClass.getMethods();
            for (Method method : methods) {
                //获取方法上的注解
                Annotation [] annotations=method.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<?> annotationClass=annotation.getClass();
                    //annotationType()返回的是注解接口  之后在直接得到注解接口对应的方法
                    Class<? extends Annotation> annotationType=annotation.annotationType();
                    //查询该注解之上是否有@AndroidEvent注解
                    AndroidEvent eventAnnotation=annotationType.getAnnotation(AndroidEvent.class);
                    if (eventAnnotation!=null){
                        //事件执行的类的类型
                        Class<?> listenerClass=eventAnnotation.listenerClass();
                        //事件注册函数
                        String setterListener=eventAnnotation.setterListener();


                        //1. 如何获取事件源 通过反射注解接口中的value()方法，得到viewIds
                        Method valueMethod=annotationType.getDeclaredMethod("value");
                        int []values= (int[]) valueMethod.invoke(annotation);
                        for (int viewId : values) {
                            Method findViewByIdMethod=activityClass.getMethod("findViewById",int.class);
                            Object view=findViewByIdMethod.invoke(activity,viewId);

                            //反射获取view的setterListener方法
                            if (view!=null){
                                Method setterListenerMethod=view.getClass().getMethod(setterListener,listenerClass);
                                //2. 获取监听器实例
                                //如何获取setterListener方法的参数  比如setOnClickLiseter(clickListener) 如何获取clickListener
                                //clickListener 是用户编写的，我们无法得到，但是我们拿到的是具体执行click的method。考虑使用动态代理，
                                //用动态代理获得的实例去执行具体执行click的method
                                Object proxy=Proxy.newProxyInstance(view.getClass().getClassLoader(),
                                        new Class[]{listenerClass},new EventInvocationHandler(activity,method));

                                //3. 注册监听器
                                setterListenerMethod.invoke(view,proxy);
                            }

                        }


                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void injectViews(Activity activity) {
        try {
            Class<?> activityClass = activity.getClass();
            //1. 获取activity中的所有字段
            Field []fields=activityClass.getDeclaredFields();

            for (Field field : fields) {
                //2. 寻找打了@BindView注解的字段，这些是需要注入的View
                BindView bindViewAnnotation=field.getAnnotation(BindView.class);

                if (bindViewAnnotation!=null){
                    //@BindView注解的value()返回的就是viewId
                    int viewId=bindViewAnnotation.value();
                    //3. 反射获取findViewById方法
                    Method findViewByIdMethod=activityClass.getMethod("findViewById",int.class);
                    //执行findViewById方法
                    Object view=findViewByIdMethod.invoke(activity,viewId);
                    //4. 将view设置给需要注入的field
                    field.set(activity,view);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void injectLayout(Activity activity) {
        try {
            Class<?> activityClass = activity.getClass();
            //获取当前 actiivty上的@Layout注解
            Layout layoutAnnotation = activityClass.getAnnotation(Layout.class);
            if (layoutAnnotation != null) {
                int layoutRes = layoutAnnotation.value();
                //需要调用activity 的 setContentView方法
                /**
                 * getDeclaredMethod：获取当前类中的所有声明的方法，包括public、protected和private修饰的方法。需要注意的是，这些方法一定是在当前类中声明的，从父类中继承的不算，实现接口的方法由于有声明所以包括在内。
                 * getMethod：获取当前类和父类的所有public的方法。这里的父类，指的是继承层次中的所有父类。
                 */
                //这里若使用getDeclaredMethod 方法将无法获得setContentView方法
                Method setContentViewMethod=activityClass.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(activity,layoutRes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
