//package com.example.onlineshop.web;
//
//import com.example.onlineshop.service.impl.StatisticServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class StatisticControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @WithMockUser(value = "admin", roles = {"ADMIN"})
//    void getStatisticTest() throws Exception {
//        StatisticServiceImpl statisticService = new StatisticServiceImpl();
//        statisticService.onRequest();
//        statisticService.onRequest();
//
//        this.mockMvc.perform(get("/statistics"))
//                .andExpect(status().isOk())
//        ;
//    }
//}
