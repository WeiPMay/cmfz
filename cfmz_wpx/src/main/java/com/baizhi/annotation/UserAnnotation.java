package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//声明自定义注解使用的时机,我们需要在运行时使用
@Retention(RetentionPolicy.RUNTIME)
//声明自定义注解使用的地方
@Target(ElementType.FIELD)
public @interface UserAnnotation {
    public String name();
}
