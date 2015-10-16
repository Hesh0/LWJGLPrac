package me.mesh.sim.exceptions;

public class ShaderCompileException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ShaderCompileException() { super(); }
	public ShaderCompileException(String message) { super(message); }
	public ShaderCompileException(String message, Throwable cause) { super(message, cause); }
	public ShaderCompileException(Throwable cause) { super(cause); }
}
