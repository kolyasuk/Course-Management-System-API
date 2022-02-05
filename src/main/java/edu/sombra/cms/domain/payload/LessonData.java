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

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 5000)
    private String description;

    @NotNull
    private Long courseId;

    @NotBlank
    @Size(max = 5000)
    private String homework;

    @NotNull
    private LocalDate date;

}
