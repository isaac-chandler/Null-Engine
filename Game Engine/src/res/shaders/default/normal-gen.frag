#version 150 core

in vec2 texCoords;

out vec4 outColor;

uniform sampler2D sampler;
uniform float maxHeight;
//uniform vec2 offset;
const ivec3 offset = ivec3(-1, 0, 1);

void main() {
	float hL = textureOffset(sampler, texCoords, offset.xy).r;
	float hR = textureOffset(sampler, texCoords, offset.zy).r;
	float hD = textureOffset(sampler, texCoords, offset.yx).r;
	float hU = textureOffset(sampler, texCoords, offset.yz).r;
	vec3 va = normalize(vec3(2, hR - hL, 0));
	vec3 vb = normalize(vec3(0, hU - hD, -2));
//	outColor = vec4(normalize(vec3(hL - hR, 1, hD - hU)), 1);
	outColor = vec4(cross(va, vb), 1);
}
