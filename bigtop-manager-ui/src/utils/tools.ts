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

import dayjs from 'dayjs'
import utc from 'dayjs/plugin/utc'
import timezone from 'dayjs/plugin/timezone'

dayjs.extend(utc)
dayjs.extend(timezone)

export const copyText = (text: string): Promise<any> => {
  if (navigator.clipboard) {
    return navigator.clipboard.writeText(text)
  }
  return new Promise(async (resolve, reject) => {
    try {
      const { default: ClipboardJS } = await import('clipboard')
      if (!ClipboardJS.isSupported()) {
        reject(new Error('ClipboardJS not support!'))
        return
      }
      const btn = document.createElement('button')
      btn.innerText = text
      const clipboard = new ClipboardJS(btn, {
        text: () => text
      })
      clipboard.on('success', () => {
        resolve(true)
        clipboard.destroy()
      })
      clipboard.on('error', (err) => {
        reject(err)
        clipboard.destroy()
      })
      btn.click()
    } catch (error) {
      console.log('copytext :>> ', error)
    }
  })
}

export const usePngImage = (imageName: string): string => {
  return new URL(`../assets/images/${imageName}.png`, import.meta.url).href
}

export const scrollToBottom = (container: HTMLElement | null) => {
  if (!container) {
    return
  }
  requestAnimationFrame(() => {
    container.scrollTop = container.scrollHeight
  })
}

export const getRandomFromTimestamp = (len: number = 6) => {
  return Date.now().toString().slice(-len)
}

export const formatTime = (time: string | undefined, formatRule: string = 'YYYY-MM-DD HH:mm:ss') => {
  return typeof time === 'string' && dayjs(time).tz(dayjs.tz.guess(), true).format(formatRule)
}

export const generateRandomId = (length = 8) => {
  return Math.random()
    .toString(36)
    .substring(2, length + 2)
}

export const pick = <T extends object, K extends keyof T>(obj: T, keys: K[]): Pick<T, K> => {
  return keys.reduce(
    (acc, key) => {
      if (obj.hasOwnProperty(key)) {
        acc[key] = obj[key]
      }
      return acc
    },
    {} as Pick<T, K>
  )
}

type Data = { [key: string]: any }
type Result = { [key: string]: any | undefined }

export const processData = (data: Data): Result => {
  const result: Result = {}
  if (!data) {
    return result
  }
  for (const [key, value] of Object.entries(data)) {
    if (value === null) {
      result[key] = undefined
    } else if (Array.isArray(value)) {
      result[key] = value[0] || undefined
    } else {
      result[key] = value
    }
  }
  return result
}
