package com.wind.arch.ioc.runtime;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created By wind
 * on 2020-02-21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@AndroidEvent(
        setterListener = "setOnTouchListener",
        listenerClass = View.OnTouchListener.class
)
public @interface OnTouch {
    int [] value();
}
