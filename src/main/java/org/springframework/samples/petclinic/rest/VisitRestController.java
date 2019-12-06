/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Vitaliy Fedoriv
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class VisitRestController {

	@Autowired
	private ClinicService clinicService;

	@Autowired
    UriComponentsBuilder ucBuilder;

    @PreAuthorize( "hasRole('OWNER_ADMIN')" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Visit>> getAllVisits(){
		Collection<Visit> visits = new ArrayList<Visit>();
		visits.addAll(this.clinicService.findAllVisits());
		if (visits.isEmpty()){
			return new ResponseEntity<Collection<Visit>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Visit>>(visits, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole('OWNER_ADMIN')" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Visit> getVisit(@PathVariable("visitId") int visitId){
		Visit visit = this.clinicService.findVisitById(visitId);
		if(visit == null){
			return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Visit>(visit, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole('OWNER_ADMIN')" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Visit> addVisit(@RequestBody @Valid Visit visit){
		HttpHeaders headers = new HttpHeaders();
		this.clinicService.saveVisit(visit);
		URI location = ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri();
		headers.setLocation(location);
		return new ResponseEntity<Visit>(visit, headers, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole('OWNER_ADMIN')" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Visit> updateVisit(@PathVariable("visitId") int visitId, @RequestBody @Valid Visit visit){
		Visit currentVisit = this.clinicService.findVisitById(visitId);
		if(currentVisit == null){
			return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
		}
		currentVisit.setDate(visit.getDate());
		currentVisit.setDescription(visit.getDescription());
		currentVisit.setPet(visit.getPet());
		this.clinicService.saveVisit(currentVisit);
		return new ResponseEntity<Visit>(currentVisit, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole('OWNER_ADMIN')" )
	@RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") int visitId){
		Visit visit = this.clinicService.findVisitById(visitId);
		if(visit == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteVisit(visit);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
