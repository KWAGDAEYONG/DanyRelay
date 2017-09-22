package com.example.relay.model;

import javax.persistence.*;

@Entity
public class Contribution {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(foreignKey = @ForeignKey(name="fk_contribution_user"))
    private User user;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(foreignKey = @ForeignKey(name="fk_contribution_work"))
    private Work work;

    /*
    기여도 기준:
    프로젝트 생성자 : 20점
    콘텐츠 추가기여자 : 10점
    투표 참여자 : 2점
    동의한 내용이 콘텐츠로 추가될 경우 : 2점
    해당 프로젝트에서의 모든 유저의 총점 : 해당 프로젝트에서의 내 총점 = 100 : x 에서 x값 저장(백분위)
    */
    private double score;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Work getWork() {
        return work;
    }

    public double getScore() {
        return score;
    }

    public void contribute(String act){
        ContributeMethod contributeMethod = ContributeMethod.valueOf(act);
        this.score += contributeMethod.getScore();
    }

}

enum ContributeMethod{
    CREATE(20), ADD(10), VOTE(2), PASS_VOTE(2);

    private double score;

    ContributeMethod(double score){
        this.score = score;
    }

    public double getScore(){
        return score;
    }
}


