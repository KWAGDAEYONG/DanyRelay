package com.example.relay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Work {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "work")
    private List<Contribution> contributions;
    private String title;
    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void addContent(String content){
        this.content += content;
    }
}
