package me.mesh.sim.util;

import static org.lwjgl.opengl.GL11.*;
// Shaders were introduced in OpenGL 2.0 so we're import functions to use shaders.
import static org.lwjgl.opengl.GL20.*;

import me.mesh.sim.exceptions.ProgramLinkageException;
import me.mesh.sim.exceptions.ShaderCompileException;

// Shaders are user defined programs to run at some stage of a graphics processor.
public class ShaderUtil
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderUtil()
	{
		System.out.println("In ShaderUtilr()");

		programID = glCreateProgram();
	}
		
	public void attachVertexShader(String name)
	{
		System.out.println("In attachVertexShader()");
		String vertexShaderSource = FileLoader.readFromFile(name);
		System.out.println(vertexShaderSource);
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		System.out.println(vertexShaderID);
		glShaderSource(vertexShaderID, vertexShaderSource);
		glCompileShader(vertexShaderID);
		
		// Check if compilation failed.
		if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new ShaderCompileException("Error compiling the vertex shader\n" + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
		glAttachShader(programID, vertexShaderID);
	}
	
	public void attachFragmentShader(String name)
	{
		System.out.println("In attachFragmentShader()");
		String fragmentShaderSource = FileLoader.readFromFile(name);
		System.out.println(fragmentShaderSource);
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		System.out.println(fragmentShaderID);
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
