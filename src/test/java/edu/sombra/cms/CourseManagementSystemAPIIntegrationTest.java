package edu.sombra.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sombra.cms.domain.payload.RegistrationData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static edu.sombra.cms.domain.enumeration.Role.ROLE_INSTRUCTOR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseManagementSystemAPIIntegrationTest {

     @Autowired
     private MockMvc mockMvc;

     @Autowired
     private ObjectMapper objectMapper;

     @Test
     @Transactional
     void registerNewUser() throws Exception {
         RegistrationData instructor = new RegistrationData("password", "Instructor1 Instructor1", "instructor1@gmail.com", ROLE_INSTRUCTOR);

         mockMvc.perform(post("/api/auth/signup")
                 .contentType("application/json")
                 .content(objectMapper.writeValueAsString(instructor)))
                 .andExpect(status().isOk())
                 .andReturn();
     }

    //TODO: add remove, user exists, success and failed create admin tests

}
