package com.bvg.type;

import java.io.Serializable;

public interface IDirectoryValue extends Serializable {
    Long getId();

    String getKey();

    String getValue();

    Long getParentId();

    Long getDirectoryId();

    String getShortValue();

    Integer getPriority();

    boolean isHidden();
}
