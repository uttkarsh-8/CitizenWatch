package com.policeapi.policerestapis.config;


import com.policeapi.policerestapis.dto.ReportDto;
import com.policeapi.policerestapis.model.Report;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Explicit mapping for Report entity to ReportDto
        modelMapper.typeMap(Report.class, ReportDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getId(), ReportDto::setUserId);
        });

        return modelMapper;
    }
}


