public class TerminationConditions {
	public static Boolean Check(){
		Boolean Check = false;
		String sampleType = Store.getSampleType();
		Double sampleWidth = Store.getSampleWidth();
		Double crackSize = Store.getCrackSize()[0];
		Double condition = crackSize/sampleWidth;
		/*switch (sampleType){
		case "C(T)":  if(condition < 0.24 || condition > 0.7){
			Check = true;	
		}
		break;

		case "M(T)":  if(2*condition < 0 || 2*condition > 1){
			Check = true;	
		}
		break;

		case "CC(T)":  if(condition < 0 || condition > 1){
			Check = true;	
		}
		break;

		case "SEN B3":  if(condition < 0 || condition > 1){
			Check = true;	
		}
		break;

		case "SEN B4":  if(condition < 0 || condition > 1){
			Check = true;	
		}
		break;

		case "SEN B5":  if(condition < 0 || condition > 1){
			Check = true;	
		}
		break;

		case "SENT (pinned end)":  if(condition < 0 || condition > 1){
			Check = true;	
		}
		case "SENT (clamped end)":  if(condition < 0 || condition > 0.95){
			Check = true;	
		}
		break;
		case "ESE(T)":  if(condition < 0 || condition > 1){
			Check = true;	
		}

		}*/
		
		switch(Store.getEndType()){
		case "Cycles":	if(Store.getCycleNumber()[0] > Store.getEndSpecific()){
			Check = true;
		}
		break;
		case "Crack Length": if(Store.getCrackSize()[4] > Store.getEndSpecific()){
			Check = true;
		}
		break;
		case "Relative Voltage Change": if(Math.abs(Store.getNotchVoltage() - Store.getCurrentGenVoltage()) > Store.getEndSpecific()){
			Check = true;
		}
		break;


		}
		
		
		return Check;
	}
}