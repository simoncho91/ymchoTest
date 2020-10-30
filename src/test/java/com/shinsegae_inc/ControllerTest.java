package com.shinsegae_inc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shinsegae_inc.swaf.common.controller.LoginController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;
    
    public void sample() throws Exception {
        mvc.perform(get("/test.do"))
           .andExpect(status().isOk())
           .andExpect(content().string("test"));
    }
}
