package net.loshodges.consul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registration {
    private final Config config;

    public Registration(Config config) {
        this.config = config;
    }



    public void doRegistration() {
        register();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                deRegister();
            }


        }));
    }

    private void deRegister() {
        try {
            URL url = new URL("http://localhost:8500/v1/agent/service/deregister/" +  config.getServiceId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/json");
            conn.setRequestProperty("charset", "UTF-8");
            conn.connect();
            conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void register() {

        String payload = makePayload();
        try {
            URL url = new URL("http://localhost:8500/v1/agent/service/register");
            byte[] postDataBytes = payload.getBytes("UTF-8");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for ( int c = in.read(); c != -1; c = in.read() )
                System.out.print((char)c);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String makePayload() {
        String tpl = "{\"ID\": \"%s\",\"Name\": \"%s\",%s \"Port\": %d}";
        return String.format(tpl, config.getServiceId(), config.getServiceName(), getTags(), config.getPort());
    }

    public String getTags() {
        if (config.getServiceTags().isEmpty()) {
            return "";
        }
        else {
            return String.format("\"Tags\": [%s],", config.joinedTags(",", true));
        }
    }
}
