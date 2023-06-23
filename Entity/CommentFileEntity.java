package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_file_table")
public class CommentFileEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String originalFileName;
	
	@Column
	private String storedFileName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private CommentEntity commentEntity;
	
	public static CommentFileEntity toCommentFileEntity(CommentEntity commentEntity, String originalFileName, String storedFileName) {
		CommentFileEntity commentFileEntity = new CommentFileEntity();
		commentFileEntity.setOriginalFileName(originalFileName);
		commentFileEntity.setStoredFileName(storedFileName);
		commentFileEntity.setCommentEntity(commentEntity);
		
		return commentFileEntity;
	}
}
