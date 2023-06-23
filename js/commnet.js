// 댓글 작성 기능
document.getElementById("comment-form").addEventListener("submit", function(event) {
    event.preventDefault();
    const commentInput = document.getElementById("comment-input");
    const cmcontent = commentInput.value.trim();
    if (cmcontent !== "") {
      const comment = createComment("익명", cmcontent);
      addComment(comment);
      commentInput.value = "";
    }
});

// 대댓글 작성 기능
function replyToComment(commentId) {
    const replyForm = document.createElement("div");
    replyForm.className = "reply-form";
    replyForm.innerHTML = `
      <input type="text" class="reply-input" placeholder="댓글을 작성하세요">
      <button class="submit-reply" onclick="submitReply(${commentId})">작성</button>
    `;
    const comment = document.getElementById(`comment-${commentId}`);
    comment.appendChild(replyForm);
}

function submitReply(commentId) {
    const replyInput = document.querySelector(`#comment-${commentId} .reply-input`);
    const cmcontent = replyInput.value.trim();
    if (cmcontent !== "") {
      const reply = createComment("익명", cmcontent);
      addComment(reply, commentId);
      replyInput.value = "";
    } else {
      // 내용이 작성되지 않았을 경우에 대한 처리
      alert("댓글 내용을 작성해주세요.");
    }
}

// 댓글 생성 함수
function createComment(cmauthor, cmcontent) {
    return {
      id: new Date().getTime(), // 댓글 고유 ID 생성 (시간 기반)
      cmauthor: cmauthor,
      cmcontent: cmcontent,
      replies: []
    };
}

// 댓글 추가 함수
function addComment(comment, parentId = null) {
    const commentList = document.getElementById("comment-list");
  
    // 댓글 요소 생성
    const commentElement = document.createElement("li");
    commentElement.className = parentId !== null ? "reply-comment" : "comment";
    commentElement.id = `comment-${comment.id}`;
    commentElement.innerHTML = `
      <span class="cmauthor">${comment.cmauthor}</span>
      <div class="cmcontent">${comment.cmcontent}</div>
    `;
  
    // 대댓글이 아닌 경우에만 답글 버튼 생성
    if (parentId === null) {
      const replyButton = document.createElement("button");
      replyButton.className = "reply-button";
      replyButton.textContent = "답글";
      replyButton.onclick = function() {
        replyToComment(comment.id);
      };
      commentElement.appendChild(replyButton);
    }
  
    // 대댓글이면 부모 댓글 아래에 추가
    if (parentId !== null) {
      const parentComment = document.getElementById(`comment-${parentId}`);
      const replyForm = parentComment.querySelector(".reply-form");
      parentComment.insertBefore(commentElement, replyForm);
    } else {
      commentList.appendChild(commentElement);
    }
  }
  