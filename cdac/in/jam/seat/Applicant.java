package cdac.in.jam.seat;

import java.util.Map;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;

class StaticData{

	static Map<String, String> categorys = new HashMap<String, String>();

	static{
			categorys.put("1","GEN");
			categorys.put("2","OBC");
			categorys.put("3","SC");
			categorys.put("4","ST");
	}
}


class Rank{

	String paperCode;
	String enrollmentId;	
	String registrationNo;
	String marks;
	int rank;
	
	Rank(String registrationNo, String enrollmentId,String rank, String marks){

		this.paperCode =  registrationNo.substring(0,2);
		this.registrationNo = registrationNo;
		this.enrollmentId = enrollmentId;	
		this.marks = marks;
		this.rank = Integer.parseInt( rank );

	}

	void print(){
		System.out.print(", "+registrationNo+", "+rank+", "+marks);
	}
}

class SortByPaperRank implements Comparator<Applicant> {

	String paper = null;

    public int compare(Applicant applicant1, Applicant applicant2){

		Rank rank1 = applicant1.ranks.get(paper);
		Rank rank2 = applicant2.ranks.get(paper);

       	return rank1.rank - rank2.rank;
    }
}


class SortByAllocatedPaperRank implements Comparator<Applicant> {

    String paper = null;

    public int compare(Applicant applicant1, Applicant applicant2){

        Rank rank1 = applicant1.ranks.get( applicant1.allocatedQuota.paper );
        Rank rank2 = applicant2.ranks.get( applicant2.allocatedQuota.paper );

        return rank1.rank - rank2.rank;
    }
}

public class Applicant{

	String  applicationId;
	String  category;
	String  dob; 

	String  acceptancePath;	
	String  challanPath;
	String  declarationPath;
	String  undertakingPath;	

	boolean isPd;
	boolean autoUpgrade;
	boolean isProvisional;
	boolean isSubmitted;
	boolean lastRoundSeat;

	String originalChoices;
	String [] validChoices;

	public Map<String,Rank> ranks;

	Quota  allocatedQuota;
	int    allocatedChoice;
	int    statusId;
	boolean   isAllocated; 
	boolean   isSupernumeri;

	Applicant(String applicationId, String validChoices, String originalChoice, String dob, String category, String isPd){

		this.applicationId = applicationId;

		this.allocatedQuota = null;
		this.isAllocated = false;
		this.isSupernumeri = false;
		this.isProvisional = false;
		this.autoUpgrade = true;	
		this.isSubmitted = false;
		this.lastRoundSeat = false;

		this.acceptancePath = "";	
		this.challanPath = "";
		this.declarationPath = "";
		this.undertakingPath = "";	

		this.category = StaticData.categorys.get(  category.trim() );	

		if( isPd.trim().equalsIgnoreCase("t") || isPd.trim().equalsIgnoreCase("true") )
			this.isPd = true;
		else
			this.isPd = false;

		this.originalChoices = originalChoice.trim();

	
		String[] orgChociesList = originalChoices.trim().split("#");
		String[] chociesList = validChoices.trim().split("#");

		/*
		if( orgChociesList.length != chociesList.length ){
			System.err.println( originalChoices+" | "+validChoices);
		}else{
			System.err.println( originalChoices+" | "+validChoices);
			System.err.println( orgChociesList.length+" | "+chociesList.length);
		}
		*/

		this.validChoices = new String[ chociesList.length ];

		for(int i = 0; i < chociesList.length; i++){

			String choice =  chociesList[i].trim();
			String []token = choice.split(":");
			if( token[3].trim().equals("V") )
				this.validChoices[i] = token[2].trim();
			else
				this.validChoices[i] = "XXXX";
		}

        this.allocatedChoice = this.validChoices.length + 1;
		ranks = new TreeMap<String, Rank>();
	}

	static void printHeaderAllocation(boolean flag){
		System.out.println("Application_id, Seat_allotted, Paper, Quota, Choice_no, Rank, Marks, statusId");
	}

	static void printHeaderAllocation(){
		System.out.println("Application_id, Seat_allotted, Paper, Quota, Choice_no, Rank, Marks, Round, Is_accepted, Is_rejected, Remark, StatusId");
	}

	void printAllocation(String round){
		System.out.print(applicationId);
		System.out.print(", "+allocatedQuota.programCode+", "+allocatedQuota.paper+", "+allocatedQuota.printname+", "+( allocatedChoice + 1 )); 	
	}

	void printAllocation(boolean flag){
		System.out.print(applicationId);
		System.out.print(", "+allocatedQuota.programCode+", "+allocatedQuota.paper+", "+allocatedQuota.printname+", "+( allocatedChoice + 1 )); 	
		System.out.println(", "+ranks.get( allocatedQuota.paper ).rank+", "+ranks.get( allocatedQuota.paper ).marks+", "+statusId); 	
	}

	static void printHeaderForPaper(){
		System.out.println("ApplicationId, RegistrationId, Paper, Paper-Rank, Paper-Mark, Category, PwD-status, Original-Choice, ValidChoices, StatusId");
	}	

	void printSortedPaperWise(String paper){
		System.out.print(applicationId+", "+ranks.get( paper ).registrationNo+", "+paper+", "+ranks.get( paper ).rank+", "+ranks.get( paper ).marks+", "+category+", "+isPd+", "+originalChoices);
		System.out.print(", ");
		for(int i =0 ; i < validChoices.length; i++){
			if( i != validChoices.length -1){
				System.out.print(validChoices[i].trim()+"-");
			}else{
				System.out.print(validChoices[i].trim());	
			}
		}
		System.out.println(", "+statusId);	
	}

	static void header(){
		System.out.println("ApplicationId, Allocated-Paper, Allocated-Program, Allocated-Quota, Allocated-Program-Choice, Allocated-Paper-Rank, Allocated-Paper-Mark,  Category, PwD-status, Reg1-ID, Reg1-Rank, Reg1-Marks, Reg2-ID, Reg2-Rank2,  Reg2-Marks,  ValidChoices, Original-Choice, statusId");
	}
	

	void print(){

		System.out.print(applicationId);

		if( allocatedQuota != null ){
			System.out.print(", "+allocatedQuota.toString()+", "+( allocatedChoice + 1 )+", "+ranks.get( allocatedQuota.paper ).rank+", "+ranks.get( allocatedQuota.paper ).marks );
		}else{
			System.out.print(", --, --, --, --, --, -- ");
		}

		System.out.print(", "+category+", "+isPd);

		for ( String regID: ranks.keySet() ){
			Rank rank = ranks.get( regID );
			rank.print();
		} 	

		if( ranks.keySet().size() == 1){
			System.out.print(", --, --, --");
		}
		
		System.out.print(", ");

		for(int i =0 ; i < validChoices.length; i++){
			if( i != validChoices.length -1){
				System.out.print(validChoices[i].trim()+"-");
			}else{
				System.out.print(validChoices[i].trim());	
			}
		}
        System.out.println(", "+originalChoices+", "+statusId);

	}
}
