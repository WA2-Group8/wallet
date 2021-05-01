package it.polito.wa2group8.wallet.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import it.polito.wa2group8.wallet.controllers.UserController
import it.polito.wa2group8.wallet.dto.RegistrationRequestDTO
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.services.UserDetailsService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController::class])
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userDetailsService: UserDetailsService

    /*
    @Test
    fun registerCreated() {
        val registrationRequestDTO = RegistrationRequestDTO("","foo","12345","12345","foo@email.com", null)
        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isCreated)
    }

//    @Test
//    fun registerCreatedWithResponse(){
//        val userDetails = UserDetailsDTO(null,"foo","12345","12345","foo@email.com", null)
//        val mvcResults: MvcResult = mockMvc.perform(post("/auth/register")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(userDetails)))
//            .andReturn()
//        val expectedResponseBody = UserDetailsDTO(1,"foo",null,null,"foo@email.com", roles = "CUSTOMER")
//        val actualResponseBody = mvcResults.response.contentAsString
//        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponseBody))
//    }

    @Test
    fun registerEmptyValue() {
        val userDetails = UserDetailsDTO(null,"","12345","12345","foo@email.com", null)
        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDetails)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun registerWrongEmailFormat() {
        val userDetails = UserDetailsDTO(null,"foo","12345","12345","foo", null)
        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDetails)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun registerNotMatchingPassword() {
        val userDetails = UserDetailsDTO(null,"foo","12345","123","foo@email.com", null)
        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDetails)))
            .andExpect(status().isBadRequest)
    }
    */
}

