package com.example.demo.Repository;

import java.time.LocalDateTime;

public interface BoardMapping {

	Long getId();
	String getBoardWriter();
	String getBoardTitle();
	int getBoardHits();
	LocalDateTime getCreatedTime();
	String getLocationGu();
	String getLocationDong();
}
