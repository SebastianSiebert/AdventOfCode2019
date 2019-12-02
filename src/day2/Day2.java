import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day2 {

	private static final int OPCODE_ADD = 1;
	private static final int OPCODE_MUL = 2;
	private static final int OPCODE_END = 99;
	private static final int INCREMENT = 4;
    private static ArrayList<Integer> intcodes = new ArrayList<Integer>();
	

    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            return;

        readFile(args[0]);
        puzzle1();
        puzzle2();
    }

    private static void readFile(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
			String[] values = st.split(",");
			for (String value : values)
				intcodes.add(Integer.parseInt(value));
        }
    }

    private static void puzzle1() {
        int pos = 0;
		
		while (pos < intcodes.size()) {
			int intcode = intcodes.get(pos);
			if (intcode == OPCODE_END) break;
			
			int pos1 = intcodes.get(pos+1);
			int pos2 = intcodes.get(pos+2);
			int pos3 = intcodes.get(pos+3);
			
			if (intcode == OPCODE_ADD) 
				intcodes.set(pos3, intcodes.get(pos1) + intcodes.get(pos2));
			if (intcode == OPCODE_MUL) 
				intcodes.set(pos3, intcodes.get(pos1) * intcodes.get(pos2));
			
			pos += INCREMENT;
		}
		
		System.out.println("Value at pos 0: " + intcodes.get(0));
    }

    private static void puzzle2() {
        
    }
}

