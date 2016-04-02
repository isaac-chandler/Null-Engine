#version 150 core

const vec3 lightPosition = vec3(0, 0, -20);

in vec3 position;
in vec2 inTexCoords;
in vec3 inNormal;

out vec2 texCoords;
out vec3 normal;
out vec3 lightDirection;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform mat4 mvp;

void main() {
	gl_Position = mvp * vec4(position, 1);
	texCoords = inTexCoords;
	normal = (modelMatrix * vec4(inNormal, 0)).xyz;
	lightDirection = lightPosition - (modelMatrix * vec4(position, 1)).xyz;
}
