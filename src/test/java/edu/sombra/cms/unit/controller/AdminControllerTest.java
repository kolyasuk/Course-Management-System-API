package edu.sombra.cms.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sombra.cms.controller.AdminController;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.util.UnsecuredWebMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static edu.sombra.cms.domain.enumeration.Role.ROLE_INSTRUCTOR;
import static edu.sombra.cms.util.ResponseBodyMatchers.responseBody;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@UnsecuredWebMvcTest(controllers = AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;


    @Test
    @DisplayName("Create user valid input returns code 200")
    @WithMockUser
    void whenValidInput_thenReturns200() throws Exception {
        RegistrationData instructor = new RegistrationData("instructor1", "password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create user invalid input returns code 400")
    @WithMockUser
    void whenInvalidInput_thenReturns400() throws Exception {
        RegistrationData instructor = new RegistrationData("", "password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create user invalid input returns code and ErrorResponse")
    @WithMockUser
    void whenInvalidInput_thenReturns400AndErrorResponse() throws Exception {
        RegistrationData instructor = new RegistrationData("", "password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsError("Username size should be between 3 and 20 characters"));
    }

    @Test
    @DisplayName("Create user valid input maps to business model")
    @WithMockUser
    void whenValidInput_thenMapsToBusinessModel() throws Exception {
        RegistrationData instructor = new RegistrationData("instructor1", "password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isOk());

        ArgumentCaptor<RegistrationData> registrationDataCaptor = ArgumentCaptor.forClass(RegistrationData.class);

        verify(userService, times(1)).create(registrationDataCaptor.capture());
        assertThat(registrationDataCaptor.getValue().getUsername()).isEqualTo("instructor1");
        assertThat(registrationDataCaptor.getValue().getPassword()).isEqualTo("password");
        assertThat(registrationDataCaptor.getValue().getFullName()).isEqualTo("Instructor1 Instructor1");
        assertThat(registrationDataCaptor.getValue().getEmail()).isEqualTo("instructor1@gmail.com");
        assertThat(registrationDataCaptor.getValue().getRole()).isEqualTo(ROLE_INSTRUCTOR);
    }

    @Test
    @DisplayName("Create user valid input returns FullUserInfoDTO")
    @WithMockUser
    void whenValidInput_thenReturnsFullUserInfoDTO() throws Exception {
        RegistrationData instructor = new RegistrationData("instructor1", "password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);
        FullUserInfoDTO userServiceReturn = new FullUserInfoDTO(1,"instructor1", "instructor1@gmail.com", "Instructor1 Instructor1");
        FullUserInfoDTO expected = new FullUserInfoDTO(1,"instructor1", "instructor1@gmail.com", "Instructor1 Instructor1");

        when(userService.create(any(RegistrationData.class))).thenReturn(userServiceReturn);

        String actualResponseBody = mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expected));
    }

}
