package graphtheory;

public class pangram {
	public static void main(String[] args) {
		String[] strings = {
			"we promptly judged antique ivory buckles for the next prize"
		};
		System.out.println(isPangram(strings));
	}
	
	 /*
     * Complete the function below.
     */
	static String isPangram(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<strings.length; i++){
        
            boolean[] letters = new boolean[26];
            boolean isPangram = true;
            for(int j=0; j<strings[i].length(); j++){
            	System.out.println(strings[i].charAt(j));
            	if(Character.isLetter(strings[i].charAt(j))) {
                    letters[(int)strings[i].charAt(j)-97] = true;
            	}
            }

            for(int j=0; j<letters.length; j++){
                if(letters[j]==false){
                    isPangram = false;
                }
            }
            if(isPangram){
                sb.append("1");
            }else{
                sb.append("0");
            }
            
        }
        return sb.toString();
    }
	
}
