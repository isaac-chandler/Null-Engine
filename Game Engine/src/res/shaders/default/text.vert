#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;

out vec2 texCoords;

uniform mat4 mvp;

uniform vec2 pos;
uniform vec2 offset;
uniform vec2 size;
uniform vec2 aspectRatio;

void main() {
	gl_Position = mvp * vec4((inPosition.xy + offset) * aspectRatio * size + pos, 0, 1);
	texCoords = inTexCoords;
}
