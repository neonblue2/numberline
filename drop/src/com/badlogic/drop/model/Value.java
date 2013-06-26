package com.badlogic.drop.model;

public class Value {
	public enum Equality {
		LESS, EQUAL, GREATER, ERROR
	}
	
	public final int numerator;
	public final int denominator;
	
	public Value(final int numerator, final int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public Equality compare(final Value value) {
		final int thisModifiedNum = numerator * value.denominator;
		final int valueModifiedNum = value.numerator * denominator;
		
		if (thisModifiedNum < valueModifiedNum) {
			return Equality.LESS;
		} else if (thisModifiedNum == valueModifiedNum) {
			return Equality.EQUAL;
		} else if (thisModifiedNum > valueModifiedNum) {
			return Equality.GREATER;
		}
		
		return Equality.ERROR;
	}
}
