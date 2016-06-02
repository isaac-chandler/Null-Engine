#version 150 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D sampler;
uniform float maxHeight;
uniform vec2 offset;

void main() {
	float hL = texture(sampler, texCoords - offset.xy).r;
	float hR = texture(sampler, texCoords + offset.xy).r;
	float hD = texture(sampler, texCoords + offset.yx).r;
	float hU = texture(sampler, texCoords - offset.yx).r;
	outColor = vec4(normalize(vec3(hL - hR, 1, hD - hU)), 1);
}
