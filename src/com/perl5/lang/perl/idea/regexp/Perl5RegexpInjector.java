/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.regexp;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtilCore;
import com.perl5.lang.perl.psi.impl.PsiPerlPerlRegexImpl;
import org.jetbrains.annotations.NotNull;

import static com.perl5.lang.perl.lexer.PerlElementTypesGenerated.REGEX_TOKEN;

/**
 * Created by hurricup on 30.11.2016.
 */
public class Perl5RegexpInjector implements LanguageInjector
{
	@Override
	public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost host, @NotNull InjectedLanguagePlaces injectionPlacesRegistrar)
	{

		if (host instanceof PsiPerlPerlRegexImpl && ((PsiPerlPerlRegexImpl) host).isMatchRegexp())
		{
			int[] sourceOffset = new int[]{0};
			host.acceptChildren(new PsiElementVisitor()
			{
				@Override
				public void visitElement(PsiElement element)
				{
					if (PsiUtilCore.getElementType(element) == REGEX_TOKEN)
					{
						injectionPlacesRegistrar.addPlace(Perl5RegexpLanguage.INSTANCE, TextRange.from(sourceOffset[0], element.getTextLength()), null, null);
						sourceOffset[0] += element.getTextLength();
					}
					else
					{
						sourceOffset[0] += element.getTextLength();
					}
				}
			});
		}

	}
}
