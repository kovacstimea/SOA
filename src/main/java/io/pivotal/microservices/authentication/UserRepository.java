package io.pivotal.microservices.authentication;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User findByUsernameAndPassword(String username,String password);

}
