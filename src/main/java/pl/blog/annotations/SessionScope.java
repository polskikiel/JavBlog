package pl.blog.annotations;

import org.springframework.context.annotation.Scope;

/**
 * Created by Majk on 2017-10-05.
 */
@Scope("session")
public @interface SessionScope {         // my own annotation which contains all annotations above
}
