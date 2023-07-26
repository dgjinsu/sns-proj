package com.snsproj.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {
    private Integer fromUserId; // 알람을 발생 시킨 사람
    private Integer targetId; // post 에 코멘트가 달렸다면 post
}
