package dev.alexengrig.myjdi.queue;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import dev.alexengrig.myjdi.event.YouthEvent;

import java.util.Objects;
import java.util.function.Consumer;

public interface YouthEventIterator extends EventIterator {
    static YouthEventIterator delegate(EventIterator event) {
        return new Delegate(event);
    }

    @Override
    YouthEvent nextEvent();

    @Override
    YouthEvent next();

    class Delegate implements YouthEventIterator {
        protected final EventIterator iterator;

        public Delegate(EventIterator iterator) {
            this.iterator = Objects.requireNonNull(iterator, "The iterator must not be null");
        }

        @Override
        public YouthEvent nextEvent() {
            return next();
        }

        @Override
        public YouthEvent next() {
            return YouthEvent.findOut(iterator.nextEvent());
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super Event> action) {
            iterator.forEachRemaining(action);
        }
    }
}
