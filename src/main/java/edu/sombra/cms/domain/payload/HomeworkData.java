package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.annotation.NullOrNotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class HomeworkData {

    @NullOrNotBlank(message = "{homeworkdata.note.blank}")
    @Size(max = 1000, message = "{homeworkdata.note.size}")
    private String note;

}
