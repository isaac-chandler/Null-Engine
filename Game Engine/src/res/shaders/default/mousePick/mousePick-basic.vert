#version 150 core

in vec3 inPosition;

out vec4 worldPosition;
out vec3 localPosition;

uniform mat4 modelMatrix;
uniform mat4 mvp;

void main() {
	worldPosition = modelMatrix * vec4(inPosition, 1);
	localPosition = inPosition;
	gl_Position = mvp * vec4(inPosition, 1);
}
