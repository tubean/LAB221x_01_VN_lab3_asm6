package vn.edu.funix.lab6.model;

import lombok.Data;

import java.util.List;

@Data
public class FirstLoginPageRequestModel {
    private List<Long> hintQuestionIdList;
    private List<String> answers;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}