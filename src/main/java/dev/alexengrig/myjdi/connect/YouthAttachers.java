package dev.alexengrig.myjdi.connect;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import dev.alexengrig.myjdi.vm.YouthVirtualMachine;

import java.util.Map;

public final class YouthAttachers {
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String TIMEOUT = "timeout";
    public static final String NAME = "name";
    public static final String PID = "pid";
    public static final String CORE = "core";
    public static final String JAVA_EXECUTABLE = "javaExecutable";
    public static final String DEBUG_SERVER_NAME = "debugServerName";

    private YouthAttachers() {
    }

//    Socket

    public static YouthAttacher socketAttacher() {
        return attacher(Connectors.socketAttachingConnector());
    }

    public static YouthAttacher socketAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.socketAttachingConnector(vmManager));
    }

    public static YouthAttacher socketAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.socketAttachingConnector(), arguments);
    }

    public static YouthAttacher socketAttacher(VirtualMachineManager vmManager,
                                               Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.socketAttachingConnector(vmManager), arguments);
    }

    public static SocketAttacherBuilder socketAttacherBuilder() {
        return new SocketAttacherBuilder(Connectors.socketAttachingConnector());
    }

    public static SocketAttacherBuilder socketAttacherBuilder(VirtualMachineManager vmManager) {
        return new SocketAttacherBuilder(Connectors.socketAttachingConnector(vmManager));
    }

//    Shared Memory

    public static YouthAttacher sharedMemoryAttacher() {
        return attacher(Connectors.sharedMemoryAttachingConnector());
    }

    public static YouthAttacher sharedMemoryAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.sharedMemoryAttachingConnector(vmManager));
    }

    public static YouthAttacher sharedMemoryAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.sharedMemoryAttachingConnector(), arguments);
    }

    public static YouthAttacher sharedMemoryAttacher(VirtualMachineManager vmManager,
                                                     Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.sharedMemoryAttachingConnector(vmManager), arguments);
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder() {
        return new SharedMemoryAttacherBuilder(Connectors.sharedMemoryAttachingConnector());
    }

    public static SharedMemoryAttacherBuilder sharedMemoryAttacherBuilder(VirtualMachineManager vmManager) {
        return new SharedMemoryAttacherBuilder(Connectors.sharedMemoryAttachingConnector(vmManager));
    }

//    Process

    public static YouthAttacher processAttacher() {
        return attacher(Connectors.processAttachingConnector());
    }

    public static YouthAttacher processAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.processAttachingConnector(vmManager));
    }

    public static YouthAttacher processAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.processAttachingConnector(), arguments);
    }

    public static YouthAttacher processAttacher(VirtualMachineManager vmManager,
                                                Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.processAttachingConnector(vmManager), arguments);
    }

    public static ProcessAttacherBuilder processAttacherBuilder() {
        return new ProcessAttacherBuilder(Connectors.processAttachingConnector());
    }

    public static ProcessAttacherBuilder processAttacherBuilder(VirtualMachineManager vmManager) {
        return new ProcessAttacherBuilder(Connectors.processAttachingConnector(vmManager));
    }

//    SA Core

    public static YouthAttacher saCoreAttacher() {
        return attacher(Connectors.saCoreAttachingConnector());
    }

    public static YouthAttacher saCoreAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.saCoreAttachingConnector(vmManager));
    }

    public static YouthAttacher saCoreAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saCoreAttachingConnector(), arguments);
    }

    public static YouthAttacher saCoreAttacher(VirtualMachineManager vmManager,
                                               Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saCoreAttachingConnector(vmManager), arguments);
    }

    public static SACoreAttacherBuilder saCoreAttacherBuilder() {
        return new SACoreAttacherBuilder(Connectors.saCoreAttachingConnector());
    }

    public static SACoreAttacherBuilder saCoreAttacherBuilder(VirtualMachineManager vmManager) {
        return new SACoreAttacherBuilder(Connectors.saCoreAttachingConnector(vmManager));
    }

//    SA PID

    public static YouthAttacher saPIDAttacher() {
        return attacher(Connectors.saPIDAttachingConnector());
    }

    public static YouthAttacher saPIDAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.saPIDAttachingConnector(vmManager));
    }

    public static YouthAttacher saPIDAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saPIDAttachingConnector(), arguments);
    }

    public static YouthAttacher saPIDAttacher(VirtualMachineManager vmManager,
                                              Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saPIDAttachingConnector(vmManager), arguments);
    }

    public static SAPIDAttacherBuilder saPIDAttacherBuilder() {
        return new SAPIDAttacherBuilder(Connectors.saPIDAttachingConnector());
    }

    public static SAPIDAttacherBuilder saPIDAttacherBuilder(VirtualMachineManager vmManager) {
        return new SAPIDAttacherBuilder(Connectors.saPIDAttachingConnector(vmManager));
    }

//    SA Debug Server

    public static YouthAttacher saDebugServerAttacher() {
        return attacher(Connectors.saDebugServerAttachingConnector());
    }

    public static YouthAttacher saDebugServerAttacher(VirtualMachineManager vmManager) {
        return attacher(Connectors.saDebugServerAttachingConnector(vmManager));
    }

    public static YouthAttacher saDebugServerAttacher(Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saDebugServerAttachingConnector(), arguments);
    }

    public static YouthAttacher saDebugServerAttacher(VirtualMachineManager vmManager,
                                                      Map<String, Connector.Argument> arguments) {
        return attacher(Connectors.saDebugServerAttachingConnector(vmManager), arguments);
    }

    public static SADebugServerAttacherBuilder saDebugServerAttacherBuilder() {
        return new SADebugServerAttacherBuilder(Connectors.saDebugServerAttachingConnector());
    }

    public static SADebugServerAttacherBuilder saDebugServerAttacherBuilder(VirtualMachineManager vmManager) {
        return new SADebugServerAttacherBuilder(Connectors.saDebugServerAttachingConnector(vmManager));
    }

//    Common

    public static YouthAttacher attacher(AttachingConnector connector) {
        return () -> YouthVirtualMachine.delegate(connector.attach(connector.defaultArguments()));
    }

    public static YouthAttacher attacher(AttachingConnector connector, Map<String, Connector.Argument> arguments) {
        return () -> {
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return YouthVirtualMachine.delegate(connector.attach(args));
        };
    }

    public static AttacherBuilder attacherBuilder(AttachingConnector connector) {
        return new AttacherBuilder(connector);
    }

//    Builder

    public static abstract class BaseAttacherBuilder<B extends BaseAttacherBuilder<B>> {
        protected final AttachingConnector connector;
        protected final Map<String, Connector.Argument> arguments;

        public BaseAttacherBuilder(AttachingConnector connector) {
            this.connector = connector;
            this.arguments = connector.defaultArguments();
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public YouthAttacher build() {
            return () -> YouthVirtualMachine.delegate(connector.attach(arguments));
        }
    }

    public static class AttacherBuilder extends BaseAttacherBuilder<AttacherBuilder> {
        public AttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected AttacherBuilder self() {
            return this;
        }
    }

    public static class SocketAttacherBuilder extends BaseAttacherBuilder<SocketAttacherBuilder> {
        public SocketAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected SocketAttacherBuilder self() {
            return this;
        }

        public SocketAttacherBuilder hostname(String value) {
            return argument(HOSTNAME, value);
        }

        public SocketAttacherBuilder port(String value) {
            return argument(PORT, value);
        }

        public SocketAttacherBuilder port(int value) {
            return port(Integer.toString(value));
        }

        public SocketAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public SocketAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }

    public static class SharedMemoryAttacherBuilder extends BaseAttacherBuilder<SharedMemoryAttacherBuilder> {
        public SharedMemoryAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected SharedMemoryAttacherBuilder self() {
            return this;
        }

        public SharedMemoryAttacherBuilder name(String value) {
            return argument(NAME, value);
        }

        public SharedMemoryAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public SharedMemoryAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }

    public static class ProcessAttacherBuilder extends BaseAttacherBuilder<ProcessAttacherBuilder> {
        public ProcessAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected ProcessAttacherBuilder self() {
            return this;
        }

        public ProcessAttacherBuilder pid(String value) {
            return argument(PID, value);
        }

        public ProcessAttacherBuilder pid(int value) {
            return pid(Integer.toString(value));
        }

        public ProcessAttacherBuilder timeout(String value) {
            return argument(TIMEOUT, value);
        }

        public ProcessAttacherBuilder timeout(long value) {
            return timeout(Long.toString(value));
        }
    }

    public static class SACoreAttacherBuilder extends BaseAttacherBuilder<SACoreAttacherBuilder> {
        public SACoreAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected SACoreAttacherBuilder self() {
            return this;
        }

        public SACoreAttacherBuilder core(String value) {
            return argument(CORE, value);
        }

        public SACoreAttacherBuilder javaExecutable(String value) {
            return argument(JAVA_EXECUTABLE, value);
        }
    }

    public static class SAPIDAttacherBuilder extends BaseAttacherBuilder<SAPIDAttacherBuilder> {
        public SAPIDAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected SAPIDAttacherBuilder self() {
            return this;
        }

        public SAPIDAttacherBuilder pid(String value) {
            return argument(PID, value);
        }

        public SAPIDAttacherBuilder pid(int value) {
            return pid(Integer.toString(value));
        }
    }

    public static class SADebugServerAttacherBuilder extends BaseAttacherBuilder<SADebugServerAttacherBuilder> {
        public SADebugServerAttacherBuilder(AttachingConnector connector) {
            super(connector);
        }

        @Override
        protected SADebugServerAttacherBuilder self() {
            return this;
        }

        public SADebugServerAttacherBuilder debugServerName(String value) {
            return argument(DEBUG_SERVER_NAME, value);
        }
    }
}
