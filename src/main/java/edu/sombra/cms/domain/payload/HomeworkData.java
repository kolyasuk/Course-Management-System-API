package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.annotation.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkData {

    @NullOrNotBlank(message = "{homeworkdata.note.blank}")
    @Size(max = 1000, message = "{homeworkdata.note.size}")
    private String note;

}
