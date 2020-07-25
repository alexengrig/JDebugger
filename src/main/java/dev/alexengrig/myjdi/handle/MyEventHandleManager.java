package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.YouthVirtualMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class MyEventHandleManager implements YouthEventHandleManager {
    protected final YouthVirtualMachine virtualMachine;
    protected final List<YouthClassPrepareHandle> classPrepareHandles;

    public MyEventHandleManager(YouthVirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.classPrepareHandles = new ArrayList<>();
    }

    @Override
    public YouthClassPrepareHandle createClassPrepareHandle(Consumer<ClassPrepareEvent> handler) {
        MyClassPrepareHandle classPrepareHandle = new MyClassPrepareHandle(handler);
        classPrepareHandles.add(classPrepareHandle);
        return classPrepareHandle;
    }

    @Override
    public List<YouthClassPrepareHandle> classPrepareHandles() {
        return Collections.unmodifiableList(classPrepareHandles);
    }
}
