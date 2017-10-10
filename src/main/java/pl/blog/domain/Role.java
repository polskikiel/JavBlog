package pl.blog.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mike on 03.08.2017.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long roleid;

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}