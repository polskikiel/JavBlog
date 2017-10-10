package pl.blog.domain.io;

import pl.blog.domain.Users;

public interface CommentIO {
    Users getUser();

    void setUser(Users user);

    String getBody();

    void setBody(String body);

}
