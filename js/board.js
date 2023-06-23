// 파일 선택 시 이미지 미리보기
const inputElement = document.getElementById("upload-input");
const previewContainer = document.getElementById("preview-container");

inputElement.addEventListener("change", handleFileSelect);

// 이미지 미리보기 초기화
function initializePreview() {
  previewContainer.innerHTML = "";
  localStorage.removeItem("uploadedImages");

  const storedImages = JSON.parse(localStorage.getItem("uploadedImages")) || [];
  storedImages.forEach(function (imageSrc) {
    const previewItem = createPreviewItem(imageSrc);
    previewContainer.appendChild(previewItem);
  });
}


// 이미지 선택 이벤트 핸들러
function handleFileSelect(event) {
  const files = event.target.files;
  const maxFiles = Math.min(files.length, 10);

  for (let i = 0; i < maxFiles; i++) {
    const file = files[i];
    const reader = new FileReader();

    reader.onload = function (e) {
      const imageSrc = e.target.result;
      const previewItem = createPreviewItem(imageSrc);
      previewContainer.appendChild(previewItem);
      saveImageToLocalStorage(imageSrc);
    };

    reader.readAsDataURL(file);
  }
}

// 이미지 미리보기 아이템 생성
function createPreviewItem(imageSrc) {
  const previewItem = document.createElement("div");
  previewItem.classList.add("preview-item");

  const image = document.createElement("img");
  image.src = imageSrc;
  image.style.width = "100%";
  image.style.height = "100%";
  image.style.objectFit = "cover";

  const deleteIcon = document.createElement("i");
  deleteIcon.classList.add("fas", "fa-trash-alt", "delete-icon");
  deleteIcon.addEventListener("click", function () {
    deletePreviewItem(previewItem, imageSrc);
  });

  previewItem.appendChild(image);
  previewItem.appendChild(deleteIcon);

  return previewItem;
}


// 이미지 미리보기 아이템 삭제
function deletePreviewItem(previewItem, imageSrc) {
  previewItem.remove();
  removeImageFromLocalStorage(imageSrc);
}

// 이미지 로컬 스토리지에서 삭제
function removeImageFromLocalStorage(imageSrc) {
  const storedImages = JSON.parse(localStorage.getItem("uploadedImages")) || [];
  const updatedImages = storedImages.filter(function (src) {
    return src !== imageSrc;
  });
  localStorage.setItem("uploadedImages", JSON.stringify(updatedImages));
}

// 이미지 로컬 스토리지에 저장
function saveImageToLocalStorage(imageSrc) {
  const storedImages = JSON.parse(localStorage.getItem("uploadedImages")) || [];
  storedImages.push(imageSrc);
  localStorage.setItem("uploadedImages", JSON.stringify(storedImages));
}

// 페이지 로드 시 이미지 미리보기 초기화
document.addEventListener("DOMContentLoaded", initializePreview);

// 새로고침 시 확인 메시지 표시
window.addEventListener("beforeunload", function (event) {
  const form = document.getElementById("boardForm");
  if (form.elements.length > 0) {
    event.preventDefault();
    event.returnValue = "작성 중인 내용이 있습니다. 페이지를 나가시겠습니까?";
  }
});

// Form submission event listener
const form = document.getElementById("boardForm");
form.addEventListener("submit", function (event) {
  event.preventDefault(); // Prevent form submission

  // Get form values
  const author = document.getElementById("nickname").textContent;
  const title = document.getElementById("title").value;
  const content = document.getElementById("content").value;
  const deposit = document.getElementById("deposit-input").value;
  const rent = document.getElementById("rent-input").value;
  const radio = document.querySelector('input[name="chk_info"]:checked').value;
  const fileImages = getUploadedImages();

  // Create data object
  const boardItem = {
    author: author,
    title: title,
    content: content,
    deposit: deposit,
    rent: rent,
    radio: radio,
    fileImages: fileImages,
  };

  // Save data to localStorage
  localStorage.setItem("boardItem", JSON.stringify(boardItem));

    // Show confirmation message
    const confirmMessage = "작성하시겠습니까?";
    if (confirm(confirmMessage)) {
      // Redirect to board_view.html
      window.location.href = "/board_view/board_view.html";
    } else {
      // Cancel form submission
      event.preventDefault();
    }

  // Redirect to board_view.html
  window.location.href = "/board_view/board_view.html";
});

// Function to get uploaded images
function getUploadedImages() {
  const previewItems = document.querySelectorAll(".preview-item");
  const images = [];
  previewItems.forEach(function (item) {
    const image = item.querySelector("img");
    if (image) {
      images.push(image.src);
    }
  });
  return images;
}
