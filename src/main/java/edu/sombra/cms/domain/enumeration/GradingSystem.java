package edu.sombra.cms.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static edu.sombra.cms.util.ArrayUtil.rangeList;

@Getter
@AllArgsConstructor
public enum GradingSystem {

    PERCENTAGE(rangeList(1, 100), 80);

    private final List<Integer> markRange;
    private final int passRate;

}
