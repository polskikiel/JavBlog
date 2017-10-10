package pl.blog.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Mike on 27.07.2017.
 */
public class ArticleDTO implements Serializable {

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 40)
    private String title;

    @NotEmpty
    @NotNull
    @Min(40)
    private String body;

    @NotNull
    @NotEmpty
    private String[] category;

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }
}
