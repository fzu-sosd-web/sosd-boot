package org.sosd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("  ____     ___    ____    ____  \n" +
                " / ___|   / _ \\  / ___|  |  _ \\ \n" +
                " \\___ \\  | | | | \\___ \\  | | | |\n" +
                "  ___) | | |_| |  ___) | | |_| |\n" +
                " |____/   \\___/  |____/  |____/ \n" +
                "                                ");
    }

}
