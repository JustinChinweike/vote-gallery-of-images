package com.galleryvote.vote;
import com.galleryvote.submission.Submission; import com.galleryvote.user.User; import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="votes",uniqueConstraints=@UniqueConstraint(name="uk_vote_user_submission",columnNames={"user_id","submission_id"}))
public class Vote { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user; @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="submission_id") private Submission submission; @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now(); protected Vote(){} public Vote(User u,Submission s){user=u;submission=s;} }
