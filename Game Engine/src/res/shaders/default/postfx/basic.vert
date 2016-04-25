#version 150

in vec3 inPosition;
in vec2 inTexCoords;

out vec2 texCoord;

void main() {
	gl_Position = vec4(inPosition, 1);
	texCoord = inTexCoords;
}
