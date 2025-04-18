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
package org.apache.bigtop.manager.server.controller;

import org.apache.bigtop.manager.dao.query.ServiceQuery;
import org.apache.bigtop.manager.server.model.vo.PageVO;
import org.apache.bigtop.manager.server.model.vo.ServiceVO;
import org.apache.bigtop.manager.server.service.ServiceService;
import org.apache.bigtop.manager.server.utils.MessageSourceUtils;
import org.apache.bigtop.manager.server.utils.ResponseEntity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private ServiceController serviceController;

    private MockedStatic<MessageSourceUtils> mockedMessageSourceUtils;

    @BeforeEach
    void setUp() {
        mockedMessageSourceUtils = Mockito.mockStatic(MessageSourceUtils.class);
        when(MessageSourceUtils.getMessage(any())).thenReturn("Mocked message");
    }

    @AfterEach
    void tearDown() {
        mockedMessageSourceUtils.close();
    }

    @Test
    void listReturnsAllServices() {
        ServiceQuery query = new ServiceQuery();
        PageVO<ServiceVO> services = new PageVO<>();
        when(serviceService.list(any())).thenReturn(services);

        ResponseEntity<PageVO<ServiceVO>> response = serviceController.list(query);

        assertTrue(response.isSuccess());
        assertEquals(services, response.getData());
    }

    @Test
    void getReturnsServiceById() {
        Long id = 1L;
        Long clusterId = 1L;
        ServiceVO service = new ServiceVO();
        when(serviceService.get(id)).thenReturn(service);

        ResponseEntity<ServiceVO> response = serviceController.get(clusterId, id);

        assertTrue(response.isSuccess());
        assertEquals(service, response.getData());
    }

    @Test
    void getReturnsNotFoundForInvalidId() {
        Long id = 999L;
        Long clusterId = 1L;
        when(serviceService.get(id)).thenReturn(null);

        ResponseEntity<ServiceVO> response = serviceController.get(clusterId, id);

        assertTrue(response.isSuccess());
        assertNull(response.getData());
    }
}
