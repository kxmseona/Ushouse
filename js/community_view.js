// Local Storage에서 저장된 제목, 내용, 이미지를 가져옵니다.
const storedTitle = localStorage.getItem("community_title");
const storedContent = localStorage.getItem("community_content");
const storedImages = JSON.parse(localStorage.getItem("community_images")); // 여러 장의 이미지를 배열로 가져옵니다.

// HTML 요소에 저장된 값들을 설정합니다.
document.getElementById("title").innerText = storedTitle;
document.getElementById("content").innerText = storedContent;

// 이미지를 표시하기 위한 변수들을 초기화합니다.
let currentImageIndex = 0;
const previewContainer = document.getElementById("preview-container");
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");

// 이미지를 표시하는 함수를 정의합니다.
function displayImage(imageIndex) {
  previewContainer.innerHTML = `<div class="preview-item"><img src="${storedImages[imageIndex]}" alt="이미지"></div>`;
}

// 초기 이미지를 표시합니다.
displayImage(currentImageIndex);

// "이전" 버튼 클릭 시 이벤트 핸들러
prevButton.addEventListener("click", function() {
  if (currentImageIndex > 0) {
    currentImageIndex--;
    displayImage(currentImageIndex);
  }
});

// "다음" 버튼 클릭 시 이벤트 핸들러
nextButton.addEventListener("click", function() {
  if (currentImageIndex < storedImages.length - 1) {
    currentImageIndex++;
    displayImage(currentImageIndex);
  }
});

// "뒤로 가기" 버튼 클릭 시 이벤트 핸들러
document.getElementById("back-button").addEventListener("click", function() {
  history.back();
});

// 신고하기 버튼 클릭 시 이벤트 핸들러
document.getElementById("moreOptions").addEventListener("click", function(event) {
  event.preventDefault();
  // 신고하기 기능 구현
});