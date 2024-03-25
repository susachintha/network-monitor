# Read Me First
This assignment has been completed using Java and Spring Boot as a microservice. In memory database has been used by initialising 
H2 dialect for Hibernate. `RequestService` links to `RequestRepository` for storing `Request` data objects. `Request` represents
each line of the log file. 

Following two methods makes the foundation for data extraction from the log file in `RequestService`

```Java
    public String extractIPAddress(String logLine) {
    }
    
    public String extractURL(String logLine) {
    }
```
Then the following method use them to create the POJO object `RequestRecord`
```java
    public RequestRecord extractRequest(String logLine) {
        return new RequestRecord(extractIPAddress(logLine), extractURL(logLine));
    }
```

Following method is to save the created data transfer object
```java
    public Request saveRequest(RequestRecord requestRecord) {
    
    }
```
Query using the following methods
```java
    List<UrlCount> findMostVisitedUrls(Long numberOfTopUrls) {
        return requestRepository.findMostVisitedUrls(numberOfTopUrls);
    }

    List<AddressCount> findMostActiveIPAddresses(Long numberOfTopAddresses) {
        return requestRepository.findMostActiveIPAddresses(numberOfTopAddresses);
    }
```

## Tests
* Unit tests have been added in `com.group.mantel.assignment.networkmonitor.RequestRepositoryTests` and `com.group.mantel.assignment.networkmonitor.RequestServiceTests` files.
* Alternatively run `gradlew test` to execute all tests at once

## Assumptions
Following assumptions made during the development of this assignment
### Input Data 
* Input data set is relatively small therefore data has been loaded to volatile in-memory database
* Data clean is not required, the format of IP addresses and urls present valid data
* In order to query most visited 3 URLs or most active IP addresses, there should be enough data. Because if there is no significant difference of data, the results could be inaccurate.
* For the demonstration purposes, the input file used only from tests.
* Log file contains only GET requests
* User type (eg. admin) of each http request has not been considered for query data
* Small set of input data has been read by a BufferedReader line by line. For bulk data, streaming might be suitable

### Services
* Only a single service created for demonstration purposes, assuming the data is not ingested via http
* API security hasn't been considered
* API supports the String/Log file data extraction specifically for the given input file and its format
* Error handling and Logging not required for the demo
* Only the IP addressess and URLs are loaded to database, omitting the rest of the data in the input file
* File reader has been used only inside the tests to demonstrate. This is suitable to move to a service and expose as an api to input the file as whole


