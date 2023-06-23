// 좋아요 목록 데이터 가져오기
function getLikeList() {
  // 실제로는 localStorage.getItem('likedPosts')를 사용하여 데이터를 가져옵니다.
  var likedPosts = JSON.parse(localStorage.getItem('likedPosts')) || [];
  return likedPosts;
}

// 좋아요 목록 표시하기
function displayLikeList() {
  var likeListElement = document.getElementById("like-list");
  var likedPosts = getLikeList();

  if (likedPosts.length === 0) {
    var emptyMessage = document.createElement("p");
    emptyMessage.textContent = "좋아요한 글이 없습니다.";
    likeListElement.appendChild(emptyMessage);
    return;
  }

  // 좋아요한 글을 역순으로 반복하여 추가합니다.
  for (var i = likedPosts.length - 1; i >= 0; i--) {
    var post = likedPosts[i];

    var likeItem = document.createElement("div");
    likeItem.classList.add("like-item");

    var titleElement = document.createElement("div");
    titleElement.classList.add("title");
    titleElement.textContent = post.title;

    var contentElement = document.createElement("div");
    contentElement.classList.add("content");
    contentElement.textContent = post.content;

    var dateElement = document.createElement("div");
    dateElement.classList.add("date");
    dateElement.textContent = formatDate(post.date);

    likeItem.appendChild(titleElement);
    likeItem.appendChild(contentElement);
    likeItem.appendChild(dateElement);

    likeListElement.appendChild(likeItem);

    // 클릭 이벤트 리스너를 등록합니다.
    likeItem.addEventListener("click", function () {
      // 게시물 정보를 가져옵니다.
      var boardData = {
        title: post.title,
        content: post.content,
        // 다른 정보도 추가합니다.
        author: post.author,
        deposit: post.deposit,
        rent: post.rent,
        radio: post.radio,
        fileImages: post.fileImages
        // 필요한 경우에 따라 더 많은 정보를 가져올 수 있습니다.
      };

      // 게시물 정보를 localStorage에 저장합니다.
      localStorage.setItem("boardItem", JSON.stringify(boardData));

      // board_view.html로 페이지 이동합니다.
      window.location.href = "/board_view/board_view.html";
    });
  }
}

// 페이지 로드 시 좋아요 목록 표시
window.addEventListener("load", function () {
  displayLikeList();
});


// 날짜 형식 변환
function formatDate(dateString) {
  var date = new Date(dateString);
  var year = date.getFullYear();
  var month = String(date.getMonth() + 1).padStart(2, '0');
  var day = String(date.getDate()).padStart(2, '0');
  var formattedDate = year + '-' + month + '-' + day;
  return formattedDate;
}