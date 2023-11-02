package com.crocobet.example;

import com.crocobet.example.domain.role.Role;
import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.model.jwt.JwtAuthenticationRequest;
import com.crocobet.example.model.jwt.JwtAuthenticationResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AdminIntegrationTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void addAdminUserTest() {

        JwtAuthenticationRequest jwtAuthenticationRequest = buildJwtAuthenticationRequest();

        UserDomain userDomain = buildUserDomain(Role.USER);

        ObjectMapper objectMapper = objectMapper();

        assertDoesNotThrow(() -> {

            JwtAuthenticationResponse jwtAuthenticationResponse = adminAuthorization(objectMapper, jwtAuthenticationRequest);

            UserDomain persistedUserDomain = persistUserDomain(objectMapper, userDomain, jwtAuthenticationResponse.getToken());

            mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/" + persistedUserDomain.getId())
                            .header("Authorization", "Bearer " + jwtAuthenticationResponse.getToken()))
                    .andExpect(status().isOk());

            JwtAuthenticationRequest userJwtAuthenticationRequest = buildJwtAuthenticationRequest(userDomain.getUsername(), userDomain.getPassword());

            JwtAuthenticationResponse userJwtAuthenticationResponse = adminAuthorization(objectMapper, userJwtAuthenticationRequest);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/" + persistedUserDomain.getId())
                            .header("Authorization", "Bearer " + userJwtAuthenticationResponse.getToken()))
                    .andExpect(status().isUnauthorized());

        });
    }

    private JwtAuthenticationResponse adminAuthorization(ObjectMapper objectMapper, JwtAuthenticationRequest jwtAuthenticationRequest) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(objectMapper.writeValueAsString(jwtAuthenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return buildJwtAuthenticationResponse(mvcResult, objectMapper);
    }

    private UserDomain persistUserDomain(ObjectMapper objectMapper, UserDomain userDomain, String token) throws Exception {
        MvcResult mvcPersistResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/users")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(userDomain))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return readUserDomain(mvcPersistResult, objectMapper);
    }

    private UserDomain readUserDomain(MvcResult mvcResult, ObjectMapper objectMapper) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDomain.class);
    }

    private JwtAuthenticationResponse buildJwtAuthenticationResponse(MvcResult mvcResult, ObjectMapper objectMapper) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JwtAuthenticationResponse.class);
    }

    private JwtAuthenticationRequest buildJwtAuthenticationRequest() {
        return JwtAuthenticationRequest
                .builder()
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

    private JwtAuthenticationRequest buildJwtAuthenticationRequest(String username, String password) {
        return JwtAuthenticationRequest
                .builder()
                .username(username)
                .password(password)
                .build();
    }

    private UserDomain buildUserDomain(Role role) {
        return UserDomain
                .builder()
                .username(getRandomString())
                .password(getRandomString())
                .firstName(getRandomString())
                .lastName(getRandomString())
                .email(getRandomString() + "@gmail.com")
                .role(role)
                .build();
    }

    private ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphabetic(10).toLowerCase();
    }
}
