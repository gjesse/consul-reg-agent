package net.loshodges.consul;

import java.lang.instrument.Instrumentation;

public class Register {

    public static void premain(final String agentArgs, Instrumentation inst) {

        try {
            final Config config = Config.parseConfig(agentArgs);
            final Registration registration = new Registration(config);
            registration.doRegistration();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
