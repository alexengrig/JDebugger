package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.Map;

public final class MyConnectors {
    private MyConnectors() {
    }

//    Launcher

    public static MyConnector commandLineLaunchingConnector() {
        return connector(Connectors.commandLineLaunchingConnector());
    }

    public static MyConnector commandLineLaunchingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.commandLineLaunchingConnector(vmManger));
    }

    public static MyConnector rawCommandLineLaunchingConnector() {
        return connector(Connectors.rawCommandLineLaunchingConnector());
    }

    public static MyConnector rawCommandLineLaunchingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.rawCommandLineLaunchingConnector(vmManger));
    }

    public static MyConnector connector(LaunchingConnector connector) {
        return new MyLaunchConnector(connector);
    }

    public static MyConnector connector(LaunchingConnector connector, Map<String, Connector.Argument> arguments) {
        return new MyLaunchConnector(connector, arguments);
    }

//    Attacher

    public static MyConnector socketAttachingConnector() {
        return connector(Connectors.socketAttachingConnector());
    }

    public static MyConnector socketAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.socketAttachingConnector(vmManger));
    }

    public static MyConnector sharedMemoryAttachingConnector() {
        return connector(Connectors.sharedMemoryAttachingConnector());
    }

    public static MyConnector sharedMemoryAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.sharedMemoryAttachingConnector(vmManger));
    }

    public static MyConnector processAttachingConnector() {
        return connector(Connectors.processAttachingConnector());
    }

    public static MyConnector processAttachingConnector(VirtualMachineManager vmManger) {
        return connector(Connectors.processAttachingConnector(vmManger));
    }

    public static MyConnector connector(AttachingConnector connector) {
        return new MyAttachConnector(connector);
    }

    public static MyConnector connector(AttachingConnector connector, Map<String, Connector.Argument> arguments) {
        return new MyAttachConnector(connector, arguments);
    }
}
