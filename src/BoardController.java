import java.util.ArrayList;

public class BoardController {
	
	public static void main(String[] args) {
		private Collection<Role> roles;
		
		/* Bad idea, but leave for now. */
		if(args.length != 2) {
	         System.err.println("USAGE: java Routes <vertex_file> <edge_file>");
	         System.exit(1);
	    }
		// add second text file to this method when completed.
		parseData(args[0]);
		
	}
	
	private Collection<Role> parseData(String f1) {
		Scanner fileS = null;
		try {
	         fileS = new Scanner(new File(f1));
	    } 
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f1);
	         System.exit(2);
	    }
		while (fileS.hasNextLine()) {
			String line = fileS.nextLine();
			Scanner lineData = new Scanner(line);
		}
	}
	
}
