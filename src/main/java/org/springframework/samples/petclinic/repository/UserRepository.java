package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.User;

public interface UserRepository {

    void save(User user);
}
