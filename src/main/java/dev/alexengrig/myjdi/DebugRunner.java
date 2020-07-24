package dev.alexengrig.myjdi;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.connect.YouthConnector;
import dev.alexengrig.myjdi.connect.YouthConnectors;

import java.io.IOException;
import java.util.logging.Logger;

public class DebugRunner {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getName());

    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, VMStartException {
        log.info("Started.");
        String classpath = "./example/build/classes/java/main";
        String mainClass = "dev.alexengrig.example.Main";
        YouthConnector connector = YouthConnectors.commandLine(classpath, mainClass);
        YouthVirtualMachine vm = connector.connect();
        SimpleEventHandler handler = new SimpleEventHandler(vm);
        handler.run();
        log.info("Finished.");
    }
}
