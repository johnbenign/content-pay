package com.task.threeline.content.dao;

import com.task.threeline.content.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {}
