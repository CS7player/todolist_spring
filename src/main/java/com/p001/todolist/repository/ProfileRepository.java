package com.p001.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p001.todolist.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
 // You can define custom queries if needed, but JpaRepository gives you many
 // methods by default.
}
