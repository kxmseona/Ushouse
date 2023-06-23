// add-button 요소 선택
var addButton = document.getElementById('add-button');

// add-button 클릭 이벤트 핸들러
addButton.addEventListener('click', function() {
  window.location.href = '/community_post/community_post.html';
});

// 게시글 목록을 가져와서 화면에 표시하는 함수
function displayPosts() {
  var posts = JSON.parse(localStorage.getItem('posts')) || [];
  var listContainer = document.getElementById('post-list');

  // 이전에 표시된 게시글 목록 삭제
  while (listContainer.firstChild) {
    listContainer.removeChild(listContainer.firstChild);
  }

  // 게시글 목록 생성
  for (var i = 0; i < posts.length; i++) {
    var post = posts[i];
    var listItem = document.createElement('li');
    listItem.textContent = post.title;
    listContainer.appendChild(listItem);
  }
}

// 페이지 로드 시 게시글 목록 표시
displayPosts();
