package edu.sombra.cms.testing;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseManagementSystemAPIIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     @Transactional
//     void registerNewUser() throws Exception {
//         RegistrationDTO user = new RegistrationDTO()
//                 .setUsername("user")
//                 .setPassword("password")
//                 .setRePassword("password")
//                 .setEmail("email@gmail.com")
//                 .setFullName("user")
//                 .setRoles(Set.of(RoleEnum.ROLE_STUDENT));

//         mockMvc.perform(post("/api/auth/signup")
//                 .contentType("application/json")
//                 .content(objectMapper.writeValueAsString(user)))
//                 .andExpect(status().isOk())
//                 .andReturn();
//     }

    //TODO: add remove, user exists, success and failed create admin tests

}
