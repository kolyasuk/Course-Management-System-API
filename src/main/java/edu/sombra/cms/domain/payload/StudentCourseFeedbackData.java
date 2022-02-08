package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class StudentCourseFeedbackData {

    @NotNull(message = "{studentcoursefeedbackdata.studentId.null}")
    private Long studentId;

    @NotBlank(message = "{studentcoursefeedbackdata.feedback.blank}")
    @Size(max = 3000, message = "{studentcoursefeedbackdata.feedback.size}")
    private String feedback;

}
