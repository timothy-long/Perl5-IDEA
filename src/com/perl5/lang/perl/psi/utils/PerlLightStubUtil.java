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

package com.perl5.lang.perl.psi.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubBase;
import com.perl5.lang.perl.psi.PerlDelegatingLightNamedElement;
import org.jetbrains.annotations.NotNull;

public class PerlLightStubUtil {
  @NotNull
  public static PerlDelegatingLightNamedElement createPsiElement(@NotNull StubBase stubBase, @NotNull PsiElement delegate) {
    throw new IllegalArgumentException("Don't know how to create psi from " + stubBase);
  }

  @NotNull
  public static StubBase createStub(@NotNull PerlDelegatingLightNamedElement lightNamedElement) {
    throw new IllegalArgumentException("Don't know how to create stub from " + lightNamedElement);
  }
}
