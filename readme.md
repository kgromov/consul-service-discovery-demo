## Project summary

Project contains of two dummy services registered in consul as server-side discovery.  
So both services are consul clients.   
One of services uses local to another by service name.   
In contrast to eureka it's not needed to have a separate app as discovery server.   
Instead another approach is used - where `consul` instance(s) are used as discovery server/agent.
And having `spring-cloud-starter-consul-discovery` in classpath automatically triggers client-side discovery   
to default (`localhost:8500`) or configured consul uri.   
Default heartbeat is implemented with the help of `spring-boot-starter-actuator` - so it should also be added to clients   
since transitive dependency on actuator added in `spring-cloud-starter-consul` > `spring-cloud-consul-core` is optional.   

In order to run consul locally in dev mode it's required to download executable per OS, add to path and run, e.g.:
```
consul agent -dev -ui -data-dir=consul/data
```

Alternatively can be run from docker:
```
docker run -d --name consul -v consul_data:/consul/data -p 8500:8500 hashicorp/consul:latest agent -dev -ui -data-dir=/consul/data -client 0.0.0.0
```