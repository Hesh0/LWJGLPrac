package me.mesh.sim.gfx.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import me.mesh.sim.gfx.RawModel;;

public class Renderer
{
	public void prepare()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void render(RawModel model)
	{
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}
