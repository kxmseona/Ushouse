package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.CommentFileEntity;
public interface CommentFileRepository extends JpaRepository<CommentFileEntity, Long>{

}
