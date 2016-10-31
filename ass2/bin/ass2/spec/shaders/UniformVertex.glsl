#version 130
uniform vec4 col;
void main(void) {
	gl_Position=gl_ModelViewProjectionMatrix*gl_Vertex;
    gl_FrontColor = col;
}


