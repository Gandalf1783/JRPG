package de.gandalf1783.tilegame.objects;

import java.util.UUID;

public class Properties {

    /**
     *
     */
    private static final long serialVersionUID = -3551438904102569304L;

    private String uuid;

    public Properties() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
