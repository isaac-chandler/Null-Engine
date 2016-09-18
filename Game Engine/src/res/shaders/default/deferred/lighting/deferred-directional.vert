#version 150 core

in vec3 inPosition;
in vec2 inTexCoord;

out vec2 texCoord;
out vec3 cameraPos;

uniform mat4 viewMatrix;

void main() {
	gl_Position = vec4(inPosition, 1);
	texCoord = inTexCoord;
	cameraPos = (inverse(viewMatrix) * vec4(0, 0, 0, 1)).xyz;
}
