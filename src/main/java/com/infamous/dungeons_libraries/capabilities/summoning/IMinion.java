package com.infamous.dungeons_libraries.capabilities.summoning;

import java.util.UUID;

public interface IMinion {

    UUID getMaster();

    void setMaster(UUID master);

    boolean isMinion();

}
