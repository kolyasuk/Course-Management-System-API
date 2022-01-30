package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CourseData {

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private List<Long> instructorIds;

}
