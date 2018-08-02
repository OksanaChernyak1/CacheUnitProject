package com.hit.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CLI extends java.util.Observable implements java.lang.Runnable {

	private OutputStream outputStream;
	private InputStream inputStream;
	private Scanner scanner;
	private PrintStream printStream;

	public CLI(InputStream in, OutputStream out) {
		this.inputStream = in;
		this.outputStream = out;

		this.scanner = new Scanner(inputStream);
		this.printStream = new PrintStream(outputStream);
	}

	public void write(String string) {
		try {
			this.printStream.println(string.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String command;
		while(true) {
			printStream.println ("Please enter your command");
			command = scanner.nextLine();
			
			if(command.equalsIgnoreCase("Start"))
            {
				
                setChanged ();
                notifyObservers ("Start");
            }
            else if(command.equalsIgnoreCase("Shutdown"))
            {
            	
                setChanged ();
                notifyObservers ("Shutdown");
            }
            else
            {
                printStream.println ("Not a valid command");
                command = "";
            }
		}

	}

}
