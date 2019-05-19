package entity;

public class LicensePlate {
	
	private String PIBA;
	private String HKCIB;
	private String SFC;
	private String MPF;
	
	
	
	public LicensePlate() {
		super();
	}
	
	public LicensePlate(String pIBA, String hKCIB, String sFC, String mPF) {
		super();
		PIBA = pIBA;
		HKCIB = hKCIB;
		SFC = sFC;
		MPF = mPF;
	}

	public String getPIBA() {
		return PIBA;
	}
	public void setPIBA(String pIBA) {
		PIBA = pIBA;
	}
	public String getHKCIB() {
		return HKCIB;
	}
	public void setHKCIB(String hKCIB) {
		HKCIB = hKCIB;
	}
	public String getSFC() {
		return SFC;
	}
	public void setSFC(String sFC) {
		SFC = sFC;
	}
	public String getMPF() {
		return MPF;
	}
	public void setMPF(String mPF) {
		MPF = mPF;
	}
	
	

}
