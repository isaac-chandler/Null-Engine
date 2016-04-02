#version 150 core

in vec2 texCoords;
in vec3 normal;
in vec3 lightDirection;

out vec4 outColor;

uniform sampler2D sampler;

void main() {
	vec3 unitLightDirection = normalize(lightDirection);
	vec3 unitNormal = normalize(normal);
	outColor = texture(sampler, texCoords) * max(dot(unitNormal, unitLightDirection), 0);
}
