#version 150 core

in vec3 inPosition;
in vec2 inTexCoord;

out vec2 texCoord;

void main() {
	gl_Position = vec4(inPosition, 1);
	texCoord = inTexCoord;
}
