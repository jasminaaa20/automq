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

package com.automq.stream.s3.cache.blockcache;

import com.automq.stream.s3.ObjectReader;
import com.automq.stream.s3.metadata.S3ObjectMetadata;
import com.automq.stream.s3.operator.ObjectStorage;

public interface ObjectReaderFactory {
    ObjectReader get(S3ObjectMetadata metadata);
    ObjectStorage getObjectStorage();
}
