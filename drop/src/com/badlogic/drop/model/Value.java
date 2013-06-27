package com.badlogic.drop.model;

import java.text.DecimalFormat;

public class Value {
	public enum Type {
		WHOLE, DECIMAL, FRACTION
	}
	
	public enum Equality {
		LESS, EQUAL, GREATER, ERROR
	}
	
	public final int numerator;
	public final int denominator;
	
	public final Type type;
	
	public Value(final Type type, final int numerator, final int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		this.type = type;
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
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		switch (type) {
		case DECIMAL:
			final double dec = (double)numerator/denominator;
			final DecimalFormat format = new DecimalFormat("#.##");
	        s.append(Double.valueOf(format.format(dec)));
			break;
		case FRACTION:
			s.append(numerator + "/" + denominator);
			break;
		case WHOLE:
		default:
			s.append(numerator);
			break;
		}
		return s.toString();
	}
}
