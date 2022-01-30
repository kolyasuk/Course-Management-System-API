package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EvaluateLessonData {

    @NotNull
    public Long lessonId;

    @NotNull
    public Long studentId;

    @NotNull
    public Integer mark;

}
