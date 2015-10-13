package me.mesh.sim.gfx;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*; // For NULL

import java.nio.ByteBuffer;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import me.mesh.sim.exceptions.WindowSetUpException;



public class Display // implements Runnable
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = WIDTH / 4 * 3;

	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	
	private long window;
	
	public void run()
	{
		System.out.println("Starting the shit with LWJGL " + Sys.getVersion());
		
		try
		{
			init();
			loop();
			
			// loop ended so destroy window
			glfwDestroyWindow(window);
			keyCallback.release();
		}
		finally
		{
			glfwTerminate();
			errorCallback.release();
		}
		
		
	}
	
	public void init()
	{
		// Error callback- will print to standard error any GLFW errors that occur.
		// GLFWErrorCallback.createPrint(System.err) doesn't work for some reason.
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		// Try to initialize the GLFW library, if that fails no point continuing the program.
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialise GLFW");
		
		// Window will stay hidden after creation.
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); 
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "Solar System Sim", NULL, NULL);
		if (window == NULL)
			throw new WindowSetUpException("Failed to set up window");
		
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, GL_TRUE);
			}
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		// center the window.
		glfwSetWindowPos(window, (vidmode.getWidth() - WIDTH) / 2, (vidmode.getHeight() - HEIGHT) / 2);
		// make the window the current context for OpenGL rendering.
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}
	
	private void loop()
	{
		GL.createCapabilities();
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		while (glfwWindowShouldClose(window) == GL_FALSE)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
	
	public static void main(String[] args)
	{
		new Display().run();
	}

}
