package com.galleryvote.contest;

import com.galleryvote.user.User;
import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="contests")
public class Contest {
    public enum Status { DRAFT, OPEN, CLOSED }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=120) private String title;
    @Column(nullable=false,length=2000) private String description;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="owner_id") private User owner;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Status status = Status.OPEN;
    @Column(name="starts_at",nullable=false) private Instant startsAt;
    @Column(name="ends_at",nullable=false) private Instant endsAt;
    @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
    protected Contest() {}
    public Contest(String title,String description,User owner,Instant startsAt,Instant endsAt){this.title=title;this.description=description;this.owner=owner;this.startsAt=startsAt;this.endsAt=endsAt;}
    public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public User getOwner(){return owner;}
    public Status getStatus(){return status;} public Instant getStartsAt(){return startsAt;} public Instant getEndsAt(){return endsAt;} public Instant getCreatedAt(){return createdAt;}
    public boolean isVotingOpen(Instant now){return status==Status.OPEN&&!now.isBefore(startsAt)&&now.isBefore(endsAt);}
}
