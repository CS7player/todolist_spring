package com.p001.todolist.service;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.p001.todolist.model.Profile;

@Service
public class ProfileService {
 @Autowired
 private JdbcTemplate jdbcTemplate;
 public List<Profile> getProfileImages(Long userId) {
  String sql = "SELECT id, user_id, filename, created_at FROM profile WHERE user_id = ? ORDER BY created_at DESC";
  RowMapper<Profile> rowMapper = (rs, rowNum) -> {
   Profile profile = new Profile();
   profile.setId(rs.getLong("id"));
   profile.setUserId(rs.getLong("user_id"));
   profile.setFileName(rs.getString("filename"));
   profile.setCreatedAt(rs.getLong("created_at"));
   return profile;
  };
  return jdbcTemplate.query(sql, rowMapper, userId);
 }
}
