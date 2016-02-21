/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//Code cited: https://github.com/googlesamples/android-testing/blob/master/ui/espresso/CustomMatcherSample/app/src/androidTest/java/com/example/android/testing/espresso/CustomMatcherSample/HintMatcher.java
package com.cs428.dit.diabetestracker;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.is;


/**
 * A custom matcher that checks the error property of an {@link android.widget.EditText}. It
 * accepts either a {@link String} or a {@link org.hamcrest.Matcher}.
 */
public class ErrorMatcher {

    static Matcher<View> withError(final String substring) {
        return withError(is(substring));
    }

    static Matcher<View> withError(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public boolean matchesSafely(EditText view) {
                final CharSequence errorText = view.getError();
                return errorText != null && stringMatcher.matches(errorText.toString());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: ");
                stringMatcher.describeTo(description);
            }
        };
    }
}
