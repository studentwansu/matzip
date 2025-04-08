package com.ezen.matzip.domain.restaurant.enums;

public enum RestaurantStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2); // 세미콜론 꼭 있어야 함

    private final int code;

    RestaurantStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RestaurantStatus fromCode(int code) {
        for (RestaurantStatus status : RestaurantStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid restaurant status code: " + code);
    }

    // 한글 표시용 (선택)
    public String getDisplayName() {
        return switch (this) {
            case PENDING -> "대기";
            case APPROVED -> "승인";
            case REJECTED -> "거절";
        };
    }
}
