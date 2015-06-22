package cdac.in.jam.seat;

public class LastRoundApplicant{

	String applicationId;
	String program;
	String paper;
	String quota;


	int choiceNo;
	int statusId;
	boolean autoUpgrade;
	boolean isProvisional;

	String  acceptancePath;	
	String  challanPath;
	String  declarationPath;
	String  undertakingPath;	

	LastRoundApplicant( String applicationId, String program, String paper, String quota, String choiceNo, String autoUpgrade, String isProvisional, String acceptancePath, String declarationPath, String undertakingPath, String challanPath, String statusId){

			this.applicationId = applicationId;
			this.program = program;
			this.paper = paper;	
			this.quota = quota;	
			this.choiceNo = Integer.parseInt( choiceNo ) - 1;
			this.statusId = Integer.parseInt( statusId ); 

			this.acceptancePath = acceptancePath;	
			this.challanPath =  challanPath;
			this.declarationPath = declarationPath; 
			this.undertakingPath = undertakingPath; 	

			this.autoUpgrade = false;
			this.isProvisional = false;
		
			if( autoUpgrade.equals("t") || autoUpgrade.equals("true") ){
				this.autoUpgrade = true;
			}

			if( isProvisional.equals("t") || isProvisional.equals("true") ){
				this.isProvisional = true;
			}
	}

}
