package me.mesh.sim.gfx;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.system.MemoryUtil.*; // For NULL

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import me.mesh.sim.exceptions.WindowSetUpException;
import me.mesh.sim.util.ShaderUtil;
import me.mesh.sim.util.render.ModelLoader;
import me.mesh.sim.gfx.render.Renderer;

public class Main
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = WIDTH / 4 * 3;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	
	private ShaderUtil shaderUtil;
	private long window;
	
	// coordinates for a Rectangle.
	private float[] vertices = {
		-0.5f, +0.5f, 0.0f,
		-0.5f, -0.5f, 0.0f,			
		+0.5f, -0.5f, 0.0f,
		+0.5f, +0.5f, 0.0f,
	};
	
	private int[] indices = {
			0, 1, 3, 
			3, 1, 2
	};

	public void run()
	{
		try
		{
			init();
			loop();
			
			// loop ended so destroy window.
			glfwDestroyWindow(window);
			keyCallback.release();
		}
		finally
		{
			// does the cleanup, frees allocated memory etc.
			glfwTerminate();
			errorCallback.release();
		}
	}
	
	public void init()
	{
		// Error callback- will print to standard error any GLFW errors that occur.
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		// Try to initialize the GLFW library, if that fails no point continuing the program.
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialise GLFW");
		
		// set Anti-Aliasing level to 4.
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "Test", NULL, NULL);
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
		// Enable v-sync.
		glfwSwapInterval(1);
		glfwShowWindow(window);
		
	}
	
	private void loop()
	{
		createCapabilities();
		System.out.println(glGetString(GL_RENDERER));
		System.out.println(glGetString(GL_VERSION));

		initShaders();
		ModelLoader loader = new ModelLoader();
		RawModel model = loader.loadtoVAO(vertices, indices);		
		Renderer renderer = new Renderer();
		
		while (glfwWindowShouldClose(window) == GL_FALSE)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			shaderUtil.bind();
			renderer.render(model);
			ShaderUtil.unbind();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		shaderUtil.dispose();
		loader.cleanUp();
	}
	
	private void initShaders()
	{
		shaderUtil = new ShaderUtil();
		shaderUtil.attachVertexShader("me/mesh/sim/gfx/triangle.vs");
		shaderUtil.attachFragmentShader("me/mesh/sim/gfx/triangle.fs");
		shaderUtil.link();
	}
	
	public static void main(String[] args)
	{
		new Main().run();
	}
}
