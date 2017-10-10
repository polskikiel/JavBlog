package pl.blog.dto;

/**
 * Created by Mike on 10.08.2017.
 */
public class MessageDTO {
    private String search;
    private String message;
    private String title;

    public MessageDTO(String search, String message, String title) {
        this.search = search;
        this.message = message;
        this.title = title;
    }

    public MessageDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
