package io.chark.undead_ninja_cop.engine.system.pickup.chain;

import io.chark.undead_ninja_cop.engine.system.pickup.TouchPickupEvent;

public interface TouchPickupEventChain {

    void process(TouchPickupEvent event);
}