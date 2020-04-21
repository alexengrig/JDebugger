package dev.alexengrig.myjdi;

import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.HashMap;
import java.util.Map;

public final class Launchers {
    public static final String HOME = "home";
    public static final String OPTIONS = "options";
    public static final String MAIN = "main";
    public static final String SUSPEND = "suspend";
    public static final String QUOTE = "quote";
    public static final String VMEXEC = "vmexec";
    public static final String COMMAND = "command";
    public static final String ADDRESS = "address";

    private Launchers() {
    }

//    Command Line

    public static Launcher commandLineLauncher() {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher commandLineLauncher(VirtualMachineManager vmManager) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher commandLineLauncher(Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static Launcher commandLineLauncher(VirtualMachineManager vmManager,
                                               Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder() {
        final LaunchingConnector connector = Connectors.commandLineLaunchingConnector();
        return new CommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

    public static CommandLineLauncherBuilder commandLineLauncherBuilder(VirtualMachineManager vmManager) {
        final LaunchingConnector connector = Connectors.commandLineLaunchingConnector(vmManager);
        return new CommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

//    Raw Command Line

    public static Launcher rawCommandLineLauncher() {
        return () -> {
            LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector();
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher rawCommandLineLauncher(VirtualMachineManager vmManager) {
        return () -> {
            LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector(vmManager);
            return connector.launch(connector.defaultArguments());
        };
    }

    public static Launcher rawCommandLineLauncher(Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector();
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static Launcher rawCommandLineLauncher(VirtualMachineManager vmManager,
                                                  Map<String, Connector.Argument> arguments) {
        return () -> {
            LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector(vmManager);
            Map<String, Connector.Argument> args = connector.defaultArguments();
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static RawCommandLineLauncherBuilder rawCommandLineLauncherBuilder() {
        final LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector();
        return new RawCommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

    public static RawCommandLineLauncherBuilder rawCommandLineLauncherBuilder(VirtualMachineManager vmManager) {
        final LaunchingConnector connector = Connectors.rawCommandLineLaunchingConnector(vmManager);
        return new RawCommandLineLauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

//    Common

    public static Launcher launcher(LaunchingConnector connector) {
        return () -> {
            Map<String, Connector.Argument> args = new HashMap<>(connector.defaultArguments());
            args.putAll(connector.defaultArguments());
            return connector.launch(args);
        };
    }

    public static Launcher launcher(LaunchingConnector connector, Map<String, Connector.Argument> arguments) {
        return () -> {
            Map<String, Connector.Argument> args = new HashMap<>(connector.defaultArguments());
            args.putAll(arguments);
            return connector.launch(args);
        };
    }

    public static LauncherBuilder launcherBuilder(LaunchingConnector connector) {
        return new LauncherBuilder(connector.defaultArguments()) {
            @Override
            protected Launcher doBuild(Map<String, Connector.Argument> arguments) {
                return () -> connector.launch(arguments);
            }
        };
    }

//    Builder

    public static abstract class BaseLauncherBuilder<B extends BaseLauncherBuilder<B>> {
        protected final Map<String, Connector.Argument> arguments;

        protected BaseLauncherBuilder(Map<String, Connector.Argument> arguments) {
            this.arguments = arguments;
        }

        public B argument(String name, String value) {
            arguments.get(name).setValue(value);
            return self();
        }

        protected abstract B self();

        public Launcher build() {
            return doBuild(arguments);
        }

        protected abstract Launcher doBuild(Map<String, Connector.Argument> arguments);
    }

    public static abstract class LauncherBuilder
            extends BaseLauncherBuilder<LauncherBuilder> {
        protected LauncherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected LauncherBuilder self() {
            return this;
        }
    }

    public static abstract class CommandLineLauncherBuilder
            extends BaseLauncherBuilder<CommandLineLauncherBuilder> {
        public CommandLineLauncherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected CommandLineLauncherBuilder self() {
            return this;
        }

        public CommandLineLauncherBuilder home(String value) {
            return argument(HOME, value);
        }

        public CommandLineLauncherBuilder options(String value) {
            return argument(OPTIONS, value);
        }

        public CommandLineLauncherBuilder main(String value) {
            return argument(MAIN, value);
        }

        public CommandLineLauncherBuilder suspend(String value) {
            return argument(SUSPEND, value);
        }

        public CommandLineLauncherBuilder suspend(boolean value) {
            return suspend(Boolean.toString(value));
        }

        public CommandLineLauncherBuilder quote(String value) {
            return argument(QUOTE, value);
        }

        public CommandLineLauncherBuilder vmexec(String value) {
            return argument(VMEXEC, value);
        }
    }

    public static abstract class RawCommandLineLauncherBuilder
            extends BaseLauncherBuilder<RawCommandLineLauncherBuilder> {
        protected RawCommandLineLauncherBuilder(Map<String, Connector.Argument> arguments) {
            super(arguments);
        }

        @Override
        protected RawCommandLineLauncherBuilder self() {
            return this;
        }

        public RawCommandLineLauncherBuilder command(String value) {
            return argument(COMMAND, value);
        }

        public RawCommandLineLauncherBuilder address(String value) {
            return argument(ADDRESS, value);
        }

        public RawCommandLineLauncherBuilder quote(String value) {
            return argument(QUOTE, value);
        }
    }
}
