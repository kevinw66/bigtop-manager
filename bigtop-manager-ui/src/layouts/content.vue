<!--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
-->

<script setup lang="ts">
  import { HomeOutlined } from '@ant-design/icons-vue'
  import Chatbot from '@/components/chatbot/chatbot.vue'
</script>

<template>
  <a-layout-content class="container">
    <a-breadcrumb class="breadcrumb">
      <a-breadcrumb-item>
        <home-outlined />
      </a-breadcrumb-item>
      <a-breadcrumb-item
        v-for="(item, index) in $route.path.substring(1).split('/')"
        :key="index"
      >
        {{ item }}
      </a-breadcrumb-item>
    </a-breadcrumb>
    <div class="content">
      <router-view />
    </div>
    <chatbot />
  </a-layout-content>
</template>

<style scoped lang="scss">
  .container {
    margin: 0 1rem;
    min-height: auto;

    .breadcrumb {
      margin: 1rem 0;
    }

    .content {
      padding: 1rem;
      border-radius: 0.5rem;
      background: #fff;
    }
  }
</style>
