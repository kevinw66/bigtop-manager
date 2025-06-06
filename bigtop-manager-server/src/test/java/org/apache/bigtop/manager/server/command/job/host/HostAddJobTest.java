/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.server.command.job.host;

import org.apache.bigtop.manager.server.command.job.JobContext;
import org.apache.bigtop.manager.server.command.stage.HostCheckStage;
import org.apache.bigtop.manager.server.command.stage.SetupJdkStage;
import org.apache.bigtop.manager.server.command.stage.Stage;
import org.apache.bigtop.manager.server.command.stage.StageContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HostAddJobTest {

    @Mock
    private HostAddJob hostAddJob;

    @Test
    public void testCreateStages() {
        JobContext jobContext = new JobContext();
        List<Stage> stages = new ArrayList<>();
        ReflectionTestUtils.setField(hostAddJob, "jobContext", jobContext);
        ReflectionTestUtils.setField(hostAddJob, "stages", stages);

        MockedConstruction<HostCheckStage> mocked1 = mockConstruction(HostCheckStage.class);
        MockedConstruction<SetupJdkStage> mocked2 = mockConstruction(SetupJdkStage.class);
        MockedStatic<StageContext> mocked3 = mockStatic(StageContext.class);

        when(StageContext.fromCommandDTO(any())).thenReturn(new StageContext());

        doCallRealMethod().when(hostAddJob).createStages();
        hostAddJob.createStages();

        assertEquals(1, mocked1.constructed().size());
        assertEquals(1, mocked2.constructed().size());
        assertEquals(2, stages.size());
        assertTrue(stages.containsAll(mocked1.constructed()));
        assertTrue(stages.containsAll(mocked2.constructed()));

        mocked1.close();
        mocked2.close();
        mocked3.close();
    }

    @Test
    public void testGetName() {
        doCallRealMethod().when(hostAddJob).getName();
        assertEquals("Add hosts", hostAddJob.getName());
    }
}
