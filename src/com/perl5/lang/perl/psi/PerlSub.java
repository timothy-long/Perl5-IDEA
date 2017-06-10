/*
 * Copyright 2015-2017 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.psi;

import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import org.jetbrains.annotations.Nullable;

public interface PerlSub extends PerlDeprecatable {
  /**
   * Returns package name for current function
   *
   * @return canonical package name from declaration or context
   */
  String getPackageName();

  /**
   * Returns function name for current function definition
   *
   * @return function name or null
   */
  String getSubName();

  /**
   * Checks PSI tree before a sub definition for annotations and builds annotations object
   *
   * @return PerlSubAnnotation object
   */
  @Nullable
  PerlSubAnnotations getAnnotations();

  /**
   * Returns canonical name PackageName::SubName
   *
   * @return name
   */
  String getCanonicalName();

  /**
   * Checks if sub defined as a method
   *
   * @return result
   */
  boolean isMethod();


  /**
   * Checks if sub defined as static, default implementation returns !isMethod(), but may be different for constants for example
   *
   * @return true if sub is static
   */
  boolean isStatic();

  /**
   * Checks if current declaration/definition is XSub
   *
   * @return true if sub located in deparsed file
   */
  boolean isXSub();


  @Nullable
  default String getReturns() {
    PerlSubAnnotations subAnnotations = getAnnotations();
    return subAnnotations != null ? subAnnotations.getReturns() : null;
  }

  default boolean isDeprecated() {
    PerlSubAnnotations subAnnotations = getAnnotations();
    return subAnnotations != null && subAnnotations.isDeprecated();
  }

}

