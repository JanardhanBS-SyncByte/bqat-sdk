package io.bqat.sdk.services.constant;

public enum ErrorMessages {
    NO_BIOSDK_PROVIDER_FOUND("No BioSDK provider found with the given version"),
    BQAT_SDK_LIB_EXCEPTION("Exception thrown by Bqat SDK library"),
    INVALID_REQUEST_BODY("Unable to parse request body"),
    BQAT_SDK_LIB_METHOD_NOT_IMPLEMENTED_EXCEPTION("Bqat SDK library does not implement following request"),
    UNCHECKED_EXCEPTION("UNCHECKED_EXCEPTION");

    private ErrorMessages(String message) {
        this.message = message;
    }

    private final String message;

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
