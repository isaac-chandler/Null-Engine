#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;

out vec2 texCoords;

uniform mat4 projectionMatrix;

void main() {
	gl_Position = projectionMatrix * vec4(inPosition, 1);
	texCoords = inTexCoords;
}
