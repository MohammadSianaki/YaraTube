package com.yaratech.yaratube.data.model.other;

public class Event {
    public final static String MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE = "MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE";
    public final static String MOBILE_PHONE_NUMBER_SUBMIT_BUTTON_CLICK_MESSAGE = "MOBILE_PHONE_NUMBER_SUBMIT_BUTTON_CLICK_MESSAGE";
    public final static String MOBILE_PHONE_NUMBER_VERIFY_BUTTON_CLICK_MESSAGE = "MOBILE_PHONE_NUMBER_VERIFY_BUTTON_CLICK_MESSAGE";
    public final static String MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE = "MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE";
    public final static String GOOGLE_SIGN_IN_SUCCESSFUL_MESSAGE = "GOOGLE_SIGN_IN_SUCCESSFUL_MESSAGE";

    public static final int LOGIN_STEP_ONE = 1;
    public static final int LOGIN_STEP_TWO = 2;
    public static final int LOGIN_STEP_THREE = 3;
    public static final int LOGIN_STEP_FINISH = -1;

    // Event used to send message from parent fragment to child fragment.

    public static class ParentChildMessage {
        private String message;

        public ParentChildMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from child fragment to parent fragment.

    public static class ChildParentMessage {
        private String message;
        private int loginStep;

        public ChildParentMessage(String message, int loginStep) {
            this.message = message;
            this.loginStep = loginStep;
        }


        public String getMessage() {
            return message;
        }

        public int getLoginStep() {
            return loginStep;
        }
    }

}
