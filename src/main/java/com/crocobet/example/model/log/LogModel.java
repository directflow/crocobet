package com.crocobet.example.model.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {

    private Long execution;

    private String ip;

    private String endpoint;

    private Object request;

    private Object response;

}
