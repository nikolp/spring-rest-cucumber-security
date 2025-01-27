package com.example.demo.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
                classes = {PermitAllSecurityConfig.class})

public class SpringIT {
//    @Test
//    @SneakyThrows
//    void starts() {
//        // Just a simple test to ensure the environment starts up
//        // Sleep so that in this short time you can hit it from your own browser
//        Thread.sleep(1 * 1000);
//    }
}
