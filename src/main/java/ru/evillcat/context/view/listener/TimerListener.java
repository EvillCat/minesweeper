package ru.evillcat.context.view.listener;

import ru.evillcat.context.view.image.NumberImage;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.TimerStartEvent;
import ru.evillcat.event.minesweeper.type.TimerTickEvent;
import ru.evillcat.view.element.timer.ViewTimer;

public class TimerListener {

    private final ViewTimer timer;

    public TimerListener(ViewTimer timer) {
        this.timer = timer;
    }

    public void subscribeListeners(EventBus eventBus) {
        eventBus.subscribe(TimerStartEvent.class, new TimerStart());
        eventBus.subscribe(TimerTickEvent.class, new TimerTick());
    }

    private void setTimerCells(int seconds) {
        int hundredValue = seconds / 100;
        int dozenValue = seconds / 10 % 10;
        int unitValue = seconds % 10;

        timer.setFirstSecondImage(NumberImage.createImage(unitValue));
        timer.setSecondSecondImage(NumberImage.createImage(dozenValue));
        timer.setThirdSecondImage(NumberImage.createImage(hundredValue));
    }

    private class TimerStart implements EventListener<TimerStartEvent> {

        @Override
        public void update(TimerStartEvent event) {
            timer.setDefaultState();
        }
    }

    private class TimerTick implements EventListener<TimerTickEvent> {

        @Override
        public void update(TimerTickEvent event) {
            setTimerCells(event.getTime());
        }
    }
}
