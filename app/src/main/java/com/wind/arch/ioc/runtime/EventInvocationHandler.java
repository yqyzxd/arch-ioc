package com.wind.arch.ioc.runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created By wind
 * on 2020-02-21
 */
public class EventInvocationHandler implements InvocationHandler {

    private Object mTarget;
    private Method mTargetMethod;

    public EventInvocationHandler(Object target,Method targetMethod){
        this.mTarget=target;
        this.mTargetMethod=targetMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return mTargetMethod.invoke(mTarget,args);
    }
}
