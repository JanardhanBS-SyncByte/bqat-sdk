package io.bqat.sdk.services.spi;

import io.bqat.sdk.services.dto.RequestDto;

public interface BqatSdkServiceProvider {
    Object getSpecVersion();

    Object init(RequestDto request);

    Object checkQuality(RequestDto request);

    Object match(RequestDto request);

    Object extractTemplate(RequestDto request);

    Object segment(RequestDto request);

    Object convertFormat(RequestDto request);
}
