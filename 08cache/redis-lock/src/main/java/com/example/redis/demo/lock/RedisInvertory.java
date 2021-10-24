/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.redis.demo.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * Redis 分布式计数器
 *
 */
public class RedisInvertory {

    private enum EnumSingleton {
        /**
         * 懒汉枚举单例
         */
        INSTANCE;
        private RedisInvertory instance;

        EnumSingleton(){
            instance = new RedisInvertory();
        }
        public RedisInvertory getSingleton(){
            return instance;
        }
    }

    public static RedisInvertory getInstance(){
        return EnumSingleton.INSTANCE.getSingleton();
    }

    private JedisPool jedisPool = new JedisPool();

    /**
     * 初始化库存
     * @param key
     * @param amount
     * @return
     */
    public boolean init(String key, int amount) {
        try(Jedis jedis = jedisPool.getResource()) {
            return "OK".equals(jedis.set(key, Integer.toString(amount)));
        }
    }

    /**
     * 减库存
     * @param key
     * @return
     */
    public boolean reduce(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            return "OK".equals(jedis.decr(key));
        }
    }

    /**
     * 获取库存
     * @param key
     * @return
     */
    public int get(String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            return Integer.valueOf(jedis.get(key));
        }
    }
}
