#version 150 core

in vec2 texCoord;
in vec3 cameraPos;
in vec3 lightPos;

out vec4 outColor;

uniform vec3 lightColor;
uniform vec3 attenuation;

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
	float diffuse = max(0, dot(unitNormal, -direction)) / dimmer;

	vec3 toCamera = normalize(cameraPos - position);
	vec3 lightOut = normalize(reflect(direction, unitNormal));

	vec4 specularVal = texture(specular, texCoord);

	float specularFactor = pow(max(0, dot(toCamera, lightOut)), specularVal.y);
	specularFactor *= specularVal.x / dimmer;

	outColor = vec4(texture(colors, texCoord).rgb * (diffuse * lightColor) + specularFactor * lightColor, 0);
}
