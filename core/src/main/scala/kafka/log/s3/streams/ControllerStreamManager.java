/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kafka.log.s3.streams;

import java.util.concurrent.CompletableFuture;
import kafka.log.s3.network.ControllerRequestSender;
import kafka.log.s3.objects.OpenStreamMetadata;
import kafka.server.KafkaConfig;
import org.apache.kafka.common.message.CreateStreamRequestData;
import org.apache.kafka.common.message.CreateStreamResponseData;
import org.apache.kafka.common.message.OpenStreamRequestData;
import org.apache.kafka.common.message.OpenStreamResponseData;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.requests.OpenStreamRequest;
import org.apache.kafka.common.requests.s3.CreateStreamRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerStreamManager implements StreamManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerStreamManager.class);
    private final KafkaConfig config;
    private final ControllerRequestSender requestSender;

    public ControllerStreamManager(ControllerRequestSender requestSender, KafkaConfig config) {
        this.config = config;
        this.requestSender = requestSender;
    }

    @Override
    public CompletableFuture<Long> createStream() {
        CreateStreamRequest.Builder request = new CreateStreamRequest.Builder(
            new CreateStreamRequestData()
        );
        return this.requestSender.send(request, CreateStreamResponseData.class).thenApply(resp -> {
            switch (Errors.forCode(resp.errorCode())) {
                case NONE:
                    return resp.streamId();
                default:
                    LOGGER.error("Error while creating stream: {}, code: {}", request, Errors.forCode(resp.errorCode()));
                    throw Errors.forCode(resp.errorCode()).exception();
            }
        });
    }

    @Override
    public CompletableFuture<OpenStreamMetadata> openStream(long streamId, long epoch) {
        OpenStreamRequest.Builder request = new OpenStreamRequest.Builder(
            new OpenStreamRequestData()
                .setStreamId(streamId)
                .setStreamEpoch(epoch)
                .setBrokerId(config.brokerId())
        );
        return this.requestSender.send(request, OpenStreamResponseData.class).thenApply(resp -> {
            switch (Errors.forCode(resp.errorCode())) {
                case NONE:
                    return new OpenStreamMetadata(streamId, epoch, resp.startOffset(), resp.nextOffset());
                case STREAM_FENCED:
                    LOGGER.error("Stream fenced while opening stream: {}, code: {}", request, Errors.forCode(resp.errorCode()));
                    throw Errors.forCode(resp.errorCode()).exception();
                default:
                    LOGGER.error("Error while opening stream: {}, code: {}", request, Errors.forCode(resp.errorCode()));
                    throw Errors.forCode(resp.errorCode()).exception();
            }
        });
    }

    @Override
    public CompletableFuture<Void> trimStream(long streamId, long epoch, long newStartOffset) {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteStream(long streamId, long epoch) {
        return null;
    }
}
