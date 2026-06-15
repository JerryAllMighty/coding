package com.seowon.coding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRequestDto {
    //각 필드에 대한 상세한 null 허용 여부나 기획적인 부부은 알 수 없어,
    // 대중적인 어노테이션으로 dto의 입력값 체크를 하였습니다.

    @NotBlank
    private String customerName;

    @NotBlank
    @Email
    private String customerEmail;

    private List<Long> productIds = new ArrayList<>();

    private List<Integer> quantities = new ArrayList<>();
}
