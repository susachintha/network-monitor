package com.group.mantel.assignment.networkmonitor;

import com.group.mantel.assignment.networkmonitor.model.AddressCount;
import com.group.mantel.assignment.networkmonitor.model.Request;
import com.group.mantel.assignment.networkmonitor.model.UrlCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends Repository<Request, Long> {

    Request save(Request request);

    @Query(value = "SELECT DISTINCT url from request", nativeQuery = true)
    List<String> findDistinctUrl();

    @Query("SELECT new com.group.mantel.assignment.networkmonitor.model.UrlCount(c.url, COUNT(c.url)) " +
            "FROM Request AS c GROUP BY c.url ORDER BY COUNT(c.url) DESC LIMIT :limit")
    List<UrlCount> findMostVisitedUrls(@Param("limit") Long limit);

    @Query("SELECT new com.group.mantel.assignment.networkmonitor.model.AddressCount(c.address, COUNT(c.address)) " +
            "FROM Request AS c GROUP BY c.address ORDER BY COUNT(c.address) DESC LIMIT :limit")
    List<AddressCount> findMostActiveIPAddresses(@Param("limit") Long limit);

}
