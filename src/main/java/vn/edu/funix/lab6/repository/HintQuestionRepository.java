package vn.edu.funix.lab6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.funix.lab6.entity.HintQuestion;

@Repository
public interface HintQuestionRepository extends JpaRepository<HintQuestion, Long> {
}
