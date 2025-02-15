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

package kafka.autobalancer.common.types.metrics;

import kafka.autobalancer.model.BrokerUpdater;
import kafka.autobalancer.model.Snapshot;

import java.util.Map;

public class AbnormalLatency extends AbstractSimpleAbnormalMetric {

    public AbnormalLatency(float abnormalLatency) {
        super(abnormalLatency);
    }

    @Override
    public boolean isAbnormalToPeer(Snapshot self, Map<BrokerUpdater.Broker, Snapshot> peers) {
        // TODO: compare with peer
        return false;
    }
}
