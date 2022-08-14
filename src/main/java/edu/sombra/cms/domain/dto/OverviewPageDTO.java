package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseOverviewStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OverviewPageDTO {

    private UserOverviewDTO userOverview;
    private List<CoursesOverviewDTO> coursesOverviewList;
    private List<ComingLessonDTO> comingLessons;


    @Getter
    @AllArgsConstructor
    public static class CoursesOverviewDTO {
        private CourseOverviewStatus status;
        private List<? extends ICourseOverview> courseOverviewList;
    }

    @Getter
    @AllArgsConstructor
    public static class ComingLessonDTO {
        private long id;
        private String name;
        private long inDays;
    }

}
