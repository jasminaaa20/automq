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

package org.apache.kafka.image.loader;

import org.apache.kafka.image.MetadataDelta;
import org.apache.kafka.image.MetadataImage;

public interface MetadataListener {

    void onChange(MetadataDelta delta, MetadataImage image);
}
