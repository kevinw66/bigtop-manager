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
  import { computed, ref, shallowRef } from 'vue'
  import { useI18n } from 'vue-i18n'
  import { useRouter } from 'vue-router'
  import Service from './service.vue'
  import Job from '@/components/job/index.vue'

  import type { TabItem } from '@/components/common/main-card/types'
  import type { GroupItem } from '@/components/common/button-group/types'
  import type { ClusterVO } from '@/api/cluster/types'

  const { t } = useI18n()
  const router = useRouter()
  const activeKey = ref('1')
  const currCluster = shallowRef<ClusterVO>({
    id: 0
  })

  const tabs = computed((): TabItem[] => [
    {
      key: '1',
      title: t('common.service')
    },
    {
      key: '2',
      title: t('common.job')
    }
  ])

  const actionGroup = computed<GroupItem[]>(() => [
    {
      shape: 'default',
      type: 'primary',
      text: t('common.add', [t('common.service')]),
      clickEvent: () => addService && addService()
    }
  ])

  const getCompName = computed(() => {
    const components = [Service, Job]
    return components[parseInt(activeKey.value) - 1]
  })

  const addService: GroupItem['clickEvent'] = () => {
    router.push({ name: 'CreateInfraService', params: { id: 0, creationMode: 'public' } })
  }
</script>

<template>
  <div>
    <header-card :title="$t('menu.infra')" :show-avatar="false" :desc="$t('infra.info')" :action-groups="actionGroup" />
    <main-card v-model:active-key="activeKey" :tabs="tabs">
      <template #tab-item>
        <keep-alive>
          <component :is="getCompName" v-bind="currCluster"></component>
        </keep-alive>
      </template>
    </main-card>
  </div>
</template>

<style lang="scss" scoped></style>
