package ass2.spec;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class Texture {
 
	private boolean enableMipMap = true;
	private int nTex = 1;
	private int[] textures = new int[nTex];
	int status = 1;
	
	//Loading data from a file
	//make sure width and height of file are a power of 2
	public Texture(GL2 gl, String fileName,String extension,boolean mipmaps){
		enableMipMap = mipmaps;
		TextureData data = null;
		GLProfile glp = GLProfile.getDefault();
		try{
			System.out.println("Abrindo: "+fileName);
			//read file into buffered image
			BufferedImage image = ImageIO.read(this.getClass().getResource(fileName));
			ImageUtil.flipImageVertically(image);
			data = AWTTextureIO.newTextureData(glp, image, false);
			
		} catch(IOException exc) {
			System.err.println(fileName);
			exc.printStackTrace();
			System.exit(status);
		}
		//get texture id - release when finished
		gl.glGenTextures(nTex, textures, 0);
		
		//Use this texture - set current texture
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
		
		gl.glTexImage2D(GL.GL_TEXTURE_2D,
				0, // level of detail: 0 = base
 				data.getInternalFormat(),
 				data.getWidth(),
 				data.getHeight(),
 				0, // border (must be 0)
 				data.getPixelFormat(),
 				data.getPixelType(),
 				data.getBuffer());
	
        setFilters(gl);

	}

	private void setFilters(GL2 gl) {
		// TODO Auto-generated method stub
		if(enableMipMap){
			//bilinear filtering with no mipmaps
			gl.glTexParameteri(
					GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_LINEAR);
			//trilinear filtering
			gl.glTexParameteri(
					GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR_MIPMAP_LINEAR);
    		
			//get opengl to auto-generate mip-maps
			gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
		} else {
			//bilinear filtering
			gl.glTexParameteri(
					GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_LINEAR);
			//bilinear filtering with no mipmaps
    		gl.glTexParameteri(
    				GL.GL_TEXTURE_2D,
    				GL.GL_TEXTURE_MIN_FILTER,
    				GL.GL_LINEAR);
		}
	}
	
	public Texture(GL2 gl, ByteBuffer buffer, int size,boolean mipmaps){
		enableMipMap = mipmaps;
		gl.glGenTextures(nTex, textures,0);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
		
		gl.glTexImage2D(
				GL2.GL_TEXTURE_2D,
				0, //level of detail: 0 = base
				GL2.GL_RGBA,//internal format
				size, //width
				size, //height
				0, // border (must be 0)
				GL2.GL_RGBA, //pixel format
				GL2.GL_UNSIGNED_BYTE, // pixel type
				buffer);
		
		setFilters(gl);
	}
	
	public int getTextures(){
		return textures[0];
	}
	
	public void release(GL2 gl){
		if(textures[0] > 0){
			gl.glDeleteTextures(nTex, textures, 0);
		}
	}
}
