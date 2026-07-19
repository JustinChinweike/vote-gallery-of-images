package com.galleryvote.vote;
import java.util.List; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Query;
public interface VoteRepository extends JpaRepository<Vote,Long>{
 boolean existsByUserIdAndSubmissionId(Long userId,Long submissionId);
 interface Score { Long getSubmissionId(); long getVotes(); }
 @Query("select v.submission.id as submissionId,count(v) as votes from Vote v where v.submission.contest.id=:contestId group by v.submission.id order by count(v) desc") List<Score> leaderboard(Long contestId);
}
