#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;
in vec3 inNormal;

out vec2 texCoords;
out vec3 normal;
out vec4 position;

uniform mat4 modelMatrix;
uniform vec3 cameraPos;
uniform mat4 mvp;

void main() {
	position = modelMatrix * vec4(inPosition, 1);
	gl_Position = mvp * vec4(inPosition, 1);
	texCoords = inTexCoords;
	normal = (modelMatrix * vec4(inNormal, 0)).xyz;
}
