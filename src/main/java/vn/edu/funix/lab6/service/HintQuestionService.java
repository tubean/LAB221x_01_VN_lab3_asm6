package vn.edu.funix.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.funix.lab6.entity.HintQuestion;
import vn.edu.funix.lab6.entity.User;
import vn.edu.funix.lab6.entity.UserQuestionChoice;
import vn.edu.funix.lab6.model.FirstLoginPageRequestModel;
import vn.edu.funix.lab6.repository.HintQuestionRepository;
import vn.edu.funix.lab6.repository.UserQuestionChoiceRepository;
import vn.edu.funix.lab6.utils.Strings;

import java.util.List;

@Service
public class HintQuestionService {
    @Autowired
    private HintQuestionRepository hintQuestionRepository;
    @Autowired
    private UserQuestionChoiceRepository userQuestionChoiceRepository;

    public void saveHintQuestionChoice(FirstLoginPageRequestModel model, User user) {
        if (model.getHintQuestionId1() != null) {
            saveChoice(model.getHintQuestionId1(), Strings.nvl(model.getAnswer1()), user);
        }
        if (model.getHintQuestionId2() != null) {
            saveChoice(model.getHintQuestionId2(), Strings.nvl(model.getAnswer2()), user);
        }
        if (model.getHintQuestionId3() != null) {
            saveChoice(model.getHintQuestionId3(), Strings.nvl(model.getAnswer3()), user);
        }
    }

    private void saveChoice(Long questionId, String answer, User user) {
        if (questionId != null) {
            hintQuestionRepository.findById(questionId).ifPresent(
                    hintQuestion -> {
                        UserQuestionChoice userQuestionChoice = new UserQuestionChoice();
                        userQuestionChoice.setHintQuestion(hintQuestion);
                        userQuestionChoice.setUser(user);
                        userQuestionChoice.setAnswer(answer);

                        userQuestionChoiceRepository.save(userQuestionChoice);
                    }
            );
        }
    }

    public List<HintQuestion> findAllHintQuestion() {
        return hintQuestionRepository.findAll();
    }
}
