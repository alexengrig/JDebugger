package dev.alexengrig.myjdi.connect;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.IOException;

@FunctionalInterface
public interface Attacher {
    VirtualMachine attach() throws IOException, IllegalConnectorArgumentsException;
}
