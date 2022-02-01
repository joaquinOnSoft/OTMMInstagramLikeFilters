package com.opentext.otmm.sc.imgfilters;

import java.util.LinkedList;
import java.util.List;

public class IMEffects {
	List<String> decorators;
	List<String> filters;
	
	public IMEffects() {
		decorators = new LinkedList<>();
		filters = new LinkedList<>();
	}
	
	public List<String> getDecorators() {
		return decorators;
	}
	
	public void setDecorators(List<String> decorators) {
		this.decorators = decorators;
	}
	
	public void addDecorator(String decoratorName) {
		decorators.add(decoratorName);
	}
	
	public void clearDecorators() {
		decorators.clear();
	}
	
	public boolean hasDecorator(String decoratorName) {
		return decorators.contains(decoratorName);
	}
	
	public List<String> getFilters() {
		return filters;
	}
	
	public void setFilters(List<String> filters) {
		this.filters = filters;
	}
	
	public void addFilter(String filterName) {
		filters.add(filterName);
	}
	
	public void clearFilters() {
		decorators.clear();
	}

	public boolean hasFilter(String filterName) {
		return filters.contains(filterName);
	}
		
	public void clearAll() {
		clearDecorators();
		clearFilters();
	}
}
