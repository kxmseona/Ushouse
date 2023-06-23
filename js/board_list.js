$(document).ready(function() {
  // 글 목록 표시
  displayPosts();

  // 글 목록에서 글 클릭 시 이벤트 핸들러
  $(document).on("click", ".board-title", function(event) {
    event.preventDefault(); // 기본 동작(링크 이동) 방지

    var boardId = $(this).data("id"); // 클릭한 글의 ID
    var boardItem = boardData.find(function(item) {
      return item.id === boardId;
    });

    if (boardItem) {
      // Redirect to board_view.html with query string
      window.location.href = "/bord_view/board_view.html?id=" + boardId;
    }
  });

  // 글 목록을 표시하는 함수
  function displayPosts() {
    var postList = $("#post-list");
    postList.empty();

    var boardData = JSON.parse(localStorage.getItem("boardData")) || [];

    boardData.forEach(function(boardItem) {
      var listItem = $("<li>").addClass("board-title");
      listItem.data("id", boardItem.id);
      listItem.css({
        "cursor": "pointer",
        "overflow": "hidden",
        "text-overflow": "ellipsis",
        "white-space": "nowrap",
      }).text(boardItem.title);
      postList.append(listItem);
    });
  }
});
