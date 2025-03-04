package com.example.hotelbooking;

import com.example.hotelbooking.entity.PresidentialSuitOfADay;
import com.example.hotelbooking.repository.PresidentialSuitOfADayRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class AppConfig {
    private final PresidentialSuitOfADayRepository presidentialSuitOfADayRepository;

    public AppConfig(PresidentialSuitOfADayRepository presidentialSuitOfADayRepository) {
        this.presidentialSuitOfADayRepository = presidentialSuitOfADayRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private  void perPopulateDatabase(){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2023, Month.JUNE, 30);
        startDate.datesUntil(endDate).forEach(it-> {
            Date currentDate  =Date.from(it.atStartOfDay(defaultZoneId).toInstant());
            PresidentialSuitOfADay presidentialSuitOfADay= presidentialSuitOfADayRepository.findByAvailableDate(currentDate);
            if (presidentialSuitOfADay==null){
                PresidentialSuitOfADay tmp  =new PresidentialSuitOfADay();
                tmp.setReserved(false);
                tmp.setAvailableDate(currentDate);
                presidentialSuitOfADayRepository.save(tmp);
            }
        });

    }
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        perPopulateDatabase();
    }
}
