# consul-reg-agent

A straightforward java agent to allow registering java services in consul without modifying their code.

# example usage

see `run-test.sh` for example usage

# response

```json
[
    {
        "Checks": [
            {
                "CheckID": "serfHealth", 
                "Name": "Serf Health Status", 
                "Node": "Jesse.local", 
                "Notes": "", 
                "Output": "Agent alive and reachable", 
                "ServiceID": "", 
                "ServiceName": "", 
                "Status": "passing"
            }
        ], 
        "Node": {
            "Address": "192.168.0.105", 
            "Node": "Jesse.local"
        }, 
        "Service": {
            "Address": "", 
            "ID": "zoidberg_service", 
            "Port": 12345, 
            "Service": "zoidberg", 
            "Tags": [
                "service"
            ]
        }
    }
]
```

# assumptions

This assumes you have consul available at `localhost:8500`. I don't want this to have any config file dependencies, so if this is not the case for you, feel free to fork and roll your own version.

Also under the current iteration, any registration or deregistration errors that occur will not block the app from running, but that may change in the future.