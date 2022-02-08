package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class LessonData {

    @NotBlank(message = "{lessondata.name.blank}")
    @Size(max = 120, message = "{lessondata.name.size}")
    private String name;

    @NotBlank(message = "{lessondata.description.blank}")
    @Size(max = 5000, message = "{lessondata.description.size}")
    private String description;

    @NotNull(message = "{lessondata.courseId.null}")
    private Long courseId;

    @NotBlank(message = "{lessondata.homework.blank}")
    @Size(max = 5000, message = "{lessondata.homework.size}")
    private String homework;

    @NotNull(message = "{lessondata.date.null}")
    private LocalDate date;

}
