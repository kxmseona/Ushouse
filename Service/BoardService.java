package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.BoardDTO;
import com.example.demo.Entity.BoardEntity;
import com.example.demo.Entity.BoardFileEntity;
import com.example.demo.Entity.LikeEntity;
import com.example.demo.Entity.MemberEntity;
import com.example.demo.Repository.BoardFileRepository;
import com.example.demo.Repository.BoardMapping;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.LikeRepository;
import com.example.demo.Repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileRepository boardFileRepository;
	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;
	
	
	public void boardsave(BoardDTO boardDTO) throws IOException{
		// throws IOException - 서블릿 쪽으로 예외를 떠넘기는
		// BoardEntity의 tosaveEntity메서드를 이용하여 받아온 DTO객체를 Entity로 변환후 boardEntity에담고
		// boardRepository의 save메서드에 넣어서 보낸다.
		// 파일 첨부 여부에 따라 로직 분리
		if(boardDTO.getBoardFile() == null) {
			// 첨부 파일이 존재하지 않음
			BoardEntity boardEntity = BoardEntity.tosaveEntity(boardDTO);
			boardRepository.save(boardEntity);
		}else {
			// 첨부 파일이 존재
			/* 1. DTO에 담긴 파일을 꺼냄
			 * 2. 파일의 이름 가져옴
			 * 3. 서버 저장용 이름을 만듦
			 * 4. 저장 경로 설정
			 * 5. 해당 경로에 파일 저장
			 * 6. board_table에 해당 데이터 save처리(게시글 정보)
			 * 7. board_file_table에 해당 데이터 save처리(파일 정보)
			 * */
			
			
			BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
			// boardEntity에는 id값이 없기 때문에 다시 가지고 와야한다.
			Long saveId = boardRepository.save(boardEntity).getId(); // 게시글 정보 저장후 부모 엔티티의 아이디를 가지고 온다.(pk값을 가지고 온것)
			// 테이블간 매핑이 되었을때 pk값이 아닌 엔티티 값을 전달해주어야한다.
			// 그러므로 findById메서드를 이용하여 pk값의 엔티티값을 찾고, get()으로 가지고 온다.(optional로 감싸서 가지고 와도 됨)
			BoardEntity board = boardRepository.findById(saveId).get();
			// 부모 엔티티값을 가지고옴
			for(MultipartFile boardFile: boardDTO.getBoardFile()) {
			// 받아오는 파일이 여러개일때 List인 MultipartFile	
//			MultipartFile boardFile = boardDTO.getBoardFile();	//1	받아오는 파일이1개 일때
			String originalFilename = boardFile.getOriginalFilename();//2
			String storedFileName = System.currentTimeMillis() + "_" + originalFilename;//3
			// .currentTimeMillis()는 1970년 기준 현재 몇 미리세컨드가 지났냐의 값을 주는 메서드
			String savePath = "C:/ushouse_img/"+ storedFileName;//4
			boardFile.transferTo(new File(savePath)); // 5
			// transferTo -스프링에서 제공하는 MultipartFile 메서드, File() java.io 가 제공하는 객체
			
			
			// 위에서 만든 originalFilename, storedFilename, 부모 엔티티값인 board를 toBoardFileEntity메서드로 BoardFileEntity로 변환 후 보드파일엔티티 객체에 담음
			// board - 게시글 id의 값, originalFilename - 파일이름, storedFileName - 저장될 파일이름 을 board_file_table에 저장
			BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
			boardFileRepository.save(boardFileEntity);
			}
			
			
			
		}
		
		
	}
	
	// db에 있는 모든 정보를 BoardEntity리스트로 받아오고 DTO 리스트로 변환하여 리턴하는 findAll메서드
	@Transactional
	public List<BoardDTO> findAll() {
		// 가져온 엔티티 정보들을 list형태로 받아줌
		List<BoardEntity> boardEntityList = boardRepository.findAll();
		List<BoardDTO> boardDTOList = new ArrayList<>();
		// boardEntityList리스트에서 반복문으로 접근하는 객체 BoardEntity(이름) 
		// boardEntity(타입)를 toBoardDTO메서드를 이용해서
		// DTO객체로 변환한뒤 만들어둔 boardDTOList에 추가한다.
		for(BoardEntity boardEntity: boardEntityList) {
			boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
		}
		return boardDTOList;
	}
	
	@Transactional
	public List<BoardDTO> findlocation(String input) {
		List<BoardEntity> boardEntityList = boardRepository.findAllByLocationGu(input);
		List<BoardDTO> boardDTOList = new ArrayList<>();
		
		for(BoardEntity boardEntity: boardEntityList) {
			boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
		}
		return boardDTOList;
	}
	
	
	
	
	// jpa가 제공하는 쿼리가 아니면 @Transactional을 붙여야한다.
	@Transactional
	public void updateHits(Long id) {
		boardRepository.updateHits(id);
	}
	
	// 가져온 id로 db에서 정보를 찾은 후 찾은 boardEntity를 boardDTO로 변환해주는 findById메서드
	@Transactional
	public BoardDTO findById(Long id) {
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
		if(optionalBoardEntity.isPresent()) {
			BoardEntity boardEntity = optionalBoardEntity.get();
			BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
			return boardDTO;
		}else {
			return null;
		}
	}
	


	public BoardDTO update(BoardDTO boardDTO) {
		BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
		boardRepository.save(boardEntity);
		
		return findById(boardDTO.getId());
		
	}

	public void delete(Long id) {
		boardRepository.deleteById(id);
		
	}

	public Page<BoardDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber() - 1;
		// page위치에 있는 값은 0부터 시작 그러므로 1을 빼줌 
		int pageLimit = 6; // 한 페이지에 보여줄 글 갯수
		// 한페이지당 3개씩 글을 보여주고 정렬 기준은 엔티티 컬럼 id기준으로 내림차순 정렬 후 Page객체로 return (Page<BoardEntity> 이름은 boardEntities)
		Page<BoardEntity> boardEntities = 
				boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
		// boardRepository의 findAll메서드를 이용해서 전달되는 값
		// PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"boardId")
		// page- 몇 페이지를 보고싶은지, pageLimit- 한페이지에 몇개씩 볼것인지, 
		// Sort.by- 가지고 올 데이터를 정렬해서 가지고 올 기준(DESC-내림차순),"id" - 즉, 엔티티 기준 컬럼 id의 내림차순으로 정렬하여 가지고 옴
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부
        
        // 메서드 map, page객체에서 제공해주는 메서드
        // board- boardEntities의 개체(매개변수), map(board -> new BoardDTO())
        // boardEntities의 개체들을(board) 하나씩 꺼내서 새로운 BoardDTO객체로 하나씩 옮겨담는다.
        // 즉, boardEntities의 엔티티 개체를 DTO객체로 옮겨 닮는다.
        // 그러면서 위의 자바 메서드들도 자연스럽게 옮겨 담겨 진다.(만약 List객체로 옮겨 닮았으면 위의 자바 메서드는 옮겨지지 않았을 것이다.)
		Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(),board.getLocationGu(), board.getLocationDong(), board.getBoardHits(), board.getLikeHits(), board.getCreatedTime()));
		// 목록에서 보여지는 속성 : 글번호(id), 작성자(writer), 제목(title), 조회수(hits), 생성시간(createdTime)- 이들로 구성된 DTO필드를 BoardDTO에 생성
		
		return boardDTOS;
		
	}

	public List<BoardDTO> mylist(String boardwriter) {
		List<BoardMapping> boardMappingList = boardRepository.findAllByBoardWriter(boardwriter);
		List<BoardDTO> boardDTOList = new ArrayList<>();
		for(BoardMapping boardMapping : boardMappingList) {
			boardDTOList.add(BoardDTO.toMylistBoardDTO(boardMapping));
		}
		return boardDTOList;
	}

	
	public int findLike(Long id, String memberEmail) {
		// 저장된 Entity가 없다면 0, 있다면 1을 return
		
		Optional<MemberEntity> optionalmemberEntity = memberRepository.findByMemberEmail(memberEmail);
		Long memberId = optionalmemberEntity.get().getId();
		
		Optional<LikeEntity> findLike = likeRepository.findByBoardEntity_IdAndMemberEntity_Id(id, memberId);
		
		if(findLike.isEmpty()) {
			return 0;
		}else {
			return 1;
		}
		
	}
	
	@Transactional
	public int saveLike(Long boardId, String memberEmail) {
		
		Optional<MemberEntity> optionalmemberEntity = memberRepository.findByMemberEmail(memberEmail);
		Long memberId = optionalmemberEntity.get().getId();
		
		
		
		Optional<LikeEntity> findLike = likeRepository.findByMemberEntity_IdAndBoardEntity_Id(memberId, boardId);
		
		if(findLike.isEmpty()) {
			MemberEntity memberEntity = memberRepository.findById(memberId).get();
			BoardEntity boardEntity = boardRepository.findById(boardId).get();
			
			LikeEntity likeEntity = LikeEntity.toLikeEntity(memberEntity, boardEntity);
			likeRepository.save(likeEntity);
			boardRepository.plusLike(boardId);
			// boardRepository에서 파라미터로 가지고온 boardId값이 boardEntity의 id값과 일치하면 좋아요수 1 증가
			return 1;
		
		}else {
			likeRepository.deleteByBoardEntity_IdAndMemberEntity_Id(boardId, memberId);
			boardRepository.minusLike(boardId);
			// boardRepository에서 파라미터로 가지고온 boardId값이 boardEntity의 id값과 일치하면 좋아요수 1 감소
			return 0;
		}
	}

	

	


	


}
