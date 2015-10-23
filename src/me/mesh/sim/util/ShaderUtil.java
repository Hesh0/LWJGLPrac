package me.mesh.sim.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import me.mesh.sim.exceptions.ProgramLinkageException;
import me.mesh.sim.exceptions.ShaderCompileException;

public class ShaderUtil
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderUtil()
	{
		programID = glCreateProgram();
	}
		
	public void attachVertexShader(String name)
	{
		String vertexShaderSource = FileLoader.readFromFile(name);
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderSource);
		glCompileShader(vertexShaderID);
		
		// Check if compilation failed.
		if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new ShaderCompileException("Error compiling the vertex shader\n" + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
		glAttachShader(programID, vertexShaderID);
	}
	
	public void attachFragmentShader(String name)
	{
		String fragmentShaderSource = FileLoader.readFromFile(name);
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderSource);
		glCompileShader(fragmentShaderID);
		
		// Check if compilation failed.
		if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new ShaderCompileException("Error compiling the fragment shader\n" +
												glGetShaderInfoLog(fragmentShaderID, 
												glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH))
											);
		glAttachShader(programID, fragmentShaderID);
	}
	
	public void attachHardCodedVertexShader(String vertexShaderSource)
	{
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderSource);
		glCompileShader(vertexShaderID);
		
		// Check if compilation failed.
		if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new ShaderCompileException("Error compiling the vertex shader\n" + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
		glAttachShader(programID, vertexShaderID);
	}
	
	public void attachHardCodeFragmentShader(String fragmentShaderSource)
	{
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderSource);
		glCompileShader(fragmentShaderID);
		
		// Check if compilation failed.
		if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new ShaderCompileException("Error compiling the fragment shader\n" +
												glGetShaderInfoLog(fragmentShaderID, 
												glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH))
											);
		glAttachShader(programID, fragmentShaderID);
	}
	
	public void link()
	{
		glLinkProgram(programID);
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
			throw new ProgramLinkageException("Shader program could not be linked");
	}
	
	public void bind()
	{
		glUseProgram(programID);
	}
	
	public static void unbind()
	{
		glUseProgram(0);
	}
	
	public void dispose()
	{
		unbind();
		
		// Detach the shaders
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);

        // Delete the shaders
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        // Delete the program
        glDeleteProgram(programID);
	}
	
	public int getProgramID()
	{
		return programID;
	}
}
