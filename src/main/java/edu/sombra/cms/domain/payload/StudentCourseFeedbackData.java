package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class StudentCourseFeedbackData {

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    @NotBlank
    @Size(max = 3000)
    private String feedback;

}
