#version 150 core

in vec2 texCoord;

out vec4 outColor;

uniform vec4 lightColor;
uniform vec3 lightPos;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;

void main() {
	vec3 unitDirection = normalize(lightPos - texture(positions, texCoord).xyz);
	vec3 unitNormal = texture(normals, texCoord).xyz;
	vec4 diffuse = lightColor * max(0, dot(unitNormal, unitDirection));
	outColor = texture(colors, texCoord) * diffuse;
}
