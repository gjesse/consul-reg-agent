package net.loshodges.consul;

import java.util.ArrayList;
import java.util.List;

public class Config {


    private final String serviceName;
    private final List<String> serviceTags;
    private final Integer port;

    public Config(String serviceName, List<String> serviceTags, Integer port) {

        this.serviceName = serviceName;
        this.serviceTags = serviceTags;
        this.port = port;
    }

    @Override
    public String toString() {
        return "Config{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceTags=" + serviceTags +
                ", port=" + port +
                '}';
    }


    public String getServiceName() {
        return serviceName;
    }

    public List<String> getServiceTags() {
        return serviceTags;
    }

    public Integer getPort() {
        return port;
    }

    public String getServiceId() {
        if (serviceTags.isEmpty()) {
            return getServiceName();
        } else {
            return getServiceName() + "_" + joinedTags("_", false);
        }
    }

    public String joinedTags(String separator, boolean quoted) {
        String joined = "";
        int tagCount = getServiceTags().size();
        for (int i = 0; i < tagCount; i++) {
            if (quoted) {
                joined += "\"";
            }
            joined += getServiceTags().get(i);
            if (quoted) {
                joined += "\"";
            }
            if (i + 1 < tagCount) {
                joined += separator;
            }
        }
        return joined;
    }

    public static Config parseConfig(String agentArgs) {


        String[] terms = agentArgs.split(",");

        String serviceId = null;
        List<String> serviceTags = new ArrayList<>();
        Integer port = null;
        for (String term : terms) {

            String[] kv = term.split(":");
            if (kv.length != 2) {
                throw new IllegalArgumentException("illegal kv length for " + term);
            }
            String k = kv[0];
            String v = kv[1];
            if (k.equals("name") && serviceId == null) {
                serviceId = v;
            } else if (k.equals("tag")) {
                serviceTags.add(v);
            } else if (k.equals("port")) {
                port = Integer.parseInt(v);
            } else {
                throw new IllegalArgumentException("unknown key " + term);
            }


        }
        return new Config(serviceId, serviceTags, port);
    }
}
