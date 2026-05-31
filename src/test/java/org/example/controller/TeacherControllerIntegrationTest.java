package org.example.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.example.dao.CourseRepository;
import org.example.dao.GroupRepository;
import org.example.dao.ScheduleRepository;
import org.example.dao.StudentRepository;
import org.example.dao.TeacherRepository;
import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeacherControllerIntegrationTest {

    @Container
    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Teacher firstTeacher;

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeEach
    void setUp() {
        cleanTables();

        firstTeacher = new Teacher();
        firstTeacher.setFirstName("Ivan");
        firstTeacher.setLastName("Ivanov");

        Teacher secondTeacher = new Teacher();
        secondTeacher.setFirstName("Petr");
        secondTeacher.setLastName("Petrov");

        teacherRepository.saveAll(List.of(firstTeacher, secondTeacher));
    }

    @AfterEach
    void tearDown() {
        cleanTables();
    }

    @Test
    void createTeacher() {
        TeacherRequestDto request = teacherRequest("Anna", "Smirnova");

        ResponseEntity<TeacherResponseDto> response = restTemplate.postForEntity(
                "/api/teachers",
                request,
                TeacherResponseDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("Anna");
        assertThat(response.getBody().getLastName()).isEqualTo("Smirnova");
    }

    @Test
    void getTeacherById() {
        ResponseEntity<TeacherResponseDto> response = restTemplate.getForEntity(
                "/api/teachers/{id}",
                TeacherResponseDto.class,
                firstTeacher.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(firstTeacher.getId());
        assertThat(response.getBody().getFirstName()).isEqualTo("Ivan");
        assertThat(response.getBody().getLastName()).isEqualTo("Ivanov");
    }

    @Test
    void getTeachers() {
        ResponseEntity<List<TeacherResponseDto>> response = restTemplate.exchange(
                "/api/teachers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody())
                .extracting(TeacherResponseDto::getLastName)
                .containsExactlyInAnyOrder("Ivanov", "Petrov");
    }

    @Test
    void updateTeacher() {
        TeacherRequestDto request = teacherRequest("Updated", "Teacher");

        ResponseEntity<TeacherResponseDto> response = restTemplate.exchange(
                "/api/teachers/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                TeacherResponseDto.class,
                firstTeacher.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(firstTeacher.getId());
        assertThat(response.getBody().getFirstName()).isEqualTo("Updated");
        assertThat(response.getBody().getLastName()).isEqualTo("Teacher");
    }

    @Test
    void deleteTeacher() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/teachers/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                firstTeacher.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(teacherRepository.existsById(firstTeacher.getId())).isFalse();
    }

    @Test
    void getTeacherByIdReturnsNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/teachers/{id}",
                String.class,
                999999L
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Teacher not found: 999999");
    }

    private TeacherRequestDto teacherRequest(String firstName, String lastName) {
        TeacherRequestDto request = new TeacherRequestDto();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        return request;
    }

    private void cleanTables() {
        scheduleRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        groupRepository.deleteAll();
        teacherRepository.deleteAll();
    }
}
