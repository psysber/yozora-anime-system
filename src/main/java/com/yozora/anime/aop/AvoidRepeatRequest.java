package com.yozora.anime.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface AvoidRepeatRequest {

    int intervalTime() default 5;

    String msg() default "技能放太快了,人家受不了了,请休息一下";
}
