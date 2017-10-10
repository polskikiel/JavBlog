package pl.blog.dto;

public class CommentCommentsDTO {
    private String body;
    private Long commentId;


    public CommentCommentsDTO() {
    }

    public CommentCommentsDTO(String body, Long commentId) {
        this.body = body;
        this.commentId = commentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
