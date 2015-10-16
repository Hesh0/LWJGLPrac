package me.mesh.sim.gfx;

public class RawModel
{
	
	private int vaoID;
	private int vboID;
	private int numVertices;
	
	
	public RawModel(int vaoID, int numVertices)
	{
		this.vaoID = vaoID;
		this.vboID = vboID;
		this.numVertices = numVertices;
	}
	
	public int getVaoID()
	{
		return vaoID;
	}

	public int getVboID()
	{
		return vboID;
	}
	
	public int getVertexCount()
	{
		return numVertices;
	}
	
	






	





	






	float[] vertices = new float[]
	{
		+0.0f, +0.5f,
		+0.5f, -0.5f,
		-0.5f, -0.5f
	};
}
