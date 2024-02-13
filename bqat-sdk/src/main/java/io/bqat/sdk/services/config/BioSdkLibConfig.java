package io.bqat.sdk.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import io.mosip.kernel.biometrics.spi.IBioApiV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class BioSdkLibConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(BioSdkLibConfig.class);
    @Autowired
    private Environment env;

    public BioSdkLibConfig() {
    }

    @PostConstruct
    public void validateBioSdkLib() throws ClassNotFoundException {
    	String sdkClass = this.env.getProperty("bqatsdk_bioapi_impl");
    	LOGGER.info("Biosdk class: " + sdkClass);
        if (StringUtils.isNotBlank(sdkClass)) {
        	LOGGER.debug("Validating Bio SDK Class is present or not");
            Class.forName(this.env.getProperty("bqatsdk_bioapi_impl"));
        }

        LOGGER.debug("ValidateBioSdkLib: Bio SDK Class is not provided");
    }

    @Bean
    @Lazy
    public IBioApiV2 iBioApiV2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    	String sdkClass = this.env.getProperty("bqatsdk_bioapi_impl");
    	LOGGER.info("Biosdk class: " + sdkClass);
    	if (StringUtils.isNotBlank(sdkClass)) {
    		LOGGER.debug("instance of Bio SDK is created");
            return (IBioApiV2)Class.forName(sdkClass).newInstance();
        } else {
        	LOGGER.debug("No Bio SDK is provided");
            throw new RuntimeException("No Bio SDK is provided");
        }
    }
}
