package pl.blog.services;

import org.springframework.stereotype.Service;

@Service
public class SessionServices {

    public boolean setRefresh(String site) {
        if (site.contains("login") || site.contains("signup") || site.contains("signin")) {
            return true;
        }
        return false;
    }
}
