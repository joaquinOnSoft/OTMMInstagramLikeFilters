package com.opentext.otmm.sc.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.opentext.mediamanager.restapi.common.BaseResource;
import com.opentext.otmm.sc.imgfilters.IMDecorator;
import com.opentext.otmm.sc.imgfilters.IMEffects;
import com.opentext.otmm.sc.imgfilters.IMFilter;

import io.swagger.v3.oas.annotations.Operation;

@Path("{version: v(5|6)}/filters")
public class Filters extends BaseResource {

	@GET
    @Operation(
    		summary = "Retrieve a list of filters and decorators names available", 
    		description = "Retrieve a list of filters names and decorator available (using Image Magick)\r\n"
    		)
    @Produces({MediaType.APPLICATION_JSON})
	@Path("/getFilters")
	public Response getFilters() {
		IMEffects  effects = new IMEffects();
		
		for (IMDecorator decorator : IMDecorator.values()) {
			effects.addDecorator(decorator.getName());
		}

		for (IMFilter filter : IMFilter.values()) {
			effects.addFilter(filter.getName());
		}		
		
		return Response.ok(effects).type(checkMediaType()).build();
	}
}
