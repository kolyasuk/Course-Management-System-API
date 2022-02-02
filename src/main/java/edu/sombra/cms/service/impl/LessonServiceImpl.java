package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.mapper.LessonMapper;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.repository.CourseRepository;
import edu.sombra.cms.repository.LessonRepository;
import edu.sombra.cms.service.LessonService;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseRepository courseRepository;
    private final StudentLessonService saveStudentLessons;

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("lessonId incorrect"));
    }

    @Override
    public LessonDTO create(LessonData lessonData) {
        Lesson lesson = new Lesson();

        lesson.setName(lessonData.getName());
        lesson.setDescription(lessonData.getDescription());
        lesson.setCourse(courseRepository.findById(lessonData.getCourseId()).orElseThrow(() -> new IllegalArgumentException("courseId incorrect")));

        var result = lessonRepository.save(lesson);

        saveStudentLessons.saveStudentLessons(lesson, result.getCourse().getStudents());

        return lessonMapper.to(result);
    }

    @Override
    public LessonDTO update(Long id, LessonData lessonData) {
        return null;
    }

}
