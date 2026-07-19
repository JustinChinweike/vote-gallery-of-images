package com.galleryvote.submission;
import com.galleryvote.contest.Contest; import com.galleryvote.user.User; import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="submissions")
public class Submission {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="contest_id") private Contest contest;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="author_id") private User author;
 @Column(nullable=false,length=160) private String title;
 @Column(name="object_key",nullable=false,unique=true) private String objectKey;
 @Column(name="content_type",nullable=false,length=50) private String contentType;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected Submission(){} public Submission(Contest c,User a,String title,String key,String type){contest=c;author=a;this.title=title;objectKey=key;contentType=type;}
 public Long getId(){return id;} public Contest getContest(){return contest;} public User getAuthor(){return author;} public String getTitle(){return title;} public String getObjectKey(){return objectKey;} public String getContentType(){return contentType;} public Instant getCreatedAt(){return createdAt;}
}
