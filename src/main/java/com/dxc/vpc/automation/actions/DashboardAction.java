package com.dxc.vpc.automation.actions;


public class DashboardAction {

	static DashboardAction DA;
	public static DashboardAction getInstance(){
		if(DA==null)
			DA= new DashboardAction();		
		return DA;
	}
}
