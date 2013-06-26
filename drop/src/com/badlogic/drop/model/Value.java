package com.badlogic.drop.model;

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
	
	public Value(final int numerator, final int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		type = Type.WHOLE;
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
			s.append((double)numerator/denominator);
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
