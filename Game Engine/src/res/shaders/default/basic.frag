#version 150 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D sampler;

void main() {
	outColor = texture(sampler, texCoords);
}
