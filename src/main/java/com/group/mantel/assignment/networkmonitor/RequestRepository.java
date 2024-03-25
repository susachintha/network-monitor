package com.group.mantel.assignment.networkmonitor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends Repository<Request, Long> {

    Request save(Request request);

    Optional<Request> findById(long id);

    @Query(value = "SELECT DISTINCT url from request", nativeQuery = true)
    List<String> findDistinctUrl();

}
