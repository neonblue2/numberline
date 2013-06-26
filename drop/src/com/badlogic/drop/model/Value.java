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
		
		if (valueModifiedNum < thisModifiedNum) {
			return Equality.LESS;
		} else if (valueModifiedNum == thisModifiedNum) {
			return Equality.EQUAL;
		} else if (valueModifiedNum > thisModifiedNum) {
			return Equality.GREATER;
		}
		
		return Equality.ERROR;
	}
}
