package com.example.demo.Entity;

import java.util.List;
import com.example.demo.Dto.SignRequest;

import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_table")
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String memberEmail;
	
	@Column
	private String memberPassword;
	
	@Column
	private String memberName;
	
	
	@Column(name = "GENDER")
	@Enumerated(EnumType.STRING)
	private Gender Gender;
	
	@OneToMany(mappedBy = "memberEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();
	
	public void setRoles(List<Authority> role) {
		this.roles = role;
		role.forEach(o -> o.setMemberEntity(this));
	}
	

	
}
 