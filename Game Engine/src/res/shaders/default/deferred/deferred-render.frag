#version 150 core

in vec2 texCoords;
in vec3 normal;
in vec4 position;

out vec4 outColor;
out vec4 outPosition;
out vec4 outNormal;

uniform sampler2D sampler;

void main() {
	outColor = texture(sampler, texCoords);
	outPosition = position;
	outNormal = vec4(normalize(normal), 1);
}
