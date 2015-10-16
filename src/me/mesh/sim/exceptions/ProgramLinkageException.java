package me.mesh.sim.exceptions;

public class ProgramLinkageException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ProgramLinkageException() { super(); }
	public ProgramLinkageException(String message) { super(message); }
	public ProgramLinkageException(String message, Throwable cause) { super(message, cause); }
	public ProgramLinkageException(Throwable cause) { super(cause); }
}
