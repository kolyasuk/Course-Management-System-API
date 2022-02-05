package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.LessonDTO;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.mapper.LessonMapper;
import edu.sombra.cms.domain.payload.LessonData;
import edu.sombra.cms.repository.LessonRepository;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.LessonService;
import edu.sombra.cms.service.StudentLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    @Lazy
    private final CourseService courseService;
    private final StudentLessonService saveStudentLessons;

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("lessonId incorrect"));
    }

    @Override
    public LessonDTO create(@Valid LessonData lessonData) {
        Lesson lesson = new Lesson();

        lesson.setName(lessonData.getName());
        lesson.setDescription(lessonData.getDescription());
        lesson.setHomework(lessonData.getHomework());
        lesson.setDate(lessonData.getDate());
        lesson.setCourse(courseService.getById(lessonData.getCourseId()));

        var result = lessonRepository.save(lesson);

        saveStudentLessons.saveStudentLessons(lesson, result.getCourse().getStudents());

        return lessonMapper.to(result);
    }

    @Override
    public LessonDTO update(Long id, @Valid LessonData lessonData) {
        return null;
    }

}
