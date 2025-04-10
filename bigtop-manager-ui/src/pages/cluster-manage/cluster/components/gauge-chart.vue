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
  import { onBeforeUnmount, onMounted, shallowRef } from 'vue'
  import * as echarts from 'echarts/core'
  import { GaugeChart, GaugeSeriesOption } from 'echarts/charts'
  import { CanvasRenderer } from 'echarts/renderers'

  echarts.use([GaugeChart, CanvasRenderer])

  type EChartsOption = echarts.ComposeOption<GaugeSeriesOption>

  const props = defineProps<{
    chartId: string
    title: string
  }>()

  const myChart = shallowRef<echarts.ECharts | null>(null)

  const initCharts = () => {
    const chartDom = document.getElementById(`${props.chartId}`)
    myChart.value = echarts.init(chartDom, null, {
      devicePixelRatio: window.devicePixelRatio
    })
    const option: EChartsOption = {
      series: [
        {
          type: 'gauge',
          radius: '90%',
          center: ['50%', '50%'],
          axisLine: {
            lineStyle: {
              width: 14,
              color: [
                [0.3, '#67e0e3'],
                [0.7, '#37a2da'],
                [1, '#fd666d']
              ]
            }
          },
          pointer: {
            itemStyle: {
              color: 'auto'
            }
          },
          axisTick: {
            distance: -50,
            length: 40,
            lineStyle: {
              color: '#fff',
              width: 1
            }
          },
          splitLine: {
            distance: -28,
            length: 28,
            lineStyle: {
              color: '#fff',
              width: 4
            }
          },
          axisLabel: {
            color: 'inherit',
            distance: 28,
            fontSize: 14
          },
          detail: {
            valueAnimation: true,
            formatter: '{value} %',
            color: 'inherit',
            fontSize: 18
          },
          data: [
            {
              value: 80
            }
          ]
        }
      ]
    }

    myChart.value?.setOption<echarts.EChartsCoreOption>({
      series: [
        {
          data: [
            {
              value: +(Math.random() * 100).toFixed(2)
            }
          ]
        }
      ]
    })

    option && myChart.value.setOption(option)
  }

  const resizeChart = async () => {
    setTimeout(() => {
      myChart.value?.resize()
    })
  }

  onMounted(() => {
    initCharts()
    window.addEventListener('resize', resizeChart, true)
  })

  onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeChart, true)
  })
</script>

<template>
  <div class="chart">
    <div class="chart-title">{{ $props.title }}</div>
    <div :id="$props.chartId" style="height: 260px; width: 100%"></div>
  </div>
</template>

<style lang="scss" scoped>
  .chart {
    height: 300px;
    box-sizing: border-box;
    padding: 10px;
    &-title {
      text-align: start;
      font-size: 12px;
      font-weight: 600;
    }
  }
</style>
