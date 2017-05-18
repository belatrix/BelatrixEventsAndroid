package com.belatrix.events.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by dvelasquez on 2/23/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UIScope {
}
