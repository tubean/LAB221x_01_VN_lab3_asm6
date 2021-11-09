package vn.edu.funix.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.funix.lab6.entity.HintQuestion;
import vn.edu.funix.lab6.entity.User;
import vn.edu.funix.lab6.entity.UserQuestionChoice;
import vn.edu.funix.lab6.model.FirstLoginPageRequestModel;
import vn.edu.funix.lab6.repository.HintQuestionRepository;
import vn.edu.funix.lab6.repository.UserQuestionChoiceRepository;

import java.util.Collections;
import java.util.List;

@Service
public class HintQuestionService {
    @Autowired
    private HintQuestionRepository hintQuestionRepository;
    @Autowired
    private UserQuestionChoiceRepository userQuestionChoiceRepository;

    public void saveHintQuestionChoice(FirstLoginPageRequestModel model, User user) {
        List<Long> hintQuestions = model.getHintQuestionIdList() == null ? Collections.emptyList() : model.getHintQuestionIdList();
        List<String> answers = model.getAnswers() == null ? Collections.emptyList() : model.getAnswers();

        int hintQuestionSize = hintQuestions.size();
        int answerSize = answers.size();

        if (hintQuestionSize != 0 && hintQuestionSize == answerSize) {
            for (int i = 0; i < hintQuestionSize; i++) {
                String finalAnswer = answers.get(i);
                hintQuestionRepository.findById(hintQuestions.get(i)).ifPresent(
                        hintQuestion -> {
                            UserQuestionChoice userQuestionChoice = new UserQuestionChoice();
                            userQuestionChoice.setHintQuestion(hintQuestion);
                            userQuestionChoice.setUser(user);
                            userQuestionChoice.setAnswer(finalAnswer);

                            userQuestionChoiceRepository.save(userQuestionChoice);
                        }
                );
            }
        }
    }

    public List<HintQuestion> findAllHintQuestion() {
        return hintQuestionRepository.findAll();
    }
}
