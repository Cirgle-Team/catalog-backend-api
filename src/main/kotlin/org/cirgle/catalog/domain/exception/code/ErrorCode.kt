package org.cirgle.catalog.domain.exception.code

enum class ErrorCode(
    val code: String,
    val message: String,
) {
    UNKNOWN_ERROR("unknown-000", "알 수 없는 오류가 발생했습니다."),

    USER_NOT_FOUND("user-001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS("user-002", "이미 존재하는 사용자입니다."),
    USER_PASSWORD_MISMATCH("user-003", "비밀번호가 일치하지 않습니다."),

    INVALID("invalid-001", "유효하지 않은 요청입니다."),
    INVALID_ID("invalid-002", "아이디는 영어, 숫자만 포함한 6자 이상 20자 이하로 입력해주세요."),
    INVALID_PASSWORD("invalid-003", "비밀번호는 영어, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요."),
    INVALID_DATE("invalid-004", "날짜 형식이 올바르지 않습니다."),
    INVALID_TOKEN("invalid-005", "유효하지 않은 토큰입니다."),

    MENU_NOT_FOUND("menu-001", "메뉴를 찾을 수 없습니다."),
    MENU_ALREADY_EXISTS("menu-002", "이미 존재하는 메뉴입니다."),
    MENU_NAME("menu-003", "메뉴 이름은 3자 이상 20자 이하로 입력해주세요."),
    MENU_CAFFEINE("menu-004", "카페인은 0 이상 1000 이하로 입력해주세요."),
    MENU_TYPE("menu-005", "메뉴 타입이 올바르지 않습니다."),


    FRIEND_NOT_FOUND("friend-001", "친구를 찾을 수 없습니다."),
    FRIEND_ALREADY_EXISTS("friend-002", "이미 등록된 친구입니다.")
    ;

    companion object {
        private val map = values().associateBy { it.code }

        fun valueOf(code: String?): ErrorCode = map[code] ?: UNKNOWN_ERROR
    }
}