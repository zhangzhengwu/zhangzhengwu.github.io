package entity;

public class Econsreport {
	private String mapdate;
	private String recruiter;//直属上级code
	private String staffcode;//consultantcode
	private String position;//职位
	private String staffname;//名字
	private String convoy;//
	private String time;//出勤时间
	public Econsreport(){}

	private String day1;//某月的第一天
	private String day2;//某月的第二天
	private String day3;//某月的第三天
	private String day4;//某月的第四天
	private String day5;//某月的第五天
	private String day6;//某月的第六天
	private String day7;//某月的第七天
	private String day8;//某月的第八天
	private String day9;//某月的第九天
	private String day10;//某月的第十天
	private String day11;//某月的第十一天
	private String day12;//某月的第十二天
	private String day13;//某月的第十三天
	private String day14;//某月的第十四天
	private String day15;//某月的第十五天
	private String day16;//某月的第十六天
	private String day17;//某月的第十七天
	private String day18;//某月的第十八天
	private String day19;//某月的第十九天
	private String day20;//某月的第二十天
	private String day21;//某月的第二十一天
	private String day22;//某月的第二十二天
	private String day23;//某月的第二十三天
	private String day24;//某月的第二十四天
	private String day25;//某月的第二十五天
	private String day26;//某月的第二十六天
	private String day27;//某月的第二十七天
	private String day28;//某月的第二十八天
	private String day29="";//某月的第二十九天
	private String day30="";//某月的第三十天
	private String day31="";//某月的第三十一天
	private String total;//该支付的金额
	private String cclub;//是否是club的成员
	private String penalty;//实际支付金额
	private String noofmap;//是否是map
	private String working;//正常工作日
	private String al_leave;//请假类型al
	private String sl_leave;//请假类型sl
	private String ol_leave;//其他请假类型
	private String borrow;//借卡次数
	private String emap;//会议次数
	private String lateness;//迟到次数
	private String noshow;//未打卡次数
	private String ontimr;//正常出勤次数
	private String e_lateness;//迟到率
	private String e_noshow;//未打卡率
	private String e_noTime;//正常出勤率率
	private String updname;//添加人
	private String upddate;//添加时间
	
	public Econsreport(String mapdate, String recruiter, String staffcode,
			String position, String staffname, String convoy, String time,
			/*String day1, String day2, String day3, String day4, String day5,
			String day6, String day7, String day8, String day9, String day10,
			String day11, String day12, String day13, String day14,
			String day15, String day16, String day17, String day18,
			String day19, String day20, String day21, String day22,
			String day23, String day24, String day25, String day26,
			String day27, String day28, String day29, String day30,
			String day31,*/ String total, String cclub, String penalty,
			String noofmap, String working, String alLeave, String slLeave,
			String olLeave, String borrow, String emap, String lateness,
			String noshow, String ontimr, String eLateness, String eNoshow,
			String eNoTime, String updname, String upddate) {
		super();
		this.mapdate = mapdate;
		this.recruiter = recruiter;
		this.staffcode = staffcode;
		this.position = position;
		this.staffname = staffname;
		this.convoy = convoy;
		this.time = time;
		/**this.day1 = day1;
		this.day2 = day2;
		this.day3 = day3;
		this.day4 = day4;
		this.day5 = day5;
		this.day6 = day6;
		this.day7 = day7;
		this.day8 = day8;
		this.day9 = day9;
		this.day10 = day10;
		this.day11 = day11;
		this.day12 = day12;
		this.day13 = day13;
		this.day14 = day14;
		this.day15 = day15;
		this.day16 = day16;
		this.day17 = day17;
		this.day18 = day18;
		this.day19 = day19;
		this.day20 = day20;
		this.day21 = day21;
		this.day22 = day22;
		this.day23 = day23;
		this.day24 = day24;
		this.day25 = day25;
		this.day26 = day26;
		this.day27 = day27;
		this.day28 = day28;
		this.day29 = day29;
		this.day30 = day30;
		this.day31 = day31;**/
		this.total = total;
		this.cclub = cclub;
		this.penalty = penalty;
		this.noofmap = noofmap;
		this.working = working;
		al_leave = alLeave;
		sl_leave = slLeave;
		ol_leave = olLeave;
		this.borrow = borrow;
		this.emap = emap;
		this.lateness = lateness;
		this.noshow = noshow;
		this.ontimr = ontimr;
		e_lateness = eLateness;
		e_noshow = eNoshow;
		e_noTime = eNoTime;
		this.updname = updname;
		this.upddate = upddate;
	}	
	
 
	/**
	 * @return the mapdate
	 */
	public String getMapdate() {
		return mapdate;
	}
	/**
	 * @return the recruiter
	 */
	public String getRecruiter() {
		return recruiter;
	}
	/**
	 * @return the staffcode
	 */
	public String getStaffcode() {
		return staffcode;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @return the staffname
	 */
	public String getStaffname() {
		return staffname;
	}
	/**
	 * @return the convoy
	 */
	public String getConvoy() {
		return convoy;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
 
	/**
	 * @return the day1
	 */
	public String getDay1() {
		return day1;
	}
	/**
	 * @return the day2
	 */
	public String getDay2() {
		return day2;
	}
	/**
	 * @return the day3
	 */
	public String getDay3() {
		return day3;
	}
	/**
	 * @return the day4
	 */
	public String getDay4() {
		return day4;
	}
	/**
	 * @return the day5
	 */
	public String getDay5() {
		return day5;
	}
	/**
	 * @return the day6
	 */
	public String getDay6() {
		return day6;
	}
	/**
	 * @return the day7
	 */
	public String getDay7() {
		return day7;
	}
	/**
	 * @return the day8
	 */
	public String getDay8() {
		return day8;
	}
	/**
	 * @return the day9
	 */
	public String getDay9() {
		return day9;
	}
	/**
	 * @return the day10
	 */
	public String getDay10() {
		return day10;
	}
	/**
	 * @return the day11
	 */
	public String getDay11() {
		return day11;
	}
	/**
	 * @return the day12
	 */
	public String getDay12() {
		return day12;
	}
	/**
	 * @return the day13
	 */
	public String getDay13() {
		return day13;
	}
	/**
	 * @return the day14
	 */
	public String getDay14() {
		return day14;
	}
	/**
	 * @return the day15
	 */
	public String getDay15() {
		return day15;
	}
	/**
	 * @return the day16
	 */
	public String getDay16() {
		return day16;
	}
	/**
	 * @return the day17
	 */
	public String getDay17() {
		return day17;
	}
	/**
	 * @return the day18
	 */
	public String getDay18() {
		return day18;
	}
	/**
	 * @return the day19
	 */
	public String getDay19() {
		return day19;
	}
	/**
	 * @return the day20
	 */
	public String getDay20() {
		return day20;
	}
	/**
	 * @return the day21
	 */
	public String getDay21() {
		return day21;
	}
	/**
	 * @return the day22
	 */
	public String getDay22() {
		return day22;
	}
	/**
	 * @return the day23
	 */
	public String getDay23() {
		return day23;
	}
	/**
	 * @return the day24
	 */
	public String getDay24() {
		return day24;
	}
	/**
	 * @return the day25
	 */
	public String getDay25() {
		return day25;
	}
	/**
	 * @return the day26
	 */
	public String getDay26() {
		return day26;
	}
	/**
	 * @return the day27
	 */
	public String getDay27() {
		return day27;
	}
	/**
	 * @return the day28
	 */
	public String getDay28() {
		return day28;
	}
	/**
	 * @return the day29
	 */
	public String getDay29() {
		return day29;
	}
	/**
	 * @return the day30
	 */
	public String getDay30() {
		return day30;
	}
	/**
	 * @return the day31
	 */
	public String getDay31() {
		return day31;
	}
	/**
	 * @param day1 the day1 to set
	 */
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	/**
	 * @param day2 the day2 to set
	 */
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	/**
	 * @param day3 the day3 to set
	 */
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	/**
	 * @param day4 the day4 to set
	 */
	public void setDay4(String day4) {
		this.day4 = day4;
	}
	/**
	 * @param day5 the day5 to set
	 */
	public void setDay5(String day5) {
		this.day5 = day5;
	}
	/**
	 * @param day6 the day6 to set
	 */
	public void setDay6(String day6) {
		this.day6 = day6;
	}
	/**
	 * @param day7 the day7 to set
	 */
	public void setDay7(String day7) {
		this.day7 = day7;
	}
	/**
	 * @param day8 the day8 to set
	 */
	public void setDay8(String day8) {
		this.day8 = day8;
	}
	/**
	 * @param day9 the day9 to set
	 */
	public void setDay9(String day9) {
		this.day9 = day9;
	}
	/**
	 * @param day10 the day10 to set
	 */
	public void setDay10(String day10) {
		this.day10 = day10;
	}
	/**
	 * @param day11 the day11 to set
	 */
	public void setDay11(String day11) {
		this.day11 = day11;
	}
	/**
	 * @param day12 the day12 to set
	 */
	public void setDay12(String day12) {
		this.day12 = day12;
	}
	/**
	 * @param day13 the day13 to set
	 */
	public void setDay13(String day13) {
		this.day13 = day13;
	}
	/**
	 * @param day14 the day14 to set
	 */
	public void setDay14(String day14) {
		this.day14 = day14;
	}
	/**
	 * @param day15 the day15 to set
	 */
	public void setDay15(String day15) {
		this.day15 = day15;
	}
	/**
	 * @param day16 the day16 to set
	 */
	public void setDay16(String day16) {
		this.day16 = day16;
	}
	/**
	 * @param day17 the day17 to set
	 */
	public void setDay17(String day17) {
		this.day17 = day17;
	}
	/**
	 * @param day18 the day18 to set
	 */
	public void setDay18(String day18) {
		this.day18 = day18;
	}
	/**
	 * @param day19 the day19 to set
	 */
	public void setDay19(String day19) {
		this.day19 = day19;
	}
	/**
	 * @param day20 the day20 to set
	 */
	public void setDay20(String day20) {
		this.day20 = day20;
	}
	/**
	 * @param day21 the day21 to set
	 */
	public void setDay21(String day21) {
		this.day21 = day21;
	}
	/**
	 * @param day22 the day22 to set
	 */
	public void setDay22(String day22) {
		this.day22 = day22;
	}
	/**
	 * @param day23 the day23 to set
	 */
	public void setDay23(String day23) {
		this.day23 = day23;
	}
	/**
	 * @param day24 the day24 to set
	 */
	public void setDay24(String day24) {
		this.day24 = day24;
	}
	/**
	 * @param day25 the day25 to set
	 */
	public void setDay25(String day25) {
		this.day25 = day25;
	}
	/**
	 * @param day26 the day26 to set
	 */
	public void setDay26(String day26) {
		this.day26 = day26;
	}
	/**
	 * @param day27 the day27 to set
	 */
	public void setDay27(String day27) {
		this.day27 = day27;
	}
	/**
	 * @param day28 the day28 to set
	 */
	public void setDay28(String day28) {
		this.day28 = day28;
	}
	/**
	 * @param day29 the day29 to set
	 */
	public void setDay29(String day29) {
		this.day29 = day29;
	}
	/**
	 * @param day30 the day30 to set
	 */
	public void setDay30(String day30) {
		this.day30 = day30;
	}
	/**
	 * @param day31 the day31 to set
	 */
	public void setDay31(String day31) {
		this.day31 = day31;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @return the cclub
	 */
	public String getCclub() {
		return cclub;
	}
	/**
	 * @return the penalty
	 */
	public String getPenalty() {
		return penalty;
	}
	/**
	 * @return the noofmap
	 */
	public String getNoofmap() {
		return noofmap;
	}
	/**
	 * @return the working
	 */
	public String getWorking() {
		return working;
	}
	/**
	 * @return the al_leave
	 */
	public String getAl_leave() {
		return al_leave;
	}
	/**
	 * @return the sl_leave
	 */
	public String getSl_leave() {
		return sl_leave;
	}
	/**
	 * @return the ol_leave
	 */
	public String getOl_leave() {
		return ol_leave;
	}
	/**
	 * @return the borrow
	 */
	public String getBorrow() {
		return borrow;
	}
	/**
	 * @return the emap
	 */
	public String getEmap() {
		return emap;
	}
	/**
	 * @return the lateness
	 */
	public String getLateness() {
		return lateness;
	}
	/**
	 * @return the noshow
	 */
	public String getNoshow() {
		return noshow;
	}
	/**
	 * @return the ontimr
	 */
	public String getOntimr() {
		return ontimr;
	}
	/**
	 * @return the e_lateness
	 */
	public String getE_lateness() {
		return e_lateness;
	}
	/**
	 * @return the e_noshow
	 */
	public String getE_noshow() {
		return e_noshow;
	}
	/**
	 * @return the e_noTime
	 */
	public String getE_noTime() {
		return e_noTime;
	}
	/**
	 * @return the updname
	 */
	public String getUpdname() {
		return updname;
	}
	/**
	 * @return the upddate
	 */
	public String getUpddate() {
		return upddate;
	}
	/**
	 * @param mapdate the mapdate to set
	 */
	public void setMapdate(String mapdate) {
		this.mapdate = mapdate;
	}
	/**
	 * @param recruiter the recruiter to set
	 */
	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}
	/**
	 * @param staffcode the staffcode to set
	 */
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @param staffname the staffname to set
	 */
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	/**
	 * @param convoy the convoy to set
	 */
	public void setConvoy(String convoy) {
		this.convoy = convoy;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	 
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @param cclub the cclub to set
	 */
	public void setCclub(String cclub) {
		this.cclub = cclub;
	}
	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	/**
	 * @param noofmap the noofmap to set
	 */
	public void setNoofmap(String noofmap) {
		this.noofmap = noofmap;
	}
	/**
	 * @param working the working to set
	 */
	public void setWorking(String working) {
		this.working = working;
	}
	/**
	 * @param alLeave the al_leave to set
	 */
	public void setAl_leave(String alLeave) {
		al_leave = alLeave;
	}
	/**
	 * @param slLeave the sl_leave to set
	 */
	public void setSl_leave(String slLeave) {
		sl_leave = slLeave;
	}
	/**
	 * @param olLeave the ol_leave to set
	 */
	public void setOl_leave(String olLeave) {
		ol_leave = olLeave;
	}
	/**
	 * @param borrow the borrow to set
	 */
	public void setBorrow(String borrow) {
		this.borrow = borrow;
	}
	/**
	 * @param emap the emap to set
	 */
	public void setEmap(String emap) {
		this.emap = emap;
	}
	/**
	 * @param lateness the lateness to set
	 */
	public void setLateness(String lateness) {
		this.lateness = lateness;
	}
	/**
	 * @param noshow the noshow to set
	 */
	public void setNoshow(String noshow) {
		this.noshow = noshow;
	}
	/**
	 * @param ontimr the ontimr to set
	 */
	public void setOntimr(String ontimr) {
		this.ontimr = ontimr;
	}
	/**
	 * @param eLateness the e_lateness to set
	 */
	public void setE_lateness(String eLateness) {
		e_lateness = eLateness;
	}
	/**
	 * @param eNoshow the e_noshow to set
	 */
	public void setE_noshow(String eNoshow) {
		e_noshow = eNoshow;
	}
	/**
	 * @param eNoTime the e_noTime to set
	 */
	public void setE_noTime(String eNoTime) {
		e_noTime = eNoTime;
	}
	/**
	 * @param updname the updname to set
	 */
	public void setUpdname(String updname) {
		this.updname = updname;
	}
	/**
	 * @param upddate the upddate to set
	 */
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
}
