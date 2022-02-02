package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.annotation.MarkRange;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EvaluateLessonData {

    @NotNull
    private Long lessonId;

    @NotNull
    private Long studentId;

    @NotNull
    @MarkRange
    private Integer mark;

}
