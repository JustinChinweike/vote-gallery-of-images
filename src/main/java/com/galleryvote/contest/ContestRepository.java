package com.galleryvote.contest;
import java.util.Optional; import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable; import org.springframework.data.jpa.repository.EntityGraph; import org.springframework.data.jpa.repository.JpaRepository;
public interface ContestRepository extends JpaRepository<Contest,Long>{ @EntityGraph(attributePaths="owner") Page<Contest> findByTitleContainingIgnoreCase(String query, Pageable pageable); @Override @EntityGraph(attributePaths="owner") Optional<Contest> findById(Long id); }
