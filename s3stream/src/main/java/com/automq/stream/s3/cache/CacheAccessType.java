/*
 * Copyright 2024, AutoMQ HK Limited.
 *
 * Use of this software is governed by the Business Source License
 * included in the file BSL.md
 *
 * As of the Change Date specified in that file, in accordance with
 * the Business Source License, use of this software will be governed
 * by the Apache License, Version 2.0
 */

package com.automq.stream.s3.cache;

public enum CacheAccessType {
    DELTA_WAL_CACHE_HIT,
    BLOCK_CACHE_HIT,
    BLOCK_CACHE_MISS,
}
