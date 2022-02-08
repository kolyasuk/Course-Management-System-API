package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CourseData {

    @NotBlank(message = "{coursedata.name.blank}")
    @Size(max = 120, message = "{coursedata.name.size}")
    private String name;

    @NotBlank(message = "{coursedata.description.blank}")
    @Size(max = 1000, message = "{coursedata.description.size}")
    private String description;

    @Size(min = 1, message = "{coursedata.instructorIds.size}")
    private List<Long> instructorIds;

}
