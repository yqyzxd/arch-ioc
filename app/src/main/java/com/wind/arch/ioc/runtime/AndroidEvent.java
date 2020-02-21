package com.wind.arch.ioc.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created By wind
 * on 2020-02-21
 *
 * 注册 android 事件 需要
 * 1. 事件源
 * 2. 注册方法
 * 3. 注册监听类
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AndroidEvent {
    String setterListener();
    Class listenerClass();
}
