package com.ssafy.dto.address;

public class GugunDto {
	int gugunCode;
	String gugunName;
	
	public GugunDto(int gugunCode, String gugunName) {
		super();
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
	}
	
	public int getGugunCode() {
		return gugunCode;
	}
	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("gugunDto [gugunCode=");
		builder.append(gugunCode);
		builder.append(", gugunName=");
		builder.append(gugunName);
		builder.append("]");
		return builder.toString();
	}
}
