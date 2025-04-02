package com.ezen.matzip.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 5자 이상 20자 이하의 영문자, 숫자로만 구성될 수 있습니다.")
    private String userId;              //아이디
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "비밀번호는 8자 이상 20자 이하이며, 숫자와 문자를 포함해야 합니다.")
    private String password;            //비밀번호
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;         //전화번호
    @NotBlank(message = "비밀번호 찾기 질문은 필수 선택 항목입니다.")
    private String passwordQuestion;    //비밀번호찾기질문
    @NotBlank(message = "비밀번호 찾기 답변은 필수 입력 항목입니다.")
    private String passwordAnswer;      //비밀번호찾기답변
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;                //이름
    @NotBlank(message = "국가는 필수 선택 항목입니다.")
    private String nationality;         //국가
    @NotNull(message = "비건 여부는 필수 선택 항목입니다.")
    @Builder.Default
    private Boolean isVegan = false;  // Boolean 타입으로 변경, 기본값 false
    @NotNull(message = "선호음식취향은 필수 선택 항목입니다.")
    private Integer categoryCode;           //선호음식취향
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;               //이메일
}


