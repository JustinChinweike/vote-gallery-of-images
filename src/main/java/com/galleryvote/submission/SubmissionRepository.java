package com.galleryvote.submission;
import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable; import org.springframework.data.jpa.repository.EntityGraph; import org.springframework.data.jpa.repository.JpaRepository;
public interface SubmissionRepository extends JpaRepository<Submission,Long>{@EntityGraph(attributePaths={"contest","author"}) Page<Submission> findByContestId(Long contestId,Pageable pageable);}
