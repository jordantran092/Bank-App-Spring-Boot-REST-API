package com.jordantran.bank_api.components.mappers;


// interface to allow for mapping entities to dto and vice versa, so that different implementations can be swapped if need be 
public interface Mapper<A, B> {
	
	public B mapTo(A entity);
	
	public A mapFrom(B dto);
}
