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

package com.perl5.lang.perl.idea.run.debugger;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.perl5.lang.perl.idea.execution.PerlCommandLine;
import com.perl5.lang.perl.idea.execution.PortMapping;
import com.perl5.lang.perl.idea.run.PerlRunConfiguration;
import com.perl5.lang.perl.idea.sdk.host.PerlHostData;
import com.perl5.lang.perl.idea.sdk.host.PerlHostHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * For execution and debugging
 */
public class PerlDebugProfileState extends PerlDebugProfileStateBase {
  public static final String DEBUG_ARGUMENT = "-d:Camelcadedb";
  public static final String PERL5_DEBUG_HOST = "PERL5_DEBUG_HOST";
  public static final String PERL5_DEBUG_PORT = "PERL5_DEBUG_PORT";
  public static final String PERL5_DEBUG_ROLE = "PERL5_DEBUG_ROLE";
  @Nullable
  private PerlHostData myHostData;

  public PerlDebugProfileState(ExecutionEnvironment environment) {
    super(environment);
  }

  @NotNull
  private PerlHostData getHostData() {
    return Objects.requireNonNull(myHostData);
  }

  @NotNull
  @Override
  protected ProcessHandler startProcess() throws ExecutionException {
    ProcessHandler process = super.startProcess();
    RunProfile runProfile = getEnvironment().getRunProfile();
    if (runProfile instanceof PerlRunConfiguration) {
      myHostData = PerlHostData.notNullFrom(((PerlRunConfiguration)runProfile).getEffectiveSdk());
    }
    else {
      myHostData = PerlHostHandler.getDefaultHandler().createData();
    }
    return process;
  }

  @NotNull
  @Override
  protected List<String> getPerlParameters(PerlRunConfiguration runProfile) throws ExecutionException {
    List<String> result = new ArrayList<>(super.getPerlParameters(runProfile));
    result.add(0, DEBUG_ARGUMENT);
    return result;
  }

  @NotNull
  @Override
  protected PerlCommandLine customizeCommandLine(@NotNull PerlCommandLine commandLine) throws ExecutionException {
    return super.customizeCommandLine(commandLine).withPortMappings(PortMapping.create(getDebugPort()));
  }

  @Override
  protected Map<String, String> calcEnv(PerlRunConfiguration runProfile) throws ExecutionException {
    Map<String, String> stringStringMap = new HashMap<>(super.calcEnv(runProfile));
    PerlDebugOptions debugOptions = getDebugOptions();
    stringStringMap.put(PERL5_DEBUG_ROLE, debugOptions.getPerlRole());
    stringStringMap.put(PERL5_DEBUG_HOST, debugOptions.getDebugHost());
    stringStringMap.put(PERL5_DEBUG_PORT, String.valueOf(getDebugPort()));
    return stringStringMap;
  }

  public String mapPathToRemote(@NotNull String localPath) {
    String remotePath = getHostData().getRemotePath(localPath);
    return remotePath == null ? localPath : remotePath;
  }

  @NotNull
  public String mapPathToLocal(@NotNull String remotePath) {
    String localPath = getHostData().getLocalPath(remotePath);
    return localPath == null ? remotePath : localPath;
  }
}
