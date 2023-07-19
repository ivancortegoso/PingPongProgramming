package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentaryRepository extends JpaRepository<Commentary, Long> {
}
