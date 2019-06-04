package rospopa.strpz2.common.configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MappingConfiguration {

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

    @Bean
    MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }
}
