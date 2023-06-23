document.addEventListener("DOMContentLoaded", function() {
  const boardData = JSON.parse(localStorage.getItem("boardItem")) || {
    author: "",
    title: "",
    content: "",
    deposit: 0,
    rent: 0,
    radio: "",
    fileImages: []
  };

  displayListingData(boardData);
  createPreviewImages(boardData.fileImages);
  addButtons();
  setupArrowButtons();

  function displayListingData(data) {
    document.getElementById("author").textContent = `작성자: ${data.author}`;
    document.getElementById("title").textContent = `${data.title}`;
    document.getElementById("content").textContent = `${data.content}`;
    document.getElementById("deposit").textContent = `보증금: ${data.deposit}`;
    document.getElementById("rent").textContent = `월세: ${data.rent}`;
    document.getElementById("management-fee").textContent = `(${data.radio === "included" ? "관리비 포함" : "관리비 미포함"})`;
  }

  function createPreviewImages(images) {
    const previewContainer = document.getElementById("preview-container");
    previewContainer.innerHTML = "";
  
    const maxImages = Math.min(images.length, 10); // 최대 10장의 이미지만 표시
  
    for (let i = 0; i < maxImages; i++) {
      const imageSrc = images[i];
      const previewItem = createPreviewItem(imageSrc);
      previewContainer.appendChild(previewItem);
    }
  }
  

  function createPreviewItem(imageSrc) {
    const previewItem = document.createElement("div");
    previewItem.classList.add("preview-item");

    const image = document.createElement("img");
    image.src = imageSrc;
    image.style.width = "100%";
    image.style.height = "100%";
    image.style.objectFit = "cover";

    previewItem.appendChild(image);

    return previewItem;
  }

  function addButtons() {
    const boardActions = document.createElement("div");
    boardActions.classList.add("board-actions");

    const heartIcon = document.createElement("i");
    heartIcon.classList.add("far", "fa-heart", "heart-icon");

    const chatButton = document.createElement("button");
    chatButton.classList.add("chat-button");
    chatButton.textContent = "채팅하기";

    heartIcon.addEventListener("click", function() {
      heartIcon.classList.toggle("far");
      heartIcon.classList.toggle("fas");
      
      // 게시물 정보 가져오기
      var boardData = JSON.parse(localStorage.getItem("boardItem"));
    
      // 좋아요 게시물 목록 가져오기
      var likedPosts = JSON.parse(localStorage.getItem("likedPosts")) || [];
    
      // 게시물 정보를 좋아요 게시물 목록에 추가
      likedPosts.push({
        title: boardData.title,
        content: boardData.content,
        date: new Date().toISOString()
      });
    
      // 좋아요 게시물 목록을 localStorage에 저장
      localStorage.setItem("likedPosts", JSON.stringify(likedPosts));
    
      // 좋아요한 글을 알림 또는 원하는 처리를 추가할 수 있습니다.
    
      // 저장된 게시물 목록을 확인하기 위해 콘솔에 출력합니다.
      console.log("Liked Posts:", likedPosts);
    });

    boardActions.appendChild(heartIcon);
    boardActions.appendChild(chatButton);

    const board = document.querySelector(".board");
    board.appendChild(boardActions);
  }

  function setupArrowButtons() {
    const prevButton = document.getElementById("prev-button");
    const nextButton = document.getElementById("next-button");
    const previewItems = document.querySelectorAll(".preview-item");
    let currentIndex = 0;

    prevButton.addEventListener("click", function() {
      if (currentIndex > 0) {
        currentIndex--;
        updatePreview();
      }
    });

    nextButton.addEventListener("click", function() {
      if (currentIndex < previewItems.length - 1) {
        currentIndex++;
        updatePreview();
      }
    });

    function updatePreview() {
      previewItems.forEach(function(item, index) {
        if (index === currentIndex) {
          item.style.display = "block";
        } else {
          item.style.display = "none";
        }
      });
    }

    updatePreview();
  }
});
