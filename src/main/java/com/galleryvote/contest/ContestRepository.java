package com.galleryvote.contest;
import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable; import org.springframework.data.jpa.repository.JpaRepository;
public interface ContestRepository extends JpaRepository<Contest,Long>{ Page<Contest> findByTitleContainingIgnoreCase(String query, Pageable pageable); }
