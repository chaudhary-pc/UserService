package com.userservice.repositories;

import com.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
    Optional<Token> findByValueAndDeleted(String value, boolean deleted);

    Optional<Token> findByValueAndDeletedAndExpiryDateGreaterThan(String value, boolean Deleted, Date expiryDate);
}
