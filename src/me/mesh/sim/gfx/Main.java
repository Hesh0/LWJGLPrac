package me.mesh.sim.gfx;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*; // For NULL

import java.nio.ByteBuffer;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvideoMode;
import org.lwjgl.opengl.GL;

import me.mesh.sim.exceptions.WindowSetUpException;
import me.mesh.sim.util.ShaderUtil;
import me.mesh.sim.util.render.ModelLoader;
import me.mesh.sim.gfx.render.Renderer;

<<<<<<< HEAD:src/me/mesh/sim/gfx/Main.java
public class Main // implements Runnable
=======
public class Display // implements Runnable
>>>>>>> fd210444c2a3015d1456b1ffaad933bee87e9a1b:src/me/mesh/sim/gfx/Display.java
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = WIDTH / 4 * 3;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	// private RawModel model;
	// private ModelLoader modelLoader = new ModelLoader();

	private float[] vertices = {
			+0.0f, +0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			+0.5f, -0.5f, 0.0f
	};

	private long window;

	private ShaderUtil shaderUtil;

	public void run()
	{
		System.out.println("Starting the shit with LWJGL " + Sys.getVersion());

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
<<<<<<< HEAD:src/me/mesh/sim/gfx/Main.java
		// GLFWErrorCallback.createPrint(System.err) - only nightly build.
=======
		// GLFWErrorCallback.createPrint(System.err)- only works for nightly build.
>>>>>>> fd210444c2a3015d1456b1ffaad933bee87e9a1b:src/me/mesh/sim/gfx/Display.java
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		// Try to initialize the GLFW library, if that fails no point continuing the program.
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialise GLFW");

		// Window will stay hidden after creation.
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
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

		GLFWvideoMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// center the window.
		glfwSetWindowPos(window, (videoMode.getWidth() - WIDTH) / 2, (videoMode.getHeight() - HEIGHT) / 2);
		// make the window the current context for OpenGL rendering.
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	private void loop()
	{
		GL.createCapabilities();
		initShaders();
		// set the background color...to black.
		// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		ModelLoader loader = new ModelLoader();
		RawModel model = loader.loadtoVAO(vertices);
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
