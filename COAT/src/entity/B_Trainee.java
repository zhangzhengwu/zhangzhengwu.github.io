package entity;

public class B_Trainee {

	private String trainee;//分税人
	private String address;//营业处
	private String handlers;//经手人
	private String handlers_position;//职位
	private double FYB;//FYB
	private double total;//国华佣金
	private String type;
	private String addr_handlers;
	
	
	
	
	
	
	
	 


	public B_Trainee(String trainee, String address, String handlers,
			String handlersPosition, double fYB, double total,String type,
			String addrHandlers) {
		super();
		this.trainee = trainee;
		this.address = address;
		this.handlers = handlers;
		handlers_position = handlersPosition;
		FYB = fYB;
		this.total = total;
		this.type=type;
		addr_handlers = addrHandlers;
	}
















	public B_Trainee() {
		// TODO Auto-generated constructor stub
	}








 







	public String getAddress() {
		return address;
	}









	public String getHandlers() {
		return handlers;
	}









	public String getHandlers_position() {
		return handlers_position;
	}









	public double getFYB() {
		return FYB;
	}








 






	public String getAddr_handlers() {
		return addr_handlers;
	}







 








	public void setAddress(String address) {
		this.address = address;
	}









	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}









	public void setHandlers_position(String handlersPosition) {
		handlers_position = handlersPosition;
	}









	public void setFYB(double fYB) {
		FYB = fYB;
	}








 





	public void setAddr_handlers(String addrHandlers) {
		addr_handlers = addrHandlers;
	}
















	public String getTrainee() {
		return trainee;
	}
















	public double getTotal() {
		return total;
	}
















	public void setTrainee(String trainee) {
		this.trainee = trainee;
	}
















	public void setTotal(double total) {
		this.total = total;
	}
















	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
















	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
	
}
