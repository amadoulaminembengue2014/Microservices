package com.vlmcode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlmcode.entity.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

	Optional<UserCredential> findByName(String username);

}
