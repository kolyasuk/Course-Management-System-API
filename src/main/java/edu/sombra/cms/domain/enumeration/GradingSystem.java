package edu.sombra.cms.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ValueRange;

@Getter
@AllArgsConstructor
public enum GradingSystem {

    PERCENTAGE(ValueRange.of(1, 100), 80);

    private final ValueRange markRange;
    private final int passRate;

}
