package com.digitalnusantarastudio.bakingapp.imageMatcher;

import android.view.View;

import org.hamcrest.Matcher;

//https://github.com/dbottillo/Blog/blob/espresso_match_imageview/app/src/androidTest/java/com/danielebottillo/blog/config/EspressoTestsMatchers.java
public class EspressoTestMatcher {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(DrawableMatcher.EMPTY);
    }

    public static Matcher<View> hasDrawable() {
        return new DrawableMatcher(DrawableMatcher.ANY);
    }
}