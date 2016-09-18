#version 150 core

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
	vec3 lightDir = position - lightPos;
	float dist = length(lightDir);
	float dimmer = attenuation.x * dist * dist + attenuation.y * dist + attenuation.z;
	lightDir = normalize(lightDir);
	float spotFactor = dot(lightDir, direction);
	spotFactor = step(attenuation.w, spotFactor) * (1.0 - (1 - spotFactor) / (1 - attenuation.w));
	float diffuse = max(0, dot(unitNormal, -lightDir)) * spotFactor / dimmer;

	vec3 toCamera = normalize(cameraPos - position);
	vec3 lightOut = normalize(reflect(lightDir, unitNormal));

	vec4 specularVal = texture(specular, texCoord);

	float specularFactor = pow(max(0, dot(toCamera, lightOut)), specularVal.y);
	specularFactor *= specularVal.x * spotFactor / dimmer;

	outColor = vec4(texture(colors, texCoord).rgb * (diffuse * lightColor) + specularFactor * lightColor, 0) * specularVal.z;
}
