package com.leeknow.summapp.module.interfaces;

import com.leeknow.summapp.module.enums.ModuleEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleChecker {

    ModuleEnums[] value();
}
