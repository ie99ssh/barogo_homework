package com.barogo.platform.common.exception;

public enum Errors {

    USER_NOT_FOUND(-1001, "등록되어 있지 않은 ID"),
    USER_WRONG_PASSWORD(-1002, "패스워드 틀림"),
    USER_DEPRECATED(-1003, "사용 정지 된 ID"),
    USER_INVALID_AUTH(-1007, "유효하지 않은 인증 정보"),
    USER_ALREADY_AUTH(-1008, "이미 인증 처리된 정보"),
    USER_EXPIRED_AUTH(-1009, "인증 유효 시간 만료"),
    USER_PASSWORD_ENCODING_FAIL(-1010, "패스워드 인코딩 오류"),
    USER_PASSWORD_INSERT_HISTORY_FAIL(-1011, "비밀번호 변경 이력 등록 실패"),
    TOKEN_INVALID(-1013, "토큰 정보가 유효하지 않습니다."),
    USER_INCONSISTENCY_REFRESH_TOKEN(-1015, "리프레쉬 토큰의 유저 정보 불일치"),
    USER_ALREADY_LOGOUT(-1016, "로그아웃 된 사용자"),
    USER_INCONSISTENCY_ACCESS_TOKEN(-1017, "액세스 토큰의 유저 정보 불일치"),
    USER_INVALID_REFRESH_TOKEN(-1018, "리프레쉬 토큰이 유효하지 않음"),
    USER_REQUEST_INCONSISTENCY_REFRESH_TOKEN(-1019, "요청한 사용자와 토큰 값의 사용자와 불일치"),
    USER_GENERATE_PASSWORD_INVALID(-1020, "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다."),
    USER_ID_ALREADY_EXIST(-1021, "해당 유저 ID는 이미 사용중입니다."),

    DELIVERY_MAX_PERIOD_EXCEED(-2001, "배달 조회 기간 조건이 3일 초과하였습니다."),
    DELIVERY_DATE_INVALID(-2002, "조회 시작일자가 종료일자보다 클수 없습니다."),
    DELIVERY_NOT_EXIST(-2003, "배달 정보가 존재하지 않습니다."),
    DELIVERY_UPDATE_NOT_POSSIBLE(-2004, "배송이 준비중인 경우에만 배송 정보 변경이 가능합니다."),
    DELIVERY_UPDATE_DEST_ADDRESS_NOT_POSSIBLE(-2005, "배송이 준비중인 경우에만 배송지 정보 변경이 가능합니다."),
    DELIVERY_ALREADY_COMPLETE(-2006, "배송이 이미 완료된 건입니다."),

    PRODUCT_NOT_EXIST(-2101, "상품이 존재하지 않습니다."),

    PDF_CREATE_ERROR(-4001, "PDF 파일 생성 오류"),
    PDF_DELETE_ERROR(-4002, "PDF 파일 삭제 오류"),

    EVIDENCE_NONE_TYPE(-5001, "지원하지 않는 적격증빙자료 유형 입니다."),
    ;

    private final int code;
    private final String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name();
    }

    public String getMessage() {
        return this.message;
    }
}
