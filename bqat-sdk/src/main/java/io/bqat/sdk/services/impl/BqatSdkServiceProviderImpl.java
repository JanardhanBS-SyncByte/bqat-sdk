package io.bqat.sdk.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.bqat.sdk.services.constant.ErrorMessages;
import io.bqat.sdk.services.dto.RequestDto;
import io.bqat.sdk.services.dto.request.CheckQualityRequestDto;
import io.bqat.sdk.services.dto.request.ConvertFormatRequestDto;
import io.bqat.sdk.services.dto.request.ExtractTemplateRequestDto;
import io.bqat.sdk.services.dto.request.InitRequestDto;
import io.bqat.sdk.services.dto.request.MatchRequestDto;
import io.bqat.sdk.services.dto.request.SegmentRequestDto;
import io.bqat.sdk.services.exceptions.BqatSdkException;
import io.bqat.sdk.services.spi.BqatSdkServiceProvider;
import io.bqat.sdk.services.utils.Utils;
import io.mosip.kernel.biometrics.model.SDKInfo;
import io.mosip.kernel.biometrics.model.Response;
import io.mosip.kernel.biometrics.spi.IBioApiV2;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.bqat.sdk.services.constant.AppConstants.LOGGER_IDTYPE;
import static io.bqat.sdk.services.constant.AppConstants.LOGGER_SESSIONID;

@Component
@Slf4j
public class BqatSdkServiceProviderImpl implements BqatSdkServiceProvider {
    private static final String BQATSDK_SERVICE_SPEC_VERSION = "1.0";
    private static final String BQATSDK_SPEC_VERSION = "0.9";
    private static final String publicKey = "";
    private static final String privateKey = "";

    @Autowired
    private IBioApiV2 iBioApiV2;

    @Autowired
    private Utils serviceUtil;

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public String getSpecVersion() {
        return BQATSDK_SERVICE_SPEC_VERSION;
    }

    @Override
    public Object init(RequestDto request){
        SDKInfo sdkInfo = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "init: ", "decoding successful");
        InitRequestDto initRequestDto = gson.fromJson(decryptedRequest, InitRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "init: ", "json to dto successful");
        try {
            sdkInfo = iBioApiV2.init(initRequestDto.getInitParams());
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "init: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.getMessage());
        }
        return sdkInfo;
    }

    @Override
    public Object checkQuality(RequestDto request) {
    	Response response = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "checkQuality: ", "decoding successful");
        CheckQualityRequestDto checkQualityRequestDto = gson.fromJson(decryptedRequest, CheckQualityRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "checkQuality: ", "json to dto successful");
        try {
            response = iBioApiV2.checkQuality(
                    checkQualityRequestDto.getSample(),
                    checkQualityRequestDto.getModalitiesToCheck(),
                    checkQualityRequestDto.getFlags()
            );
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "checkQuality: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
        return response;
    }

    @Override
    public Object match(RequestDto request) {
    	Response response = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE,"match: ", "decoding successful");
        MatchRequestDto matchRequestDto = gson.fromJson(decryptedRequest, MatchRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE,"match: ", "json to dto successful");
        try {
            response = iBioApiV2.match(
                    matchRequestDto.getSample(),
                    matchRequestDto.getGallery(),
                    matchRequestDto.getModalitiesToMatch(),
                    matchRequestDto.getFlags()
            );
        } catch (BqatSdkException e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "match: ", e.toString()+" "+e.getMessage());
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "match: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
        return response;
    }

    @Override
    public Object extractTemplate(RequestDto request) {
    	Response response = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "extractTemplate: ", "decoding successful");
        ExtractTemplateRequestDto extractTemplateRequestDto = gson.fromJson(decryptedRequest, ExtractTemplateRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "extractTemplate: ", "json to dto successful");
        try {
            response = iBioApiV2.extractTemplate(
                    extractTemplateRequestDto.getSample(),
                    extractTemplateRequestDto.getModalitiesToExtract(),
                    extractTemplateRequestDto.getFlags()
            );
        } catch (BqatSdkException e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "extractTemplate: ", e.toString()+" "+e.getMessage());
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "extractTemplate: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
        return response;
    }

    @Override
    public Object segment(RequestDto request) {
    	Response response = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "segment: ", "decoding successful");
        SegmentRequestDto segmentRequestDto = gson.fromJson(decryptedRequest, SegmentRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "segment: ", "json to dto successful");
        try {
            response = iBioApiV2.segment(
                    segmentRequestDto.getSample(),
                    segmentRequestDto.getModalitiesToSegment(),
                    segmentRequestDto.getFlags()
            );
        } catch (BqatSdkException e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "segment: ", e.toString()+" "+e.getMessage());
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "segment: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
        return response;
    }

    @Override
    public Object convertFormat(RequestDto request) {
    	Response response = null;
        String decryptedRequest = decode(request.getRequest());
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "convertFormat: ", "decoding successful");
        ConvertFormatRequestDto convertFormatRequestDto = gson.fromJson(decryptedRequest, ConvertFormatRequestDto.class);
        log.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "convertFormat: ", "json to dto successful");
        try {
        	response = iBioApiV2.convertFormatV2(
                    convertFormatRequestDto.getSample(),
                    convertFormatRequestDto.getSourceFormat(),
                    convertFormatRequestDto.getTargetFormat(),
                    convertFormatRequestDto.getSourceParams(),
                    convertFormatRequestDto.getTargetParams(),
                    convertFormatRequestDto.getModalitiesToConvert()
            );
        } catch (BqatSdkException e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "convertFormat: ", e.toString()+" "+e.getMessage());
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "convertFormat: ", e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.BQAT_SDK_LIB_EXCEPTION.toString(), ErrorMessages.BQAT_SDK_LIB_EXCEPTION.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
        return null;
    }

    private String decode(String data){
        try {
            return Utils.base64Decode(data);
        } catch (Exception e){
            e.printStackTrace();
            log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, ErrorMessages.INVALID_REQUEST_BODY.toString(), e.toString()+" "+e.getMessage());
            throw new BqatSdkException(ErrorMessages.INVALID_REQUEST_BODY.toString(), ErrorMessages.INVALID_REQUEST_BODY.getMessage()+": "+e.toString()+" "+e.getMessage());
        }
    }
}
