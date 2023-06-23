// 이미지 미리보기를 위한 함수
function previewImages() {
  var previewContainer = document.getElementById('preview-container');
  var uploadInput = document.getElementById('upload-input');

  // 이전에 표시된 미리보기 이미지 삭제
  while (previewContainer.firstChild) {
    previewContainer.removeChild(previewContainer.firstChild);
  }

  // 선택한 이미지 파일들을 미리보기에 추가
  var files = uploadInput.files;
  for (var i = 0; i < files.length; i++) {
    var file = files[i];
    var reader = new FileReader();

    reader.onload = (function (curFile) {
      return function (event) {
        var image = document.createElement('img');
        image.src = event.target.result;

        var previewItem = document.createElement('div');
        previewItem.classList.add('preview-item');
        previewItem.appendChild(image);

        var deleteIcon = document.createElement('i');
        deleteIcon.classList.add('fas', 'fa-trash-alt', 'delete-icon');
        deleteIcon.addEventListener('click', function () {
          // 클릭한 삭제 아이콘의 부모 요소인 미리보기 항목을 제거
          previewContainer.removeChild(previewItem);
        });
        previewItem.appendChild(deleteIcon);

        previewContainer.appendChild(previewItem);
      };
    })(file);

    reader.readAsDataURL(file);
  }
}

// 파일 선택 시 이미지 미리보기 함수 호출
document.getElementById('upload-input').addEventListener('change', previewImages);

// 작성 완료 버튼 클릭 시 데이터 저장 및 페이지 이동
document.getElementById('submit-btn').addEventListener('click', function (event) {
  event.preventDefault(); // submit 이벤트 기본 동작 막기

  var title = document.getElementById('title').value;
  var content = document.getElementById('content').value;
  var imageFiles = document.getElementById('upload-input').files;

  // 데이터 유효성 검사
  if (title.trim() === '' || content.trim() === '') {
    alert('제목과 내용을 입력해주세요.');
    return;
  }

  var post = {
    title: title,
    content: content,
    images: []
  };

  // 이미지 파일을 Base64 문자열로 변환하여 저장
  if (imageFiles.length > 0) {
    var imageCount = imageFiles.length;
    var completedCount = 0;

    for (var i = 0; i < imageFiles.length; i++) {
      var file = imageFiles[i];
      var reader = new FileReader();

      reader.onload = function (event) {
        var imageData = event.target.result;
        post.images.push(imageData);

        completedCount++;

        // 모든 이미지 저장 후 로컬 스토리지에 데이터 저장
        if (completedCount === imageCount) {
          var posts = JSON.parse(localStorage.getItem('posts')) || [];
          posts.push(post);
          localStorage.setItem('posts', JSON.stringify(posts));

          // 데이터 저장 후 페이지 이동
          alert('게시글이 작성되었습니다.');
          window.location.href = '/community_view/community_view.html';
        }
      };

      reader.readAsDataURL(file);
    }
  } else {
    // 이미지 선택이 없을 경우
    var posts = JSON.parse(localStorage.getItem('posts')) || [];
    posts.push(post);
    localStorage.setItem('posts', JSON.stringify(posts));

    // 데이터 저장 후 페이지 이동
    alert('게시글이 작성되었습니다.');
    window.location.href = '/community_view/community_view.html';
  }
});