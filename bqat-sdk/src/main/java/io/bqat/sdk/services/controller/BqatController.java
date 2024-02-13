package io.bqat.sdk.services.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.bqat.sdk.services.dto.ErrorDto;
import io.bqat.sdk.services.dto.RequestDto;
import io.bqat.sdk.services.dto.ResponseDto;
import io.bqat.sdk.services.constant.ErrorMessages;
import io.bqat.sdk.services.exceptions.BqatSdkException;
import io.bqat.sdk.services.factory.BqatSdkServiceFactory;
import io.bqat.sdk.services.service.CheckQualityService;
import io.bqat.sdk.services.spi.BqatSdkServiceProvider;
import io.bqat.sdk.services.utils.Utils;
import io.mosip.kernel.biometrics.spi.IBioApiV2;
import io.mosip.kernel.core.logger.spi.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;

import static io.bqat.sdk.services.constant.AppConstants.LOGGER_IDTYPE;
import static io.bqat.sdk.services.constant.AppConstants.LOGGER_SESSIONID;

@RestController
@RequestMapping("/")
@Api(tags = "Sdk")
@CrossOrigin("*")
@Slf4j
public class BqatController {

	@Autowired
	private Utils serviceUtil;

	@Autowired
	private IBioApiV2 iBioApi;

	@Autowired
	private BqatSdkServiceFactory bqatSdkServiceFactory;

	private Gson gson = new GsonBuilder().serializeNulls().create();

	@GetMapping(path = "/")
	@ApiOperation(value = "Service status")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Service is running...") })
	public ResponseEntity<String> status() {
		Date d = new Date();
		return ResponseEntity.status(HttpStatus.OK).body("Service is running... " + d.toString());
	}

	@PreAuthorize("hasAnyRole('BQAT_BIOMETRIC_QUALITY_PROCESSOR')")
	@GetMapping(path = "/s")
	@ApiOperation(value = "Service status 1")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Service is running...") })
	public ResponseEntity<String> status1() {
		Date d = new Date();
		return ResponseEntity.status(HttpStatus.OK).body("Service is running... " + d.toString());
	}

	@PostMapping(path = "/init", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Initialization")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Initialization successful") })
	public ResponseEntity<String> init(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.init(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	@PostMapping(path = "/match", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Match")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Match successful") })
	public ResponseEntity<String> match(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.match(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	@PostMapping(path = "/check-quality", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Check quality")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Check successful") })
	public ResponseEntity<String> checkQuality(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.checkQuality(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		log.info(LOGGER_SESSIONID, LOGGER_IDTYPE, "checkQuality: Ended");
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	@PostMapping(path = "/extract-template", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Extract template")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Extract successful") })
	public ResponseEntity<String> extractTemplate(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.extractTemplate(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	@PostMapping(path = "/convert-format", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Convert format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Convert successful") })
	public ResponseEntity<String> convertFormat(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.convertFormat(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	@PostMapping(path = "/segment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Segment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Segment successful") })
	public ResponseEntity<String> segment(@Validated @RequestBody(required = true) RequestDto request,
			@ApiIgnore Errors errors) {
		ResponseDto responseDto = generateResponseTemplate(request.getVersion());
		try {
			responseDto.setVersion(request.getVersion());
			BqatSdkServiceProvider bioSdkServiceProviderImpl = null;
			bioSdkServiceProviderImpl = bqatSdkServiceFactory.getBioSdkServiceProvider(request.getVersion());
			responseDto.setResponse(bioSdkServiceProviderImpl.segment(request));
		} catch (BqatSdkException e) {
			log.error(LOGGER_SESSIONID, LOGGER_IDTYPE, "BqatSdkException: ", e.getMessage());
			ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorText());
			responseDto.getErrors().add(errorDto);
			return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(responseDto));
	}

	private ResponseDto generateResponseTemplate(String version) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setVersion(version);
		responseDto.setResponsetime(serviceUtil.getCurrentResponseTime());
		responseDto.setErrors(new ArrayList<ErrorDto>());
		responseDto.setResponse("");
		return responseDto;
	}

	private String getVersion(String request) throws BqatSdkException {
		JSONParser parser = new JSONParser();
		try {
			JSONObject js = (JSONObject) parser.parse(request);
			return js.get("version").toString();
		} catch (ParseException e) {
			throw new BqatSdkException(ErrorMessages.UNCHECKED_EXCEPTION.toString(), e.getMessage());
		}
	}
}
