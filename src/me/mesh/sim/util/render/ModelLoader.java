package me.mesh.sim.util.render;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL41;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.ArrayList;


import me.mesh.sim.gfx.RawModel;

public class ModelLoader
{
	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();

	public RawModel loadtoVAO(float[] positions)
	{
		int vaoID = createVAO();
		storeDataInAttributesList(0, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length);
	}
	
	private void unbindVAO()
	{
		glBindVertexArray(0);	
	}

	private void storeDataInAttributesList(int attribNum, float[] data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID );
		FloatBuffer buffer = dataToFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attribNum, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private int createVAO()
	{
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private FloatBuffer dataToFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void cleanUp()
	{
		for (int vao : vaos)
		{
			glDeleteVertexArrays(vao);
		}
		
		for (int vbo : vbos)
		{
			glDeleteBuffers(vbo);
		}
	}
}
