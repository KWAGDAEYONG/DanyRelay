package com.example.relay.repository;

import com.example.relay.model.Contribution;
import com.example.relay.model.User;
import com.example.relay.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    public Contribution findByUserAndWork(User user, Work work);
}
