#version 150 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D sampler;
uniform vec4 color;

void main() {
	outColor = texture(sampler, texCoords) * color;
}
