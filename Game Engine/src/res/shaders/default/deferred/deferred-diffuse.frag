#version 150 core

in vec2 texCoord;

out vec4 outColor;

uniform vec4 lightColor;
uniform vec3 lightPos;
uniform vec3 cameraPos;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;
uniform sampler2D specular;

void main() {
	vec3 position = texture(positions, texCoord).xyz;
	vec3 unitDirection = normalize(lightPos - position);
	vec3 unitNormal = texture(normals, texCoord).xyz;
	float diffuse = max(0, dot(unitNormal, unitDirection));

	vec3 toCamera = normalize(cameraPos - position);
	vec3 lightOut = reflect(-unitDirection, unitNormal);

	vec4 specularVal = texture(specular, texCoord);

	float specularFactor = pow(max(0, dot(toCamera, lightOut)), specularVal.y) * specularVal.x;
	specularFactor *= step(1e-37, diffuse);

	outColor = texture(colors, texCoord) * (diffuse * lightColor) + (specularFactor * lightColor);
}
