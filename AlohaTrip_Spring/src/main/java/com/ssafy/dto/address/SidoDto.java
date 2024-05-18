package com.ssafy.dto.address;

public class SidoDto {
	int sidoCode;
	String sidoName;
	
	public SidoDto(int sidoCode, String sidoName) {
		super();
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}
	
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SidoDto [sidoCode=");
		builder.append(sidoCode);
		builder.append(", sidoName=");
		builder.append(sidoName);
		builder.append("]");
		return builder.toString();
	}
}
