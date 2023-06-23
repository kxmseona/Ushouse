function toggleMoreOptions(event) {
    event.preventDefault(); // 폼 전송 막기
    var moreOptions = document.getElementById("moreOptions");
    moreOptions.classList.toggle("show");
    event.stopPropagation();
  }
  
  
  // More Options 영역 외의 다른 부분을 클릭했을 때 More Options 영역을 닫기 위한 이벤트 핸들러
  document.addEventListener("click", function(event) {
    var moreOptions = document.getElementById("moreOptions");
    if (!event.target.matches(".more-btn") && !moreOptions.contains(event.target)) {
      moreOptions.classList.remove("show");
    }
  });
  
  
  function scrollToBottom() {
    var chatRoom = document.getElementById("chat-room");
    chatRoom.scrollTop = chatRoom.scrollHeight;
  }
  
function getCurrentTime() {
  var date = new Date();
  var hours = date.getHours();
  var minutes = date.getMinutes();
  var ampm = hours >= 12 ? 'PM' : 'AM';
  hours %= 12;
  hours = hours ? hours : 12;
  minutes = minutes < 10 ? '0' + minutes : minutes;
  var time = hours + ':' + minutes + ' ' + ampm;
  return time;
}
  
  function resetInputSize() {
    var input = document.getElementById("messageInput");
    input.style.height = ""; // 입력창의 높이 초기화
  }
  
  function sendMessage(message) {
    var messageInput = document.getElementById("messageInput");
    message = message.trim();
    if (message !== "") {
      var chatRoom = document.getElementById("chat-room");
  
      var bubble = document.createElement("div");
      bubble.className = "bubble my-message";
      bubble.innerHTML = `
        <p>${message}</p>
        <span class="time-me">${getCurrentTime()}</span>
      `;
  
      chatRoom.insertBefore(bubble, messageInput.parentNode);
  
      messageInput.value = "";
      chatRoom.scrollTop = chatRoom.scrollHeight;
    }
    resetInputSize(); // 입력창 크기 초기화
  }
  
  function sendMessageFromButton() {
    var message = document.getElementById("messageInput").value.trim();
    event.preventDefault();
    if (message !== "") {
      sendMessage(message);
      document.getElementById("messageInput").value = "";
    }
  }

  function receiveMessage(message) {
    var chatRoom = document.getElementById("chat-room");
  
    var bubble = document.createElement("div");
    bubble.className = "bubble other-message";
    bubble.innerHTML = `
      <p>${message}</p>
      <span class="time-other">${getCurrentTime()}</span>
    `;
  
    chatRoom.insertBefore(bubble, document.getElementById("messageInput").parentNode);
  
    chatRoom.scrollTop = chatRoom.scrollHeight;
  }
  
  // 예시로 상대방이 보낸 채팅 데이터를 받아와서 receiveMessage 함수를 호출합니다.
  var receivedMessage = "안녕하세요!";
  receiveMessage(receivedMessage);
  
  
  function handleImageUpload(event) {
    var imageFile = event.target.files[0];
    if (imageFile) {
      var reader = new FileReader();
      reader.onload = function(e) {
        var chatRoom = document.getElementById("chat-room");
    
        var bubble = document.createElement("div");
        bubble.className = "bubble my-message";
    
        var thumbnailImage = new Image();
        thumbnailImage.src = e.target.result;
        thumbnailImage.onload = function() {
          var resizedImage = resizeImage(thumbnailImage, 300); // 작은 크기로 조정
    
          var imageWrapper = document.createElement("div");
          imageWrapper.className = "image-wrapper";
          imageWrapper.innerHTML = `
            <img src="${resizedImage}" alt="전송된 이미지">
            <span class="time-me">${getCurrentTime()}</span>
          `;
          imageWrapper.addEventListener("click", function() {
            openModal(imageFile); // 원본 이미지를 모달로 보여줌
          });
    
          bubble.appendChild(imageWrapper);
    
          chatRoom.insertBefore(bubble, document.getElementById("messageInput").parentNode);
    
          chatRoom.scrollTop = chatRoom.scrollHeight;
        };
      };
    
      reader.readAsDataURL(imageFile);
    }
    // 이미지 선택창이 닫히도록 추가
    event.target.value = "";
    
    // 이미지 업로드 이벤트 핸들러 제거
    fileInput.removeEventListener("change", handleImageUpload);
  }
  
  function openModal(imageFile) {
    var modal = document.getElementById("modal");
    var modalImage = document.getElementById("modalImage");
    var reader = new FileReader();
    reader.onload = function(e) {
      modalImage.src = e.target.result; // 원본 이미지를 모달에 표시
      modal.style.display = "block";
  
      // 이미지 클릭 시 모달 닫기
      modalImage.addEventListener("click", closeModal);
    };
    reader.readAsDataURL(imageFile);
  }
  
  function closeModal() {
    var modal = document.getElementById("modal");
    modal.style.display = "none";
  
    // 모달 닫을 때 모달 내의 클릭 이벤트 리스너 제거
    modalImage.removeEventListener("click", closeModal);
  }
  
  
  // 이미지 클릭 시 모달 열기
  document.addEventListener("click", function(event) {
    if (event.target.matches(".bubble.my-message img")) {
      var imageSrc = event.target.src;
      openModal(imageSrc);
    }
  });
  
  // 이미지 클릭 이벤트 위임
  document.getElementById("chat-room").addEventListener("click", function(event) {
    if (event.target.matches(".bubble.my-message img")) {
      var imageSrc = event.target.src;
      openModal(imageSrc);
    }
  });
  
  
  
  
  // chat-input input 요소에 keydown 이벤트 리스너 추가
  var chatInput = document.getElementById("messageInput");
  chatInput.addEventListener("keydown", function(event) {
    if (event.keyCode === 13 && !event.shiftKey) {
      event.preventDefault();
      var message = chatInput.value.trim();
      if (message !== "") {
        sendMessage(message);
        chatInput.value = "";
      }
    }
  });
  
  
 // 이미지 아이콘 클릭 시 파일 선택 이벤트 처리
var fileInput = document.createElement("input");
fileInput.type = "file";
fileInput.accept = "image/*";
fileInput.style.display = "none";

imageIcon.addEventListener("click", function() {
  fileInput.click();
});

fileInput.addEventListener("change", function() {
  var file = fileInput.files[0];
  if (file) {
    var reader = new FileReader();
    reader.onload = function(e) {
      var image = new Image();
      image.src = e.target.result;
      image.onload = function() {
        var resizedImage = resizeImage(image, 300); // 적당한 크기로 조절
        sendMessage(resizedImage); // 조절된 이미지 보내기
      };
    };
    reader.readAsDataURL(file);
  }
});

// 이미지 크기 조절 함수
function resizeImage(image, maxSize) {
  var canvas = document.createElement("canvas");
  var width = image.width;
  var height = image.height;

  if (width > height) {
    if (width > maxSize) {
      height *= maxSize / width;
      width = maxSize;
    }
  } else {
    if (height > maxSize) {
      width *= maxSize / height;
      height = maxSize;
    }
  }

  canvas.width = width;
  canvas.height = height;

  var ctx = canvas.getContext("2d");
  ctx.drawImage(image, 0, 0, width, height);

  return canvas.toDataURL("image/jpeg/jpg"); // 이미지를 Base64 데이터 URL로 반환
}

function adjustTextareaHeight() {
  const textarea = document.getElementById("messageInput");
  textarea.style.height = "auto"; // 초기 높이로 설정

  const maxHeight = 5 * parseFloat(getComputedStyle(textarea).lineHeight);
  if (textarea.scrollHeight > maxHeight) {
    textarea.style.height = maxHeight + "px";
  } else {
    textarea.style.height = textarea.scrollHeight + "px";
  }
}

// 입력창의 내용이 변경될 때마다 호출하여 높이를 조정
document.getElementById("messageInput").addEventListener("input", adjustTextareaHeight);

// 페이지 로드 후 초기 높이 조정
window.addEventListener("load", adjustTextareaHeight);
