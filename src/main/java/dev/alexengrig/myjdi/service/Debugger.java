package dev.alexengrig.myjdi.service;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import dev.alexengrig.myjdi.DebugRunner;
import dev.alexengrig.myjdi.domain.Config;
import dev.alexengrig.myjdi.domain.Option;
import dev.alexengrig.myjdi.util.PrettyUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

public class Debugger extends Thread {
    private static final Logger log = Logger.getLogger(DebugRunner.class.getSimpleName());

    protected final LaunchingConnectorService launchingConnectorService;
    protected final Class<?> debugClass;
    protected final Integer[] breakPointLines;

    protected VirtualMachine vm;
    protected boolean connected;
    protected boolean died;

    public Debugger(Config config) {
        super("myjdi");
        launchingConnectorService = new LaunchingConnectorService();
        debugClass = config.get(Option.CLASS_NAME);
        breakPointLines = config.get(Option.BREAK_POINTS);
    }

    @Override
    public void run() {
        prepare();
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    handleEvent(event);
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (VMDisconnectedException e) {
                log.severe(String.format("VM is disconnected: %s", e.getMessage()));
                handleVMDisconnectedException();
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    protected void prepare() {
        prepareVm();
        prepareRequests();
    }

    protected void prepareVm() {
        final LaunchingConnector launchingConnector = launchingConnectorService.connect();
        log.info(String.format("Connector:%n%s", PrettyUtil.pretty(launchingConnector)));
        final Map<String, String> values = Collections.singletonMap("main", debugClass.getName());
        log.info(String.format("Connector values: %s.", values));
        final Map<String, Connector.Argument> arguments = launchingConnectorService.arguments(launchingConnector, values);
        try {
            vm = launchingConnector.launch(arguments);
            vm.setDebugTraceMode(VirtualMachine.TRACE_NONE);
            connected = true;
        } catch (IOException e) {
            throw new Error(String.format("Unable to launch target VM: %s.", e));
        } catch (IllegalConnectorArgumentsException e) {
            throw new Error(String.format("Internal error: %s.", e));
        } catch (VMStartException e) {
            throw new Error(String.format("Target VM failed to initialize: %s.", e.getMessage()));
        }
    }

    protected void prepareRequests() {
        enableExceptionRequest(null, true, true);
        // method
        enableMethodExitRequest();
        enableMethodEntryRequest();
        // monitor
        enableMonitorWaitedRequest();
        enableMonitorWaitRequest();
        enableMonitorContendedEnteredRequest();
        enableMonitorContendedEnterRequest();
        // class
        enableClassUnloadRequest();
        enableClassPrepareRequest();
        // thread
        enableThreadDeathRequest();
        enableThreadStartRequest();
    }

    protected void enableExceptionRequest(ReferenceType refType, boolean notifyCaught, boolean notifyUncaught) {
        final EventRequestManager manager = vm.eventRequestManager();
        final ExceptionRequest request = manager.createExceptionRequest(refType, notifyCaught, notifyUncaught);
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableBreakpointRequest(Location location) {
        final EventRequestManager manager = vm.eventRequestManager();
        final BreakpointRequest request = manager.createBreakpointRequest(location);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableStepRequest(ThreadReference thread, int size, int depth) {
        final EventRequestManager manager = vm.eventRequestManager();
        final StepRequest request = manager.createStepRequest(thread, size, depth);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Watchpoint requests

    protected void enableAccessWatchpointRequest(Field field) {
        final EventRequestManager manager = vm.eventRequestManager();
        final AccessWatchpointRequest request = manager.createAccessWatchpointRequest(field);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableModificationWatchpointRequest(Field field) {
        final EventRequestManager manager = vm.eventRequestManager();
        final ModificationWatchpointRequest request = manager.createModificationWatchpointRequest(field);
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Method requests

    protected void enableMethodExitRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MethodExitRequest request = manager.createMethodExitRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMethodEntryRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MethodEntryRequest request = manager.createMethodEntryRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Monitor requests

    protected void enableMonitorWaitedRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorWaitedRequest request = manager.createMonitorWaitedRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorWaitRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorWaitRequest request = manager.createMonitorWaitRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorContendedEnteredRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorContendedEnteredRequest request = manager.createMonitorContendedEnteredRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableMonitorContendedEnterRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final MonitorContendedEnterRequest request = manager.createMonitorContendedEnterRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Class requests

    protected void enableClassUnloadRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ClassUnloadRequest request = manager.createClassUnloadRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableClassPrepareRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ClassPrepareRequest request = manager.createClassPrepareRequest();
        request.addClassExclusionFilter("java.*");
        request.addClassExclusionFilter("javax.*");
        request.addClassExclusionFilter("sun.*");
        request.addClassExclusionFilter("com.sun.*");
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Thread requests

    protected void enableThreadDeathRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ThreadDeathRequest request = manager.createThreadDeathRequest();
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

    protected void enableThreadStartRequest() {
        final EventRequestManager manager = vm.eventRequestManager();
        final ThreadStartRequest request = manager.createThreadStartRequest();
        request.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        request.enable();
    }

//    Handling

    protected void handleEvent(Event event) {
        if (event instanceof ExceptionEvent) {
            handleExceptionEvent((ExceptionEvent) event);
        } else if (event instanceof BreakpointEvent) {
            handleBreakpointEvent((BreakpointEvent) event);
        } else if (event instanceof StepEvent) {
            handleStepEvent((StepEvent) event);
        }
        // watchpoint events
        else if (event instanceof AccessWatchpointEvent) {
            handleAccessWatchpointEvent((AccessWatchpointEvent) event);
        } else if (event instanceof ModificationWatchpointEvent) {
            handleModificationWatchpointEvent((ModificationWatchpointEvent) event);
        }
        // method events
        else if (event instanceof MethodExitEvent) {
            handleMethodExitEvent((MethodExitEvent) event);
        } else if (event instanceof MethodEntryEvent) {
            handleMethodEntryEvent((MethodEntryEvent) event);
        }
        // monitor events
        else if (event instanceof MonitorWaitedEvent) {
            handleMonitorWaitedEvent((MonitorWaitedEvent) event);
        } else if (event instanceof MonitorWaitEvent) {
            handleMonitorWaitEvent((MonitorWaitEvent) event);
        } else if (event instanceof MonitorContendedEnteredEvent) {
            handleMonitorContendedEnteredEvent((MonitorContendedEnteredEvent) event);
        } else if (event instanceof MonitorContendedEnterEvent) {
            handleMonitorContendedEnterEvent((MonitorContendedEnterEvent) event);
        }
        // class events
        else if (event instanceof ClassUnloadEvent) {
            handleClassUnloadEvent((ClassUnloadEvent) event);
        } else if (event instanceof ClassPrepareEvent) {
            handleClassPrepareEvent((ClassPrepareEvent) event);
        }
        // thread events
        else if (event instanceof ThreadDeathEvent) {
            handleThreadDeathEvent((ThreadDeathEvent) event);
        } else if (event instanceof ThreadStartEvent) {
            handleThreadStartEvent((ThreadStartEvent) event);
        }
        // vm events
        else if (event instanceof VMDeathEvent) {
            handleVmDeathEvent((VMDeathEvent) event);
        } else if (event instanceof VMDisconnectEvent) {
            handleVmDisconnectEvent((VMDisconnectEvent) event);
        } else if (event instanceof VMStartEvent) {
            handleVmStartEvent((VMStartEvent) event);
        }
        // unexpected
        else {
            throw new Error(String.format("Unexpected event type: %s.", event.getClass().getName()));
        }
    }

    protected void handleExceptionEvent(ExceptionEvent event) {
        final ObjectReference exception = event.exception();
        final Location location = event.catchLocation();
        log.info(String.format("Exception: %s; in location: %s.", exception, location));
    }

    protected void handleBreakpointEvent(BreakpointEvent event) {
        log.info("BreakpointEvent.");
    }

    protected void handleStepEvent(StepEvent event) {
        log.info("StepEvent.");
    }

//    Watchpoint events

    protected void handleAccessWatchpointEvent(AccessWatchpointEvent event) {
        final Field field = event.field();
        final Value value = event.valueCurrent();
        log.info("AccessWatchpointEvent.");
    }

    protected void handleModificationWatchpointEvent(ModificationWatchpointEvent event) {
        log.info("ModificationWatchpointEvent.");

    }

//    Method events

    protected void handleMethodExitEvent(MethodExitEvent event) {
        log.info("MethodExitEvent.");
    }

    protected void handleMethodEntryEvent(MethodEntryEvent event) {
        log.info("MethodEntryEvent.");
    }

//    Monitor events

    protected void handleMonitorWaitedEvent(MonitorWaitedEvent event) {
        log.info("MonitorWaitedEvent.");
    }

    protected void handleMonitorWaitEvent(MonitorWaitEvent event) {
        log.info("MonitorWaitEvent.");
    }

    protected void handleMonitorContendedEnteredEvent(MonitorContendedEnteredEvent event) {
        log.info("MonitorContendedEnteredEvent.");
    }

    protected void handleMonitorContendedEnterEvent(MonitorContendedEnterEvent event) {
        log.info("MonitorContendedEnterEvent.");
    }

//    Class events

    protected void handleClassUnloadEvent(ClassUnloadEvent event) {
        log.info(String.format("Class unloaded: %s.", event.className()));
    }

    protected void handleClassPrepareEvent(ClassPrepareEvent event) {
        log.info(String.format("Class prepared: %s.", event.referenceType()));
//        final EventRequestManager manager = vm.eventRequestManager();
//        final List<Field> fields = event.referenceType().visibleFields();
//        for (Field field : fields) {
//            final ModificationWatchpointRequest request = manager.createModificationWatchpointRequest(field);
//            request.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//            request.enable();
//        }
//        ClassType classType = (ClassType) event.referenceType();
//        for (int lineNumber : breakPointLines) {
//            List<Location> locations = classType.locationsOfLine(lineNumber);
//            if (!locations.isEmpty()) {
//                Location location = locations.get(0);
//                EventRequestManager eventRequestManager = vm.eventRequestManager();
//                BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
//                breakpointRequest.enable();
//            }
//        }
    }

//    Thread events

    protected void handleThreadDeathEvent(ThreadDeathEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("\"%s\" thread terminated.", thread.name()));
    }

    protected void handleThreadStartEvent(ThreadStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("\"%s\" thread started.", thread.name()));
    }

//    Vm events

    protected void handleVmDeathEvent(VMDeathEvent event) {
        died = true;
        log.info("VM finished.");
    }

    protected void handleVmDisconnectEvent(VMDisconnectEvent event) {
        connected = false;
        if (!died) {
            log.info("VM disconnected.");
        }
    }

    protected void handleVmStartEvent(VMStartEvent event) {
        final ThreadReference thread = event.thread();
        log.info(String.format("VM started in \"%s\" thread.", thread.name()));
    }


    /***
     * A VMDisconnectedException has happened while dealing with
     * another event. We need to flush the event queue, dealing only
     * with exit events (VMDeath, VMDisconnect) so that we terminate
     * correctly.
     */
    protected synchronized void handleVMDisconnectedException() {
        final EventQueue eventQueue = vm.eventQueue();
        while (connected) {
            try {
                final EventSet events = eventQueue.remove();
                for (Event event : events) {
                    if (event instanceof VMDeathEvent) {
                        handleVmDeathEvent((VMDeathEvent) event);
                    } else if (event instanceof VMDisconnectEvent) {
                        handleVmDisconnectEvent((VMDisconnectEvent) event);
                    }
                }
                vm.resume(); //FIXME: call it after all or after each event handling?
            } catch (InterruptedException ignore) {
                log.severe("Interrupted.");
            }
        }
    }
}
