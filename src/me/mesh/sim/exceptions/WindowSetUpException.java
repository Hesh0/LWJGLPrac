package me.mesh.sim.exceptions;

public class WindowSetUpException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	 public WindowSetUpException() { super(); }
	 public WindowSetUpException(String message) { super(message); }
	 public WindowSetUpException(String message, Throwable cause) { super(message, cause); }
	 public WindowSetUpException(Throwable cause) { super(cause); }
}


