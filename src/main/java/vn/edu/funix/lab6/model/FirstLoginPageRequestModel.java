package vn.edu.funix.lab6.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

    public String validateRequestModel() {
        if (hintQuestionId1 == null && hintQuestionId2 == null && hintQuestionId3 == null) {
            return "Please select and answer at least one question";
        }
        if (isQuestionDuplicate(hintQuestionId1, hintQuestionId2)
                || isQuestionDuplicate(hintQuestionId2, hintQuestionId3)
                || isQuestionDuplicate(hintQuestionId1, hintQuestionId3)) {
            return "Please do not choose 2 questions in the same.";
        }
        if (hintQuestionId1 == null && !StringUtils.isEmpty(answer1)) {
            return "Please select question 1";
        }
        if (hintQuestionId1 != null && StringUtils.isEmpty(answer1)) {
            return "Please answer question 1";
        }
        if (hintQuestionId2 == null && !StringUtils.isEmpty(answer2)) {
            return "Please select question 2";
        }
        if (hintQuestionId2 != null && StringUtils.isEmpty(answer2)) {
            return "Please answer question 2";
        }
        if (hintQuestionId3 == null && !StringUtils.isEmpty(answer3)) {
            return "Please select question 3";
        }
        if (hintQuestionId3 != null && StringUtils.isEmpty(answer3)) {
            return "Please answer question 3";
        }
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)) {
            return "Please fill required fields";
        }
        if (!Objects.equals(newPassword, confirmPassword)) {
            return "New password and confirm password are not match";
        }
        if (!StringUtils.isEmpty(newPassword) && newPassword.length() != 8) {
            return "Password must be 8 characters";
        }
        return "success";
    }

    private boolean isQuestionDuplicate(Long hintQuestionId1, Long hintQuestionId2) {
        if (hintQuestionId1 == null && hintQuestionId2 == null) {
            return false;
        } else return Objects.equals(hintQuestionId1, hintQuestionId2);
    }
}