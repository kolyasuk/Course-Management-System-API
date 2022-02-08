package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.annotation.MarkRange;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EvaluateLessonData {

    @NotNull(message = "{evaluatelessondata.studentId.null}")
    private Long studentId;

    @NotNull(message = "{evaluatelessondata.mark.null}")
    @MarkRange(message = "{evaluatelessondata.mark.range}")
    private Integer mark;

    @Size(max = 3000, message = "{evaluatelessondata.feedback.size}")
    private String feedback;

}
