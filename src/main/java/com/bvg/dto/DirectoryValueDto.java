package com.bvg.dto;

import com.bvg.type.IDirectoryValue;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DirectoryValueDto implements IDirectoryValue {

    private Long id;
    private String key;
    private String value;
    private Long parentId;
    private Long directoryId;
    private String shortValue;
    private boolean hidden;
    private Integer priority = 0;
    private Map additionalInfo = new LinkedHashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DirectoryValueDto that = (DirectoryValueDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
