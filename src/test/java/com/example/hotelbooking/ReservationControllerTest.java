package com.example.hotelbooking;

import com.example.hotelbooking.entity.Reservation;
import com.example.hotelbooking.repository.ReservationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Value(value="${server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void getApiFailureTest() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/booking/ef07c50c-61ff-494b-937a-a01e60a082e")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void getApiSuccessTest() throws Exception {
        List<Reservation> reservationList =   reservationRepository.findAll();
        this.mockMvc.perform(get("http://localhost:8080/booking/".concat(String.valueOf(reservationList.get(1).getReservationId())))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testReservationSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.createObjectNode()
                .put("email", "osmangoni14@gmail.com")
                .put("firstName","Osman")
                .put("lastName", "Gani")
                .put("guestCount", 3)
                .put("startDate", "2023-01-20")
                .put("endDate", "2023-01-21");

        // Perform a POST request to the API
        mockMvc.perform(post("http://localhost:8080/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString()))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testSameReservation() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.createObjectNode()
                .put("email", "osmangoni14@gmail.com")
                .put("firstName","Osman")
                .put("lastName", "Gani")
                .put("guestCount", 3)
                .put("startDate", "2023-01-22")
                .put("endDate", "2023-01-23");

        // Perform a POST request to the API
        mockMvc.perform(post("http://localhost:8080/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString()))
                .andExpect(status().isOk()).andDo(print());

        mapper = new ObjectMapper();
        jsonNode = mapper.createObjectNode()
                .put("email", "osmangoni14@gmail.com")
                .put("firstName","Osman")
                .put("lastName", "Gani")
                .put("guestCount", 3)
                .put("startDate", "2023-01-22")
                .put("endDate", "2023-01-23");

        // Perform a POST request to the API
        mockMvc.perform(post("http://localhost:8080/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString()))
                .andExpect(status().isBadRequest()).andDo(print());
    }


    @Test
    public void testReservationCancel() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.createObjectNode()
                .put("email", "osmangoni14@gmail.com")
                .put("firstName","Osman")
                .put("lastName", "Gani")
                .put("guestCount", 3)
                .put("startDate", "2023-01-24")
                .put("endDate", "2023-01-24");

        // Perform a POST request to the API
        mockMvc.perform(post("http://localhost:8080/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString()))
                .andExpect(status().isOk()).andDo(print());
        List<Reservation> reservationList =   reservationRepository.findAll();
        mockMvc.perform(put("http://localhost:8080/booking/".concat(String.valueOf(reservationList.get(1).getReservationId()))))
                .andExpect(status().isOk()).andDo(print());
    }

}
