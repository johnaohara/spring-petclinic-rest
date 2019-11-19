package org.springframework.samples.petclinic.util;

import org.springframework.web.util.UriComponentsBuilder;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

public class UriComponentsBuilderProducer {

    private static UriComponentsBuilder componentsBuilder = UriComponentsBuilder.newInstance();

    @Produces
    public UriComponentsBuilder componentsBuilder() {
        return componentsBuilder;
    }
}
