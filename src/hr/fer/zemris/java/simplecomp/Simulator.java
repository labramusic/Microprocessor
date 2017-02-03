package hr.fer.zemris.java.simplecomp;

import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * A simulator program of the {@link ComputerImpl} class.
 * Expects the path to the file with the assembler code of the program which needs to be compiled and run.
 * If no arguments are given, the user is asked to type the path to the file.
 * @author labramusic
 *
 */
public class Simulator {

	/**
	 * The main method.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		String fileName = "";
		if (args.length > 1) {
			System.err.println("Illegal number of arguments.");
			System.exit(1);
		} else if (args.length == 1) {
			fileName = args[0];
		} else {
			System.out.println("Enter the path to the file with assembler code:");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			fileName = sc.nextLine();
			//sc.close();
		}
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions"
				);
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(
					fileName, 
					comp,
					creator
					);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		try {
			exec.go(comp);
		} catch (RuntimeException e) {
			System.out.println("Exception "+e.getMessage()+" ocurred while running program from file "+fileName+".");
			e.printStackTrace();
		}
	}

}
