package io.chark.undead_ninja_cop.engine.system.pickup.chain;

import io.chark.undead_ninja_cop.engine.system.pickup.TouchPickupEvent;

public class ProcessNothing implements TouchPickupEventChain {

    @Override
    public void process(TouchPickupEvent event) {
    }
}