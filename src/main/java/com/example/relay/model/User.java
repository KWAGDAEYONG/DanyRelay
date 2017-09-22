package com.example.relay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String eamil;
    private String password;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Contribution> contributions;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }



    public Long getId() {
        return id;
    }

    public String getEamil() {
        return eamil;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

}
