package vn.edu.funix.lab6.model;

import lombok.Data;

@Data
public class FirstLoginPageRequestModel {
    private Long hintQuestionId1;
    private Long hintQuestionId2;
    private Long hintQuestionId3;

    private String answer1;
    private String answer2;
    private String answer3;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}