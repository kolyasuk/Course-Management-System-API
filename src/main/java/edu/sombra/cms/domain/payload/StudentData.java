package edu.sombra.cms.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentData {

    @NotBlank(message = "{studentdata.firstName.blank}")
    @Size(max = 40, message = "{studentdata.firstName.size}")
    private String firstName;

    @NotBlank(message = "{studentdata.lastName.blank}")
    @Size(max = 40, message = "{studentdata.lastName.size}")
    private String lastName;

    @NotBlank(message = "{studentdata.group.blank}")
    @Size(max = 40, message = "{studentdata.group.size}")
    private String group;

    @NotBlank(message = "{studentdata.faculty.blank}")
    @Size(max = 40, message = "{studentdata.faculty.size}")
    private String faculty;


}
