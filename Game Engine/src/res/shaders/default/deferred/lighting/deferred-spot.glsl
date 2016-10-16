#VS
#include <VS:deferred-point.glsl>
#VS

#FS
#version 150 core

#include <DIFF:lighting.glsl>
#include <SPEC:lighting.glsl>
#include <ATTEN:lighting.glsl>

in vec2 texCoord;
in vec3 cameraPos;
in vec3 lightPos;

out vec4 outColor;

uniform vec3 lightColor;
uniform vec3 direction;
uniform vec4 attenuation;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;
uniform sampler2D specular;

void main() {
	vec3 position = texture(positions, texCoord).xyz;
	vec3 unitNormal = texture(normals, texCoord).xyz;
	vec3 direction = position - lightPos;
	float dist = length(direction);
	float dimmer = attenuation.x * dist * dist + attenuation.y * dist + attenuation.z;
	direction = normalize(direction);
	float spotFactor = dot(direction, direction);
	if (attenuation.w > spotFactor) {
		spotFactor = (1.0 - (1 - spotFactor) / (1 - attenuation.w));
	}
	float diffuse = calcDiff(unitNormal, direction) * spotFactor / dimmer;

	vec3 toCamera = normalize(cameraPos - position);

	vec4 specularVal = texture(specular, texCoord);

	float specularFactor = calcSpec(toCamera, direction, unitNormal, specularVal.y, specularVal.x) * spotFactor / dimmer;

	outColor = vec4(texture(colors, texCoord).rgb * (diffuse * lightColor) + specularFactor * lightColor, 0) * specularVal.z;
}
#FS