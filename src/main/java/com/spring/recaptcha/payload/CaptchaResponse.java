package com.spring.recaptcha.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class CaptchaResponse {

    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    enum ErrorCode {
        MissingSecret("missing-input-secret"),
        InvalidSecret("invalid-input-secret"),
        MissingResponse("missing-input-response"),
        InvalidResponse("invalid-input-response"),
        BadRequest("bad-request"),
        TimeoutOrDuplicate("timeout-or-duplicate");

        private final String value;

        public String getValue() {
            return this.value;
        }

        ErrorCode(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ErrorCode forValue(String value) {
            return Arrays.stream(ErrorCode.values())
                    .filter(errorCode -> errorCode.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown enum value"));
        }
    }

    @JsonIgnore
    public boolean hasClientError() {
        if (this.errorCodes == null)
            return false;
        for (ErrorCode error : this.errorCodes) {
            switch (error) {
                case MissingResponse:
                case InvalidResponse:
                case BadRequest:
                case TimeoutOrDuplicate:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    public CaptchaResponse(boolean success, String challengeTs, ErrorCode[] errorCodes) {
        this.success = success;
        this.challengeTs = challengeTs;
        this.errorCodes = errorCodes;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public ErrorCode[] getErrorCodes() {
        return errorCodes;
    }
}
