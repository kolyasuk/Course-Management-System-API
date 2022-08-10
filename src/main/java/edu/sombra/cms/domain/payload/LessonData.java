package edu.sombra.cms.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonData {

    @NotBlank(message = "{lessondata.name.blank}")
    @Size(max = 120, message = "{lessondata.name.size}")
    private String name;

    @NotBlank(message = "{lessondata.description.blank}")
    @Size(max = 5000, message = "{lessondata.description.size}")
    private String description;

    @NotNull(message = "{lessondata.courseId.null}")
    private Long courseId;

    @NotNull(message = "{lessondata.instructorId.null}")
    private Long instructorId;

    @NotBlank(message = "{lessondata.homework.blank}")
    @Size(max = 5000, message = "{lessondata.homework.size}")
    private String homework;

    @NotNull(message = "{lessondata.date.null}")
    private LocalDate date;

}
