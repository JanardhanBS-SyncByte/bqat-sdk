package io.bqat.sdk.services.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.bqat.sdk.services.constant.ErrorMessages;
import io.bqat.sdk.services.exceptions.BqatSdkException;
import io.bqat.sdk.services.spi.BqatSdkServiceProvider;

@Component
public class BqatSdkServiceFactory {
    @Autowired
    private List<BqatSdkServiceProvider> bqatSdkServiceProviders;

    public BqatSdkServiceProvider getBioSdkServiceProvider(String version){
        for(BqatSdkServiceProvider provider : bqatSdkServiceProviders) {
            if(provider.getSpecVersion().equals(version)){
                return provider;
            }
        }
        throw new BqatSdkException(ErrorMessages.NO_BIOSDK_PROVIDER_FOUND.getMessage(), ErrorMessages.NO_BIOSDK_PROVIDER_FOUND.getMessage());
    }
}
